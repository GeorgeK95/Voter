import React, {Component} from 'react';
import './ResourceNotFound.css';

class ResourceNotFound extends Component {
    render() {
        return (
            <div className="page-not-found">
                <h1 className="title">404</h1>
                <div className="desc">Resource was not found.</div>
            </div>
        );
    }
}

export default ResourceNotFound;