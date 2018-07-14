import React, {Component} from 'react';
import {createPoll, getContextTags} from '../../util/Requester';
import {
    MAX_CHOICES,
    POLL_QUESTION_MAX_LENGTH,
    POLL_CHOICE_MAX_LENGTH,
    SOMETHING_WENT_WRONG_MESSAGE,
    SUCCESS,
    ERROR,
    APP_NAME,
    PLEASE_LOGIN_TO_CREATE_POLL_MESSAGE,
    LOGIN_URL,
    SLASH_URL,
    PLEASE_ENTER_YOUR_QUESTION_MESSAGE,
    QUESTION_TOO_LONG_MESSAGE,
    PLEASE_ENTER_CHOICE_MESSAGE,
    CHOICE_TOO_LONG_MESSAGE,
    HASHTAG, SPACE
} from '../../util/webConstants';
import './NewPoll.css';
import {Form, AutoComplete, Input, Button, Icon, Select, Col, notification} from 'antd';
import PopUp from "../common/popup/PopUp";

const Option = Select.Option;
const FormItem = Form.Item;
const {TextArea} = Input

class NewPoll extends Component {
    constructor(props) {
        super(props);

        this.state = {
            question: {
                text: ''
            },
            choices: [{
                text: ''
            }, {
                text: ''
            }],
            pollLength: {
                days: 1,
                hours: 0
            },
            value: '',
            tagsContent: []
        };

        this.addChoice = this.addChoice.bind(this);
        this.removeChoice = this.removeChoice.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleQuestionChange = this.handleQuestionChange.bind(this);
        this.handleTagSearch = this.handleTagSearch.bind(this);
        this.handleTagChange = this.handleTagChange.bind(this);
        this.handleChoiceChange = this.handleChoiceChange.bind(this);
        this.handlePollDaysChange = this.handlePollDaysChange.bind(this);
        this.handlePollHoursChange = this.handlePollHoursChange.bind(this);
        this.isFormInvalid = this.isFormInvalid.bind(this);
    }

    addChoice(event) {
        const choices = this.state.choices.slice();
        this.setState({
            choices: choices.concat([{
                text: ''
            }])
        });
    }

    removeChoice(choiceNumber) {
        const choices = this.state.choices.slice();
        this.setState({
            choices: [...choices.slice(0, choiceNumber), ...choices.slice(choiceNumber + 1)]
        });
    }

    handleSubmit(event) {
        event.preventDefault();

        console.log(this.state.value)

        let splittedTags = this.state.value.split(SPACE);

        const pollData = {
            question: this.state.question.text,
            choices: this.state.choices.map(choice => {
                return {text: choice.text}
            }),
            tags: splittedTags.map(tag => {
                return {text: tag}
            }),
            pollLength: this.state.pollLength
        };

        createPoll(pollData)
            .then(response => {
                this.props.history.push(SLASH_URL);
            }).catch(error => {
            if (error.status === 401) {
                this.props.handleLogout(LOGIN_URL, ERROR, PLEASE_LOGIN_TO_CREATE_POLL_MESSAGE);
            } else {
                notification.error({
                    message: APP_NAME,
                    description: error.message || SOMETHING_WENT_WRONG_MESSAGE
                });
            }
        });
    }

    validateQuestion = (questionText) => {
        if (questionText.length === 0) {
            return {
                validateStatus: ERROR,
                errorMsg: PLEASE_ENTER_YOUR_QUESTION_MESSAGE
            }
        } else if (questionText.length > POLL_QUESTION_MAX_LENGTH) {
            return {
                validateStatus: ERROR,
                errorMsg: QUESTION_TOO_LONG_MESSAGE
            }
        } else {
            return {
                validateStatus: SUCCESS,
                errorMsg: null
            }
        }
    }

    handleQuestionChange(event) {
        const value = event.target.value;
        this.setState({
            question: {
                text: value,
                ...this.validateQuestion(value)
            }
        });
    }

    handleTagChange = (value) => {
        this.setState({value});
    };

    handleTagSearch(tag) {
        /*if (!tag.includes(HASHTAG) || tag.includes(' ') || tag.includes('\n')) {
            tag = HASHTAG;
            return;
        }*/

        let lastTag = tag.substring(tag.lastIndexOf(SPACE) + 1);

        getContextTags(lastTag)
            .then(res => {
                this.setState(() => {
                    return {
                        tagsContent: res.map((tagObj) => {
                            return tagObj.content;
                        }),
                    }
                });
            })
            .catch(err => {
            });
    }

    validateChoice = (choiceText) => {
        if (choiceText.length === 0) {
            return {
                validateStatus: ERROR,
                errorMsg: PLEASE_ENTER_CHOICE_MESSAGE
            }
        } else if (choiceText.length > POLL_CHOICE_MAX_LENGTH) {
            return {
                validateStatus: ERROR,
                errorMsg: CHOICE_TOO_LONG_MESSAGE
            }
        } else {
            return {
                validateStatus: SUCCESS,
                errorMsg: null
            }
        }
    }

    handleChoiceChange(event, index) {
        const choices = this.state.choices.slice();
        const value = event.target.value;

        choices[index] = {
            text: value,
            ...this.validateChoice(value)
        }

        this.setState({choices: choices});
    }


    handlePollDaysChange(value) {
        const pollLength = Object.assign(this.state.pollLength, {days: value});
        this.setState({pollLength: pollLength});
    }

    handlePollHoursChange(value) {
        const pollLength = Object.assign(this.state.pollLength, {hours: value});
        this.setState({pollLength: pollLength});
    }

    isFormInvalid() {
        if (this.state.question.validateStatus !== SUCCESS) return true;

        for (let i = 0; i < this.state.choices.length; i++) {
            const choice = this.state.choices[i];
            if (choice.validateStatus !== SUCCESS) return true;
        }
    }

    render() {
        const choiceViews = [];

        this.state.choices.forEach((choice, index) => {
            choiceViews.push(<PollChoice key={index} choice={choice} choiceNumber={index}
                                         removeChoice={this.removeChoice}
                                         handleChoiceChange={this.handleChoiceChange}/>);
        });

        /*let popUp;
        if (this.state.tagsContent.length !== 0) popUp = <PopUp tags={this.state.tagsContent}/>;*/

        return (
            <div className="new-poll-container">
                <h1 className="page-title">Create Poll</h1>
                <div className="new-poll-content">
                    <Form onSubmit={this.handleSubmit} className="create-poll-form">
                        <FormItem validateStatus={this.state.question.validateStatus}
                                  help={this.state.question.errorMsg} className="poll-form-row">
                        <TextArea
                            placeholder="Enter your question"
                            style={{fontSize: '16px'}}
                            autosize={{minRows: 3, maxRows: 6}}
                            name="question"
                            value={this.state.question.text}
                            onChange={this.handleQuestionChange}/>
                        </FormItem>

                        {/*{popUp}*/}

                        <FormItem>
                            <AutoComplete
                                dataSource={this.state.tagsContent}
                                onSearch={this.handleTagSearch}
                                onChange={this.handleTagChange}
                                placeholder="Enter tags..."
                            />
                            {/*<Input
                                 placeholder="#tag#tag#tag"
                                 style={{fontSize: '16px'}}
                                 // autosize={{minRows: 3, maxRows: 6}}
                                 // name="question"
                                 value={this.state.tags}
                                 onChange={this.handleTagChange}/>*/}
                        </FormItem>

                        {choiceViews}

                        <FormItem className="poll-form-row">
                            <Button type="dashed" onClick={this.addChoice}
                                    disabled={this.state.choices.length === MAX_CHOICES}>
                                <Icon type="plus"/> Add a choice
                            </Button>
                        </FormItem>
                        <FormItem className="poll-form-row">
                            <Col xs={24} sm={4}>
                                Poll length:
                            </Col>
                            <Col xs={24} sm={20}>    
                                <span style={{marginRight: '18px'}}>
                                    <Select
                                        name="days"
                                        defaultValue="1"
                                        onChange={this.handlePollDaysChange}
                                        value={this.state.pollLength.days}
                                        style={{width: 60}}>
                                        {
                                            Array.from(Array(8).keys()).map(i =>
                                                <Option key={i}>{i}</Option>
                                            )
                                        }
                                    </Select> &nbsp;Days
                                </span>
                                <span>
                                    <Select
                                        name="hours"
                                        defaultValue="0"
                                        onChange={this.handlePollHoursChange}
                                        value={this.state.pollLength.hours}
                                        style={{width: 60}}>
                                        {
                                            Array.from(Array(24).keys()).map(i =>
                                                <Option key={i}>{i}</Option>
                                            )
                                        }
                                    </Select> &nbsp;Hours
                                </span>
                            </Col>
                        </FormItem>
                        <FormItem className="poll-form-row">
                            <Button type="primary"
                                    htmlType="submit"
                                    size="large"
                                    disabled={this.isFormInvalid()}
                                    className="create-poll-form-button">Create Poll</Button>
                        </FormItem>
                    </Form>
                </div>
            </div>
        );
    }
}

function PollChoice(props) {
    return (
        <FormItem validateStatus={props.choice.validateStatus}
                  help={props.choice.errorMsg} className="poll-form-row">
            <Input
                placeholder={'Choice ' + (props.choiceNumber + 1)}
                size="large"
                value={props.choice.text}
                className={props.choiceNumber > 1 ? "optional-choice" : null}
                onChange={(event) => props.handleChoiceChange(event, props.choiceNumber)}/>

            {
                props.choiceNumber > 1 ? (
                    <Icon
                        className="dynamic-delete-button"
                        type="close"
                        disabled={props.choiceNumber <= 1}
                        onClick={() => props.removeChoice(props.choiceNumber)}
                    />) : null
            }
        </FormItem>
    );
}

export default NewPoll;