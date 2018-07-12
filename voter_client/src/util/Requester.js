import {
    POLL_LIST_SIZE,
    ACCESS_TOKEN,
    APPLICATION_JSON,
    BEARER,
    HTTP_GET,
    HTTP_POST,
    AUTHORIZATION,
    SIGN_IN_URL,
    SIGN_UP_URL,
    USERNAME_AVAILABILITY_URL,
    GET_USER_VOTED_POLLS,
    GET_USER_CREATED_POLLS,
    GET_USER_PROFILE_URL,
    NO_ACCESS_TOKEN_SET_MESSAGE,
    GET_CURRENT_USER_URL,
    EMAIL_AVAILABILITY_URL,
    CAST_VOTE,
    CREATE_POLL_URL,
    ALL_POLLS_URL
} from '../util/webConstants';

const request = (options) => {
    const headers = new Headers({
        CONTENT_TYPE: APPLICATION_JSON,
    })

    if (localStorage.getItem(ACCESS_TOKEN)) {
        headers.append(AUTHORIZATION, BEARER + localStorage.getItem(ACCESS_TOKEN))
    }

    const defaults = {headers: headers};
    options = Object.assign({}, defaults, options);

    return fetch(options.url, options)
        .then(response =>
            response.json().then(json => {
                if (!response.ok) {
                    return Promise.reject(json);
                }
                return json;
            })
        );
};

export function getAllPolls(page, size) {
    page = page || 0;
    size = size || POLL_LIST_SIZE;

    return request({
        url: ALL_POLLS_URL,
        method: HTTP_GET
    });
}

export function createPoll(pollData) {
    return request({
        url: CREATE_POLL_URL,
        method: HTTP_POST,
        body: JSON.stringify(pollData)
    });
}

export function castVote(voteData) {
    return request({
        url: CAST_VOTE,
        method: HTTP_POST,
        body: JSON.stringify(voteData)
    });
}

export function login(loginRequest) {
    return request({
        url: SIGN_IN_URL,
        method: HTTP_POST,
        body: JSON.stringify(loginRequest)
    });
}

export function signup(signupRequest) {
    return request({
        url: SIGN_UP_URL,
        method: 'POST',
        body: JSON.stringify(signupRequest)
    });
}

export function checkUsernameAvailability(username) {
    return request({
        url: USERNAME_AVAILABILITY_URL,
        method: HTTP_GET
    });
}

export function checkEmailAvailability(email) {
    return request({
        url: EMAIL_AVAILABILITY_URL,
        method: HTTP_GET
    });
}


export function getCurrentUser() {
    if (!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject(NO_ACCESS_TOKEN_SET_MESSAGE);
    }

    return request({
        url: GET_CURRENT_USER_URL,
        method: HTTP_GET
    });
}

export function getUserProfile(username) {
    return request({
        url: GET_USER_PROFILE_URL,
        method: HTTP_GET
    });
}

export function getUserCreatedPolls(username, page, size) {
    page = page || 0;
    size = size || POLL_LIST_SIZE;

    return request({
        url: GET_USER_CREATED_POLLS,
        method: HTTP_GET
    });
}

export function getUserVotedPolls(username, page, size) {
    page = page || 0;
    size = size || POLL_LIST_SIZE;

    return request({
        url: GET_USER_VOTED_POLLS,
        method: HTTP_GET
    });
}