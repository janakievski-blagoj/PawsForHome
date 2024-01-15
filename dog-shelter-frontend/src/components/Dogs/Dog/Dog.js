import React, {useContext} from "react";
import './Dog.css'
import {Link} from "react-router-dom";
import {AuthContext} from "../../../context/authenticationContext";
import email from "../../../images/email3.svg";
import {differenceInMonths, differenceInYears, parseISO} from "date-fns";


export const Dog = ({dog, onDelete, handleBadgeClick, handleDogClick}) => {
    const {loggedIn, role} = useContext(AuthContext);
    let age = differenceInYears(new Date(), parseISO(dog.birthday)) + " years";
    if (age.charAt(0) === "0")
        age = differenceInMonths(new Date(), parseISO(dog.birthday)) + " months";

    return (
        <div className="card">
            <img className="card-img" src={dog.photoUrl} alt={dog.name} onClick={() => handleDogClick(dog.id)}/>
            <div className="card-body">
                <div className="row d-flex flex-row flex-nowrap align-items-center justify-content-between m-1 my-3">
                    <p className="col-9 badge-pill"
                       onClick={() => handleBadgeClick(dog.breed.name)}>{dog.breed.name}</p>
                    {role === '[ROLE_USER]' && loggedIn &&
                        <a className="col-3 d-flex justify-content-center align-items-center email-wrapper"
                           href={`mailto:paws_for_home@gmail.com?subject=Adopt ${dog.name}`}>
                            <img className="email-icon" src={email} alt="Send email"/>
                        </a>}
                </div>
                <div className="row d-flex align-items-start">
                    <h5 className="card-title col-sm-6">{dog.name}</h5>
                </div>
                <p className="card-text"><strong>Age:</strong> {age}</p>
                <p className="card-text"><strong>Breed:</strong> {dog.breed.name}</p>
                <p className="card-text"><strong>Status:</strong> {dog.certified ? 'Certified' : 'Not certified'}</p>
                <p className="card-text">
                    <strong>Vaccination:</strong> {dog.vaccinated ? 'Vaccinated' : 'Not vaccinated'}</p>
                {role === '[ROLE_ADMIN]' &&
                    <div className="d-grid gap-2">
                        <Link
                            className="btn btn-outline-warning"
                            onClick={() => onDelete(dog.id)}
                            to="/dogs"
                        >
                            Delete
                        </Link>
                        <Link
                            className="btn btn-outline-warning"
                            to={`/dogs/edit/${dog.id}`}
                        >
                            Edit
                        </Link>
                    </div>
                }
            </div>
        </div>
    );
};

