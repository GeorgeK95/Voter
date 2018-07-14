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

import java.util.List;

import static bg.galaxi.voter.util.AppConstants.*;

@RestController
@RequestMapping(API_POLLS_BY_TAGS_URL)
public class PollsByTagsController {

    private final IPollService pollService;

    private static final Logger logger = LoggerFactory.getLogger(PollsByTagsController.class);

    @Autowired
    public PollsByTagsController(IPollService pollService) {
        this.pollService = pollService;
    }

    @GetMapping
    public List<PollResponseModel> getPollsByTags(@CurrentUser UserPrincipal currentUser, @RequestParam String tags) {
        return pollService.getPollsByTags(tags, currentUser);
    }


}
