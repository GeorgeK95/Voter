package bg.galaxi.voter.controller;

import bg.galaxi.voter.model.entity.Poll;
import bg.galaxi.voter.model.request.PollRequestModel;
import bg.galaxi.voter.model.request.VoteRequestModel;
import bg.galaxi.voter.model.response.ApiResponseModel;
import bg.galaxi.voter.model.response.PagedResponseModel;
import bg.galaxi.voter.model.response.PollResponseModel;
import bg.galaxi.voter.repository.PollRepository;
import bg.galaxi.voter.repository.UserRepository;
import bg.galaxi.voter.repository.VoteRepository;
import bg.galaxi.voter.security.user.CurrentUser;
import bg.galaxi.voter.security.user.UserPrincipal;
import bg.galaxi.voter.service.PollService;
import bg.galaxi.voter.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static bg.galaxi.voter.util.AppConstants.*;

@RestController
@RequestMapping(API_POLLS_URL)
public class PollController {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PollService pollService;

    private static final Logger logger = LoggerFactory.getLogger(PollController.class);

    @GetMapping
    public PagedResponseModel<PollResponseModel> getPolls(@CurrentUser UserPrincipal currentUser,
                                                          @RequestParam(value = PAGE, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                          @RequestParam(value = SIZE, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return pollService.getAllPolls(currentUser, page, size);
    }

    @PostMapping
    @PreAuthorize(HAS_ROLE_USER)
    public ResponseEntity<?> createPoll(@Valid @RequestBody PollRequestModel pollRequestModel) {
        Poll poll = pollService.createPoll(pollRequestModel);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path(POLL_ID_URL)
                .buildAndExpand(poll.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponseModel(true, POLL_CREATED_SUCCESSFULLY_MESSAGE));
    }


    @GetMapping(POLL_ID_URL)
    public PollResponseModel getPollById(@CurrentUser UserPrincipal currentUser,
                                         @PathVariable Long pollId) {
        return pollService.getPollById(pollId, currentUser);
    }

    @PostMapping(POLL_ID_VOTES_URL)
    @PreAuthorize(HAS_ROLE_USER)
    public PollResponseModel castVote(@CurrentUser UserPrincipal currentUser,
                                      @PathVariable Long pollId,
                                      @Valid @RequestBody VoteRequestModel voteRequestModel) {
        return pollService.castVoteAndGetUpdatedPoll(pollId, voteRequestModel, currentUser);
    }

}
