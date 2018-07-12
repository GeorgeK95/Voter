import React, {Component} from 'react';
import {signup, checkUsernameAvailability, checkEmailAvailability} from '../../util/Requester';
import './Signup.css';
import {Link} from 'react-router-dom';
import {
    NAME_MIN_LENGTH,
    NAME_MAX_LENGTH,
    USERNAME_MIN_LENGTH,
    USERNAME_MAX_LENGTH,
    EMAIL_MAX_LENGTH,
    PASSWORD_MIN_LENGTH,
    PASSWORD_MAX_LENGTH,
    SUCCESSFULLY_REGISTERED_MESSAGE,
    SOMETHING_WENT_WRONG_MESSAGE,
    ERROR,
    SUCCESS,
    NAME_TOO_LONG_MESSAGE,
    NAME_TOO_SHORT_MESSAGE,
    PASSWORD_TOO_SHORT_MESSAGE,
    PASSWORD_TOO_LONG_MESSAGE,
    APP_NAME
} from '../../util/webConstants';

import {Form, Input, Button, notification} from 'antd';

const FormItem = Form.Item;

class Signup extends Component {
    constructor(props) {
        super(props);

        this.state = {
            name: {
                value: ''
            },
            username: {
                value: ''
            },
            email: {
                value: ''
            },
            password: {
                value: ''
            }
        }

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.validateUsernameAvailability = this.validateUsernameAvailability.bind(this);
        this.validateEmailAvailability = this.validateEmailAvailability.bind(this);
        this.isFormInvalid = this.isFormInvalid.bind(this);
    }

    handleInputChange(event, validationFun) {
        const target = event.target;
        const inputName = target.name;
        const inputValue = target.value;

        this.setState({
            [inputName]: {
                value: inputValue,
                ...validationFun(inputValue)
            }
        });
    }

    handleSubmit(event) {
        event.preventDefault();

        const signupRequest = {
            name: this.state.name.value,
            email: this.state.email.value,
            username: this.state.username.value,
            password: this.state.password.value
        };
        signup(signupRequest)
            .then(response => {
                notification.success({
                    message: APP_NAME,
                    description: SUCCESSFULLY_REGISTERED_MESSAGE,
                });
                this.props.history.push("/login");
            }).catch(error => {
            notification.error({
                message: APP_NAME,
                description: error.message || SOMETHING_WENT_WRONG_MESSAGE
            });
        });
    }

    isFormInvalid() {
        return !(this.state.name.validateStatus === SUCCESS &&
            this.state.username.validateStatus === SUCCESS &&
            this.state.email.validateStatus === SUCCESS &&
            this.state.password.validateStatus === SUCCESS
        );
    }

    render() {
        return (
            <div className="signup-container">
                <h1 className="page-title">Sign Up</h1>
                <div className="signup-content">
                    <Form onSubmit={this.handleSubmit} className="signup-form">
                        <FormItem
                            label="Full Name"
                            validateStatus={this.state.name.validateStatus}
                            help={this.state.name.errorMsg}>
                            <Input
                                size="large"
                                name="name"
                                autoComplete="off"
                                placeholder="Your full name"
                                value={this.state.name.value}
                                onChange={(event) => this.handleInputChange(event, this.validateName)}/>
                        </FormItem>
                        <FormItem label="Username"
                                  hasFeedback
                                  validateStatus={this.state.username.validateStatus}
                                  help={this.state.username.errorMsg}>
                            <Input
                                size="large"
                                name="username"
                                autoComplete="off"
                                placeholder="A unique username"
                                value={this.state.username.value}
                                onBlur={this.validateUsernameAvailability}
                                onChange={(event) => this.handleInputChange(event, this.validateUsername)}/>
                        </FormItem>
                        <FormItem
                            label="Email"
                            hasFeedback
                            validateStatus={this.state.email.validateStatus}
                            help={this.state.email.errorMsg}>
                            <Input
                                size="large"
                                name="email"
                                type="email"
                                autoComplete="off"
                                placeholder="Your email"
                                value={this.state.email.value}
                                onBlur={this.validateEmailAvailability}
                                onChange={(event) => this.handleInputChange(event, this.validateEmail)}/>
                        </FormItem>
                        <FormItem
                            label="Password"
                            validateStatus={this.state.password.validateStatus}
                            help={this.state.password.errorMsg}>
                            <Input
                                size="large"
                                name="password"
                                type="password"
                                autoComplete="off"
                                placeholder="A password between 6 to 20 characters"
                                value={this.state.password.value}
                                onChange={(event) => this.handleInputChange(event, this.validatePassword)}/>
                        </FormItem>
                        <FormItem>
                            <Button type="primary"
                                    htmlType="submit"
                                    size="large"
                                    className="signup-form-button"
                                    disabled={this.isFormInvalid()}>Sign up</Button>
                            Already registed? <Link to="/login">Login now!</Link>
                        </FormItem>
                    </Form>
                </div>
            </div>
        );
    }

    // Validation Functions

    validateName = (name) => {
        if (name.length < NAME_MIN_LENGTH) {
            return {
                validateStatus: ERROR,
                errorMsg: NAME_TOO_SHORT_MESSAGE
            }
        } else if (name.length > NAME_MAX_LENGTH) {
            return {
                validationStatus: ERROR,
                errorMsg: NAME_TOO_LONG_MESSAGE
            }
        } else {
            return {
                validateStatus: SUCCESS,
                errorMsg: null,
            };
        }
    }

    validateEmail = (email) => {
        if (!email) {
            return {
                validateStatus: ERROR,
                errorMsg: EMTPY_EMAIL_MESSAGE
            }
        }

        if (!EMAIL_REGEX.test(email)) {
            return {
                validateStatus: ERROR,
                errorMsg: NOT_VALID_EMAIL_MESSAGE
            }
        }

        if (email.length > EMAIL_MAX_LENGTH) {
            return {
                validateStatus: ERROR,
                errorMsg: EMAIL_TOO_LONG_MESSAGE
            }
        }

        return {
            validateStatus: null,
            errorMsg: null
        }
    }

    validateUsername = (username) => {
        if (username.length < USERNAME_MIN_LENGTH) {

            return {
                validateStatus: ERROR,
                errorMsg: USERNAME_TOO_sHORT_MESSAGE
            }
        } else if (username.length > USERNAME_MAX_LENGTH) {
            return {
                validationStatus: ERROR,
                errorMsg: USERNAME_TOO_LONG_MESSAGE
            }
        } else {
            return {
                validateStatus: null,
                errorMsg: null
            }
        }
    }

    validateUsernameAvailability() {
        // First check for client side errors in username
        const usernameValue = this.state.username.value;
        const usernameValidation = this.validateUsername(usernameValue);

        if (usernameValidation.validateStatus === ERROR) {
            this.setState({
                username: {
                    value: usernameValue,
                    ...usernameValidation
                }
            });
            return;
        }

        this.setState({
            username: {
                value: usernameValue,
                validateStatus: VALIDATING,
                errorMsg: null
            }
        });

        checkUsernameAvailability(usernameValue)
            .then(response => {
                if (response.available) {
                    this.setState({
                        username: {
                            value: usernameValue,
                            validateStatus: SUCCESS,
                            errorMsg: null
                        }
                    });
                } else {

                    this.setState({
                        username: {
                            value: usernameValue,
                            validateStatus: ERROR,
                            errorMsg: USERNAME_ALREADY_TAKEN_MESSAGE
                        }
                    });
                }
            }).catch(error => {
            // Marking validateStatus as success, Form will be recchecked at server
            this.setState({
                username: {
                    value: usernameValue,
                    validateStatus: SUCCESS,
                    errorMsg: null
                }
            });
        });
    }

    validateEmailAvailability() {
        // First check for client side errors in email
        const emailValue = this.state.email.value;
        const emailValidation = this.validateEmail(emailValue);

        if (emailValidation.validateStatus === ERROR) {
            this.setState({
                email: {
                    value: emailValue,
                    ...emailValidation
                }
            });
            return;
        }

        this.setState({
            email: {
                value: emailValue,
                validateStatus: VALIDATING,
                errorMsg: null
            }
        });

        checkEmailAvailability(emailValue)
            .then(response => {
                if (response.available) {
                    this.setState({
                        email: {
                            value: emailValue,
                            validateStatus: SUCCESS,
                            errorMsg: null
                        }
                    });
                } else {
                    this.setState({
                        email: {
                            value: emailValue,
                            validateStatus: ERROR,
                            errorMsg: EMAIL_ALREADY_REGISTERED_MESSAGE
                        }
                    });
                }
            }).catch(error => {
            // Marking validateStatus as success, Form will be recchecked at server
            this.setState({
                email: {
                    value: emailValue,
                    validateStatus: SUCCESS,
                    errorMsg: null
                }
            });
        });
    }

    validatePassword = (password) => {
        if (password.length < PASSWORD_MIN_LENGTH) {

            return {
                validateStatus: ERROR,
                errorMsg: PASSWORD_TOO_SHORT_MESSAGE
            }
        } else if (password.length > PASSWORD_MAX_LENGTH) {
            return {
                validationStatus: ERROR,
                errorMsg: PASSWORD_TOO_LONG_MESSAGE
            }
        } else {
            return {
                validateStatus: SUCCESS,
                errorMsg: null,
            };
        }
    }

}

export default Signup;