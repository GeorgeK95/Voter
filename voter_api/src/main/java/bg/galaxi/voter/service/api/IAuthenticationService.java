package bg.galaxi.voter.service.api;

import bg.galaxi.voter.model.request.LoginRequestModel;
import org.springframework.http.ResponseEntity;

public interface IAuthenticationService {
    ResponseEntity<?> authenticateUser(LoginRequestModel loginRequestModel);
}
