package bg.galaxi.voter.service.api;

import bg.galaxi.voter.model.entity.Poll;
import bg.galaxi.voter.model.request.PollRequestModel;
import bg.galaxi.voter.model.request.SignUpRequestModel;
import bg.galaxi.voter.model.request.VoteRequestModel;
import bg.galaxi.voter.model.response.PagedResponseModel;
import bg.galaxi.voter.model.response.PollResponseModel;
import bg.galaxi.voter.security.user.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface IPollService {
    PagedResponseModel<PollResponseModel> getAllPolls(UserPrincipal currentUser, int page, int size);

    PagedResponseModel<PollResponseModel> getPollsCreatedBy(String username, UserPrincipal currentUser, int page, int size);

    PagedResponseModel<PollResponseModel> getPollsVotedBy(String username, UserPrincipal currentUser, int page, int size);

    Poll createPoll(PollRequestModel pollRequestModel);

    PollResponseModel getPollById(Long pollId, UserPrincipal currentUser);

    PollResponseModel castVoteAndGetUpdatedPoll(Long pollId, VoteRequestModel voteRequestModel, UserPrincipal currentUser);

    ResponseEntity<?> createPollExpanded(PollRequestModel pollRequestModel);

    long countByCreatedBy(Long id);

    long countByUserId(Long id);

    Page<Poll> findAll(Pageable pageable);
}
