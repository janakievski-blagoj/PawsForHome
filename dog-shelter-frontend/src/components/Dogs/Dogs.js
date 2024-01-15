import React, { useContext, useEffect, useState } from "react";
import { Dog } from "./Dog/Dog";
import { SearchBar } from "./SearchBar/SearchBar";
import { deleteDogById, fetchDogs } from "../../service/DogShelterService";
import { Link, useNavigate } from "react-router-dom";
import { AuthContext } from "../../context/authenticationContext";

export const Dogs = () => {
  const navigate = useNavigate();
  const [dogs, setDogs] = useState([]);
  const [query, setQuery] = useState("");
  const { role } = useContext(AuthContext);

  useEffect(() => {
    loadDogs();
  }, []);

  const loadDogs = async () => {
    try {
      const data = await fetchDogs();
      setDogs(data);
    } catch (error) {
      console.error("Failed to fetch dogs:", error);
    }
  };

  const deleteDog = async (id) => {
    await deleteDogById(id).then(() => {
      loadDogs();
    });
  };

  const handleSearch = (query) => {
    setDogs(filterDogs(query));
    setQuery(query);
  };

  const handleClearQuery = () => {
    setQuery("");
    loadDogs();
  };

  const handleChange = (e) => {
    setQuery(e.target.value);
    if (!!e.target.value) {
      setDogs(filterDogs(e.target.value));
    } else {
      loadDogs();
    }
  };

  const handleBadgeClick = (query) => {
    setQuery(query);
    setDogs(filterDogs(query));
  };

  const handleDogClick = (id) => {
    navigate(`/dog/${id}`);
  };

  const filterDogs = (query) => {
    return dogs.filter((dog) =>
      dog.breed.name.toLowerCase().includes(query.toLowerCase().trim())
    );
  };

  return (
    <div className="container-fluid">
      <div className="row my-5">
        <SearchBar
          handleSearch={handleSearch}
          handleChange={handleChange}
          value={query}
        />
        <div className="row d-flex justify-content-between align-items-center my-5">
          <p className="col-6 m-0">
            {dogs.length} {dogs.length > 1 ? `results` : `result`}
          </p>
          {query && (
            <p className="col-6 badge-pill" onClick={handleClearQuery}>
              Clear Filter
            </p>
          )}
        </div>
        {dogs?.map((dog) => (
          <div className="col-md-4 mb-4" key={dog.id}>
            <Dog
              dog={dog}
              onDelete={deleteDog}
              handleBadgeClick={handleBadgeClick}
              handleDogClick={handleDogClick}
            />
          </div>
        ))}
        {role === "[ROLE_ADMIN]" && (
          <Link className="btn btn-primary mt-5" to="/dogs/add">
            Add new dog
          </Link>
        )}
      </div>
    </div>
  );
};
