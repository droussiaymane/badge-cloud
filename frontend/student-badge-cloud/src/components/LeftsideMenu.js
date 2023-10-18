
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom'
export const LeftsideMenu = () => {
    const [locationPath, setLocationPath] = useState();
    useEffect(() => {
        let path = window.location.pathname;
        setLocationPath(path)
    })


    return (
        <div className="leftside-menu" style={{ paddingTop: 120, background: "#313a46" }}>
            <a className="logo text-center logo-light">
                <span className="logo-lg py-2">
                    <img src="assets/img/badgeCloudLogo.png" height={100} />
                </span>
                <span className="logo-sm py-2" style={{ background: "#313a46" }}>
                    <img src="assets/img/badgeCloudLogo.png" height={36} />
                </span>
            </a>
            <div className="h-100" id="" data-simplebar>
                <ul className="side-nav">
                    <li className="side-nav-item">
                        <Link to='/' className="side-nav-link" style={{ color: locationPath === '/' ? "#fff" : 'inherit' }}>
                            <i className="uil-dashboard" />
                            <span> Dashboards </span>
                        </Link>
                    </li>
                    <li className="side-nav-item">
                        <Link to='/home' className="side-nav-link" style={{ color: locationPath === '/home' ? "#fff" : 'inherit' }}>
                            <i className="uil-home-alt" />
                            <span> Home </span>
                        </Link>
                    </li>
                    <li className="side-nav-item">
                        <Link to="/student-profile" className="side-nav-link" style={{ color: locationPath === '/student-profile' ? "#fff" : 'inherit' }}>
                            <i className="uil-users-alt" />
                            <span> Student Profile </span>
                        </Link>
                    </li>
                    <li className="side-nav-item">
                        <a href="/application" className="side-nav-link" style={{ color: locationPath === '/application' ? "#fff" : 'inherit' }}>
                            <i className="uil-apps" />
                            <span> Applications </span>
                        </a>
                    </li>
                    <li className="side-nav-item">
                        <a href="organization.html" className="side-nav-link">
                            <i className="uil-building" />
                            <span> Organizations </span>
                        </a>
                    </li>
                    <li className="side-nav-item">
                        <a href="preferences.html" className="side-nav-link">
                            <i className="dripicons-bell" />
                            <span> Preferences </span>
                        </a>
                    </li>
                    <li className="side-nav-item">
                        <a href="privacy-and-security.html" className="side-nav-link">
                            <i className="uil-shield-check" />
                            <span> Privacy and Security </span>
                        </a>
                    </li>
                </ul>
                <div className="clearfix" />
            </div>
        </div>
    );
}
