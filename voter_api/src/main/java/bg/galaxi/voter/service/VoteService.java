package bg.galaxi.voter.service;

import bg.galaxi.voter.model.dto.ChoiceVoteCountDto;
import bg.galaxi.voter.model.entity.Vote;
import bg.galaxi.voter.repository.VoteRepository;
import bg.galaxi.voter.service.api.IVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VoteService implements IVoteService {

    private final VoteRepository voteRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Override
    public Page<Long> findVotedPollIdsByUserId(Long id, Pageable pageable) {
        return this.voteRepository.findVotedPollIdsByUserId(id, pageable);
    }

    @Override
    public List<ChoiceVoteCountDto> countByPollIdGroupByChoiceId(Long pollId) {
        return this.voteRepository.countByPollIdGroupByChoiceId(pollId);
    }

    @Override
    public Vote findByUserIdAndPollId(Long userId, Long pollId) {
        return this.voteRepository.findByUserIdAndPollId(userId, pollId);
    }

    @Override
    public Vote persist(Vote vote) {
        return this.voteRepository.save(vote);
    }

    @Override
    public List<ChoiceVoteCountDto> countByPollIdInGroupByChoiceId(List<Long> pollIds) {
        return this.voteRepository.countByPollIdInGroupByChoiceId(pollIds);
    }

    @Override
    public List<Vote> findByUserIdAndPollIdIn(Long id, List<Long> pollIds) {
        return this.voteRepository.findByUserIdAndPollIdIn(id, pollIds);
    }

    @Override
    public long countByUserId(Long id) {
        return this.voteRepository.countByUserId(id);
    }
}
