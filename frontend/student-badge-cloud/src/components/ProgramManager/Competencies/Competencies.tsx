
import type { FC } from 'react';
import { Link } from 'react-router-dom';
const Competencies: FC = () => {

    return (

        <div className="content-page">
            <div>
                <div className="content">
                    <div className="container-fluid">
                        {/* start page title */}
                        <div className="row">
                            <div className="col-12">
                                <div className="page-title-box">
                                    <div className="page-title-right">
                                        <Link to="/program/add-competencies" className="btn btn-primary">Add Competency</Link>
                                    </div>
                                    <h4 className="page-title">Competency List</h4>
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
                                                    <th>Order</th>
                                                    <th>Competency Name</th>
                                                    <th className="sorting_disabled action-datatable">isVizzibl</th>
                                                    <th className="sorting_disabled action-datatable">Action</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>1</td>
                                                    <td>Linux</td>
                                                    <td>
                                                        <input type="checkbox" id="switch1" data-switch="primary" />
                                                        <label htmlFor="switch1" data-on-label="On" data-off-label="Off" />
                                                    </td>
                                                    <td>
                                                        <div className="dropdown">
                                                            <a href="#" className="dropdown-toggle arrow-none card-drop p-0" data-bs-toggle="dropdown" aria-expanded="false">
                                                                <i className="mdi mdi-dots-vertical" />
                                                            </a>
                                                            <div className="dropdown-menu dropdown-menu-animated">
                                                                <a href="#viewPrograms" data-bs-toggle="modal" role="button" className="dropdown-item">View programs</a>
                                                                <a href="edit-competency.html" className="dropdown-item">Edit</a>
                                                                <a href="#delete" data-bs-toggle="modal" role="button" className="dropdown-item text-danger">Delete</a>
                                                            </div>
                                                        </div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>2</td>
                                                    <td>Security Audit</td>
                                                    <td>
                                                        <input type="checkbox" id="switch2" data-switch="primary" />
                                                        <label htmlFor="switch2" data-on-label="On" data-off-label="Off" />
                                                    </td>
                                                    <td>
                                                        <div className="dropdown">
                                                            <a href="#" className="dropdown-toggle arrow-none card-drop p-0" data-bs-toggle="dropdown" aria-expanded="false">
                                                                <i className="mdi mdi-dots-vertical" />
                                                            </a>
                                                            <div className="dropdown-menu dropdown-menu-animated">
                                                                <a href="#viewPrograms" data-bs-toggle="modal" role="button" className="dropdown-item">View programs</a>
                                                                <a href="edit-competency.html" className="dropdown-item">Edit</a>
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
                {/* View programs modal */}
                <div>
                    <div className="modal fade" id="viewPrograms" tabIndex={-1} aria-labelledby="viewProgramsLabel" aria-hidden="true">
                        <div className="modal-dialog modal-dialog-centered">
                            <div className="modal-content">
                                <div className="modal-header modal-colored-header bg-primary">
                                    <h4 className="modal-title" id="viewProgramsLabel">List of Programs</h4>
                                    <button type="button" className="btn-close" data-bs-dismiss="modal" aria-hidden="true" />
                                </div>
                                <div className="modal-body table-responsive">
                                    <table className="table table-centered table-striped align-middle" id="table_id">
                                        <tbody>
                                            <tr>
                                                <td>Program 1</td>
                                                <td>
                                                    <a href="edit-program.html" className="btn btn-outline-primary btn-sm">Edit</a>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Program 2</td>
                                                <td>
                                                    <a href="edit-program.html" className="btn btn-outline-primary btn-sm">Edit</a>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
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
                                        <button type="button" className="btn btn-danger mx-1 my-2" data-bs-dismiss="modal">Yes</button>
                                        <button type="button" className="btn btn-outline-danger mx-1 my-2" data-bs-dismiss="modal">No</button>
                                    </div>
                                </div>
                            </div>{/* /.modal-content */}
                        </div>{/* /.modal-dialog */}
                    </div>{/* /.modal */}
                </div>
            </div>


        </div>

    )
};

export default Competencies;
