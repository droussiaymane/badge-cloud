import { createContext, useState } from "react";

const AuthContext = createContext({});

const setSession = (data ) => {
    if (!data) {
      localStorage.removeItem('accessToken');
      localStorage.removeItem('refreshToken');
    } else {
      const accessToken = data.access_token;
      const refreshToken = data.refresh_token;
      if (accessToken || refreshToken) {
        localStorage.setItem('accessToken', accessToken);
        localStorage.setItem('refreshToken', refreshToken);
      }
    }
  };

export const AuthProvider = ({ children }) => {
    const [auth, setAuth] = useState({});
    const [persist, setPersist] = useState(JSON.parse(localStorage.getItem("persist")) || false);
    console.log("persist", persist)


    return (
        <AuthContext.Provider value={{ auth, setAuth, persist, setPersist }}>
            {children}
        </AuthContext.Provider>
    )
}

export default AuthContext;