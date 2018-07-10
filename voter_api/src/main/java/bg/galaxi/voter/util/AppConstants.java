package bg.galaxi.voter.util;

public interface AppConstants {
    String DEFAULT_PAGE_NUMBER = "0";
    String DEFAULT_PAGE_SIZE = "30";
    String PAGE = "page";
    String SIZE = "size";
    int MAX_PAGE_SIZE = 50;
    String USERNAME = "username";
    String USER = "User";
    String EMAIL = "email";
    String CREATED_AT = "createdAt";
    String UPDATED_AT = "updatedAt";

    //    URLS
    String API_AUTH_ALL_URL = "/api/auth/**";
    String API_USER_CHECK_USERNAME_AVAILABILITY_URL = "/api/user/checkUsernameAvailability";
    String API_USER_CHECK_EMAIL_AVAILABILITY_URL = "/api/user/checkEmailAvailability";
    String API_USERS_ALL_URL = "/api/users/**";
    String API_POLLS_ALL_URL = "/api/polls/**";
    String API_AUTH_URL = "/api/auth";
    String SIGNIN_URL = "/signin";
    String SIGNUP_URL = "/signup";
    String USERS_USERNAME_URL = "/users/{username}";
    String ALL_URL = "/**";
    String API_POLLS_URL = "/api/polls";
    String POLL_ID_URL = "/{pollId}";
    String POLL_ID_VOTES_URL = "/{pollId}/votes";
    String API_URL = "/api";
    String USER_ME_URL = "/user/me";
    String USER_CHECK_USERNAME_AVAILABILITY_URL = "/user/checkUsernameAvailability";
    String USER_CHECK_EMAIL_AVAILABILITY_URL = "/user/checkEmailAvailability";
    String USERS_USERNAME_POLLS_URL = "/users/{username}/polls";
    String USERS_USERNAME_VOTES_URL = "/users/{username}/votes";

    //    MESSAGES
    String POLL_CREATED_SUCCESSFULLY_MESSAGE = "Poll Created Successfully";
    String USER_REGISTERED_SUCCESSFULLY_MESSAGE = "User registered successfully";
    String EMAIL_ADDRESS_ALREADY_IN_USE_MESSAGE = "Email Address already in use!";
    String USERNAME_IS_ALREADY_TAKEN_MESSAGE = "Username is already taken!";
    String USER_ROLE_NOT_SET_MESSAGE = "User Role not set.";

    //    PreAuthorize params
    String HAS_ROLE_USER = "hasRole('USER')";

    //    CONSTANTS
    int DAYS_MAX_VALUE = 7;
    int HOURS_MAX_VALUE = 23;
    String CHOICES = "choices";
    int TEXT_MAX_VALUE = 40;
    String POLL_ID = "poll_id";
    String POLLS = "polls";
    String POLL = "poll";
    int CHOISES_MIN_VALUE = 2;
    int CHOISES_MAX_VALUE = 6;
    int CHOISES_BATCH_SIZE = 30;
    int QUESTION_MAX_VALUE = 140;
    int ROLE_NAME_LENGH_VALUE = 60;
    String USERS = "users";
    String ROLES = "roles";
    int NAME_MAX_VALUE = 40;
    int USER_NAME_MAX_VALUE = 15;
    int EMAIL_MAX_VALUE = 40;
    int PASSWORD_MAX_VALUE = 100;
    String USER_ROLES = "user_roles";
    String USER_ID = "user_id";
    String ROLE_ID = "role_id";
    String VOTES = "votes";
    String CHOICE_ID = "choice_id";
}
