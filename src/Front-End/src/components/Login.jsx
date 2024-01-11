import {useAuth} from "../utils/TokenContext";
import React, {useState} from "react";

import moon from "../svg/moonlogo.svg";
import user from "../svg/mdi-user.svg";
import passw from "../svg/solar-password-bold.svg";
import {useNavigate} from "react-router-dom";

import "./Login/Login.css"

function Login() {
    const navigate = useNavigate();
    const { login } = useAuth();
    const [userID, setUserID] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(null);


    const HandleLogin = async (e) => {
        e.preventDefault();

        try {
            const response = await fetch('http://localhost:8080/authentication/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({userID, password}),
            });

            if (response.ok) {
                // El inicio de sesión fue exitoso, obtén el token o cualquier otra información necesaria
                const data = await response.json();

                // Usa la función login del contexto para almacenar el token
                login(data.jwt);
                setError(null);
                navigate('/locals');


                // Llama a la función proporcionada desde el componente padre;


            }
        } catch (error) {
            console.error('Error en el inicio de sesión:', error.message);
            setError('Credenciales incorrectas. Inténtelo de nuevo.')
        }
    };



    return (
        <>
            <div className="login-container">
                <div className='login-logo-container'>
                    <img className="login-logo-image" alt="Vector" src={moon}/>
                    <div className="login-logo-text">CLUBB’r</div>
                </div>
                <div className="login-input-field-container">
                    <input className="login-user-input-field"
                           placeholder='username'
                           value={userID}
                           onChange={(e) => setUserID(e.target.value)}
                    />
                    <img className="login-icon" alt="Mdi user" src={user} />
                </div>
                <div className="login-input-field-container">
                    <input className="login-user-input-field"
                           placeholder='password'
                           type="password"
                           value={password}
                           onChange={(e) => setPassword(e.target.value)}
                    />
                    <img className="login-icon" alt="Solar password bold" src={passw} />
                </div>
                <div className="login-submit" onClick={HandleLogin}>
                    <h2>Send</h2>
                </div>

                {error && <div className="login-error">{error}</div>}

            </div>
        </>
    )
}

export default Login