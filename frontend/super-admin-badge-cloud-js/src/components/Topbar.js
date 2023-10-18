import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { jwtActions } from "../Redux/store/Slices/userSlice";

const TopBar = () => {
    const navigate = useNavigate()
    const dispatch = useDispatch();
    const logoutHandler = async () => {
        await dispatch(jwtActions.clearJWT());
        navigate("/login")
    }
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
                            <img src="../assets/img/user.png" alt="user-image" className="rounded-circle" />
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
            <button className="button-menu-mobile open-left">
                <i className="mdi mdi-menu" />
            </button>
            <div className="app-search dropdown d-none d-lg-block">
                <form>
                    <div className="input-group">
                        <input type="text" className="form-control dropdown-toggle" placeholder="Search..." id="top-search" />
                        <span className="mdi mdi-magnify search-icon" />
                        <button className="input-group-text btn-primary" type="submit">Search</button>
                    </div>
                </form>
            </div>
        </div>
    );
}


export default TopBar;