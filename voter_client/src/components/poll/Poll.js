import React, {Component} from 'react';
import './Poll.css';
import {Avatar, Icon} from 'antd';
import {Link} from 'react-router-dom';
import {getAvatarColor} from '../../util/Colors';
import {formatDateTime} from '../../util/DateFormatter';

import {Radio, Button} from 'antd';
import {
    APP_NAME,
    DAYS_LEFT,
    HOURS_LEFT,
    LESS_THAN_SECOND_LEFT,
    MINUTES_LEFT, NOTIFICATION_BANNED_MESSAGE,
    SECONDS_LEFT,
    SPACE
} from "../../util/webConstants";
import {deletePoll} from "../../util/Requester";
import {notification} from "antd/lib/index";

const RadioGroup = Radio.Group;

class Poll extends Component {
    constructor(props) {
        super(props)

        this.state = {
            isDeleted: false
        };

        this.handleDelete = this.handleDelete.bind(this);
    }

    calculatePercentage = (choice) => {
        if (this.props.poll.totalVotes === 0) {
            return 0;
        }
        return (choice.voteCount * 100) / (this.props.poll.totalVotes);
    };

    handleDelete() {
        deletePoll(this.props.poll.id)
            .then(() => {
                this.setState({isDeleted: true});

                notification.success({
                    message: APP_NAME,
                    description: NOTIFICATION_BANNED_MESSAGE,
                });
            });
    }

    isSelected = (choice) => {
        return this.props.poll.selectedChoice === choice.id;
    }

    getWinningChoice = () => {
        return this.props.poll.choices.reduce((prevChoice, currentChoice) =>
                currentChoice.voteCount > prevChoice.voteCount ? currentChoice : prevChoice,
            {voteCount: -Infinity}
        );
    }

    getTimeRemaining = (poll) => {
        const expirationTime = new Date(poll.expirationDateTime).getTime();
        const currentTime = new Date().getTime();

        let msLeft = expirationTime - currentTime;
        let seconds = Math.floor((msLeft / 1000) % 60);
        let minutes = Math.floor((msLeft / 1000 / 60) % 60);
        let hours = Math.floor((msLeft / (1000 * 60 * 60)) % 24);
        let days = Math.floor(msLeft / (1000 * 60 * 60 * 24));

        let timeRemaining;

        if (days > 0) {
            timeRemaining = days + DAYS_LEFT;
        } else if (hours > 0) {
            timeRemaining = hours + HOURS_LEFT;
        } else if (minutes > 0) {
            timeRemaining = minutes + MINUTES_LEFT;
        } else if (seconds > 0) {
            timeRemaining = seconds + SECONDS_LEFT;
        } else {
            timeRemaining = LESS_THAN_SECOND_LEFT;
        }

        return timeRemaining;
    };

    render() {
        const pollChoices = [];
        if (this.props.poll.selectedChoice || this.props.poll.expired) {
            const winningChoice = this.props.poll.expired ? this.getWinningChoice() : null;

            this.props.poll.choices.forEach(choice => {
                pollChoices.push(<CompletedOrVotedPollChoice
                    key={choice.id}
                    choice={choice}
                    isWinner={winningChoice && choice.id === winningChoice.id}
                    isSelected={this.isSelected(choice)}
                    percentVote={this.calculatePercentage(choice)}
                />);
            });
        } else {
            this.props.poll.choices.forEach(choice => {
                pollChoices.push(<Radio className="poll-choice-radio" key={choice.id}
                                        value={choice.id}>{choice.text}</Radio>)
            })
        }

        let deleteSpan;

        if (this.state.isDeleted) deleteSpan =
            <Icon type="delete" style={{fontSize: 20}} className='right-aligned' styles={{disabled: true}}/>;
        else if (this.props.deleteEnabled) deleteSpan =
            <Icon type="delete" style={{fontSize: 20}} className='right-aligned' onClick={this.handleDelete}/>;

        let tags = '';
        if (this.props.poll && this.props.poll.tags) {
            this.props.poll.tags.forEach((t, i) => {
                if (i === 3) return;
                tags += t.content + SPACE;
            })
        }

        let pollDivClassName = 'poll-content';
        if (this.state.isDeleted) pollDivClassName = 'hide';

        return (
            <div className={pollDivClassName}>
                <div className="poll-header">
                    <div className="poll-creator-info">
                        <Link className="creator-link" to={`/users/${this.props.poll.createdBy.username}`}>
                            <Avatar className="poll-creator-avatar"
                                    style={{backgroundColor: getAvatarColor(this.props.poll.createdBy.name)}}>
                                {this.props.poll.createdBy.name[0].toUpperCase()}
                            </Avatar>
                            <span className="poll-creator-name">
                                {this.props.poll.createdBy.name}
                            </span>
                            <span className="poll-creator-username">
                                @{this.props.poll.createdBy.username}
                            </span>
                            <span className="poll-creation-date">
                                {formatDateTime(this.props.poll.creationDateTime)}
                            </span>
                        </Link>
                    </div>
                    <div className="poll-question">
                        {this.props.poll.question}
                    </div>
                </div>
                <div className="poll-choices">
                    <RadioGroup
                        className="poll-choice-radio-group"
                        onChange={this.props.handleVoteChange}
                        value={this.props.currentVote}>
                        {pollChoices}
                    </RadioGroup>
                </div>
                <div className="poll-footer">
                    {
                        !(this.props.poll.selectedChoice || this.props.poll.expired) ?
                            (<Button className="vote-button" disabled={!this.props.currentVote}
                                     onClick={this.props.handleVoteSubmit}>Vote</Button>) : null
                    }
                    <span className="total-votes">{this.props.poll.totalVotes} votes</span>
                    <span className="separator">•</span>
                    <span className="time-left">
                        {
                            this.props.poll.expired ? "Final results" :
                                this.getTimeRemaining(this.props.poll)
                        }
                    </span>

                    <span className="separator">•</span>

                    {tags}

                    {deleteSpan}

                </div>
            </div>
        );
    }
}

function CompletedOrVotedPollChoice(props) {
    return (
        <div className="cv-poll-choice">
            <span className="cv-poll-choice-details">
                <span className="cv-choice-percentage">
                    {Math.round(props.percentVote * 100) / 100}%
                </span>
                <span className="cv-choice-text">
                    {props.choice.text}
                </span>
                {
                    props.isSelected ? (
                        <Icon
                            className="selected-choice-icon"
                            type="check-circle-o"
                        />) : null
                }
            </span>
            <span className={props.isWinner ? 'cv-choice-percent-chart winner' : 'cv-choice-percent-chart'}
                  style={{width: props.percentVote + '%'}}>
            </span>
        </div>
    );
}


export default Poll;