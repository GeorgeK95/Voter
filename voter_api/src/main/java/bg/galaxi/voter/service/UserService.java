package bg.galaxi.voter.service;

import bg.galaxi.voter.exception.InternalServerErrorException;
import bg.galaxi.voter.exception.ResourceNotFoundException;
import bg.galaxi.voter.model.dto.UserProfileDto;
import bg.galaxi.voter.model.entity.Role;
import bg.galaxi.voter.model.entity.User;
import bg.galaxi.voter.model.enumeration.RoleName;
import bg.galaxi.voter.model.request.SignUpRequestModel;
import bg.galaxi.voter.model.response.ApiResponseModel;
import bg.galaxi.voter.repository.UserRepository;
import bg.galaxi.voter.service.api.IPollService;
import bg.galaxi.voter.service.api.IRoleService;
import bg.galaxi.voter.service.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static bg.galaxi.voter.util.AppConstants.*;
import static bg.galaxi.voter.util.AppConstants.USER_REGISTERED_SUCCESSFULLY_MESSAGE;

@Service
@Transactional
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final IRoleService roleService;

    private final IPollService pollService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, IRoleService roleService, PasswordEncoder passwordEncoder, IPollService pollService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.pollService = pollService;
    }

    @Override
    public ResponseEntity<?> registerUser(SignUpRequestModel signUpRequestModel) {
        ResponseEntity<?> validated = this.validateRegistrationData(signUpRequestModel);
        if (validated != null) return validated;

        User user = new User(signUpRequestModel.getName(), signUpRequestModel.getUsername(),
                signUpRequestModel.getEmail(), signUpRequestModel.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = this.roleService.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new InternalServerErrorException(USER_ROLE_NOT_SET_MESSAGE));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path(USERS_USERNAME_URL)
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponseModel(true, USER_REGISTERED_SUCCESSFULLY_MESSAGE));
    }

    private ResponseEntity<?> validateRegistrationData(SignUpRequestModel signUpRequestModel) {
        if (userRepository.existsByUsername(signUpRequestModel.getUsername()))
            return new ResponseEntity(new ApiResponseModel(false, USERNAME_IS_ALREADY_TAKEN_MESSAGE),
                    HttpStatus.BAD_REQUEST);

        if (userRepository.existsByEmail(signUpRequestModel.getEmail()))
            return new ResponseEntity(new ApiResponseModel(false, EMAIL_ADDRESS_ALREADY_IN_USE_MESSAGE),
                    HttpStatus.BAD_REQUEST);

        return null;
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public UserProfileDto getUserProfile(String username) {
        User user = this.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(USER, USERNAME, username));

        long pollCount = this.pollService.countByCreatedBy(user.getId());
        long voteCount = this.pollService.countByUserId(user.getId());

        return new UserProfileDto(user.getId(), user.getUsername(), user.getName(), user.getCreatedAt(), pollCount, voteCount);
    }

    @Override
    public Optional<User> findById(Long createdBy) {
        return this.userRepository.findById(createdBy);
    }

    @Override
    public User getOne(Long id) {
        return this.userRepository.getOne(id);
    }

    @Override
    public List<User> findByIdIn(List<Long> creatorIds) {
        return this.userRepository.findByIdIn(creatorIds);
    }
}
