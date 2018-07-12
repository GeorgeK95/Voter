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
    NO_ACCESS_TOKEN_SET_MESSAGE,
    GET_CURRENT_USER_URL,
    CREATE_POLL_URL,
} from '../util/webConstants';

import {API_BASE_URL} from "./webConstants";

const request = (options) => {
    const headers = new Headers({
        'Content-Type': APPLICATION_JSON,
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

    const ALL_POLLS_URL = API_BASE_URL + "/polls?page=" + page + "&size=" + size;

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
    const CAST_VOTE = API_BASE_URL + "/polls/" + voteData.pollId + "/votes";

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
    const USERNAME_AVAILABILITY_URL = API_BASE_URL + "/user/checkUsernameAvailability?username=" + username;

    return request({
        url: USERNAME_AVAILABILITY_URL,
        method: HTTP_GET
    });
}

export function checkEmailAvailability(email) {
    const EMAIL_AVAILABILITY_URL = API_BASE_URL + "/user/checkEmailAvailability?email=" + email;

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
    const GET_USER_PROFILE_URL = API_BASE_URL + "/users/" + username;

    return request({
        url: GET_USER_PROFILE_URL,
        method: HTTP_GET
    });
}

export function getUserCreatedPolls(username, page, size) {
    page = page || 0;
    size = size || POLL_LIST_SIZE;

    const GET_USER_CREATED_POLLS = API_BASE_URL + "/users/" + username + "/polls?page=" + page + "&size=" + size;

    return request({
        url: GET_USER_CREATED_POLLS,
        method: HTTP_GET
    });
}

export function getUserVotedPolls(username, page, size) {
    page = page || 0;
    size = size || POLL_LIST_SIZE;

    const GET_USER_VOTED_POLLS = API_BASE_URL + "/users/" + username + "/votes?page=" + page + "&size=" + size;

    return request({
        url: GET_USER_VOTED_POLLS,
        method: HTTP_GET
    });
}