import React, {Component} from 'react';
import {castVote, getAllPolls, getUserCreatedPolls, getUserVotedPolls} from '../../util/Requester';
import Poll from './Poll';
import LoadingIndicator from '../common/indicator/Loading';
import {Button, Icon, notification} from 'antd';
import {
    ADMIN,
    APP_NAME,
    LOGIN_URL, PLEASE_LOGIN_TO_VOTE_MESSAGE,
    POLL_LIST_SIZE,
    SOMETHING_WENT_WRONG_MESSAGE, USER_CREATED_POLLS,
    USER_VOTED_POLLS
} from '../../util/webConstants';
import {withRouter} from 'react-router-dom';
import './PollList.css';

class PollList extends Component {
    constructor(props) {
        super(props);

        this.state = {
            polls: [],
            page: 0,
            size: 10,
            totalElements: 0,
            totalPages: 0,
            last: true,
            currentVotes: [],
            isLoading: false
        };

        this.loadPollList = this.loadPollList.bind(this);
        this.handleLoadMore = this.handleLoadMore.bind(this);
    }

    loadPollList(page = 0, size = POLL_LIST_SIZE) {
        let promise;
        if (this.props.username) {
            if (this.props.type === USER_CREATED_POLLS) {
                promise = getUserCreatedPolls(this.props.username, page, size);
            } else {
                if (this.props.type === USER_VOTED_POLLS) {
                    promise = getUserVotedPolls(this.props.username, page, size);
                }
            }
        } else {
            promise = getAllPolls(page, size);
        }

        if (!promise) {
            return;
        }

        this.setState({
            isLoading: true
        });

        promise
            .then(res => {
                const polls = this.state.polls.slice();
                const currentVotes = this.state.currentVotes.slice();

                this.setState({
                    polls: polls.concat(res.content),
                    page: res.page,
                    size: res.size,
                    totalElements: res.totalElements,
                    totalPages: res.totalPages,
                    last: res.last,
                    currentVotes: currentVotes.concat(Array(res.content.length).fill(null)),
                    isLoading: false
                })
            }).catch(err => {
            this.setState({
                isLoading: false
            })
        });

    }

    componentWillMount() {
        this.loadPollList();
    }

    componentWillReceiveProps(nextProps) {
        if (this.props.isAuthenticated !== nextProps.isAuthenticated) {
            // Reset State
            this.setState({
                polls: [],
                page: 0,
                size: 10,
                totalElements: 0,
                totalPages: 0,
                last: true,
                currentVotes: [],
                isLoading: false
            });

            this.loadPollList();
        }
    }

    handleLoadMore() {
        this.loadPollList(this.state.page + 1);
    }

    handleVoteChange(event, pollIndex) {
        const currentVotes = this.state.currentVotes.slice();
        currentVotes[pollIndex] = event.target.value;

        this.setState({
            currentVotes: currentVotes
        });
    }


    handleVoteSubmit(event, pollIndex) {
        event.preventDefault();
        if (!this.props.isAuthenticated) {
            this.props.history.push(LOGIN_URL);
            notification.info({
                message: APP_NAME,
                description: PLEASE_LOGIN_TO_VOTE_MESSAGE,
            });
            return;
        }

        const poll = this.state.polls[pollIndex];
        const selectedChoice = this.state.currentVotes[pollIndex];

        const voteData = {
            pollId: poll.id,
            choiceId: selectedChoice
        };

        castVote(voteData)
            .then(response => {
                const polls = this.state.polls.slice();
                polls[pollIndex] = response;
                this.setState({
                    polls: polls
                });
            }).catch(error => {
            if (error.status === 401) {
                this.props.handleLogout(LOGIN_URL, Error, PLEASE_LOGIN_TO_VOTE_MESSAGE);
            } else {
                notification.error({
                    message: APP_NAME,
                    description: error.message || SOMETHING_WENT_WRONG_MESSAGE
                });
            }
        });
    }

    render() {
        const pollViews = [];

        let isAdmin = false;
        if (this.props.currentUser) isAdmin = this.props.currentUser.role === ADMIN;

        this.state.polls.forEach((poll, pollIndex) => {
            pollViews.push(<Poll
                deleteEnabled={isAdmin}
                key={poll.id}
                poll={poll}
                currentVote={this.state.currentVotes[pollIndex]}
                handleVoteChange={(event) => this.handleVoteChange(event, pollIndex)}
                handleVoteSubmit={(event) => this.handleVoteSubmit(event, pollIndex)}/>)
        });

        return (
            <div className="polls-container">
                {pollViews}
                {
                    !this.state.isLoading && this.state.polls.length === 0 ? (
                        <div className="no-polls-found">
                            <span>No Polls Found.</span>
                        </div>
                    ) : null
                }
                {
                    !this.state.isLoading && !this.state.last ? (
                        <div className="load-more-polls">
                            <Button type="dashed" onClick={this.handleLoadMore} disabled={this.state.isLoading}>
                                <Icon type="plus"/> Load more
                            </Button>
                        </div>) : null
                }
                {
                    this.state.isLoading ?
                        <LoadingIndicator/> : null
                }
            </div>
        );
    }
}

export default withRouter(PollList);