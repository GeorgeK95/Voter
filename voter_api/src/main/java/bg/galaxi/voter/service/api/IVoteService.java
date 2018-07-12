package bg.galaxi.voter.service.api;

import bg.galaxi.voter.model.dto.ChoiceVoteCountDto;
import bg.galaxi.voter.model.entity.Vote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IVoteService {
    Page<Long> findVotedPollIdsByUserId(Long id, Pageable pageable);

    List<ChoiceVoteCountDto> countByPollIdGroupByChoiceId(Long pollId);

    Vote findByUserIdAndPollId(Long userId, Long pollId);

    Vote persist(Vote vote);

    List<ChoiceVoteCountDto> countByPollIdInGroupByChoiceId(List<Long> pollIds);

    List<Vote> findByUserIdAndPollIdIn(Long id, List<Long> pollIds);

    long countByUserId(Long id);
}
