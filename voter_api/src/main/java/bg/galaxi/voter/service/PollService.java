package bg.galaxi.voter.service;

import bg.galaxi.voter.exception.BadRequestException;
import bg.galaxi.voter.exception.ResourceNotFoundException;
import bg.galaxi.voter.model.entity.*;
import bg.galaxi.voter.model.dto.ChoiceVoteCountDto;
import bg.galaxi.voter.model.request.TagRequestModel;
import bg.galaxi.voter.model.response.ApiResponseModel;
import bg.galaxi.voter.model.response.PagedResponseModel;
import bg.galaxi.voter.model.request.PollRequestModel;
import bg.galaxi.voter.model.response.PollResponseModel;
import bg.galaxi.voter.model.request.VoteRequestModel;
import bg.galaxi.voter.repository.PollRepository;
import bg.galaxi.voter.security.user.UserPrincipal;
import bg.galaxi.voter.service.api.IPollService;
import bg.galaxi.voter.service.api.IUserService;
import bg.galaxi.voter.service.api.IVoteService;
import bg.galaxi.voter.util.AppConstants;
import bg.galaxi.voter.util.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static bg.galaxi.voter.util.AppConstants.*;

@Service
@Transactional
public class PollService implements IPollService {

    private final PollRepository pollRepository;

    private final IUserService userService;

    private final IVoteService voteService;

    private static final Logger logger = LoggerFactory.getLogger(PollService.class);

    @Autowired
    public PollService(PollRepository pollRepository, IVoteService voteService, IUserService userService) {
        this.pollRepository = pollRepository;
        this.voteService = voteService;
        this.userService = userService;
    }

    public PagedResponseModel<PollResponseModel> getAllPolls(UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);
        Page<Poll> polls = this.pollRepository.findAll(pageable);

        if (polls.getNumberOfElements() == 0) {
            return new PagedResponseModel<>(Collections.emptyList(), polls.getNumber(),
                    polls.getSize(), polls.getTotalElements(), polls.getTotalPages(), polls.isLast());
        }

        List<Long> pollIds = polls.map(Poll::getId).getContent();
        Map<Long, Long> choiceVoteCountMap = getChoiceVoteCountMap(pollIds);
        Map<Long, Long> pollUserVoteMap = getPollUserVoteMap(currentUser, pollIds);
        Map<Long, User> creatorMap = getPollCreatorMap(polls.getContent());

        List<PollResponseModel> pollResponseModels = polls.map(poll -> ModelMapper.mapPollToPollResponse(poll,
                choiceVoteCountMap,
                creatorMap.get(poll.getCreatedBy()),
                pollUserVoteMap == null ? null : pollUserVoteMap.getOrDefault(poll.getId(), null))).getContent();

        return new PagedResponseModel<>(pollResponseModels, polls.getNumber(),
                polls.getSize(), polls.getTotalElements(), polls.getTotalPages(), polls.isLast());
    }

    public PagedResponseModel<PollResponseModel> getPollsCreatedBy(String username, UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        User user = this.userService.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(USER, USERNAME, username));

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);
        Page<Poll> polls = pollRepository.findByCreatedBy(user.getId(), pageable);

        if (polls.getNumberOfElements() == 0) {
            return new PagedResponseModel<>(Collections.emptyList(), polls.getNumber(),
                    polls.getSize(), polls.getTotalElements(), polls.getTotalPages(), polls.isLast());
        }

        List<Long> pollIds = polls.map(Poll::getId).getContent();
        Map<Long, Long> choiceVoteCountMap = getChoiceVoteCountMap(pollIds);
        Map<Long, Long> pollUserVoteMap = getPollUserVoteMap(currentUser, pollIds);

        List<PollResponseModel> pollResponsModels = polls.map(poll -> ModelMapper.mapPollToPollResponse(poll,
                choiceVoteCountMap,
                user,
                pollUserVoteMap == null ? null : pollUserVoteMap.getOrDefault(poll.getId(), null))).getContent();

        return new PagedResponseModel<>(pollResponsModels, polls.getNumber(),
                polls.getSize(), polls.getTotalElements(), polls.getTotalPages(), polls.isLast());
    }

    public PagedResponseModel<PollResponseModel> getPollsVotedBy(String username, UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        User user = this.userService.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(USER, USERNAME, username));

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);
        Page<Long> userVotedPollIds = this.voteService.findVotedPollIdsByUserId(user.getId(), pageable);

        if (userVotedPollIds.getNumberOfElements() == 0) {
            return new PagedResponseModel<>(Collections.emptyList(), userVotedPollIds.getNumber(),
                    userVotedPollIds.getSize(), userVotedPollIds.getTotalElements(),
                    userVotedPollIds.getTotalPages(), userVotedPollIds.isLast());
        }

        List<Long> pollIds = userVotedPollIds.getContent();

        Sort sort = new Sort(Sort.Direction.DESC, CREATED_AT);
        List<Poll> polls = pollRepository.findByIdIn(pollIds, sort);

        Map<Long, Long> choiceVoteCountMap = getChoiceVoteCountMap(pollIds);
        Map<Long, Long> pollUserVoteMap = getPollUserVoteMap(currentUser, pollIds);
        Map<Long, User> creatorMap = getPollCreatorMap(polls);

        List<PollResponseModel> pollResponsModels = polls.stream().map(poll -> ModelMapper.mapPollToPollResponse(poll,
                choiceVoteCountMap,
                creatorMap.get(poll.getCreatedBy()),
                pollUserVoteMap == null ? null : pollUserVoteMap.getOrDefault(poll.getId(), null))).collect(Collectors.toList());

        return new PagedResponseModel<>(pollResponsModels, userVotedPollIds.getNumber(), userVotedPollIds.getSize(), userVotedPollIds.getTotalElements(), userVotedPollIds.getTotalPages(), userVotedPollIds.isLast());
    }

    public Poll createPoll(PollRequestModel pollRequestModel) {
        Poll poll = new Poll();
        poll.setQuestion(pollRequestModel.getQuestion());

        pollRequestModel.getChoices().forEach(choiceRequest -> {
            poll.addChoice(new Choice(choiceRequest.getText()));
        });

        pollRequestModel.getTags().stream()
                .filter(tag -> !tag.getText().isEmpty())
                .forEach(tag -> poll.addTag(new Tag(tag.getText())));

        Instant now = Instant.now();
        Instant expirationDateTime = now.plus(Duration.ofDays(pollRequestModel.getPollLength().getDays()))
                .plus(Duration.ofHours(pollRequestModel.getPollLength().getHours()));

        poll.setExpirationDateTime(expirationDateTime);

        return pollRepository.save(poll);
    }

    public PollResponseModel getPollById(Long pollId, UserPrincipal currentUser) {
        Poll poll = pollRepository.findById(pollId).orElseThrow(
                () -> new ResourceNotFoundException(POLL, ID, pollId));

        List<ChoiceVoteCountDto> votes = this.voteService.countByPollIdGroupByChoiceId(pollId);

        Map<Long, Long> choiceVotesMap = votes.stream()
                .collect(Collectors.toMap(ChoiceVoteCountDto::getChoiceId, ChoiceVoteCountDto::getVoteCount));

        User creator = this.userService.findById(poll.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException(USER, ID, poll.getCreatedBy()));

        Vote userVote = null;
        if (currentUser != null) {
            userVote = this.voteService.findByUserIdAndPollId(currentUser.getId(), pollId);
        }

        return ModelMapper.mapPollToPollResponse(poll, choiceVotesMap,
                creator, userVote != null ? userVote.getChoice().getId() : null);
    }

    public PollResponseModel castVoteAndGetUpdatedPoll(Long pollId, VoteRequestModel voteRequestModel, UserPrincipal currentUser) {
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new ResourceNotFoundException(POLL, ID, pollId));

        if (poll.getExpirationDateTime().isBefore(Instant.now())) {
            throw new BadRequestException(SORRY_THIS_POLL_HAS_ALREADY_EXPIRED_MESSAGE);
        }

        User user = this.userService.getOne(currentUser.getId());

        Choice selectedChoice = poll.getChoices().stream()
                .filter(choice -> choice.getId().equals(voteRequestModel.getChoiceId()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(CHOICE, ID, voteRequestModel.getChoiceId()));

        Vote vote = new Vote();
        vote.setPoll(poll);
        vote.setUser(user);
        vote.setChoice(selectedChoice);

        try {
            vote = this.voteService.persist(vote);
        } catch (DataIntegrityViolationException ex) {
            logger.info(USER_HAS_ALREADY_VOTED_IN_POLL_MESSAGE, currentUser.getId(), pollId);
            throw new BadRequestException(SORRY_YOU_HAVE_ALREADY_CAST_YOUR_VOTE_IN_THIS_POLL_MESSAGE);
        }

        List<ChoiceVoteCountDto> votes = this.voteService.countByPollIdGroupByChoiceId(pollId);

        Map<Long, Long> choiceVotesMap = votes.stream()
                .collect(Collectors.toMap(ChoiceVoteCountDto::getChoiceId, ChoiceVoteCountDto::getVoteCount));

        User creator = this.userService.findById(poll.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException(USER, ID, poll.getCreatedBy()));

        return ModelMapper.mapPollToPollResponse(poll, choiceVotesMap, creator, vote.getChoice().getId());
    }

    public ResponseEntity<?> createPollExpanded(PollRequestModel pollRequestModel) {
        Poll poll = this.createPoll(pollRequestModel);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{pollId}")
                .buildAndExpand(poll.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponseModel(true, "Poll Created Successfully"));
    }

    @Override
    public long countByCreatedBy(Long id) {
        return this.pollRepository.countByCreatedBy(id);
    }

    @Override
    public long countByUserId(Long id) {
        return this.voteService.countByUserId(id);
    }

    @Override
    public Page<Poll> findAll(Pageable pageable) {
        return this.pollRepository.findAll(pageable);
    }

    private void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BadRequestException(PAGE_NUMBER_CANNOT_BE_LESS_THAN_ZERO_MESSAGE);
        }

        if (size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException(PAGE_SIZE_MUST_NOT_BE_GREATER_THAN_MESSAGE + AppConstants.MAX_PAGE_SIZE);
        }
    }

    private Map<Long, Long> getChoiceVoteCountMap(List<Long> pollIds) {
        List<ChoiceVoteCountDto> votes = this.voteService.countByPollIdInGroupByChoiceId(pollIds);

        Map<Long, Long> choiceVotesMap = votes.stream()
                .collect(Collectors.toMap(ChoiceVoteCountDto::getChoiceId, ChoiceVoteCountDto::getVoteCount));

        return choiceVotesMap;
    }

    private Map<Long, Long> getPollUserVoteMap(UserPrincipal currentUser, List<Long> pollIds) {
        Map<Long, Long> pollUserVoteMap = null;
        if (currentUser != null) {
            List<Vote> userVotes = this.voteService.findByUserIdAndPollIdIn(currentUser.getId(), pollIds);

            pollUserVoteMap = userVotes.stream()
                    .collect(Collectors.toMap(vote -> vote.getPoll().getId(), vote -> vote.getChoice().getId()));
        }
        return pollUserVoteMap;
    }

    private Map<Long, User> getPollCreatorMap(List<Poll> polls) {
        List<Long> creatorIds = polls.stream()
                .map(Poll::getCreatedBy)
                .distinct()
                .collect(Collectors.toList());

        List<User> creators = this.userService.findByIdIn(creatorIds);
        Map<Long, User> creatorMap = creators.stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        return creatorMap;
    }

}
