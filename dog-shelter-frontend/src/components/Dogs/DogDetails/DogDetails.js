import React, {useContext, useEffect, useState} from "react";
import {Link, useNavigate, useParams} from "react-router-dom";
import {AuthContext} from "../../../context/authenticationContext";
import email from "../../../images/email3.svg";
import {differenceInMonths, differenceInYears, parseISO} from "date-fns";
import {deleteDogById, getDogById} from "../../../service/DogShelterService";
import "./DogDetails.css";

export const DogDetails = () => {
    const {loggedIn, role} = useContext(AuthContext);
    const navigate = useNavigate();
    const {id} = useParams();
    const [dog, setDog] = useState({});

    useEffect(() => {
        const loadDog = async () => {
            try {
                const data = await getDogById(id);
                setDog(data);
            } catch (error) {
                console.error("Failed to fetch dog:", error);
            }
        };
        loadDog();
    }, [id]);

   

    const handleDeleteDog = async () => {
        await deleteDogById(dog.id).then(() => {
            navigate(`/dogs`);
        });
    };

    if (!dog) {
        return (
            <div className="container mt-5">
                <div className="row">
                    <div className="col-md-6">
                        <h2 className="mb-4 text-uppercase title">Loading...</h2>
                    </div>
                </div>
            </div>
        );
    }

    let age = differenceInYears(new Date(), parseISO(dog.birthday)) + " years";
    if (age.charAt(0) === "0")
        age = differenceInMonths(new Date(), parseISO(dog.birthday)) + " months";

    let birthday = new Date(dog.birthday);
    console.log(birthday);

    return (
        <div className="m-5">
            <div className="container mt-5">
                <div className="row d-flex justify-content-center align-items-center flex-column">
                    <div className="col-md-6 m-5">
                        <img
                            className="img-fluid image"
                            src={dog.photoUrl}
                            alt={dog.name}
                        />
                    </div>

                    <div className="col-md-6 py-5">
                        <div className="d-flex flex-column justify-content-start align-items-start">
                            {loggedIn && role === '[ROLE_USER]' && <a
                                className="d-flex justify-content-center align-items-center email-wrapper"
                                href={`mailto:paws_for_home@gmail.com?subject=Adopt ${dog.name}`}
                            >
                                <img className="email-icon my-2" src={email} alt="Send email"/>
                            </a>}
                            <h3>{dog.name}</h3>
                        </div>

                        <p>
                            <strong>Age:</strong> {age}
                        </p>
                        <p>
                            <strong>Birthday:</strong> {birthday.toLocaleDateString("en-GB")}
                        </p>
                        <p>
                            <strong>Breed:</strong> {dog?.breed?.name}
                        </p>
                        <p>
                            <strong>Status:</strong>{" "}
                            {dog.certified ? "Certified" : "Not certified"}
                        </p>
                        <p>
                            <strong>Vaccination:</strong>{" "}
                            {dog.vaccinated ? "Vaccinated" : "Not vaccinated"}
                        </p>

                        {role === '[ROLE_ADMIN]' && (
                            <div className="d-grid gap-2 mt-3">
                                <Link
                                    className="btn btn-outline-warning"
                                    onClick={handleDeleteDog}
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
                        )}
                    </div>
                </div>
            </div>
            <div className="background"></div>
        </div>
    );
};
