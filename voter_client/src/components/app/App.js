import React, {Component} from 'react';
import './App.css';
import {
    Route,
    withRouter,
    Switch
} from 'react-router-dom';

import {getCurrentUser, getPollsByTags} from '../../util/Requester';
import {
    ACCESS_TOKEN,
    APP_NAME, GLAD_TO_SEE_YOU_AGAIN_MESSAGE,
    NOTIFICATION_DURATION,
    NOTIFICATION_TOP, SLASH_URL,
    SUCCESS, SUCCESSFULLY_SIGNED_OUT_MESSAGE,
    TOP_RIGHT
} from '../../util/webConstants';

import PollList from '../poll/PollList';
import NewPoll from '../poll/NewPoll';
import Login from '../user/login/Login';
import Signup from '../user/signup/Signup';
import Profile from '../user/profile/Profile';
import AppHeader from '../common/header/HeaderComponent';
import ResourceNotFound from '../common/error/ResourceNotFound';
import LoadingIndicator from '../common/indicator/Loading';
import ProtectedRoute from '../common/router/ProtectedRoute';

import {Layout, notification} from 'antd';

const {Content} = Layout;


class App extends Component {
    constructor(props) {
        super(props);

        this.state = {
            currentUser: null,
            isAuthenticated: false,
            foundPollsByTags: [],
            isLoading: false
        }

        this.handleLogout = this.handleLogout.bind(this);
        this.loadCurrentUser = this.loadCurrentUser.bind(this);
        this.handleLogin = this.handleLogin.bind(this);
        this.handleSearchBase = this.handleSearchBase.bind(this);

        notification.config({
            placement: TOP_RIGHT,
            top: NOTIFICATION_TOP,
            duration: NOTIFICATION_DURATION,
        });
    }

    loadCurrentUser() {
        this.setState({
            isLoading: true
        });
        getCurrentUser()
            .then(response => {
                this.setState({
                    currentUser: response,
                    isAuthenticated: true,
                    isLoading: false
                });
            }).catch(error => {
            this.setState({
                isLoading: false
            });
        });
    }

    componentWillMount() {
        this.loadCurrentUser();
    }

    handleSearchBase(targetTags) {
        getPollsByTags(targetTags)
            .then(res => {
                this.setState({
                    foundPollsByTags: res
                });
            })
            .catch(err => {
            });

    }

    handleLogout(redirectTo = SLASH_URL, notificationType = SUCCESS, description = SUCCESSFULLY_SIGNED_OUT_MESSAGE) {
        localStorage.removeItem(ACCESS_TOKEN);

        this.setState({
            currentUser: null,
            isAuthenticated: false
        });

        this.props.history.push(redirectTo);

        notification[notificationType]({
            message: APP_NAME,
            description: description,
        });
    }

    handleLogin() {
        notification.success({
            message: APP_NAME,
            description: GLAD_TO_SEE_YOU_AGAIN_MESSAGE,
        });

        this.loadCurrentUser();
        this.props.history.push(SLASH_URL);
    }

    render() {
        if (this.state.isLoading) {
            return <LoadingIndicator/>
        }

        let homeRoute =
            <Route exact path="/"
                   render={(props) => <PollList
                       isAuthenticated={this.state.isAuthenticated}
                       currentUser={this.state.currentUser}
                       handleLogout={this.handleLogout} {...props} />}>
            </Route>;

        if (this.state.foundPollsByTags.length > 0) {
            homeRoute =
                <Route exact path="/"
                       render={(props) => <PollList
                           foundPollsByTags={this.state.foundPollsByTags}
                           isAuthenticated={this.state.isAuthenticated}
                           currentUser={this.state.currentUser}
                           handleLogout={this.handleLogout} {...props} />}>
                </Route>;
        }

        return (
            <Layout className="app-container">
                <AppHeader isAuthenticated={this.state.isAuthenticated}
                           currentUser={this.state.currentUser}
                           onLogout={this.handleLogout}
                           onSearch={this.handleSearchBase}/>

                <Content className="app-content">
                    <div className="container">
                        <Switch>

                            {homeRoute}

                            <Route path="/login"
                                   render={(props) => <Login onLogin={this.handleLogin} {...props} />}></Route>
                            <Route path="/signup" component={Signup}></Route>
                            <Route path="/users/:username"
                                   render={(props) => <Profile isAuthenticated={this.state.isAuthenticated}
                                                               currentUser={this.state.currentUser} {...props}  />}>
                            </Route>
                            <ProtectedRoute authenticated={this.state.isAuthenticated} path="/poll/new"
                                            component={NewPoll} handleLogout={this.handleLogout}></ProtectedRoute>
                            <Route component={ResourceNotFound}></Route>
                        </Switch>
                    </div>
                </Content>
            </Layout>
        );
    }
}

export default withRouter(App);
