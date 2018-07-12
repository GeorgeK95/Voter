import React, {Component} from 'react';
import {login} from '../../util/Requester';
import './Login.css';
import {Link} from 'react-router-dom';
import {
    ACCESS_TOKEN,
    APP_NAME,
    INVALID_CREDENTIALS_MESSAGE,
    PLEASE_INPUT_YOUR_PASSWORD_MESSAGE,
    PLEASE_INPUT_YOUR_USERNAME_EMAIL_MESSAGE,
    SOMETHING_WENT_WRONG_MESSAGE
} from '../../util/webConstants';

import {Form, Input, Button, Icon, notification} from 'antd';

const FormItem = Form.Item;

class Login extends Component {
    render() {
        const AntWrappedLoginForm = Form.create()(LoginForm)
        return (
            <div className="login-container">
                <h1 className="page-title">Login</h1>
                <div className="login-content">
                    <AntWrappedLoginForm onLogin={this.props.onLogin}/>
                </div>
            </div>
        );
    }
}

class LoginForm extends Component {
    constructor(props) {
        super(props);

        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event) {
        event.preventDefault();

        this.props.form.validateFields((err, values) => {
            if (!err) {
                const loginRequest = Object.assign({}, values);
                login(loginRequest)
                    .then(response => {
                        localStorage.setItem(ACCESS_TOKEN, response.accessToken);
                        this.props.onLogin();
                    }).catch(error => {
                    if (error.status === 401) {

                        notification.error({
                            message: APP_NAME,
                            description: INVALID_CREDENTIALS_MESSAGE
                        });
                    } else {
                        notification.error({
                            message: APP_NAME,
                            description: error.message || SOMETHING_WENT_WRONG_MESSAGE
                        });
                    }
                });
            }
        });
    }

    render() {
        const {getFieldDecorator} = this.props.form;
        return (
            <Form onSubmit={this.handleSubmit} className="login-form">
                <FormItem>
                    {getFieldDecorator('usernameOrEmail', {
                        rules: [{required: true, message: PLEASE_INPUT_YOUR_USERNAME_EMAIL_MESSAGE}],
                    })(
                        <Input
                            prefix={<Icon type="user"/>}
                            size="large"
                            name="usernameOrEmail"
                            placeholder="Username or Email"/>
                    )}
                </FormItem>
                <FormItem>
                    {getFieldDecorator('password', {
                        rules: [{required: true, message: PLEASE_INPUT_YOUR_PASSWORD_MESSAGE}],
                    })(
                        <Input
                            prefix={<Icon type="lock"/>}
                            size="large"
                            name="password"
                            type="password"
                            placeholder="Password"/>
                    )}
                </FormItem>
                <FormItem>
                    <Button type="primary" htmlType="submit" size="large" className="login-form-button">Login</Button>
                    Or <Link to="/signup">Click here to sign up.</Link>
                </FormItem>
            </Form>
        );
    }
}

export default Login;