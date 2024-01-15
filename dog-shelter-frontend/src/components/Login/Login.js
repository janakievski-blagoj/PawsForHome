import React, {useContext, useState} from "react";
import {useNavigate} from "react-router-dom";
import {AuthContext} from "../../context/authenticationContext";

export const Login = () => {
    const navigate = useNavigate();
    const {handleLogin} = useContext(AuthContext);
    const [formData, updateFormData] = useState({
        username: "",
        password: ""
    });

    const handleChange = (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value.trim(),
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        handleLogin({
            username: formData.username,
            password: formData.password
        });
        navigate("/");
    };

    return (
        <div className="row mt-5">
            <div className="col-md-8 offset-md-2">
                <h2 className="mb-4 text-uppercase title">Login</h2>
                <form onSubmit={handleSubmit}>
                    <div className="form-group row mt-4">
                        <label htmlFor="username" className="col-sm-3 col-form-label">
                            Username
                        </label>
                        <div className="col-sm-9">
                            <input
                                type="text"
                                className="form-control"
                                id="username"
                                name="username"
                                required
                                placeholder="Enter username"
                                onChange={handleChange}
                            />
                        </div>
                    </div>
                    <div className="form-group row mt-4">
                        <label htmlFor="password" className="col-sm-3 col-form-label">
                            Password
                        </label>
                        <div className="col-sm-9">
                            <input
                                type="password"
                                className="form-control"
                                id="password"
                                name="password"
                                placeholder="Enter password"
                                required
                                onChange={handleChange}
                            />
                        </div>
                    </div>
                    <button id="submit" type="submit" className="btn btn-primary mt-4">
                        Login
                    </button>
                </form>
                <a id="submit" className="btn btn-primary mt-4" href="/register">
                    Register
                </a>
            </div>
        </div>
    );
};
