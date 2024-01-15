import React from "react";
import "./SearchBar.css";
import search from "../../../images/search.svg";

export const SearchBar = ({value, handleSearch, handleChange}) => {
    const handleSubmit = (event) => {
        event.preventDefault();
        const query = event.target.elements.query.value;
        handleSearch(query);
    };

    return (
        <form onSubmit={handleSubmit} className="search-bar mt-5 mb-5">
            <input
                type="text"
                className="form-control mr-2"
                id="query"
                name="query"
                placeholder="Search"
                onChange={handleChange}
                value={value}
            />
            <button className="search-icon" type="submit">
                <img src={search} alt="search"/>
            </button>
        </form>
    );
};
