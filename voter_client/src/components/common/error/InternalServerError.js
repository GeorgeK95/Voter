import React, {Component} from 'react';
import './InternalServerError.css';

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