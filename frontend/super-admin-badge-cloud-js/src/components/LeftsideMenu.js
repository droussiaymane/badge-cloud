import { makeStyles } from '@material-ui/core';
import { useLayoutEffect } from 'react';
import { useEffect } from 'react';
import { useMemo } from 'react';
import { useState } from 'react';
import { useSelector } from 'react-redux';
import { Link, useLocation } from 'react-router-dom';
import { store } from '../Redux/store/store';

const state = store.getState();
const sideBarMenu = {
    "/": 1,
    "/user-manager": 2,
    "/program/program-list": 4,
    "/program/competencies": 5,
    "/program/earning-requirements": 6,
    '/program/standards': 7,
    '/program/evidences': 8,
    '/program/additional-details': 9,
    '/badge/badge-collection': 10
}

const styles = makeStyles((theme) => ({
    // '@global': {
    //   '*::-webkit-scrollbar': {
    //     width: '0.4em'
    //   },
    //   '*::-webkit-scrollbar-track': {
    //     '-webkit-box-shadow': 'inset 0 0 6px rgba(0,0,0,0.00)'
    //   },
    //   '*::-webkit-scrollbar-thumb': {
    //     backgroundColor: 'rgba(0,0,0,.1)',
    //     outline: '1px solid slategrey'
    //   }
    // },
    list: {
        overflowY: "auto",
        margin: 0,
        padding: 0,
        listStyle: "none",
        height: "100%",
        '&::-webkit-scrollbar': {
          width: '0.4em'
        },
        '&::-webkit-scrollbar-track': {
          boxShadow: 'inset 0 0 6px rgba(0,0,0,0.00)',
          webkitBoxShadow: 'inset 0 0 6px rgba(0,0,0,0.00)'
        },
        '&::-webkit-scrollbar-thumb': {
          backgroundColor: 'rgba(0,0,0,.1)',
          outline: '1px solid slategrey'
        }
      }
  }));
let menuColor = 1;
export const LeftsideMenu = () => {
    const standard = useSelector((state) => state.standard);
    const classes = styles();
    const location = useLocation();
    const changeColorHandler = (num) => {
        menuColor = num;
    }

    console.log("localstorage",state);
    const changeColorInit = () => {
        Object.entries(sideBarMenu).map((item) => {
            if (item.includes(location.pathname)) {
                menuColor = item[1];
            }
        })
    }
    const changeProgramHandler = (e) => {
        // program =e.currentTarget.dataset.tag;
    }
    useMemo(() => {
        if (menuColor != 3) {
            changeColorInit();
        }
    }, [menuColor]);

    return (
        <div className={` leftside-menu`} style={{ paddingTop: 120 }}>
            <a className='logo text-center logo-light'>
                <span className='logo-lg py-2'>
                    <img src={require('../assets/img/badgeCloudLogo.png')} height={100} />
                </span>
                <span className='logo-sm py-2'>
                    <img src={require('../assets/img/badgeCloudLogo.png')} height={36} />
                </span>
            </a>
            <div className='h-100' id='leftside-menu-container' data-simplebar>
                <ul className='side-nav'>
                    <li className='side-nav-item' onClick={() => changeColorHandler(1)}>
                        <Link to='/' className='side-nav-link' >
                            <i className='uil-home-alt' style={{ color: menuColor == 1 ? "#e3eaef" : "#8391A2" }} />
                            <span style={{ color: menuColor == 1 ? "#e3eaef" : "#8391A2" }}>Dashboard </span>
                        </Link>
                    </li>
                    <li className='side-nav-item' onClick={() => changeColorHandler(2)}>
                        <Link to='/user-manager' className='side-nav-link' >
                            <i className='uil-users-alt' style={{ color: menuColor == 2 ? "#e3eaef" : "#8391A2" }} />
                            <span style={{ color: menuColor === 2 ? "#e3eaef" : "#8391A2" }}>User Manager</span>
                        </Link>
                    </li>
                    <li className='side-nav-item' >
                        <a
                            data-bs-toggle='collapse'
                            href='#sidebarProgram'
                            aria-expanded={menuColor > 2 && menuColor < 10 ? true : false}
                            aria-controls='sidebarProgram'
                            className='side-nav-link'
                            data-tag={3}
                            onClick={changeProgramHandler}
                        >
                            <i className='uil-sitemap' style={{ color: (menuColor > 2 && menuColor < 10) ? "#e3eaef" : "#8391A2" }} />
                            <span style={{ color: (menuColor > 2 && menuColor < 10) ? "#e3eaef" : "#8391A2" }}> Program Manager </span>
                            <span className='menu-arrow' style={{ color: (menuColor > 2 && menuColor < 10) ? "#e3eaef" : "#8391A2" }} />
                        </a>
                        <div className='collapse' id='sidebarProgram'>
                            <ul className='side-nav-second-level'>
                                <li onClick={() => changeColorHandler(4)} >
                                    <Link to='/program/program-list' style={{ color: menuColor == 4 ? "#e3eaef" : "#8391A2" }}> Program List </Link>
                                </li>
                                <li onClick={() => changeColorHandler(5)}>
                                    <Link to='/program/competencies' style={{ color: menuColor == 5 ? "#e3eaef" : "#8391A2" }}> Competencies </Link>
                                </li>
                                <li onClick={() => changeColorHandler(6)}>
                                    <Link to='/program/earning-requirements' style={{ color: menuColor == 6 ? "#e3eaef" : "#8391A2" }}>
                                        Earning Requirements
                                    </Link>
                                </li>
                                <li onClick={() => changeColorHandler(7)}>
                                    <Link to='/program/standards' style={{ color: menuColor == 7 ? "#e3eaef" : "#8391A2" }}>Standards</Link>
                                </li>
                                <li onClick={() => changeColorHandler(8)}>
                                    <Link to='/program/evidences' style={{ color: menuColor == 8 ? "#e3eaef" : "#8391A2" }}>Evidence</Link>
                                </li>
                                <li onClick={() => changeColorHandler(9)}>
                                    <Link to='/program/additional-details' style={{ color: menuColor == 9 ? "#e3eaef" : "#8391A2" }}>
                                        Additional Details
                                    </Link>
                                </li>
                            </ul>
                        </div>
                    </li>
                    <li className='side-nav-item' onClick={() => changeColorHandler(10)}>
                        <Link to='/badge/badge-collection' style={{ color: menuColor == 10 ? "#e3eaef" : "#8391A2" }} className='side-nav-link'>
                            <i className='uil-award' />
                            <span> Badge Template Manager </span>
                        </Link>
                    </li>
                    <li className='side-nav-item'>
                        <a
                            data-bs-toggle='collapse'
                            href='#sidebarTagManager'
                            aria-expanded='false'
                            aria-controls='sidebarTagManager'
                            className='side-nav-link'
                        >
                            <i className='uil-tag-alt' />
                            <span> Tag Manager </span>
                            <span className='menu-arrow' />
                        </a>
                        <div className='collapse' id='sidebarTagManager'>
                            <ul className='side-nav-second-level'>
                                <li>
                                    <a href='tag-manager/tag-manager-badge-collection.html'>
                                        Badge Collection
                                    </a>
                                </li>
                                <li>
                                    <a href='tag-manager/tag-manager-new-badge.html'>New Badge</a>
                                </li>
                            </ul>
                        </div>
                    </li>
                    <li className='side-nav-item'>
                        <a
                            href='attribute-manager/attribute-manager.html'
                            className='side-nav-link'
                        >
                            <i className='uil-subject' />
                            <span> Attribute Manager </span>
                        </a>
                    </li>
                    <li className='side-nav-item'>
                        <a
                            href='curriculum-manager/curriculum-manager.html'
                            className='side-nav-link'
                        >
                            <i className='uil-book-open' />
                            <span> Curriculum Manager </span>
                        </a>
                    </li>
                    <li className='side-nav-item'>
                        <a href='webform-manager.html' className='side-nav-link'>
                            <i className='uil-window-grid' />
                            <span> Webform Manager </span>
                        </a>
                    </li>
                    <li className='side-nav-item'>
                        <a href='#' className='side-nav-link'>
                            <i className='uil-building' />
                            <span> Organizations </span>
                        </a>
                    </li>
                    <li className='side-nav-item'>
                        <a href='#' className='side-nav-link'>
                            <i className='uil-folder-plus' />
                            <span> File Manager </span>
                        </a>
                    </li>
                    <li className='side-nav-item'>
                        <a href='#' className='side-nav-link'>
                            <i className='uil-money-stack' />
                            <span> License </span>
                        </a>
                    </li>
                    <li className='side-nav-item'>
                        <a href='#' className='side-nav-link'>
                            <i className='uil-cloud-upload' />
                            <span> Deployment </span>
                        </a>
                    </li>
                    <li className='side-nav-item'>
                        <a href='#' className='side-nav-link'>
                            <i className='uil-analytics' />
                            <span> Report </span>
                        </a>
                    </li>
                    <li className='side-nav-item'>
                        <a href='#' className='side-nav-link'>
                            <i className='uil-chart-line' />
                            <span> Workflow </span>
                        </a>
                    </li>
                    <li className='side-nav-item'>
                        <a href='#' className='side-nav-link'>
                            <i className='uil-technology' />
                            <span> Integration </span>
                        </a>
                    </li>
                    <li className='side-nav-item'>
                        <a href='#' className='side-nav-link'>
                            <i className='uil-presentation-lines-alt' />
                            <span> API Library </span>
                        </a>
                    </li>
                    <li className='side-nav-item'>
                        <a href='#' className='side-nav-link'>
                            <i className='uil-shield-check' />
                            <span> Security </span>
                        </a>
                    </li>
                    <li className='side-nav-item'>
                        <a href='#' className='side-nav-link'>
                            <i className='uil-cog' />
                            <span> System Settings </span>
                        </a>
                    </li>
                </ul>
                <div className='clearfix' />
            </div>
        </div>
    );
};
