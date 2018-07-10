package bg.galaxi.voter.controller;

import bg.galaxi.voter.exception.ResourceNotFoundException;
import bg.galaxi.voter.model.entity.User;
import bg.galaxi.voter.model.dto.UserAvailability;
import bg.galaxi.voter.model.dto.UserContent;
import bg.galaxi.voter.model.dto.UserProfile;
import bg.galaxi.voter.model.response.PagedResponse;
import bg.galaxi.voter.model.response.PollResponse;
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

@RestController
@RequestMapping("/api")
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

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserContent getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return new UserContent(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        return new UserAvailability(!userRepository.existsByUsername(username));
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        return new UserAvailability(!userRepository.existsByEmail(email));

    }

    @GetMapping("/users/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        long pollCount = pollRepository.countByCreatedBy(user.getId());
        long voteCount = voteRepository.countByUserId(user.getId());

        UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getCreatedAt(), pollCount, voteCount);

        return userProfile;
    }

    @GetMapping("/users/{username}/polls")
    public PagedResponse<PollResponse> getPollsCreatedBy(@PathVariable(value = "username") String username,
                                                         @CurrentUser UserPrincipal currentUser,
                                                         @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                         @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return pollService.getPollsCreatedBy(username, currentUser, page, size);
    }

    @GetMapping("/users/{username}/votes")
    public PagedResponse<PollResponse> getPollsVotedBy(@PathVariable(value = "username") String username,
                                                       @CurrentUser UserPrincipal currentUser,
                                                       @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                       @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return pollService.getPollsVotedBy(username, currentUser, page, size);
    }

}
