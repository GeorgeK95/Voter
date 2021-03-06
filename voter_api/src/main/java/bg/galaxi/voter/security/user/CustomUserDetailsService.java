package bg.galaxi.voter.security.user;

import bg.galaxi.voter.exception.ResourceNotFoundException;
import bg.galaxi.voter.model.entity.User;
import bg.galaxi.voter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static bg.galaxi.voter.util.AppConstants.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (!user.isPresent())
            new UsernameNotFoundException(USER_NOT_FOUND_WITH_USERNAME_OR_EMAIL_MESSAGE + username);

        if (user.get().getBanned()) new UsernameNotFoundException(BANNED_USER_MESSAGE);

        return UserPrincipal.create(user.get());
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(USER, ID, id)
        );

        return UserPrincipal.create(user);
    }
}