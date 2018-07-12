import React from 'react';
import {
    Route,
    Redirect
} from "react-router-dom";

import {LOGIN_URL} from "../../../util/webConstants";

const ProtectedRoute = ({component: Component, authenticated, ...rest}) => (
    <Route
        {...rest}
        render={props =>
            authenticated ? (
                <Component {...rest} {...props} />
            ) : (
                <Redirect
                    to={{
                        pathname: LOGIN_URL,
                        state: {from: props.location}
                    }}
                />
            )
        }
    />
);

export default ProtectedRoute