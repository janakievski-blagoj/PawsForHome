import React, {useContext, useState} from "react";
import {AuthContext} from "../../context/authenticationContext";
import {useNavigate} from "react-router-dom";

export const Register = () => {
    const navigate = useNavigate();
    const {handleRegister} = useContext(AuthContext);
    const [formData, updateFormData] = useState({
        username: "",
        password: "",
        repeatedPassword: "",
        name: "",
        surname: ""
    });

    const handleChange = (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value.trim()
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        handleRegister({
            username: formData.username,
            password: formData.password,
            repeatedPassword: formData.repeatedPassword,
            name: formData.name,
            surname: formData.surname
        });
        navigate("/login");
    }

    return (
        <div className="row mt-5">
            <div className="col-md-8 offset-md-2">
                <h2 className="mb-4 text-uppercase title">Register</h2>
                <form onSubmit={handleSubmit}>
                    <div className="form-group row mt-4">
                        <label htmlFor="name" className="col-sm-3 col-form-label">
                            Name
                        </label>
                        <div className="col-sm-9">
                            <input type="text"
                                   id="name"
                                   name="name"
                                   className="form-control"
                                   placeholder="Enter Name"
                                   required
                                   onChange={handleChange}/>
                        </div>
                    </div>
                    <div className="form-group row mt-4">
                        <label htmlFor="surname" className="col-sm-3 col-form-label">
                            Surname
                        </label>
                        <div className="col-sm-9">
                            <input type="text"
                                   id="surname"
                                   name="surname"
                                   className="form-control"
                                   placeholder="Enter Surname"
                                   required
                                   onChange={handleChange}/>
                        </div>
                    </div>
                    <div className="form-group row mt-4">
                        <label htmlFor="username" className="col-sm-3 col-form-label">
                            Username
                        </label>
                        <div className="col-sm-9">
                            <input type="text"
                                   id="username"
                                   name="username"
                                   className="form-control"
                                   placeholder="Enter Username"
                                   required
                                   onChange={handleChange}/>
                        </div>
                    </div>
                    <div className="form-group row mt-4">
                        <label htmlFor="password" className="col-sm-3 col-form-label">
                            Password
                        </label>
                        <div className="col-sm-9">
                            <input type="password"
                                   id="password"
                                   name="password"
                                   className="form-control"
                                   placeholder="Enter Password"
                                   required
                                   onChange={handleChange}/>
                        </div>
                    </div>
                    <div className="form-group row mt-4">
                        <label htmlFor="repeatedPassword" className="col-sm-3 col-form-label">
                            Repeat Password
                        </label>
                        <div className="col-sm-9">
                            <input type="password"
                                   id="repeatedPassword"
                                   name="repeatedPassword"
                                   className="form-control"
                                   placeholder="Repeat Password"
                                   required
                                   onChange={handleChange}/>
                        </div>
                    </div>
                    <button id="submit" type="submit" className="btn btn-primary mt-4">
                        Sign up
                    </button>
                </form>
                <a id="submit" href="/login" className="btn btn-primary mt-4">
                    Already have an account? Login here!
                </a>
            </div>
        </div>
    )
}