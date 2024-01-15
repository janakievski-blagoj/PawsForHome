import axios from "axios";

const instance = axios.create({
    baseURL: "http://localhost:8181/api",
    headers: {
        'Authorization': document.cookie['JWT'],
    }
});

instance.interceptors.response.use(
    (response) => response.data,
    (error) => {
        console.error("Axios error:", error);
        throw error;
    }
);

export default instance;
