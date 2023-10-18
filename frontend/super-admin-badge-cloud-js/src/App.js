import Lazy from '@loadable/component';
import React from 'react';
import { Provider } from 'react-redux';
import { ReactKeycloakProvider, useKeycloak } from '@react-keycloak/web';
import { BrowserRouter, Route, Router, Routes, } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { persistStore } from 'redux-persist';
import { PersistGate } from 'redux-persist/integration/react';
import { Footer } from './components/Footer';
import { LeftsideMenu } from './components/LeftsideMenu';
import NotFoundView from './components/notFoundPage/pageNotFound';
import AddCompetencies from './components/ProgramManager/Competencies/Add/AddCompetencies';
import EditCompetency from './components/ProgramManager/Competencies/Edit';
import AddStandard from './components/ProgramManager/Standards/Add/AddStandard';
import EditStandard from './components/ProgramManager/Standards/Edit/EditStandard';
import StandardsList from './components/ProgramManager/Standards/StandardList';
import TopBar from './components/Topbar';
import Evidence from './components/ProgramManager/Evidence';
import AddEvidence from './components/ProgramManager/Evidence/Add/AddEvidence';
import EditEvidence from './components/ProgramManager/Evidence/Edit/EditEvidence';
import BadgeList from './components/Badge Template/badgeList';
import AddBadge from './components/Badge Template/Add/Addbadge';
import EditBadge from './components/Badge Template/Edit/EditBadge';
import LoginPage from './components/login/login';
import PersistLogin from './components/login/PersistLogin';
import EditUser from './components/UserManager/EditUser';
import EditProgram from './components/ProgramManager/ProgramList/EditProgram';
import AdditionalDetailsList from './components/ProgramManager/Additional Details/additionalDetails';
import AddAdditionalDetails from './components/ProgramManager/Additional Details/Add/AddAdditionalDetails';
import EditAdditionalDetails from './components/ProgramManager/Additional Details/Edit/EditAdditionalDetails';
import { injectStore } from './utils/axios';
import { store } from './Redux/store/store';
import AssignProgram from './components/UserManager/Assign/AssignProgram';
import ViewStudent from './components/ProgramManager/ProgramList/ViewStudent/view-students';

injectStore(store);
const DashBoard = Lazy(() => import('./components/dashboard/dashboard'));
const ProgramList = Lazy(() =>
  import('./components/ProgramManager/ProgramList')
);
const Competencies = Lazy(() =>
  import('./components/ProgramManager/Competencies')
);
const UserManager = Lazy(() => import('./components/UserManager/UserManager'));
const AddProgram = Lazy(() =>
  import('./components/ProgramManager/ProgramList/AddProgram')
);
const BulkProgram = Lazy(() =>
  import('./components/ProgramManager/ProgramList/BulkProgram')
);
const EnableBulkCollection = Lazy(() =>
  import('./components/ProgramManager/ProgramList/EnableBulkCollection')
);
const AddUser = Lazy(() => import('./components/UserManager/AddUser'));
const AddBulkUser = Lazy(() => import('./components/UserManager/AddBulkUser'));
const AssignBulkProgram = Lazy(() => import('./components/UserManager/AssignBulkProgram'));
const AddEarningRequirements = Lazy(() =>
  import('./components/ProgramManager/EarningRequirements/Add')
);
const EditEarningRequirements = Lazy(() =>
  import('./components/ProgramManager/EarningRequirements/Edit/index')
);

const EarningRequirementsList = Lazy(() =>
  import(
    './components/ProgramManager/EarningRequirements/EarningRequirementsList'
  )
);

function App() {
  return (

    <div className='wrapper' style={{ height: "0px !important" }}>

      <ToastContainer />
      <React.Suspense fallback={<div> loading ....</div>}>
        <Routes>
          <Route
            path='/login'
            element={
              <LoginPage />
            }
          />
          <Route exact path='/' element={<PersistLogin />}>
            <Route
              path='/'
              element={
                <>
                  <LeftsideMenu />
                  <TopBar />
                  <DashBoard />
                  <Footer />
                </>
              }
            />
            <Route
              path='/user-manager'
              element={
                <>
                  <LeftsideMenu />
                  <TopBar />
                  <UserManager />
                  <Footer />
                </>
              }
            />
            <Route
              path='/user-manager/add-user'
              element={
                <>
                  <LeftsideMenu />
                  <TopBar />
                  <AddUser />
                  <Footer />
                </>
              }
            />
            <Route
              path='/user-manager/add-bulk-user'
              element={
                <>
                  <LeftsideMenu />
                  <TopBar />
                  <AddBulkUser />
                  <Footer />
                </>
              }
            />
            <Route
              path='/user-manager/assign-bulk-program'
              element={
                <>
                  <LeftsideMenu />
                  <TopBar />
                  <AssignBulkProgram />
                  <Footer />
                </>
              }
            />
            <Route
              path='/user-manager/edit-user'
              element={
                <>
                  <LeftsideMenu />
                  <TopBar />
                  <EditUser />
                  <Footer />
                </>
              }
            />

            <Route
              path='/user-manager/assign-program'
              element={
                <>
                  <LeftsideMenu />
                  <TopBar />
                  <AssignProgram />
                  <Footer />
                </>
              }
            />
            
            <Route
              path='/view/list-of-students'
              element={
                <>
                  <LeftsideMenu />
                  <TopBar />
                  <ViewStudent />
                  <Footer />
                </>
              }
            />
            <Route
              path='/program/program-list'
              element={
                <>
                  <LeftsideMenu />
                  <TopBar />
                  <ProgramList />
                  <Footer />
                </>
              }
            />
            <Route
              path='/program/add-program'
              element={
                <>
                  <LeftsideMenu />
                  <TopBar />
                  <AddProgram />
                  <Footer />
                </>
              }
            />
            <Route
              path='/program/add-bulk-program'
              element={
                <>
                  <LeftsideMenu />
                  <TopBar />
                  <BulkProgram />
                  <Footer />
                </>
              }
            />
            <Route
              path='/program/enable-bulk-collection'
              element={
                <>
                  <LeftsideMenu />
                  <TopBar />
                  <EnableBulkCollection />
                  <Footer />
                </>
              }
            />
            <Route
              path='/program/edit-program'
              element={
                <>
                  <LeftsideMenu />
                  <TopBar />
                  <EditProgram />
                  <Footer />
                </>
              }
            />
            <Route
              path='/program/additional-details'
              element={
                <>
                  <LeftsideMenu />
                  <TopBar />
                  <AdditionalDetailsList />
                  <Footer />
                </>
              }
            />
            <Route
              path='/program/add-additional-details'
              element={
                <>
                  <LeftsideMenu />
                  <TopBar />
                  <AddAdditionalDetails />
                  <Footer />
                </>
              }
            />
            <Route
              path='/program/edit-additional-details'
              element={
                <>
                  <LeftsideMenu />
                  <TopBar />
                  <EditAdditionalDetails />
                  <Footer />
                </>
              }
            />
            <Route
              path='/program/competencies'
              element={
                <>
                  <LeftsideMenu />
                  <TopBar />
                  <Competencies />
                  <Footer />
                </>
              }
            />
            <Route
              path='/program/add-competencies'
              element={
                <>
                  <LeftsideMenu />
                  <TopBar />
                  <AddCompetencies />
                  <Footer />
                </>
              }
            />
            <Route
              path='/program/edit-competencies'
              element={
                <>
                  <LeftsideMenu />
                  <TopBar />
                  <EditCompetency />
                  <Footer />
                </>
              }
            />

            <Route
              path='/program/evidences'
              element={
                <>
                  <LeftsideMenu />
                  <TopBar />
                  <Evidence />
                  <Footer />
                </>
              }
            />
            <Route
              path='/program/add-evidence'
              element={
                <>
                  <LeftsideMenu />
                  <TopBar />
                  <AddEvidence />
                  <Footer />
                </>
              }
            />
            <Route
              path='/program/edit-evidence'
              element={
                <>
                  <LeftsideMenu />
                  <TopBar />
                  <EditEvidence />
                  <Footer />
                </>
              }
            />
            <Route
              path='/program/earning-requirements'
              element={
                <>
                  <LeftsideMenu />
                  <TopBar />
                  <EarningRequirementsList />
                  <Footer />
                </>
              }
            />
            <Route
              path='/program/add-earning-requirements'
              element={
                <>
                  <LeftsideMenu />
                  <TopBar />
                  <AddEarningRequirements />
                  <Footer />
                </>
              }
            />
            <Route
              path='/program/edit-earning-requirements'
              element={
                <>
                  <LeftsideMenu />
                  <TopBar />
                  <EditEarningRequirements />
                  <Footer />
                </>
              }
            />

            <Route
              path='/program/standards'
              element={
                <>
                  <LeftsideMenu />
                  <TopBar />
                  <StandardsList />
                  <Footer />
                </>
              }
            />
            <Route
              path='/program/add-standard'
              element={
                <>
                  <LeftsideMenu />
                  <TopBar />
                  <AddStandard />
                  <Footer />
                </>
              }
            />
            <Route
              path='/program/edit-standard'
              element={
                <>
                  <LeftsideMenu />
                  <TopBar />
                  <EditStandard />
                  <Footer />
                </>
              }
            />
            <Route
              path='/badge/badge-collection'
              element={
                <>
                  <LeftsideMenu />
                  <TopBar />
                  <BadgeList />
                  <Footer />
                </>
              }
            />
            <Route
              path='/badge/add-badge'
              element={
                <>
                  <LeftsideMenu />
                  <TopBar />
                  <AddBadge />
                  <Footer />
                </>
              }
            />
            <Route
              path='/badge/edit-badge'
              element={
                <>
                  <LeftsideMenu />
                  <TopBar />
                  <EditBadge />
                  <Footer />
                </>
              }
            />

            <Route
              path='/login'
              element={
                <>
                  <LoginPage />
                </>
              }
            />
          </Route>
          <Route path='*' element={<NotFoundView />} />
        </Routes>

      </React.Suspense>
    </div>
  );
}

export default App;
