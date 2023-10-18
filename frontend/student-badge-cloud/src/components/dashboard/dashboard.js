import {useEffect} from "react";
import {useState} from "react";
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import {useSelector} from "react-redux";
import {studentApi} from "../../api/userApi";
import CapitalizeWords from "../../utils/capitalizeWords";


const DashBoard = () => {
    const [date, setdata] = useState(new Date());
    const imgBaseUrl = process.env.REACT_APP_PUBLIC_BADGE_URL;
    const {userId, username} = useSelector((state) => state.jwt);
    const [records, setRecords] = useState([]);
    const [openBadgeView, setOpenBadgeView] = useState({});
    const getEarnedBadges = async () => {
        try {
            const response = await studentApi.getEarnedBadges(userId);
            setRecords(response?.data || []);
            console.log("response", response)
        } catch (error) {
            console.log("error", error);
        }
    }

    const getEarnedOpenBadgeView = async (programId) => {
        try {
            const response = await studentApi.getEarnedOpenBadgeView(userId, programId);
            setOpenBadgeView(response?.data || {});
            console.log(" getEarnedOpenBadgeView response", response)
        } catch (error) {
            console.log("error", error);
        }
    }
    const downloadOpenBadge = async (programId) => {
        try {
            const response = await studentApi.downloadOpenBadge(userId, programId);
            setOpenBadgeView(response?.data || {});
            console.log(" getEarnedOpenBadgeView response", response)
        } catch (error) {
            console.log("error", error);
        }
    }

    useEffect(() => {
        getEarnedBadges()
    }, []);
    console.log("records", records)
    return (

        <div className="content-page">
            <div className="content">
                <div className="container-fluid">
                    {/* start page title */}
                    <div className="row">
                        <div className="col-12">
                            <div className="page-title-box">
                                <h4 className="page-title">Welcome Back, <span>{CapitalizeWords(username)}</span></h4>
                            </div>
                        </div>
                    </div>
                    {/* end page title */}
                    <div className="row">
                        <div className="col-md-7 d-grid">
                            <div className="card">
                                <div className="card-body">
                                    <div className="row">
                                        <div
                                            className="col-2 col-xxl-1 px-0 d-flex align-items-start justify-content-center">
                                            <i className=" uil-sign-right widget-icon"/>
                                        </div>
                                        <div className="col-10 col-xxl-11">
                                            <h4 className="mb-3">My learning path</h4>
                                            <p>IT Security Audit</p>
                                            <p>Incident Analysis/Response</p>
                                            <p>Penetration/Vulnerability Testing</p>
                                            <p>Threat Intelligence</p>
                                            <p>Web Application Testing</p>
                                            <p>IT Security Audit</p>
                                            <p>Incident Analysis/Response</p>
                                            <p>Penetration/Vulnerability Testing</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="col-md-5">
                            <div className="card">
                                <div className="card-body pb-0">
                                    <div className="d-flex justify-content-between align-items-center">
                                        <h4 className="header-title">Calendar</h4>
                                    </div>
                                </div>
                                <div className="card-body px-2 pb-2 pt-0">
                                    <Calendar onChange={setdata} value={date}/>
                                    {/* <div data-provide="datepicker-inline" data-date-today-highlight="true" className="calendar-widget" /> */}
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-12">
                            <div className="card">
                                <div className="card-body">
                                    <h4 className="mb-3">Badges earned</h4>
                                    <div className="row">
                                        {records.length > 0 && records.map((item) => {
                                            return (
                                                <div className="col-md-6 col-xl-4">
                                                    <div className="card">
                                                        <div className="card-body px-5 py-4">
                                                            <div className="dropdown float-end">
                                                                <a href="#"
                                                                   className="dropdown-toggle arrow-none card-drop position-absolute p-0"
                                                                   data-bs-toggle="dropdown" aria-expanded="false">
                                                                    <i className="mdi mdi-dots-vertical"/>
                                                                </a>
                                                                <div className="dropdown-menu dropdown-menu-animated">
                                                                    <a href="#viewBadgeInfo" data-bs-toggle="modal"
                                                                       role="button" className="dropdown-item"
                                                                       onClick={() => getEarnedOpenBadgeView(item.programId)}>View
                                                                        badge info</a>
                                                                    <a
                                                                        href="#"
                                                                        // href={imgBaseUrl + item?.image}
                                                                        className="dropdown-item"
                                                                        onClick={() => downloadOpenBadge(item.programId)}>Download
                                                                        badge</a>
                                                                    <a href="#" className="dropdown-item disabled">Export
                                                                        badge</a>
                                                                </div>
                                                            </div>
                                                            <img src={imgBaseUrl + item?.image} className="img-fluid"
                                                                 alt="badge"/>
                                                        </div>

                                                    </div>
                                                </div>
                                            )
                                        })
                                        }
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                {/* container */}
            </div>
            {/* content */}
            {/* view tag modal */}
            <div>
                <div className="modal fade" id="viewBadgeInfo" tabIndex={-1} aria-labelledby="viewBadgeInfoLabel"
                     aria-hidden="true">
                    <div className="modal-dialog modal-dialog-centered modal-lg">
                        <div className="modal-content">
                            <div className="modal-header modal-colored-header bg-primary">
                                <h5 className="modal-title" id="viewBadgeInfoLabel">View Badge Info</h5>
                                <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"/>
                            </div>
                            <div className="modal-body" style={{padding: '1rem 2rem 2rem'}}>
                                <div className="row">
                                    <div className="col-md-12 pt-4 table-responsive">

                                        <div className="modal-body" style={{padding: '30px 20px'}}>
                                            <div className="row">
                                                <div className="col-md-4 pe-3 text-center">
                                                    <img
                                                        src={imgBaseUrl + openBadgeView?.photo}
                                                        className="img-fluid badge-img" alt="Badge"/>
                                                </div>
                                                <div className="col-md-8 ps-md-3">
                                                    {/*<h5 className="mb-2">Name: </h5><span>{b.name}</span> */}

                                                    <div className="mb-3">
                                                        <p><span className="fw-bold">Name :</span>
                                                            <i>{openBadgeView?.name}</i></p>
                                                    </div>
                                                    <div className="row">
                                                        <div className="col-md-12">
                                                            <div className="mb-3">
                                                                <p className="mb-2 fw-bold">Description :</p>
                                                                <i><p>{openBadgeView?.description}</p></i>
                                                            </div>
                                                            <div className="mb-3">
                                                                <p><span className="fw-bold">Issuer Name :</span>
                                                                    <i>{openBadgeView?.issuerName}</i></p>
                                                            </div>
                                                            <div className="mb-3">
                                                                <p><span className="fw-bold">IssuedOn Date :</span>
                                                                    <i>{openBadgeView?.issuedOn}</i></p>
                                                            </div>

                                                            <div className="mb-3">
                                                                <p><span className="fw-bold">Expiration Status :</span>
                                                                    <i>{openBadgeView?.expirationStatus ? 'TRUE' : 'FALSE'}</i>
                                                                </p>
                                                            </div>

                                                            {openBadgeView?.expirationDate !== 'Does Not Expire' && openBadgeView.expirationStatus && (
                                                                <div className="mb-3">
                                                                    <p><span
                                                                        className="fw-bold">Expiration Date :</span>
                                                                        <i>{openBadgeView?.expirationDate}</i></p>
                                                                </div>
                                                            )}

                                                            <div className="mb-3">
                                                                <p><span className="fw-bold">Revoked Status :</span>
                                                                    <i>{openBadgeView?.revokedStatus ? 'TRUE' : 'FALSE'}</i>
                                                                </p>
                                                            </div>
                                                            {openBadgeView?.revokedStatus && (
                                                                <div className="mb-3">
                                                                    <p className="mb-2 fw-bold">Revoked Reason :</p>
                                                                    <i><p>{openBadgeView?.revokedReason}</p></i>
                                                                </div>
                                                            )}
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
                </div>
            </div>
        </div>
    )
};
export default DashBoard;
