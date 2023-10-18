
import { Navigate, Outlet, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { useLayoutEffect } from "react";
import jwtDecode from 'jwt-decode';
import { refreshTokenApi } from "../../Redux/store/Slices/refreshTokenSlice";
import { useEffect } from "react";
import { jwtActions } from "../../Redux/store/Slices/userSlice";

const PersistLogin = () => {
    const navigate = useNavigate();
    const { token, isAuthenticated,refreshToken } = useSelector(state => state.jwt);
    const dispatch = useDispatch();
    function isValidToken(token) {
        if (!token) {
            return false;
        }
        const decoded = jwtDecode(token);
        if (!decoded || !decoded.exp) return true;
        const currentTime = Date.now() / 1000;
        return decoded.exp > currentTime;
    }

    const init = async () => {
        console.log("called init", isValidToken(token) )
            if (!isValidToken(token)) {
                await dispatch(jwtActions.clearJWT());
                await dispatch(
                    refreshTokenApi({ refreshToken })
                );
            }
    }
    useEffect(() => {
        //   init();
    }, []);
    return (
        <>
            {token && isAuthenticated
                ? <Outlet />
                : <Navigate to="/login" />
            }
        </>
    )
}

export default PersistLogin