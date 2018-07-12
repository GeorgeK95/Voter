package bg.galaxi.voter.service.api;

import bg.galaxi.voter.model.entity.Role;
import bg.galaxi.voter.model.enumeration.RoleName;

import java.util.Optional;

public interface IRoleService {
    Optional<Role> findByName(RoleName roleUser);
}
