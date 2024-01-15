import {useContext, useEffect} from "react";
import {AuthContext} from "../../context/authenticationContext";
import {useNavigate} from "react-router-dom";

export const Logout = () => {
    const {handleLogout} = useContext(AuthContext);
    const navigate = useNavigate();

    useEffect(() => {
        handleLogout();
    }, [handleLogout]);

    navigate("/");
};
