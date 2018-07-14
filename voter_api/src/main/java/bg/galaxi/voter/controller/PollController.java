package bg.galaxi.voter.controller;

import bg.galaxi.voter.model.request.PollRequestModel;
import bg.galaxi.voter.model.request.VoteRequestModel;
import bg.galaxi.voter.model.response.PagedResponseModel;
import bg.galaxi.voter.model.response.PollResponseModel;
import bg.galaxi.voter.security.user.CurrentUser;
import bg.galaxi.voter.security.user.UserPrincipal;
import bg.galaxi.voter.service.api.IPollService;
import bg.galaxi.voter.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static bg.galaxi.voter.util.AppConstants.*;

@RestController
@RequestMapping(API_POLLS_URL)
public class PollController {

    private final IPollService pollService;

    private static final Logger logger = LoggerFactory.getLogger(PollController.class);

    @Autowired
    public PollController(IPollService pollService) {
        this.pollService = pollService;
    }

    @GetMapping
    public PagedResponseModel<PollResponseModel> getPolls(@CurrentUser UserPrincipal currentUser,
                                                          @RequestParam(value = PAGE, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                          @RequestParam(value = SIZE, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return pollService.getAllPolls(currentUser, page, size);
    }

    @PostMapping
    @PreAuthorize(HAS_ROLE_USER)
    public ResponseEntity<?> createPoll(@Valid @RequestBody PollRequestModel pollRequestModel) {
        return this.pollService.createPollExpanded(pollRequestModel);
    }


    @GetMapping(POLL_ID_URL)
    public PollResponseModel getPollById(@CurrentUser UserPrincipal currentUser, @PathVariable Long pollId) {
        return pollService.getPollById(pollId, currentUser);
    }

    @PostMapping(POLL_ID_VOTES_URL)
    @PreAuthorize(HAS_ROLE_USER)
    public PollResponseModel castVote(@CurrentUser UserPrincipal currentUser, @PathVariable Long pollId,
                                      @Valid @RequestBody VoteRequestModel voteRequestModel) {
        return pollService.castVoteAndGetUpdatedPoll(pollId, voteRequestModel, currentUser);
    }

    @PostMapping(DELETE_POLL_BY_ID)
    @PreAuthorize(HAS_ROLE_ADMIN)
    public boolean deletePollProcess(@CurrentUser UserPrincipal currentUser, @PathVariable Long pollId) {
        return pollService.deletePoll(pollId, currentUser);
    }
}
