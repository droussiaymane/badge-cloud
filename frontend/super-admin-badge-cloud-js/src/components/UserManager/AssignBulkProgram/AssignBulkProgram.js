
import { makeStyles } from '@material-ui/core';
import { useEffect, useState, useRef } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import * as Yup from 'yup';
import { UserApi } from '../../../api/userApi';
import { toastActions } from '../../../Redux/store/Slices/toast';


const useStyles = makeStyles((theme) => ({
    img: {
        width: '150px !important',
        margin: "20px 0px",
        height: '150px !important',
    },
}));
const AssignBulkProgram = () => {
    const classes = useStyles();
    const dispatch = useDispatch();

    const [selectedFile, setSelectedFile] = useState(null);
    const { tenantId } = useSelector((state) => state.jwt);
    const inputFileRef = useRef(null);
    const handleSubmit = async (event) => {
        event.preventDefault();

        const formData = new FormData();
        formData.append('file', selectedFile);

        try {
            console.log(tenantId);
            const res = await UserApi.assignBulkProgram(formData, tenantId);
            console.log(res.data);
            await dispatch(
                toastActions.succesMessage({
                    message: res.message,
                })

            );
            handleReset();
            if (res == null) {
                toastActions.errorMessage({
                    message: 'Something went wrong please try again.',
                })
            }
            // Handle the response data as needed
        } catch (error) {
            console.error(error);
            toastActions.errorMessage({
                message: 'Something went wrong please try again.',
            })
            // Handle any errors that occurred during the request
        }
    };

    const handleFileChange = (event) => {
        setSelectedFile(event.target.files[0]);
    };

    const handleReset = () => {
        setSelectedFile(null);
        if (inputFileRef.current) {
            inputFileRef.current.value = null;
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <div className="content-page">
                <div className="content">
                    <div className="container-fluid">
                        <div className="row">
                            <div className="col-12">
                                <div className="page-title-box">
                                    <div className="page-title-right">
                                        <Link to="/user-manager" className="btn btn-primary">Go To User List</Link>
                                    </div>
                                    <h4 className="page-title">Assign Bulk Program</h4>
                                </div>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-lg-12">
                                <div className="card">
                                    <div className="card-body">
                                        <div className="row mb-4">
                                            <div className="col-lg-12">
                                                <h4 className="header-title mb-3">Select CSV File</h4>
                                                <p className="fw-semibold mb-2">Choose a file from your device</p>
                                                <div className="tower-file">
                                                    <input
                                                        type="file"
                                                        id="profile-picture"
                                                        accept="text/csv"
                                                        name="users-csv"
                                                        onChange={handleFileChange}
                                                        style={{ display: "none" }}
                                                        ref={inputFileRef}
                                                    />
                                                    {selectedFile && <p>Selected File: {selectedFile.name}</p>}
                                                    <label htmlFor="profile-picture" className="btn btn-outline-primary mb-2 me-2">
                                                        Select a File
                                                    </label>
                                                    <button type="button" className="tower-file-clear btn btn-outline-danger mb-2" onClick={handleReset}>
                                                        Clear
                                                    </button>

                                                </div>
                                            </div>
                                        </div>
                                        <button className="btn btn-primary me-2" type="submit">Submit</button>
                                        <Link to="/user-manager" className="btn btn-outline-primary">Cancel</Link>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </form>
    )
};

export default AssignBulkProgram;
