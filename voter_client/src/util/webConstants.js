//app base
export const APP_NAME = 'Voter';
export const API_BASE_URL = 'http://localhost:7000/api';
export const ADMIN = 'admin';

//app constants
export const HASHTAG = '#';
export const EMPTY = '';
export const SPACE = ' ';

export const POLL_LIST_SIZE = 3; //30;
export const MAX_CHOICES = 6;
export const POLL_QUESTION_MAX_LENGTH = 140;
export const POLL_CHOICE_MAX_LENGTH = 40;

export const NAME_MIN_LENGTH = 4;
export const NAME_MAX_LENGTH = 40;

export const USERNAME_MIN_LENGTH = 3;
export const USERNAME_MAX_LENGTH = 15;

export const EMAIL_MAX_LENGTH = 40;

export const PASSWORD_MIN_LENGTH = 6;
export const PASSWORD_MAX_LENGTH = 20;

//http
export const HTTP_GET = 'GET';
export const HTTP_POST = 'POST';
export const HTTP_DELETE = 'DELETE';

export const APPLICATION_JSON = 'application/json';

export const AUTHORIZATION = 'Authorization';
export const BEARER = 'Bearer ';
export const ACCESS_TOKEN = 'accessToken';

//urls
export const SIGN_IN_URL = API_BASE_URL + "/auth/signin";
export const SIGN_UP_URL = API_BASE_URL + "/auth/signup";
export const NO_ACCESS_TOKEN_SET_MESSAGE = "No access token set.";
export const GET_CURRENT_USER_URL = API_BASE_URL + "/user/me";
export const CREATE_POLL_URL = API_BASE_URL + "/polls";
export const GET_POLLS_BY_TAGS = API_BASE_URL + "/pollsByTags";
export const GET_CONTEXT_TAGS_URL = API_BASE_URL + "/tags/";
export const SLASH_URL = "/";
export const LOGIN_URL = '/login';
export const TAGS_URL = 'http://localhost:7000/api/tags/';

export const SUCCESS = 'success';
export const ERROR = 'error';
export const USER_CREATED_POLLS = 'USER_CREATED_POLLS';
export const USER_VOTED_POLLS = 'USER_VOTED_POLLS';
export const SIGN_OUT = "logout";

//notification
export const NOTIFICATION_TOP = 70;
export const NOTIFICATION_DURATION = 3;
export const TOP_RIGHT = 'topRight';

//messages
export const INVALID_CREDENTIALS_MESSAGE = 'Your Username or Password is incorrect. Please try again!';
export const SOMETHING_WENT_WRONG_MESSAGE = 'Sorry! Something went wrong. Please try again!';
export const PLEASE_INPUT_YOUR_PASSWORD_MESSAGE = 'Please input your Password!';
export const PLEASE_INPUT_YOUR_USERNAME_EMAIL_MESSAGE = 'Please input your username or email!';
export const SUCCESSFULLY_REGISTERED_MESSAGE = "Thank you! You're successfully registered. Please Login to continue!";
export const NAME_TOO_SHORT_MESSAGE = `Name is too short (Minimum ${NAME_MIN_LENGTH} characters needed.)`;
export const NAME_TOO_LONG_MESSAGE = `Name is too long (Maximum ${NAME_MAX_LENGTH} characters allowed.)`;
export const PASSWORD_TOO_SHORT_MESSAGE = `Password is too short (Minimum ${PASSWORD_MIN_LENGTH} characters needed.)`;
export const PASSWORD_TOO_LONG_MESSAGE = `Password is too long (Maximum ${PASSWORD_MAX_LENGTH} characters allowed.)`;
export const VALIDATING = 'validating';
export const USERNAME_ALREADY_TAKEN_MESSAGE = 'This username is already taken';
export const EMAIL_ALREADY_REGISTERED_MESSAGE = 'This Email is already registered';
export const USERNAME_TOO_SHORT_MESSAGE = `Username is too short (Minimum ${USERNAME_MIN_LENGTH} characters needed.)`;
export const USERNAME_TOO_LONG_MESSAGE = `Username is too long (Maximum ${USERNAME_MAX_LENGTH} characters allowed.)`;
export const EMAIL_REGEX_EXPRESSION = '[^@ ]+@[^@ ]+\\.[^@ ]+';
export const EMAIL_TOO_LONG_MESSAGE = `Email is too long (Maximum ${EMAIL_MAX_LENGTH} characters allowed)`;
export const NOT_VALID_EMAIL_MESSAGE = 'Email not valid';
export const EMPTY_EMAIL_MESSAGE = 'Email may not be empty';
export const EMAIL_REGEX = RegExp(EMAIL_REGEX_EXPRESSION);
export const PLEASE_LOGIN_TO_CREATE_POLL_MESSAGE = 'You have been logged out. Please login to create poll.';
export const PLEASE_ENTER_YOUR_QUESTION_MESSAGE = 'Please enter your question!';
export const QUESTION_TOO_LONG_MESSAGE = `Question is too long (Maximum ${POLL_QUESTION_MAX_LENGTH} characters allowed)`;
export const PLEASE_ENTER_CHOICE_MESSAGE = 'Please enter a choice!';
export const CHOICE_TOO_LONG_MESSAGE = `Choice is too long (Maximum ${POLL_CHOICE_MAX_LENGTH} characters allowed)`;
export const PLEASE_LOGIN_TO_VOTE_MESSAGE = 'You have been logged out. Please login to vote';
export const USER_BANNED_MESSAGE = 'User was banned.';
export const NOTIFICATION_BANNED_MESSAGE = 'Poll was removed.';
export const SUCCESSFULLY_SIGNED_OUT_MESSAGE = "You're successfully logged out.";
export const GLAD_TO_SEE_YOU_AGAIN_MESSAGE = "Glad to see you again.";

//time
export const HOURS_LEFT = " hours left";
export const SECONDS_LEFT = " seconds left";
export const MINUTES_LEFT = " minutes left";
export const DAYS_LEFT = " days left";
export const LESS_THAN_SECOND_LEFT = "less than a second left";