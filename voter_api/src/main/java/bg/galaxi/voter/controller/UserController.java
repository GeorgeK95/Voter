package bg.galaxi.voter.controller;

import bg.galaxi.voter.model.dto.UserAvailabilityDto;
import bg.galaxi.voter.model.dto.UserContentDto;
import bg.galaxi.voter.model.dto.UserProfileDto;
import bg.galaxi.voter.model.response.PagedResponseModel;
import bg.galaxi.voter.model.response.PollResponseModel;
import bg.galaxi.voter.security.user.CurrentUser;
import bg.galaxi.voter.security.user.UserPrincipal;
import bg.galaxi.voter.service.api.IPollService;
import bg.galaxi.voter.service.api.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static bg.galaxi.voter.util.AppConstants.*;

@RestController
@RequestMapping(API_URL)
public class UserController {

    public static final String USER = "user";
    public static final String ADMIN = "admin";
    private final IPollService pollService;

    private final IUserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(IPollService pollService, IUserService userService) {
        this.pollService = pollService;
        this.userService = userService;
    }

    @GetMapping(USER_ME_URL)
    @PreAuthorize(HAS_ROLE_USER)
    public UserContentDto getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return new UserContentDto(currentUser.getId(), currentUser.getUsername(), currentUser.getName(),
                currentUser.getAuthorities().size() == 1 ? USER : ADMIN);
    }

    @GetMapping(USER_CHECK_USERNAME_AVAILABILITY_URL)
    public UserAvailabilityDto checkUsernameAvailability(@RequestParam(value = USERNAME) String username) {
        return new UserAvailabilityDto(!this.userService.existsByUsername(username));
    }

    @GetMapping(USER_CHECK_EMAIL_AVAILABILITY_URL)
    public UserAvailabilityDto checkEmailAvailability(@RequestParam(value = EMAIL) String email) {
        return new UserAvailabilityDto(!this.userService.existsByEmail(email));
    }

    @GetMapping(USERS_USERNAME_URL)
    public UserProfileDto getUserProfile(@PathVariable(value = USERNAME) String username) {
        return this.userService.getUserProfile(username);
    }

    @GetMapping(USERS_USERNAME_POLLS_URL)
    public PagedResponseModel<PollResponseModel> getPollsCreatedBy(@PathVariable(value = USERNAME) String username,
                                                                   @CurrentUser UserPrincipal currentUser,
                                                                   @RequestParam(value = PAGE, defaultValue = DEFAULT_PAGE_NUMBER) int page,
                                                                   @RequestParam(value = SIZE, defaultValue = DEFAULT_PAGE_SIZE) int size) {
        return this.pollService.getPollsCreatedBy(username, currentUser, page, size);
    }

    @GetMapping(USERS_USERNAME_VOTES_URL)
    public PagedResponseModel<PollResponseModel> getPollsVotedBy(@PathVariable(value = USERNAME) String username,
                                                                 @CurrentUser UserPrincipal currentUser,
                                                                 @RequestParam(value = PAGE, defaultValue = DEFAULT_PAGE_NUMBER) int page,
                                                                 @RequestParam(value = SIZE, defaultValue = DEFAULT_PAGE_SIZE) int size) {
        return this.pollService.getPollsVotedBy(username, currentUser, page, size);
    }

}
