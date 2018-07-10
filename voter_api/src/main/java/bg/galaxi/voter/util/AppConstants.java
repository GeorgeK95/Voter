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
}
