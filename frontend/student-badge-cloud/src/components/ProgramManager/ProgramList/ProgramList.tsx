
import type { FC } from 'react';
import { Link } from 'react-router-dom';
const ProgramList: FC = () => {
    return (
        <div className="content-page">
            <div className="content">
                <div className="container-fluid">
                    {/* start page title */}
                    <div className="row">
                        <div className="col-12">
                            <div className="page-title-box">
                                <div className="page-title-right">
                                    <Link to='/program/add-program' className="btn btn-primary">Add Program </Link>
                                </div>
                                <h4 className="page-title">Program List</h4>
                            </div>
                        </div>
                    </div>
                    {/* end page title */}
                    <div className="row">
                        <div className="col-12">
                            <div className="card">
                                <div className="card-body">
                                    <table id="basic-datatable" className="table table-striped dt-responsive nowrap align-middle w-100">
                                        <thead>
                                            <tr>
                                                <th>Program Name</th>
                                                <th>Start Date</th>
                                                <th>End Date</th>
                                                <th>Instructor(s)</th>
                                                <th className="sorting_disabled action-datatable">Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>IT Security Audit</td>
                                                <td>08/02/2022</td>
                                                <td>08/02/2022</td>
                                                <td>Admin</td>
                                                <td>
                                                    <div className="dropdown">
                                                        <a href="#" className="dropdown-toggle arrow-none card-drop p-0" data-bs-toggle="dropdown" aria-expanded="false">
                                                            <i className="mdi mdi-dots-vertical" />
                                                        </a>
                                                        <div className="dropdown-menu dropdown-menu-animated">
                                                            <a href="#viewStudents" data-bs-toggle="modal" role="button" className="dropdown-item">View students</a>
                                                            <a href="#viewCollections" data-bs-toggle="modal" role="button" className="dropdown-item">View collections</a>
                                                            <a href="#" className="dropdown-item">Import definition</a>
                                                            <a href="#" className="dropdown-item">Export Definition</a>
                                                            <a href="edit-program.html" className="dropdown-item">Edit</a>
                                                            <a href="#delete" data-bs-toggle="modal" role="button" className="dropdown-item text-danger">Delete</a>
                                                        </div>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Security Audit</td>
                                                <td>08/02/2022</td>
                                                <td>08/02/2022</td>
                                                <td>Admin</td>
                                                <td>
                                                    <div className="dropdown">
                                                        <a href="#" className="dropdown-toggle arrow-none card-drop p-0" data-bs-toggle="dropdown" aria-expanded="false">
                                                            <i className="mdi mdi-dots-vertical" />
                                                        </a>
                                                        <div className="dropdown-menu dropdown-menu-animated">
                                                            <a href="#viewStudents" data-bs-toggle="modal" role="button" className="dropdown-item">View students</a>
                                                            <a href="#viewCollections" data-bs-toggle="modal" role="button" className="dropdown-item">View collections</a>
                                                            <a href="#" className="dropdown-item">Import definition</a>
                                                            <a href="#" className="dropdown-item">Export Definition</a>
                                                            <a href="edit-program.html" className="dropdown-item">Edit</a>
                                                            <a href="#delete" data-bs-toggle="modal" role="button" className="dropdown-item text-danger">Delete</a>
                                                        </div>
                                                    </div>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div> {/* end card body*/}
                            </div> {/* end card */}
                        </div>{/* end col*/}
                    </div> {/* end row*/}
                </div> {/* container */}
            </div> {/* content */}
            {/* View students modal */}
            <div>
                <div className="modal fade" id="viewStudents" tabIndex={-1} aria-labelledby="viewStudentsLabel" aria-hidden="true">
                    <div className="modal-dialog modal-dialog-centered modal-lg">
                        <div className="modal-content">
                            <div className="modal-header modal-colored-header bg-primary">
                                <h4 className="modal-title" id="viewStudentsLabel">List of Students</h4>
                                <button type="button" className="btn-close" data-bs-dismiss="modal" aria-hidden="true" />
                            </div>
                            <div className="modal-body table-responsive">
                                <table className="table table-centered table-striped align-middle">
                                    <tbody>
                                        <tr>
                                            <td>Nana Friddausi Mohammed</td>
                                            <td>
                                                <button className="btn btn-outline-primary btn-sm">Disable collection</button>
                                            </td>
                                            <td>
                                                <a href="#delete" role="button" data-bs-toggle="modal" className="btn btn-danger btn-sm"><i className="dripicons-trash" /></a>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Gloria Noah</td>
                                            <td>
                                                <button className="btn btn-outline-primary btn-sm">Disable collection</button>
                                            </td>
                                            <td>
                                                <a href="#delete" role="button" data-bs-toggle="modal" className="btn btn-danger btn-sm"><i className="dripicons-trash" /></a>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            {/* View collections modal */}
            <div>
                <div className="modal fade" id="viewCollections" tabIndex={-1} aria-labelledby="viewCollectionsLabel" aria-hidden="true">
                    <div className="modal-dialog modal-dialog-centered">
                        <div className="modal-content">
                            <div className="modal-header modal-colored-header bg-primary">
                                <h4 className="modal-title" id="viewCollectionsLabel">Program Name Here</h4>
                                <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close" />
                            </div>
                            <div className="modal-body">
                                <div className="row justify-content-center">
                                    <div className="col-md-8">
                                        <div className="my-3 px-4 py-3 d-flex justify-content-center align-items-center">
                                            <img src="../assets/img/cybergirls-blue-brown.png" className="img-fluid" alt="badge" />
                                        </div>
                                    </div>
                                    <div className="col-md-8">
                                        <div className="my-3 px-4 py-3 d-flex justify-content-center align-items-center">
                                            <img src="../assets/img/Cyber Girls Oracle.png" className="img-fluid" alt="badge" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            {/* modal for view collections */}
            {/* Delete Modal */}
            <div>
                <div id="delete" className="modal fade" tabIndex={-1} role="dialog" aria-hidden="true">
                    <div className="modal-dialog modal-sm">
                        <div className="modal-content">
                            <div className="modal-body p-4">
                                <div className="text-center">
                                    <i className="dripicons-warning h1 text-danger" />
                                    <h4 className="mt-2">Are you sure?</h4>
                                    <button type="button" className="btn btn-danger mx-1 my-2" data-bs-dismiss="modal">Yes</button>
                                    <button type="button" className="btn btn-outline-danger mx-1 my-2" data-bs-dismiss="modal">No</button>
                                </div>
                            </div>
                        </div>{/* /.modal-content */}
                    </div>{/* /.modal-dialog */}
                </div>{/* /.modal */}
            </div>
        </div>
    )
};

export default ProgramList;
