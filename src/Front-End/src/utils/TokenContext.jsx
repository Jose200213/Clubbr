import { createContext, useContext, useEffect, useState } from 'react';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [authToken, setAuthToken] = useState(() => {
        const storedToken = sessionStorage.getItem('authToken');
        return storedToken || '';
    });

    useEffect(() => {
        // Almacena el token en sessionStorage cada vez que cambie
        sessionStorage.setItem('authToken', authToken);
    }, [authToken]);

    const login = (token) => {
        setAuthToken(token);
    };
    const logout = () => {
        setAuthToken(null);
    };
    const getToken = () => {
        return authToken;
    };

    return (
        <AuthContext.Provider value={{ authToken, login, logout, getToken }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => {

    return useContext(AuthContext);
};
