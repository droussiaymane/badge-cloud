import { makeStyles } from "@material-ui/core";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { ProgramApi } from "../../../../api/ProgramAPI";
const useStyles = makeStyles((theme) => ({
    noDataFound: {
        textAlign: 'center'
    },
    blockDisplay: {
        display: "block",
    },
    noneDisplay: {
        display: "none",
    },
    switch: {
        // display: "flex",
        alignItems: "center",
        textAlign: "center",
        marginBottom: "1.2rem",
    },
    revokeStatusTitle: {
        fontSize: '20px',
        textAlign: "center",
    },
    textElem: {
        margin: "1.5rem",
    },
    textArea: {
        minHeight: "calc(1.5em + 0.75rem + 2px)",
        height: '30vh'
    }
}));
export const ExpirationDateModal = ({ record, rerenderFlag, setRerenderFlag }) => {
    console.log("status", record)
    const classes = useStyles();
    const navigate = useNavigate();
    const [expiresStatus, setExpiresStatus] = useState(record.expiresStatus);
    const [expirationDate, setExpirationDate] = useState('');
    const [isSubmitting, setIsSubmitting] = useState(false)

    const handleSubmit = async (event) => {
        event.preventDefault();
        // setIsSubmitting(true);
        const data = {};
        if (expiresStatus != null) {
            data['expiresStatus'] = expiresStatus;
        }
        if (expirationDate.trim() != '') {
            data['expirationDate'] = expirationDate;
        }
        data['studentId'] = record.studentId;
        data['programId'] = record.programId;
        const response = await ProgramApi.updateRevokeStatusOrExpirationDate(data);
        // setIsSubmitting(false);
        setRerenderFlag(!rerenderFlag);
        navigate(`/view/list-of-students?program-id=${record?.programId}`)
    }
    console.log("expiresStatus", expiresStatus)
    return (
        <div>
            <div className={`modal fade`} id="viewStudentExpirationStatus" tabIndex={-1} aria-labelledby="viewStudentsLabel" aria-hidden={"true"}>
                <div className="modal-dialog modal-dialog-centered modal-lg">
                    <div className="modal-content">
                        <div className="row">
                            <div className="col-lg-12">
                                <div className="card">
                                    <div className="modal-header modal-colored-header bg-primary">
                                        <h4 className="modal-title" id="viewStudentsLabel">Edit Expiration Date</h4>
                                        <button type="button" className="btn-close" data-bs-dismiss="modal" aria-hidden="true" />
                                    </div>
                                    <div className={classes.switch}>
                                        <h6 className={`${classes.revokeStatusTitle} mb-3`}>Expiration Date Status</h6>
                                        <input
                                            type="checkbox"
                                            id={record?.studentId + record?.programId}
                                            data-switch="primary"
                                            defaultChecked={record?.expiresStatus}
                                            onClick={(event) => setExpiresStatus(event.target.checked)}
                                        />
                                        <label htmlFor={record?.studentId + record?.programId} data-on-label="On" data-off-label="Off" />
                                    </div>

                                    <div style={{ display: expiresStatus ? "block" : "none" }}>
                                        <h6 className={`${classes.revokeStatusTitle} mb-3`}>Expiration Date</h6>
                                        <div className={classes.textElem}>
                                            <div className="col-lg-12">
                                                <div className="position-relative" id="start_date">
                                                    <input
                                                        type='text'
                                                        className="form-control"
                                                        htmlFor="start_date"
                                                        defaultValue={record?.expires || ''}
                                                        onSelectCapture={(event) => setExpirationDate(event.target.value)}
                                                        name='startDate'
                                                        data-provide="datepicker"
                                                    />
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div className="card-body">
                                        <button onClick={(event) => handleSubmit(event)} className="btn btn-primary me-2" type="submit" data-bs-dismiss="modal" aria-hidden="true">Submit</button>
                                        <Link onClick={() => navigate(`/view/list-of-students?program-id=${record?.programId}`)} className="btn btn-outline-primary" data-bs-dismiss="modal" aria-hidden="true">Cancel </Link>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    );
}