import { makeStyles } from "@material-ui/core";
import { useState } from "react";

import { isUndefined } from 'lodash';
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
export const EditRevokeStatus = ({ record, rerenderFlag, setRerenderFlag }) => {
    const classes = useStyles();
    const navigate = useNavigate();
    const [revokeStatus, setRevokeStatus] = useState(record?.revokeStatus);
    const [textareaValue, setTextareaValue] = useState('');
    const [isSubmitting, setIsSubmitting] = useState(false)

    const handleSubmit = async (event) => {
        event.preventDefault();
        setIsSubmitting(true);
        const data = {};
        if (revokeStatus != null) {
            data['revokeStatus'] = revokeStatus;
        }
        if (textareaValue.trim() !== '') {
            data['revokeReason'] = textareaValue;
        }
        data['studentId'] = record.studentId;
        data['programId'] = record.programId;
        console.log(data);
        const response = await ProgramApi.updateRevokeStatusOrExpirationDate(data);
        setRerenderFlag(!rerenderFlag);
        setIsSubmitting(false);
        navigate(`/view/list-of-students?program-id=${record?.programId}`)

    }

    console.log("records", textareaValue, "revokeStatus", revokeStatus);
    return (
        <div>
            <div className={`modal fade`} id="viewStudentRevokeStatus" tabIndex={-1} aria-labelledby="viewStudentsLabel" aria-hidden={"true"}>
                <div className="modal-dialog modal-dialog-centered modal-lg">
                    <div className="modal-content">
                        <div className="row">
                            <div className="col-lg-12">
                                <div className="card">
                                    <div className="modal-header modal-colored-header bg-primary">
                                        <h4 className="modal-title" id="viewStudentsLabel">Edit Revoke Status</h4>
                                        <button type="button" className="btn-close" data-bs-dismiss="modal" aria-hidden="true" />
                                    </div>
                                    <div className={classes.switch}>
                                        <h6 className={`${classes.revokeStatusTitle} mb-3`}>Revoke Status</h6>
                                        <input
                                            type="checkbox"
                                            id={record?.studentId}
                                            data-switch="primary"
                                            defaultChecked={record?.revokeStatus}
                                            onChange={() => setRevokeStatus(!revokeStatus)}
                                        />
                                        <label htmlFor={record?.studentId} data-on-label="On" data-off-label="Off"></label>
                                    </div>

                                    <div style={{ display: revokeStatus ? "block" : "none" }}>
                                        <h6 className={`${classes.revokeStatusTitle} mb-3`}>Revoke Reason</h6>
                                        <div className={classes.textElem}>
                                <textarea
                                    defaultValue={record?.revokeReason}
                                    className={`${classes.textArea} form-control`}
                                    name="revokeReason"
                                    rows="4"
                                    onChange={(e) => setTextareaValue(e.target.value)}
                                />
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