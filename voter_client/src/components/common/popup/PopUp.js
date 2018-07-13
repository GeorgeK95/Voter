import React, {Component} from 'react';
import {Popover} from 'antd';

class PopUp extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                {this.props.tags.map((tag, i) => <span key={i}>#{tag.content}</span>)}
            </div>
        )
    }
}

export default PopUp;