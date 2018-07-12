package bg.galaxi.voter.service.api;

import bg.galaxi.voter.model.dto.UserProfileDto;
import bg.galaxi.voter.model.entity.User;
import bg.galaxi.voter.model.request.SignUpRequestModel;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    ResponseEntity<?> registerUser(SignUpRequestModel signUpRequestModel);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);

    UserProfileDto getUserProfile(String username);

    Optional<User> findById(Long createdBy);

    User getOne(Long id);

    List<User> findByIdIn(List<Long> creatorIds);
}
