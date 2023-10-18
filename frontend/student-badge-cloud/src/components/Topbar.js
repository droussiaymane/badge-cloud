import { useEffect } from "react";
import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { studentApi } from "../api/userApi";
import { sidebarActions } from "../Redux/store/Slices/sidebar";
import { jwtActions } from "../Redux/store/Slices/userSlice";

const TopBar = () => {
    const imgBaseUrl = process.env.REACT_APP_PUBLIC_BADGE_URL;
    const { userId } = useSelector((state) => state.jwt);
    const sidebarValue = useSelector(state => state.sidebar.sideBar);
    const dispatch = useDispatch();
    const [studentData, setStudentData] = useState(null);
    console.log(sidebarValue);
    const navigate = useNavigate()
    const logoutHandler = async () => {
        await dispatch(jwtActions.clearJWT());
        navigate("/login")
    }

    const getSingleStudent = async () => {
        try {
            const res = await studentApi.getSingleStudent(userId)
            setStudentData(res?.data);
        } catch (err) {
            console.log(err?.response?.data?.message || err?.message);
        }
    }
    useEffect(() => {
        getSingleStudent();
    }, [])
    return (

        <div className="navbar-custom">
            <ul className="list-unstyled topbar-menu float-end mb-0">
                <li className="dropdown notification-list topbar-dropdown">
                    <a className="nav-link dropdown-toggle arrow-none" data-bs-toggle="dropdown" href="#" role="button" aria-haspopup="false" aria-expanded="false">
                        <span className="align-middle">Change Role</span> <i className="mdi mdi-chevron-down align-middle" />
                    </a>
                    <div className="dropdown-menu dropdown-menu-end dropdown-menu-animated topbar-dropdown-menu">
                        {/* item*/}
                        <a href="javascript:void(0);" className="dropdown-item notify-item">
                            <span className="align-middle">Org Admin</span>
                        </a>
                        {/* item*/}
                        <a href="javascript:void(0);" className="dropdown-item notify-item">
                            <span className="align-middle">Org Instructor</span>
                        </a>
                        {/* item*/}
                        <a href="javascript:void(0);" className="dropdown-item notify-item">
                            <span className="align-middle">Org Student</span>
                        </a>
                    </div>
                </li>
                <li className="notification-list topbar-dropdown">
                    <a className="nav-link" href="javascript: void(0);">
                        <span className="align-middle"> Help </span>
                    </a>
                </li>
                <li className="dropdown notification-list">
                    <a className="nav-link dropdown-toggle nav-user arrow-none me-0 custom-nav-user" data-bs-toggle="dropdown" href="#" role="button" aria-haspopup="false" aria-expanded="false">
                        <span className="account-user-avatar">
                            <img src={studentData?.image ? `${imgBaseUrl + studentData?.image}` : "../assets/img/user.png"} alt="user-image" className="rounded-circle" />
                        </span>
                    </a>
                    <div className="dropdown-menu dropdown-menu-end dropdown-menu-animated topbar-dropdown-menu profile-dropdown"

                        onClick={() => logoutHandler()}
                    >
                        {/* item*/}
                        <a href="javascript:void(0);" className="dropdown-item notify-item">
                            <i className="mdi mdi-logout me-1" />
                            <span>Logout</span>
                        </a>
                    </div>
                </li>
            </ul>
            <button className="button-menu-mobile open-left" onClick={() => dispatch(sidebarActions.showSidebar({ value: !sidebarValue }))}>
                <i className="mdi mdi-menu" />
            </button>
        </div>
    );
}


export default TopBar;