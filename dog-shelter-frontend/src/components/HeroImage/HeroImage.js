import React from "react";
import {Link} from "react-router-dom";
import "./HeroImage.css";

export const HeroImage = () => {
    return (
        <div className="hero-banner">
            <div className="hero-text">
                <h1>Adopt a Dog Today</h1>
                <p>Find your new best friend and give them a forever home</p>
                <Link className="btn btn-primary" to="/dogs">
                    View Dogs
                </Link>
            </div>
        </div>
    );
};
