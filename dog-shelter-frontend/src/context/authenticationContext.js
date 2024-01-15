import {createContext, useState} from "react";
import {login, register} from "../service/DogShelterService";

export const AuthContext = createContext({
    loggedIn: false,
    role: "[ROLE_USER]"
});

export const AuthProvider = ({children}) => {
    const [loggedIn, setLoggedIn] = useState(false);
    const [role, setRole] = useState("[ROLE_USER]");

    const handleLogin = async ({username, password}) => {
        await login(username, password)
            .then((response) => {
                const data = response.split(",");
                document.cookie = `JWT=${data[0]}`;
                setRole(data[1]);
            });
        setLoggedIn(true);
    };

    const handleLogout = () => {
        setLoggedIn(false);
    };

    const handleRegister = async ({username, password, repeatedPassword, name, surname}) => {
        await register(username, password, repeatedPassword, name, surname);
    };

    return (
        <AuthContext.Provider
            value={{loggedIn, role, handleLogin, handleLogout, handleRegister}}
        >
            {children}
        </AuthContext.Provider>
    );
};
