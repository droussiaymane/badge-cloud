import { useSelector } from 'react-redux';
import { useState } from 'react';
import { useEffect } from 'react';
import capitalizeWords from '../../utils/capitalizeWords';
import { Footer } from '../Footer';
import { LeftsideMenu } from '../LeftsideMenu';
import TopBar from '../Topbar';
import { UserApi } from '../../api/userApi';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import './dashboard.css'
const DashBoard = () => {
  const {username} = useSelector(state=>state.jwt);
  const [studentCount, setStudentCount] = useState(0);
  const [collectionCount, setCollectionCount] = useState(0);
  const [instructorCount, setInstructorCount] = useState(0);
  const [programCount, setProgramCount] = useState(0);
  const [script, setScript] = useState(0);
  useEffect(() => {
    const script = document.createElement('script');
    script.async = true;
    script.src = 'https://srv2.weatherwidget.org/js/?id=ww_a1deb85eb85e2';
    setScript(script);
    console.log('this is script', script);
    document.body.appendChild(script);

    return () => {
      document.body.removeChild(script);
    };
  }, []);



  const getStatistics = async () => {
    try {
      const response = await UserApi.getDashboardStatistics();
      console.log('response', response);
      setStudentCount(response?.studentCount || 0);
      setCollectionCount(response?.collectionCount || 0);
      setInstructorCount(response?.instructorCount || 0);
      setProgramCount(response?.programCount || 0);
    } catch (error) {
      setStudentCount(0);
      setCollectionCount(0);
      setInstructorCount(0);
      setProgramCount(0);
    }
  };
  useEffect(() => {
    getStatistics();
  }, [studentCount]);
  return (
    <>
      <LeftsideMenu />
      <TopBar />
      <div className='content-page'>
        <div className='content'>
          <div className='container-fluid'>
            <div className='row'>
              <div className='col-12'>
                <div className='page-title-box'>
                  <h4 className='page-title'>
                   {capitalizeWords(username)}
                    , Welcome Back!
                  </h4>
                </div>
              </div>
            </div>
            {/* end page title */}
            <div className='row'>
              <div className='col-sm-6'>
                <div className='card widget-flat'>
                  <div className='card-body'>
                    <div className='float-end'>
                      <i className='mdi mdi-format-list-bulleted widget-icon' />
                    </div>
                    <h5
                      className='text-muted fw-normal mt-0'
                      title='Number of Customers'
                    >
                      Number of programs
                    </h5>
                    <h3 className='mt-3 mb-3'>{programCount}</h3>
                  </div>{' '}
                </div>{' '}
              </div>{' '}
              <div className='col-sm-6'>
                <div className='card widget-flat'>
                  <div className='card-body'>
                    <div className='float-end'>
                      <i className='mdi mdi-view-grid-plus-outline widget-icon' />
                    </div>
                    <h5
                      className='text-muted fw-normal mt-0'
                      title='Number of Orders'
                    >
                      Number of collections
                    </h5>
                    <h3 className='mt-3 mb-3'>{collectionCount}</h3>
                  </div>
                </div>{' '}
              </div>{' '}
            </div>
            <div className='row'>
              <div className='col-sm-6'>
                <div className='card widget-flat'>
                  <div className='card-body'>
                    <div className='float-end'>
                      <i className='mdi mdi-account-tie-outline widget-icon' />
                    </div>
                    <h5
                      className='text-muted fw-normal mt-0'
                      title='Number of Customers'
                    >
                      Number of instructors
                    </h5>
                    <h3 className='mt-3 mb-3'>{instructorCount}</h3>
                  </div>
                </div>
              </div>
              <div className='col-sm-6'>
                <div className='card widget-flat'>
                  <div className='card-body'>
                    <div className='float-end'>
                      <i className='dripicons-graduation widget-icon' />
                    </div>
                    <h5
                      className='text-muted fw-normal mt-0'
                      title='Number of Orders'
                    >
                      Number of students
                    </h5>
                    <h3 className='mt-3 mb-3'>{studentCount}</h3>
                  </div>
                </div>
              </div>
            </div>
            <div className='row'>
              <div className='col-md-6'>
                <div className='card'>
                  <div className='card-body pb-0'>
                    <div className='d-flex justify-content-between align-items-center'>
                      <h4 className='header-title'>Calendar</h4>
                    </div>

                    <div className="datepicker-container">
                      <DatePicker
                        highlightDates={[new Date()]}
                        inline
                        calendarClassName="custom-calendar"
                        nextMonthButtonLabel=">>"
                        previousYearButtonLabel="<<"
                      />
                    </div>
                  </div>

                </div>
              </div>
              <div className='col-md-6'>
                <div className='card'>
                  <div className='card-body pb-0'>
                    <h4 className='header-title'>Weather</h4>
                    <div className='d-flex justify-content-between align-items-center'>

                      <div className="card-body pt-2">
                        <div
                          id="ww_a1deb85eb85e2"
                          v="1.20"
                          loc="auto"
                          a='{"t":"horizontal","lang":"en","ids":[],"cl_bkg":"image","cl_font":"#FFFFFF","cl_cloud":"#FFFFFF",
		  "cl_persp":"#81D4FA","cl_sun":"#FFC107","cl_moon":"#FFFFFF","cl_thund":"#5DA1FF","sl_sot":"celsius",
		  "sl_ics":"one","font":"Arial","el_whr":3,"el_phw":3}'
                        >
                          <a
                            href="https://weatherwidget.org/"
                            id="ww_a1deb85eb85e2_u"
                            target="_blank"
                            style={{ color: '#fafbfe' }}
                          >
                            Weather widget html for website by Weatherwidget.org
                          </a>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </>
  );
};

export default DashBoard;
