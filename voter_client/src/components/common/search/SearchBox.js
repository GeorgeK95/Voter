import React, {Component} from "react";
import {Icon, Input, Button} from 'antd';

import './SearchBoxStyles.css'

class SearchBox extends Component {
    constructor(props) {
        super(props);

        this.state = {
            searchFor: ''
        };

        this.handleSearchBoxChange = this.handleSearchBoxChange.bind(this);
    }

    handleSearchBoxChange(value) {
        console.log(value)
    }

    render() {
        return (
            <div className="app-title search-form">
                <Input
                    size="large"
                    type="text"
                    placeholder="Search for tag.."
                    style={{fontSize: '16px'}}
                    value={this.state.searchFor}
                    onChange={this.handleSearchBoxChange}/>
            </div>
        )
    }
}

export default SearchBox;