import { createContext, useContext, useState } from 'react';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [authToken, setAuthToken] = useState(null);

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
