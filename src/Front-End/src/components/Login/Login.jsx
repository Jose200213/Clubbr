import {useAuth} from "../../utils/TokenContext";
import React, {useState} from "react";

import moon from "../../svg/moonlogo.svg";
import user from "../../svg/mdi-user.svg";
import passw from "../../svg/solar-password-bold.svg";

import "./Login.css"

function Login({onLogin}) {
    const { login } = useAuth();
    const [userID, setUserID] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(null);


    const handleLogin = async (e) => {
        e.preventDefault();

        try {
            const response = await fetch('http://localhost:3000/authentication/login', {
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

                // Llama a la función proporcionada desde el componente padre
                onLogin();


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
                    <img className="login-icon" alt="Username-input" src={user} />
                </div>

                <div className="login-input-field-container">
                    <input className="login-user-input-field"
                           placeholder='password'
                           type="password"
                           value={password}
                           onChange={(e) => setPassword(e.target.value)}
                    />
                    <img className="login-icon" alt="Userpassw-input" src={passw} />
                </div>
                
                <div className="login-submit" onClick={handleLogin}>
                    <h2>Send</h2>
                </div>

                {error && <div className="login-error">{error}</div>}

            </div>
        </>
    )
}

export default Login