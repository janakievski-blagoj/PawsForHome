import {BrowserRouter, Route, Routes} from "react-router-dom";
import {Dogs} from "../Dogs/Dogs";
import {Header} from "../Header/Header";
import {AuthProvider} from "../../context/authenticationContext";
import {Login} from "../Login/Login";
import {Logout} from "../Logout/Logout";
import {Register} from "../Register/Register";
import {DogForm} from "../Dogs/DogForm/DogForm";
import {DogDetails} from "../Dogs/DogDetails/DogDetails";
import {HeroImage} from "../HeroImage/HeroImage";
import {NotFound} from "../NotFound/NotFound";

export function App() {
    return (
        <AuthProvider>
            <BrowserRouter>
                <Header/>
                <main>
                    <div className="container">
                        <Routes>
                            <Route path="/" element={<HeroImage/>}/>
                            <Route path="/dogs" element={<Dogs/>}/>
                            <Route path="/dog/:id" element={<DogDetails/>}/>
                            <Route path="/login" element={<Login/>}/>
                            <Route path="/logout" element={<Logout/>}/>
                            <Route path="/register" element={<Register/>}/>
                            <Route path="/dogs/add" element={<DogForm/>}/>
                            <Route path="/dogs/edit/:id" element={<DogForm isEditing/>}/>
                            <Route path="*" element={<NotFound/>}/>
                        </Routes>
                    </div>
                </main>
            </BrowserRouter>
        </AuthProvider>
    );
}