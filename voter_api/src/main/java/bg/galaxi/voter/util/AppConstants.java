package bg.galaxi.voter.util;

public interface AppConstants {
    String DEFAULT_PAGE_NUMBER = "0";
    String DEFAULT_PAGE_SIZE = "3"; //"30";
    String PAGE = "page";
    String SIZE = "size";
    int MAX_PAGE_SIZE = 50;
    String USERNAME = "username";
    String USER = "User";
    String EMAIL = "email";
    String CREATED_AT = "createdAt";
    String UPDATED_AT = "updatedAt";
    String SPACE = " ";

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
    String API_POLLS_BY_TAGS_URL = "/api/pollsByTags";
    String API_TAGS_URL = "/api/tags";
    String POLL_ID_URL = "/{pollId}";
    String TAG_NAME_URL = "/{content}";
    String DELETE_POLL_BY_ID = "/delete/{pollId}";
    String POLL_ID_VOTES_URL = "/{pollId}/votes";
    String API_URL = "/api";
    String USER_ME_URL = "/user/me";
    String USER_CHECK_USERNAME_AVAILABILITY_URL = "/user/checkUsernameAvailability";
    String USER_CHECK_EMAIL_AVAILABILITY_URL = "/user/checkEmailAvailability";
    String USERS_USERNAME_POLLS_URL = "/users/{username}/polls";
    String USERS_USERNAME_VOTES_URL = "/users/{username}/votes";
    String USERS_DELETE_ID_URL = "/users/delete/{id}";

    //    MESSAGES
    String POLL_CREATED_SUCCESSFULLY_MESSAGE = "Poll Created Successfully";
    String USER_REGISTERED_SUCCESSFULLY_MESSAGE = "User registered successfully";
    String EMAIL_ADDRESS_ALREADY_IN_USE_MESSAGE = "Email Address already in use!";
    String USERNAME_IS_ALREADY_TAKEN_MESSAGE = "Username is already taken!";
    String USER_ROLE_NOT_SET_MESSAGE = "User Role not set.";
    String RESPONDING_WITH_UNAUTHORIZED_ERROR_MESSAGE = "Responding with unauthorized error. Message - {}";
    String SORRY_YOU_RE_NOT_AUTHORIZED_TO_ACCESS_THIS_RESOURCE_MESSAGE = "Sorry, You're not authorized to access this resource.";
    String COULD_NOT_SET_USER_AUTHENTICATION_IN_SECURITY_CONTEXT_MESSAGE = "Could not set user authentication in security context";
    String AUTHORIZATION = "Authorization";
    String BEARER_ = "Bearer ";

    //    PreAuthorize params
    String HAS_ROLE_USER = "hasRole('USER')";
    String HAS_ROLE_ADMIN = "hasRole('ADMIN')";
    String HAS_ANY_ROLE_AUTH = "hasAnyRole('USER', 'ADMIN')";

    //    JWT
    String $_APP_JWT_SECRET = "${app.jwtSecret}";
    String $_APP_JWT_EXPIRATION_IN_MS = "${app.jwtExpirationInMs}";
    String INVALID_JWT_SIGNATURE = "Invalid JWT signature";
    String INVALID_JWT_TOKEN = "Invalid JWT token";
    String EXPIRED_JWT_TOKEN = "Expired JWT token";
    String UNSUPPORTED_JWT_TOKEN = "Unsupported JWT token";
    String JWT_CLAIMS_STRING_IS_EMPTY = "JWT claims string is empty.";

    //    CONSTANTS
    long DAYS_MAX_VALUE = 7;
    long HOURS_MAX_VALUE = 23;
    String CHOICES = "choices";
    int TEXT_MAX_VALUE = 40;
    String POLL_ID = "poll_id";
    String POLLS = "polls";
    String LOGGER = "logger";
    String POLL = "poll";
    int CHOISES_MIN_VALUE = 2;
    int CHOISES_MAX_VALUE = 6;
    int CHOISES_BATCH_SIZE = 30;
    int QUESTION_MAX_VALUE = 140;
    int ROLE_NAME_LENGH_VALUE = 60;
    int TAG_NAME_LENGH_VALUE = 100;
    String USERS = "users";
    String ROLES = "roles";
    String TAGS = "Tags";
    int NAME_MAX_VALUE = 40;
    int NAME_MIN_VALUE = 4;
    int USER_NAME_MIN_VALUE = 3;
    int USER_NAME_MAX_VALUE = 15;
    int EMAIL_MAX_VALUE = 40;
    int PASSWORD_MAX_VALUE = 100;
    String POLL_TAG = "poll_tag";
    String USER_ROLES = "user_roles";
    String USER_ID = "user_id";
    String TAG_ID = "tag_id";
    String ROLE_ID = "role_id";
    String VOTES = "votes";
    String CHOICE_ID = "choice_id";
    String USER_NOT_FOUND_WITH_USERNAME_OR_EMAIL_MESSAGE = "User not found with username or email : ";
    String BANNED_USER_MESSAGE = "User was banned from admin.";
    String ID = "id";
    String SORRY_THIS_POLL_HAS_ALREADY_EXPIRED_MESSAGE = "Sorry! This Poll has already expired";
    String CHOICE = "Choice";
    String USER_HAS_ALREADY_VOTED_IN_POLL_MESSAGE = "User {} has already voted in Poll {}";
    String SORRY_YOU_HAVE_ALREADY_CAST_YOUR_VOTE_IN_THIS_POLL_MESSAGE = "Sorry! You have already cast your vote in this poll";
    String PAGE_NUMBER_CANNOT_BE_LESS_THAN_ZERO_MESSAGE = "Page number cannot be less than zero.";
    String PAGE_SIZE_MUST_NOT_BE_GREATER_THAN_MESSAGE = "Page size must not be greater than ";
    long DAYS_MIN_VALUE = 0;
    long HOURS_MIN_VALUE = 0;
}
