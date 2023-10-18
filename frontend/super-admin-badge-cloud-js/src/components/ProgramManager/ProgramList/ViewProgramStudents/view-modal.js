import { makeStyles } from "@material-ui/core";
import { useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
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
    }
}));
export const ViewStudentModal = ({ record, rerenderFlag, setRerenderFlag }) => {
    const classes = useStyles();
    const { tenantId } = useSelector((state) => state.jwt);
    const [deleteStudent, SetDeleteStudent] = useState(null);
    const deleteStudentFromProgram = async (studentId) => {
        try {
            await ProgramApi.deleteStudentFromProgram(record?.id, studentId);
        }
        catch (error) {

        }
    }

    const disableStudentFromProgram = async (student) => {
        try {
            const body = {
                studentId: student.id,
                programId: record.id,
                tenantId,
                isEnableCollection: !student.status,
            }
            await ProgramApi.disableStudentFromProgram(body);
            setRerenderFlag(!rerenderFlag)
        }
        catch (error) {

        }
    }
    return (
        <div>
            <div className={`modal fade`} id="viewStudents" tabIndex={-1} aria-labelledby="viewStudentsLabel" aria-hidden={"true"}>
                <div className="modal-dialog modal-dialog-centered modal-lg">
                    <div className="modal-content">
                        <div className="modal-header modal-colored-header bg-primary">
                            <h4 className="modal-title" id="viewStudentsLabel">List of Students</h4>
                            <button type="button" className="btn-close" data-bs-dismiss="modal" aria-hidden="true" />
                        </div>
                        <div className="modal-body table-responsive">
                            <table className="table table-centered table-striped align-middle">
                                <tbody>
                                    {
                                        record?.students?.length > 0 &&
                                        record?.students.map(student =>
                                        (
                                            <tr key={student.id}>
                                                <td>{student.name}</td>
                                                <td>
                                                    <button className={`btn  btn-sm ${student?.status ?  'btn-outline-danger': 'btn-outline-success' } `} data-bs-dismiss="modal" onClick={() => disableStudentFromProgram(student)}>{student?.status ? "Disable collection": "Enable collection"}</button>
                                                </td>
                                                <td>
                                                    <a href="#deleteStudent" role="button" data-bs-toggle="modal" className="btn btn-danger btn-sm" onClick={() => SetDeleteStudent(student.id)}><i className="dripicons-trash" /></a>
                                                </td>
                                            </tr>
                                        )
                                        )
                                    }
                                    {
                                        !record?.students && <tr className={classes.noDataFound}>
                                            <td>No data found!</td></tr>
                                    }
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>


            {/* Delete Modal */}
            <div>
                <div id="deleteStudent" className="modal fade" tabIndex={-1} role="dialog" aria-hidden="true">
                    <div className="modal-dialog modal-sm">
                        <div className="modal-content">
                            <div className="modal-body p-4">
                                <div className="text-center">
                                    <i className="dripicons-warning h1 text-danger" />
                                    <h4 className="mt-2">Are you sure?</h4>
                                    <button type="button" className="btn btn-danger mx-1 my-2" data-bs-dismiss="modal" onClick={() => {
                                        deleteStudentFromProgram(deleteStudent);
                                        setRerenderFlag(!rerenderFlag)
                                    }
                                    }>Yes</button>
                                    <button type="button" className="btn btn-outline-danger mx-1 my-2" data-bs-dismiss="modal">No</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>

    );
}