package bg.galaxi.voter.service;

import bg.galaxi.voter.model.request.LoginRequestModel;
import bg.galaxi.voter.model.response.JwtAuthenticationResponseModel;
import bg.galaxi.voter.security.jwt.JwtTokenProvider;
import bg.galaxi.voter.service.api.IAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthenticationService implements IAuthenticationService {

    private final JwtTokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(JwtTokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public ResponseEntity<?> authenticateUser(LoginRequestModel loginRequestModel) {
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
}
