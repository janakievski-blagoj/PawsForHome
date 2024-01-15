import React, {useEffect, useState} from "react";
import {fetchBreeds} from "../../service/DogShelterService";

export const Breeds = () => {
    const [breeds, setBreeds] = useState([]);

    useEffect(() => {
        loadBreeds();
    }, []);

    const loadBreeds = async () => {
        try {
            const data = await fetchBreeds();
            setBreeds(data);
        } catch (error) {
            console.error("Error loading breeds:", error);
        }
    };

    return (
        <div className={"container mb-4 mt-5"}>
            <div className={"row"}>
                <div className={"table-responsive"}>
                    <table className={"table table-striped"}>
                        <thead>
                        <tr>
                            <th scope={"col"}>ID</th>
                            <th scope={"col"}>Name</th>
                            <th scope={"col"}>Growth</th>
                            <th scope={"col"}>Shedding Volume</th>
                            <th scope={"col"}>Age Expectancy</th>
                        </tr>
                        </thead>
                        <tbody>
                        {breeds?.map((breed) => {
                            return (
                                <tr key={breed.id}>
                                    <td>{breed.id}</td>
                                    <td>{breed.name}</td>
                                    <td>{breed.growth.toLocaleLowerCase()}</td>
                                    <td>{breed.sheddingVolume.toLocaleLowerCase()}</td>
                                    <td>
                                        {breed.ageExpectancyFrom}-{breed.ageExpectancyTo} years
                                    </td>
                                </tr>
                            );
                        })}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
};