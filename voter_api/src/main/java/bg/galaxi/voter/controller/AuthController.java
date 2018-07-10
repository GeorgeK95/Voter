package bg.galaxi.voter.controller;

import bg.galaxi.voter.exception.InternalServerErrorException;
import bg.galaxi.voter.model.entity.Role;
import bg.galaxi.voter.model.enumeration.RoleName;
import bg.galaxi.voter.model.entity.User;
import bg.galaxi.voter.model.response.ApiResponseModel;
import bg.galaxi.voter.model.response.JwtAuthenticationResponseModel;
import bg.galaxi.voter.model.request.LoginRequestModel;
import bg.galaxi.voter.model.request.SignUpRequestModel;
import bg.galaxi.voter.repository.RoleRepository;
import bg.galaxi.voter.repository.UserRepository;
import bg.galaxi.voter.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

import static bg.galaxi.voter.util.AppConstants.*;

@RestController
@RequestMapping(API_AUTH_URL)
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping(SIGNIN_URL)
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestModel loginRequestModel) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestModel.getUsernameOrEmail(),
                        loginRequestModel.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponseModel(jwt));
    }

    @PostMapping(SIGNUP_URL)
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequestModel signUpRequestModel) {
        if (userRepository.existsByUsername(signUpRequestModel.getUsername())) {
            return new ResponseEntity(new ApiResponseModel(false, USERNAME_IS_ALREADY_TAKEN_MESSAGE),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequestModel.getEmail())) {
            return new ResponseEntity(new ApiResponseModel(false, EMAIL_ADDRESS_ALREADY_IN_USE_MESSAGE),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequestModel.getName(), signUpRequestModel.getUsername(),
                signUpRequestModel.getEmail(), signUpRequestModel.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new InternalServerErrorException(USER_ROLE_NOT_SET_MESSAGE));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path(USERS_USERNAME_URL)
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponseModel(true, USER_REGISTERED_SUCCESSFULLY_MESSAGE));
    }
}
