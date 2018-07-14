import React, {Component} from 'react';
import {
    Link,
    withRouter
} from 'react-router-dom';
import './Header.css';
import {Layout, Menu, Dropdown, Icon} from 'antd';
import {SIGN_OUT} from "../../../util/webConstants";
import SearchBox from "../search/SearchBox";

const Header = Layout.Header;

class HeaderComponent extends Component {
    constructor(props) {
        super(props);

        this.handleMenuClick = this.handleMenuClick.bind(this);
    }

    handleMenuClick({key}) {
        if (key === SIGN_OUT) {
            this.props.onLogout();
        }
    }

    render() {
        let menuItems;
        if (this.props.currentUser) {
            menuItems = [
                <Menu.Item key="/">
                    <Link to="/">
                        <Icon type="home" className="nav-icon"/>
                    </Link>
                </Menu.Item>,
                <Menu.Item key="/poll/new">
                    <Link to="/poll/new">
                        <Icon type="plus" className="nav-icon"/>
                    </Link>
                </Menu.Item>,
                <Menu.Item key="/profile" className="profile-menu">
                    <ProfileDropdownMenu
                        currentUser={this.props.currentUser}
                        handleMenuClick={this.handleMenuClick}/>
                </Menu.Item>
            ];
        } else {
            menuItems = [
                <Menu.Item key="/login">
                    <Link to="/login">Login</Link>
                </Menu.Item>,
                <Menu.Item key="/signup">
                    <Link to="/signup">Signup</Link>
                </Menu.Item>
            ];
        }

        return (
            <Header className="app-header">
                <div className="container">
                    <div className="app-title">
                        <img src='https://pbs.twimg.com/profile_images/684471274194649088/6Y_UXIZy_400x400.png'
                             width='50px' height='50px'/>
                        {/*<Link to="/">Voter</Link>*/}
                    </div>

                    <SearchBox onSearchButtonClicked={this.props.onSearch}/>

                    <Menu
                        className="app-menu"
                        mode="horizontal"
                        selectedKeys={[this.props.location.pathname]}
                        style={{lineHeight: '64px'}}>
                        {menuItems}
                    </Menu>
                </div>
            </Header>
        );
    }
}

function ProfileDropdownMenu(props) {
    const dropdownMenu = (
        <Menu onClick={props.handleMenuClick} className="profile-dropdown-menu">
            <Menu.Item key="user-info" className="dropdown-item" disabled>
                <div className="user-full-name-info">
                    {props.currentUser.name}
                </div>
                <div className="username-info">
                    @{props.currentUser.username}
                </div>
            </Menu.Item>
            <Menu.Divider/>
            <Menu.Item key="profile" className="dropdown-item">
                <Link to={`/users/${props.currentUser.username}`}>Profile</Link>
            </Menu.Item>
            <Menu.Item key="logout" className="dropdown-item">
                Logout
            </Menu.Item>
        </Menu>
    );

    return (
        <Dropdown
            overlay={dropdownMenu}
            trigger={['click']}
            getPopupContainer={() => document.getElementsByClassName('profile-menu')[0]}>
            <a className="ant-dropdown-link">
                <Icon type="user" className="nav-icon" style={{marginRight: 0}}/> <Icon type="down"/>
            </a>
        </Dropdown>
    );
}


export default withRouter(HeaderComponent);