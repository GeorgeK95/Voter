package bg.galaxi.voter.loader;

import bg.galaxi.voter.model.entity.*;
import bg.galaxi.voter.model.enumeration.RoleName;
import bg.galaxi.voter.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DataLoader implements ApplicationRunner {

    private static final String ADMIN = "admin";
    private static final String ADMIN_PASS = "adminadmin";
    private static final String USER = "user";
    private static final String USER_PASS = "useruser";

    private static final String ADMIN_EMAIL = "admin@abv.bg";
    private static final String USER_EMAIL = "user@abv.bg";

    private static final String BULGARIA = "Bulgaria";
    private static final String UK = "UK";
    private static final String USA = "USA";
    private static final String BG = "bg";
    private static final String DONALD_TRUMP = "Donald Trump";
    private static final String ELECTIONS = "elections";
    private static final String CONTENT = "2020";
    private static final String JACK_FELLURE = "Jack Fellure";
    private static final String JONATHON_SHARKEY = "Jonathon Sharkey";
    private static final String FRANCE = "France";
    private static final String FR = "fr";
    private static final String FOOTBALL = "football";
    private static final String CROATIA = "Croatia";
    private static final String CR = "cr";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PollRepository pollRepository;
    private final ChoiceRepository choiceRepository;
    private final TagRepository tagRepository;

    private final String sportQuestion = "Who will grab the WC tonight ?";
    private final String countryQuestion = "Most beautiful country ?";
    private final String electionQuestion = "Who are you voting for ?";
    private static final Tag USA_TAG = new Tag(USA);
    private User user;

    @Autowired
    public DataLoader(UserRepository userRepository, RoleRepository roleRepository, PollRepository pollRepository, ChoiceRepository choiceRepository, TagRepository tagRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.pollRepository = pollRepository;
        this.choiceRepository = choiceRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        this.seedUsers();
        this.seedPolls();
    }

    private void seedPolls() {
        this.seedElectionVoter();
        this.seedCountryVoter();
        this.seedSportVoter();
    }

    private void seedSportVoter() {
        List<Choice> choices = List.of(new Choice(FRANCE), new Choice(CROATIA));
        Set tags = Set.of(new Tag(FR), new Tag(CR), new Tag(FOOTBALL));
        Instant expirationDateTime = Instant.now().plus(Duration.ofDays(1));

        Poll poll = new Poll(sportQuestion, tags, choices, expirationDateTime);
        poll.setCreatedBy(this.user.getId());
        poll.setUpdatedBy(this.user.getId());

        choices.forEach(c -> c.setPoll(poll));

        this.pollRepository.save(poll);
    }

    private void seedCountryVoter() {
        List<Choice> choices = List.of(new Choice(BULGARIA), new Choice(UK), new Choice(USA));
        Set tags = Set.of(new Tag(BG), new Tag(UK));
        Instant expirationDateTime = Instant.now().plus(Duration.ofDays(1));

        Poll poll = new Poll(countryQuestion, tags, choices, expirationDateTime);
//        poll.addTag(this.tagRepository.findByContent(USA));
        poll.setCreatedBy(this.user.getId());
        poll.setUpdatedBy(this.user.getId());

        choices.forEach(c -> c.setPoll(poll));

        this.pollRepository.save(poll);
    }

    private void seedElectionVoter() {
        List<Choice> choices = List.of(new Choice(DONALD_TRUMP), new Choice(JACK_FELLURE), new Choice(JONATHON_SHARKEY));
        Set tags = Set.of(new Tag(ELECTIONS), new Tag(CONTENT), USA_TAG);
        Instant expirationDateTime = Instant.now().plus(Duration.ofDays(1));

        Poll poll = new Poll(electionQuestion, tags, choices, expirationDateTime);
        poll.setCreatedBy(this.user.getId());
        poll.setUpdatedBy(this.user.getId());

        choices.forEach(c -> c.setPoll(poll));

        this.pollRepository.save(poll);
    }

    private void seedUsers() {
        Role roleUser = new Role(RoleName.ROLE_USER);
        this.roleRepository.save(roleUser);

        Role roleAdmin = new Role(RoleName.ROLE_ADMIN);
        this.roleRepository.save(roleAdmin);

        User admin = new User(ADMIN, ADMIN, ADMIN_EMAIL, new BCryptPasswordEncoder().encode(ADMIN_PASS));
        admin.setRoles(Set.of(roleAdmin, roleUser));
        this.userRepository.save(admin);

        this.user = new User(USER, USER, USER_EMAIL, new BCryptPasswordEncoder().encode(USER_PASS));
        user.setRoles(Set.of(roleUser));
        this.userRepository.save(user);
    }
}
