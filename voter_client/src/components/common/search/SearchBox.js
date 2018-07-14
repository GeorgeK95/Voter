import React, {Component} from 'react';
import {AutoComplete, Button} from 'antd';
import {getContextTags} from "../../../util/Requester";
import {SPACE} from "../../../util/webConstants";

class SearchBox extends Component {
    constructor(props) {
        super(props);

        this.state = {
            value: '',
            dataSource: []
        };

        this.handleSearchProcess = this.handleSearchProcess.bind(this);
    }

    handleSearch = (value) => {
        let wantedTags = value.trim();
        let wantedTagsAsArrayBySpace = wantedTags.split(SPACE);

        if (wantedTags === '') return;

        let lastTag = wantedTagsAsArrayBySpace[wantedTagsAsArrayBySpace.length - 1];

        getContextTags(lastTag)
            .then(res => {
                this.setState(() => {
                    return {
                        dataSource: res.map((tagObj) => {
                            return tagObj.content;
                        }),
                    }
                });
            })
            .catch(err => {
            });

    };

    handleChange = (value) => {
        this.setState({value});
    };

    handleSearchProcess = (e) => {
        let targetTags = this.state.value;

        this.props.onSearchButtonClicked(targetTags);

        this.setState({value: ''});
    };

    render() {
        const {dataSource} = this.state;

        return (
            <div className="app-menu search-form">
                <AutoComplete
                    value={this.state.value}
                    dataSource={dataSource}
                    onSearch={this.handleSearch}
                    onChange={this.handleChange}
                    placeholder="Search for tags..."
                />
                <Button style={{marginLeft: 10}} onClick={this.handleSearchProcess} shape="circle" icon="search"
                        htmlType="submit"/>
            </div>
        );
    }
}

export default SearchBox;