import axios from '../axios/axios';

export const fetchDogs = () => {
    return axios.get("/dogs");
}

export const fetchBreeds = () => {
    return axios.get("/breeds");
}

export const deleteDogById = (id) => {
    return axios.delete(`/dogs/delete/${id}`);
}

export const getDogById = (id) => {
    return axios.get(`/dogs/${id}`);
}

export const editDogById = (id, name, birthday, certified, vaccinated) => {
    return axios.put(`/dogs/edit/${id}`, {
        "name": name,
        "birthday": birthday,
        "certified": certified,
        "vaccinated": vaccinated
    });
}

export const addNewDog = (name, breedId, birthday, certified, vaccinated) => {
    return axios.post("/dogs/add", {
        "name": name,
        "breedId": breedId,
        "birthday": birthday,
        "certified": certified,
        "vaccinated": vaccinated
    });
}

export const login = (username, password) => {
    return axios.post("/login", {
        "username": username,
        "password": password
    });
}

export const register = (username, password, repeatedPassword, name, surname) => {
    return axios.post("/register", {
        "username": username,
        "password": password,
        "repeatedPassword": repeatedPassword,
        "name": name,
        "surname": surname
    });
}