package bg.galaxi.voter.loader;

import bg.galaxi.voter.model.entity.Role;
import bg.galaxi.voter.model.entity.User;
import bg.galaxi.voter.model.enumeration.RoleName;
import bg.galaxi.voter.repository.RoleRepository;
import bg.galaxi.voter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataLoader implements ApplicationRunner {

    private static final String ADMIN = "admin";
    private static final String ADMIN_PASS = "adminadmin";
    private static final String USER = "user";
    private static final String USER_PASS = "useruser";

    private static final String ADMIN_EMAIL = "admin@abv.bg";
    private static final String USER_EMAIL = "user@abv.bg";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public DataLoader(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Role roleUser = new Role(RoleName.ROLE_USER);
        this.roleRepository.save(roleUser);

        Role roleAdmin = new Role(RoleName.ROLE_ADMIN);
        this.roleRepository.save(roleAdmin);

        User admin = new User(ADMIN, ADMIN, ADMIN_EMAIL, new BCryptPasswordEncoder().encode(ADMIN_PASS));
        admin.setRoles(Set.of(roleAdmin, roleUser));
        this.userRepository.save(admin);

        User user = new User(USER, USER, USER_EMAIL, new BCryptPasswordEncoder().encode(USER_PASS));
        user.setRoles(Set.of(roleUser));
        this.userRepository.save(user);
    }
}
