package bg.galaxi.voter.controller;

import bg.galaxi.voter.exception.ResourceNotFoundException;
import bg.galaxi.voter.model.entity.User;
import bg.galaxi.voter.model.dto.UserAvailabilityDto;
import bg.galaxi.voter.model.dto.UserContentDto;
import bg.galaxi.voter.model.dto.UserProfileDto;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static bg.galaxi.voter.util.AppConstants.*;

@RestController
@RequestMapping(API_URL)
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private PollService pollService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping(USER_ME_URL)
    @PreAuthorize(HAS_ROLE_USER)
    public UserContentDto getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return new UserContentDto(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
    }

    @GetMapping(USER_CHECK_USERNAME_AVAILABILITY_URL)
    public UserAvailabilityDto checkUsernameAvailability(@RequestParam(value = USERNAME) String username) {
        return new UserAvailabilityDto(!userRepository.existsByUsername(username));
    }

    @GetMapping(USER_CHECK_EMAIL_AVAILABILITY_URL)
    public UserAvailabilityDto checkEmailAvailability(@RequestParam(value = EMAIL) String email) {
        return new UserAvailabilityDto(!userRepository.existsByEmail(email));

    }

    @GetMapping(USERS_USERNAME_URL)
    public UserProfileDto getUserProfile(@PathVariable(value = USERNAME) String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(USER, USERNAME, username));

        long pollCount = pollRepository.countByCreatedBy(user.getId());
        long voteCount = voteRepository.countByUserId(user.getId());

        UserProfileDto userProfileDto = new UserProfileDto(user.getId(), user.getUsername(), user.getName(), user.getCreatedAt(), pollCount, voteCount);

        return userProfileDto;
    }

    @GetMapping(USERS_USERNAME_POLLS_URL)
    public PagedResponseModel<PollResponseModel> getPollsCreatedBy(@PathVariable(value = USERNAME) String username,
                                                                   @CurrentUser UserPrincipal currentUser,
                                                                   @RequestParam(value = PAGE, defaultValue = DEFAULT_PAGE_NUMBER) int page,
                                                                   @RequestParam(value = SIZE, defaultValue = DEFAULT_PAGE_SIZE) int size) {
        return pollService.getPollsCreatedBy(username, currentUser, page, size);
    }

    @GetMapping(USERS_USERNAME_VOTES_URL)
    public PagedResponseModel<PollResponseModel> getPollsVotedBy(@PathVariable(value = USERNAME) String username,
                                                                 @CurrentUser UserPrincipal currentUser,
                                                                 @RequestParam(value = PAGE, defaultValue = DEFAULT_PAGE_NUMBER) int page,
                                                                 @RequestParam(value = SIZE, defaultValue = DEFAULT_PAGE_SIZE) int size) {
        return pollService.getPollsVotedBy(username, currentUser, page, size);
    }

}
