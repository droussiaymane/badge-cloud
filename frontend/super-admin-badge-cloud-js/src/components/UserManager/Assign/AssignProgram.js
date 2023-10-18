import { makeStyles } from "@material-ui/core";
import { ErrorMessage, Form, Formik } from 'formik';
import { useEffect } from "react";
import { useState } from "react";
import { useDispatch, useSelector } from 'react-redux';
import { useLocation, useNavigate } from "react-router-dom";
import * as Yup from 'yup';
import Select from 'react-select';
import { UserApi } from "../../../api/userApi";
import { ProgramApi } from "../../../api/ProgramAPI";
import { toastActions } from "../../../Redux/store/Slices/toast";
const renderError = (message) => (
    <p className='help is-danger' style={{ color: 'red' }}>
        {message}
    </p>
);
const useStyles = makeStyles(() => ({
    center: {
        textAlign: 'center',
        padding: 10,
    }
}));

const AssignProgram = () => {
    const classes = useStyles();
    const location = useLocation();
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const validationSchema = Yup.object().shape({
        assignedProgramIds: Yup.array().min(1, 'Available  Program is required!')
    });
    const { tenantId } = useSelector((state) => state.jwt);
    const [availableOption, setAvailableOption] = useState([]);
    const [availableOptions, setAvailableOptions] = useState([]);
    const [currentOption, setCurrentOption] = useState([]);
    const [assignProgram, setAssignProgram] = useState([]);
    const [currentProgramId, setCurrentProgramId] = useState('');

    const initialValues = {
        studentId: location.state.id || '',
        currentProgramIds: [],
        assignedProgramIds: assignProgram
    };
    const getStudentPrograms = async () => {
        try {
            const response = await UserApi.getStudentPrograms(location.state.id)
            if (response?.availablePrograms.length > 0) {
                const data = response.availablePrograms.map((item) => {
                    return {
                        value: item.id,
                        label: item.programName
                    }
                })
                setAvailableOptions(data)
            }
            else setAvailableOptions([]);
            if (response?.currentPrograms.length > 0) {
                setCurrentOption(response.currentPrograms)
            }
            else setCurrentOption([]);
        }
        catch (error) {
            console.log(error);
            setCurrentOption([]);
            setAvailableOptions([]);
        }
    }

    const getAndDeleteById = async () => {
        try {
            const body = {
                programId: currentProgramId,
                studentId: location.state.id,
                tenantId,
            }
            await ProgramApi.deleteProgramFromStudent(body);
            window.location.reload();
        } catch (error) { }
    };


    useEffect(() => {
        getStudentPrograms();
    }, [])
    return (
        <Formik
            initialValues={initialValues}
            validationSchema={validationSchema}
            enableReinitialize
            onSubmit={async (
                values,
                { setSubmitting, resetForm, setErrors, setStatus }
            ) => {
                try {
                    setSubmitting(true);

                    values.currentProgramIds = currentOption.flatMap(item => item.id);
                    const res = await ProgramApi.assignProgram(values.currentProgramIds, values.assignedProgramIds, values.studentId);
                    console.log("response", res);
                    await dispatch(
                        toastActions.succesMessage({
                            message: res.message,
                        })
                    );
                    setStatus({ success: true });
                    setSubmitting(false);
                    resetForm();
                    navigate('/user-manager');
                } catch (error) {
                    await dispatch(
                        toastActions.errorMessage({
                            message: error.message,
                        })
                    );
                    setStatus({ success: false });
                    setErrors({ submit: error.message });
                    setSubmitting(false);
                    console.log('Error', error);
                }
            }}
        >
            {({
                isSubmitting,
                errors,
                handleBlur,
                handleChange,
                handleSubmit,
                touched,
                values,
            }) => (
                <Form>
                    <div className="content-page">
                        <div className="content">
                            <div className="container-fluid">
                                {/* start page title */}
                                <div className="row">
                                    <div className="col-12">
                                        <div className="page-title-box">
                                            <div className="page-title-right">
                                                <a href="/user-manager" className="btn btn-primary">Go To User List</a>
                                            </div>
                                            <h4 className="page-title">Assign Program</h4>
                                        </div>
                                    </div>
                                </div>
                                {/* end page title */}
                                <div className="row">
                                    <div className="col-lg-6 d-grid">
                                        <div className="card">
                                            <div className="card-body">
                                                <div className="row">
                                                    <div className="col-lg-12">
                                                        <div className="mb-3">
                                                            <label htmlFor="select_additional_details" className="form-label">Available Programs
                                                            </label>
                                                            <Select
                                                                isMulti
                                                                name="assignedProgramIds"
                                                                value={availableOption}
                                                                onChange={(e) => {
                                                                    setAvailableOption(e);
                                                                    const data = e.flatMap(item => item.value);
                                                                    values.assignedProgramIds = data;
                                                                    setAssignProgram(data);
                                                                }}
                                                                options={availableOptions}
                                                                className="basic-multi-select"
                                                                classNamePrefix="select"
                                                            />
                                                            <ErrorMessage name='assignedProgramIds' render={renderError} />
                                                        </div>
                                                    </div>
                                                </div>
                                                <button className="btn btn-primary " type="submit">Save Changes</button>
                                            </div> {/* end card-body*/}
                                        </div> {/* end card*/}
                                    </div> {/* end col*/}
                                    <div className="col-lg-6 d-grid">
                                        <div className="card">
                                            <div className="card-body">
                                                <table className="table table-striped table-centered mb-0">
                                                    <thead>
                                                        <tr>
                                                            <th>Current Program</th>
                                                            <th>Action</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        {
                                                            currentOption?.length > 0 && currentOption?.map((currentProgram) => {
                                                                return (
                                                                    <tr>
                                                                        <td>{currentProgram.programName}</td>
                                                                        <td className="table-action">
                                                                            <a href="#delete" data-bs-toggle="modal" role="button" className="action-icon"><i className="dripicons-trash text-danger" onClick={() => setCurrentProgramId(currentProgram.id)} /></a>
                                                                        </td>
                                                                    </tr>
                                                                )
                                                            })
                                                        }
                                                    </tbody>
                                                </table>
                                                {
                                                    currentOption.length < 1 &&
                                                    <div className={classes.center}>
                                                        No data found!
                                                    </div>
                                                }
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        {/* Delete Modal */}
                        <div>
                            <div id="delete" className="modal fade" tabIndex={-1} role="dialog" aria-hidden="true">
                                <div className="modal-dialog modal-sm">
                                    <div className="modal-content">
                                        <div className="modal-body p-4">
                                            <div className="text-center">
                                                <i className="dripicons-warning h1 text-danger" />
                                                <h4 className="mt-2">Are you sure?</h4>
                                                <button type="button" className="btn btn-danger mx-1 my-2" data-bs-dismiss="modal" onClick={() => getAndDeleteById()}>Yes</button>
                                                <button type="button" className="btn btn-outline-danger mx-1 my-2" data-bs-dismiss="modal">No</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </Form>
            )
            }
        </Formik >
    );
}

export default AssignProgram;