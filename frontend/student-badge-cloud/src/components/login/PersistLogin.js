
import { Navigate, Outlet } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { useLayoutEffect } from "react";
import jwtDecode from 'jwt-decode';
import { jwtActions } from "../../Redux/store/Slices/userSlice";

const PersistLogin = () => {
    const { token, isAuthenticated } = useSelector(state => state.jwt);
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
        await dispatch(
            jwtActions.setJWT({
                isSelected: true,
                isAuthenticated: false
            })
        );

    }

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