import React, {Component} from 'react';
import './InternalServerError.css';
import {Link} from 'react-router-dom';
import {Button} from 'antd';

class NotFound extends Component {
    render() {
        return (
            <div className="server-error-page">
                <h1 className="server-error-title">500</h1>
                <div className="server-error-desc">Oops! Something went wrong at our Server.</div>
            </div>
        );
    }
}

export default NotFound;