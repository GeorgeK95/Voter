package bg.galaxi.voter.controller;

import bg.galaxi.voter.model.request.LoginRequestModel;
import bg.galaxi.voter.model.request.SignUpRequestModel;
import bg.galaxi.voter.service.api.IAuthenticationService;
import bg.galaxi.voter.service.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static bg.galaxi.voter.util.AppConstants.*;

@RestController
@RequestMapping(API_AUTH_URL)
public class AuthController {

    private final IAuthenticationService authenticationService;

    private final IUserService userService;

    @Autowired
    public AuthController(IAuthenticationService authenticationService, IUserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping(SIGNIN_URL)
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestModel loginRequestModel) {
        return this.authenticationService.authenticateUser(loginRequestModel);
    }

    @PostMapping(SIGNUP_URL)
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequestModel signUpRequestModel) {
        return this.userService.registerUser(signUpRequestModel);
    }
}
