import React from 'react';
import { Route, Redirect, RouteProps } from 'react-router-dom';

const PrivateAdminRoute: React.FC<RouteProps> = ({ component: Component, ...rest }) => {
    const isAdmin = localStorage.getItem('admin') === 'true';
    const isLoggedIn = !!localStorage.getItem('login');

    return (
        <Route
            {...rest}
            render={props => {
                if (!isLoggedIn || !isAdmin) {
                    return <Redirect to="/" />;
                }
                return Component ? <Component {...props} /> : null;
            }}
        />
    );
};

export default PrivateAdminRoute;
