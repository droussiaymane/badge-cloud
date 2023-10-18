import Lazy from '@loadable/component';
import { isUndefined } from 'lodash';
import React from "react";
import { useSelector } from 'react-redux';
import {
  BrowserRouter as Router, Route, Routes
} from "react-router-dom";
import { Footer } from "./components/Footer";
import { LeftsideMenu } from "./components/LeftsideMenu";
import LoginPage from './components/login/login';
import PersistLogin from './components/login/PersistLogin';
import NotFoundView from './components/notFoundPage/pageNotFound';
import LinkedInBadgeError from './components/socialMediaBadge/linkdinBadgeError';
import SocialMediaSharingBadge from './components/socialMediaBadge/socialMediaSharingBadge';
import TopBar from "./components/Topbar";

const DashBoard = Lazy(() => import("./components/dashboard/dashboard"));

const Home = Lazy(() => import("./components/home/home"))
const StudentProfile = Lazy(() => import("./components/StudentProfile/AddStudentProfile/AddStudentProfile.js"))


function App() {


  const sidebarValue = useSelector(state => state.sidebar.sideBar);
  console.log("sidebarValue", sidebarValue);

  return (
    <div className="wrapper">
      <React.Suspense fallback={<div> loading ....</div>}>
        <Router>
          <Routes>
            <Route
              exact
              path='/badges/:id'
              element={<SocialMediaSharingBadge />}
            />
            <Route
              exact
              path='/badges/linkdinerror'
              element={<LinkedInBadgeError />}
            />
            <Route
              exact
              path='/login'
              element={<LoginPage />}
            />
            <Route exact path='/' element={<PersistLogin />}>
              <Route path="/"
                element={
                  <React.Fragment>
                    <TopBar />,
                    <LeftsideMenu />,
                    <DashBoard />
                    <Footer />
                  </React.Fragment>
                }
              />
              <Route path="/home"
                element={
                  <React.Fragment>
                    <TopBar />,
                    <LeftsideMenu />,
                    <Home />
                    <Footer />
                  </React.Fragment>
                }
              />

              <Route path="/student-profile"
                element={
                  <React.Fragment>
                    <TopBar />,
                    <LeftsideMenu />,
                    <StudentProfile />,
                    <Footer />
                  </React.Fragment>
                }
              />
            </Route>
            <Route path='*' element={<NotFoundView />} />

            <Route path="/earned-badge"
              element={
                <React.Fragment>
                  <SocialMediaSharingBadge />
                </React.Fragment>
              }
            />
          </Routes>
        </Router>
      </React.Suspense>
    </div>
  );
}

export default App;
