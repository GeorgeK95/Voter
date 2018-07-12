//app base
export const APP_NAME = 'Voter';
export const API_BASE_URL = 'http://localhost:7000/api';

//app constants
export const POLL_LIST_SIZE = 30;
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

export const CONTENT_TYPE = 'Content-Type';
export const APPLICATION_JSON = 'application/json';

export const AUTHORIZATION = 'Authorization';
export const BEARER = 'Bearer ';
export const ACCESS_TOKEN = 'accessToken';

//urls
export const SIGN_IN_URL = API_BASE_URL + "/auth/signin";
export const SIGN_UP_URL = API_BASE_URL + "/auth/signup";
export const USERNAME_AVAILABILITY_URL = API_BASE_URL + "/user/checkUsernameAvailability?username=" + username;
export const GET_USER_VOTED_POLLS = API_BASE_URL + "/users/" + username + "/votes?page=" + page + "&size=" + size;
export const GET_USER_CREATED_POLLS = API_BASE_URL + "/users/" + username + "/polls?page=" + page + "&size=" + size;
export const GET_USER_PROFILE_URL = API_BASE_URL + "/users/" + username;
export const NO_ACCESS_TOKEN_SET_MESSAGE = "No access token set.";
export const GET_CURRENT_USER_URL = API_BASE_URL + "/user/me";
export const EMAIL_AVAILABILITY_URL = API_BASE_URL + "/user/checkEmailAvailability?email=" + email;
export const CAST_VOTE = API_BASE_URL + "/polls/" + voteData.pollId + "/votes";
export const CREATE_POLL_URL = API_BASE_URL + "/polls";
export const ALL_POLLS_URL = API_BASE_URL + "/polls?page=" + page + "&size=" + size;

export const SUCCESS = 'success';
export const ERROR = 'error';

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
export const USERNAME_TOO_sHORT_MESSAGE = `Username is too short (Minimum ${USERNAME_MIN_LENGTH} characters needed.)`;
export const USERNAME_TOO_LONG_MESSAGE = `Username is too long (Maximum ${USERNAME_MAX_LENGTH} characters allowed.)`;
export const EMAIL_REGEX_EXPRESSION = '[^@ ]+@[^@ ]+\\.[^@ ]+';
export const EMAIL_TOO_LONG_MESSAGE = `Email is too long (Maximum ${EMAIL_MAX_LENGTH} characters allowed)`;
export const NOT_VALID_EMAIL_MESSAGE = 'Email not valid';
export const EMTPY_EMAIL_MESSAGE = 'Email may not be empty';
export const EMAIL_REGEX = RegExp(EMAIL_REGEX_EXPRESSION);
