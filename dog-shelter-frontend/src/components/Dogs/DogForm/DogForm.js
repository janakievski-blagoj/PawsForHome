import React, {useEffect, useState} from "react";
import {addNewDog, editDogById, fetchBreeds, getDogById} from "../../../service/DogShelterService";
import {useNavigate, useParams} from "react-router-dom";
import "./DogForm.css";

export const DogForm = ({isEditing = false}) => {
    const navigate = useNavigate();
    const {id} = useParams();
    const date = new Date();
    date.setMonth(date.getMonth() - 2);
    const [breeds, setBreeds] = useState([]);
    const [dog, setDog] = useState({
        name: "",
        breedId: 1,
        birthday: "",
        certified: false,
        vaccinated: false
    });

    // Fetch breed list and/or dog details if editing
    useEffect(() => {
        if (!isEditing) {
            loadBreeds();
        } else {
            const loadDog = async () => {
                try {
                    const data = await getDogById(id);
                    setDog(data);
                } catch (error) {
                    console.error("Error loading dog:", error);
                }
            };
            loadDog();
        }
    }, [isEditing, id]);

    const loadBreeds = async () => {
        try {
            const data = await fetchBreeds();
            setBreeds(data);
        } catch (error) {
            console.error("Error loading breeds:", error);
        }
    };

    const handleChange = (event) => {
        setDog({
            ...dog,
            [event.target.name]:
                event.target.type === "checkbox"
                    ? event.target.checked
                    : event.target.value.trim(),
        });
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        const {name, breedId, birthday, certified, vaccinated} = dog;
        if (isEditing && id) {
            await editDogById(id, name, birthday, certified, vaccinated);
        } else {
            await addNewDog(name, breedId, birthday, certified, vaccinated);
        }
        navigate("/dogs");
    };

    return (
        <div className="row mt-5">
            <div className="col-md-8 offset-md-2">
                <h2 className="mb-4 text-uppercase title">
                    {isEditing ? "Edit Dog" : "Add New Dog"}
                </h2>
                <form onSubmit={handleSubmit}>
                    <div className="form-group row mt-4">
                        <label htmlFor="name" className="col-sm-3 col-form-label">
                            Dog name
                        </label>
                        <div className="col-sm-9">
                            <input
                                type="text"
                                className="form-control "
                                id="name"
                                name="name"
                                required
                                placeholder="Enter dog name"
                                value={dog.name}
                                onChange={handleChange}
                            />
                        </div>
                    </div>
                    <div className="form-group row mt-4">
                        <label htmlFor="breedId" className="col-sm-3 col-form-label">
                            Breed
                        </label>
                        <div className="col-sm-9">
                            <select
                                name="breedId"
                                id="breedId"
                                className="form-control"
                                disabled={isEditing}
                                onChange={handleChange}
                            >
                                {!isEditing ? (
                                    breeds?.map((data) => (
                                        <option value={data.id} key={data.id}>
                                            {data.name}
                                        </option>
                                    ))
                                ) : (
                                    <option value={dog.breedId} key={dog.breedId}>
                                        {dog.breedId}
                                    </option>
                                )}
                            </select>
                        </div>
                    </div>
                    <div className="form-group row mt-4">
                        <label htmlFor="birthday" className="col-sm-3 col-form-label">
                            Birth date
                        </label>
                        <div className="col-sm-9">
                            <input type="date"
                                   id="birthday"
                                   name="birthday"
                                   min="2008-01-01"
                                   max={date.toISOString().split('T')[0]}
                                   value={dog.birthday}
                                   onChange={handleChange}/>
                        </div>
                    </div>
                    <div className="form-check my-3">
                            <input
                                type="checkbox"
                                id="certified"
                                name="certified"
                                className="form-check-input"
                                checked={dog.certified}
                                onChange={handleChange}
                            />
                        <label htmlFor="certified" className="form-check-label">
                            Certified
                        </label>
                    </div>
                    <div className="form-check my-3">
                            <input
                                type="checkbox"
                                id="vaccinated"
                                name="vaccinated"
                                className="form-check-input"
                                checked={dog.vaccinated}
                                onChange={handleChange}
                            />
                        <label htmlFor="vaccinated" className="form-check-label">
                            Vaccinated
                        </label>
                    </div>
                    <button id="submit" type="submit" className="btn btn-primary mt-4">
                        Submit
                    </button>
                </form>
            </div>
        </div>
    );
};
