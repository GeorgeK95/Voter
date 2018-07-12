package bg.galaxi.voter.service.api;

import bg.galaxi.voter.model.entity.Role;
import bg.galaxi.voter.model.enumeration.RoleName;
import bg.galaxi.voter.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> findByName(RoleName roleUser) {
        return this.roleRepository.findByName(roleUser.ROLE_USER);
    }
}
