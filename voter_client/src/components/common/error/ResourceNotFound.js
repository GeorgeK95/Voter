import React, {Component} from 'react';
import './ResourceNotFound.css';
import {Link} from 'react-router-dom';
import {Button} from 'antd';

class ResourceNotFound extends Component {
    render() {
        return (
            <div className="page-not-found">
                <h1 className="title">404</h1>
                <div className="desc">Resource was not found.</div>
                {/*<Link to="/"><Button className="go-back-btn" type="primary" size="large">Go Back</Button></Link>*/}
            </div>
        );
    }
}

export default ResourceNotFound;