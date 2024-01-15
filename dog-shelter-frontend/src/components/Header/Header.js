import React, {useContext} from 'react';
import {Link} from 'react-router-dom';
import './Header.css';
import logo from "../../images/logo.svg";
import {AuthContext} from '../../context/authenticationContext';

export const Header = () => {
    const {loggedIn} = useContext(AuthContext);

    return (
        <header>
            <nav className="navbar navbar-expand-md navbar-dark bg-brown">
                <div className="container">
                    <img src={logo} alt="" className="logo"/>
                    <Link className="navbar-brand" to="/">PAWS FOR HOME</Link>
                    <button className="navbar-toggler" type="button" data-toggle="collapse"
                            data-target="#navbarCollapse"
                            aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse justify-content-end" id="navbarCollapse">
                        <Link className="btn btn-outline-warning" to={loggedIn ? "/logout" : "/login"}>
                            {loggedIn ? "Logout" : "Login"}
                        </Link>
                    </div>
                </div>
            </nav>
        </header>
    );
}
