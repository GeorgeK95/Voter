import React, {Component} from 'react';
import PollList from '../../poll/PollList';
import {banUser, getUserProfile} from '../../../util/Requester';
import {Button, Avatar, Tabs} from 'antd';
import {getAvatarColor} from '../../../util/Colors';
import {formatDate} from '../../../util/DateFormatter';
import LoadingIndicator from '../../common/indicator/Loading';
import './Profile.css';
import ResourceNotFound from '../../common/error/ResourceNotFound';
import ServerError from '../../common/error/InternalServerError';
import {ADMIN, APP_NAME, USER_BANNED_MESSAGE} from "../../../util/webConstants";
import {notification} from "antd/lib/index";

const TabPane = Tabs.TabPane;

class Profile extends Component {
    constructor(props) {
        super(props);

        this.state = {
            user: null,
            isBanned: false,
            isLoading: false
        }

        this.loadUserProfile = this.loadUserProfile.bind(this);
        this.handleDelete = this.handleDelete.bind(this);
    }

    loadUserProfile(username) {
        this.setState({
            isLoading: true
        });

        getUserProfile(username)
            .then(response => {
                this.setState({
                    user: response,
                    isLoading: false
                });
            }).catch(error => {
            if (error.status === 404) {
                this.setState({
                    notFound: true,
                    isLoading: false
                });
            } else {
                this.setState({
                    serverError: true,
                    isLoading: false
                });
            }
        });
    }

    handleDelete() {
        banUser(this.state.user.id)
            .then(res => {
                if (res === true) this.setState({isBanned: true});

                notification.success({
                    message: APP_NAME,
                    description: USER_BANNED_MESSAGE,
                });
            });
    }

    componentDidMount() {
        const username = this.props.match.params.username;
        this.loadUserProfile(username);
    }

    componentWillReceiveProps(nextProps) {
        if (this.props.match.params.username !== nextProps.match.params.username) {
            this.loadUserProfile(nextProps.match.params.username);
        }
    }

    render() {
        if (this.state.isLoading) {
            return <LoadingIndicator/>;
        }

        if (this.state.notFound) {
            return <ResourceNotFound/>;
        }

        if (this.state.serverError) {
            return <ServerError/>;
        }

        const tabBarStyle = {
            textAlign: 'center'
        };

        let deleteUserDiv;

        if (this.state.isBanned || (this.state.user && this.state.user.banned)) {
            deleteUserDiv =
                <div className="delete-user-btn">
                    <Button type="danger" style={{disabled: true}}>Banned</Button>
                </div>
        } else if (this.props.currentUser && this.props.currentUser.role === ADMIN &&
            this.state.user && this.state.user.role !== ADMIN) {
            deleteUserDiv =
                <div className="delete-user-btn">
                    <Button type="danger" onClick={this.handleDelete}>Delete</Button>
                </div>
        }

        return (
            <div className="profile">
                {
                    this.state.user ? (
                        <div className="user-profile">
                            <div className="user-details">
                                <div className="user-avatar">
                                    <Avatar className="user-avatar-circle"
                                            style={{backgroundColor: getAvatarColor(this.state.user.name)}}>
                                        {this.state.user.name[0].toUpperCase()}
                                    </Avatar>
                                </div>
                                <div className="user-summary">
                                    <div className="full-name">{this.state.user.name}</div>
                                    <div className="username">@{this.state.user.username}</div>
                                    <div className="user-joined">
                                        Joined {formatDate(this.state.user.joinedAt)}
                                    </div>

                                    {deleteUserDiv}

                                </div>
                            </div>
                            <div className="user-poll-details">
                                <Tabs defaultActiveKey="1"
                                      animated={false}
                                      tabBarStyle={tabBarStyle}
                                      size="large"
                                      className="profile-tabs">
                                    <TabPane tab={`${this.state.user.pollCount} Polls`} key="1">
                                        <PollList username={this.props.match.params.username}
                                                  type="USER_CREATED_POLLS"/>
                                    </TabPane>
                                    <TabPane tab={`${this.state.user.voteCount} Votes`} key="2">
                                        <PollList username={this.props.match.params.username} type="USER_VOTED_POLLS"/>
                                    </TabPane>
                                </Tabs>
                            </div>
                        </div>
                    ) : null
                }
            </div>
        );
    }
}

export default Profile;