
import type { FC } from 'react';
import { Link } from 'react-router-dom';
const UserManager: FC = () => {

    return (
        <div className="content-page">
            <div className="content">
                {/* Start Content*/}
                <div className="container-fluid">
                    {/* start page title */}
                    <div className="row">
                        <div className="col-12">
                            <div className="page-title-box">
                                <div className="page-title-right">
                                    <Link to="/user-manager/add-user" className="btn btn-primary">Add User</Link>
                                </div>
                                <h4 className="page-title">User List</h4>
                            </div>
                        </div>
                    </div>
                    {/* end page title */}
                    <div className="row">
                        <div className="col-12">
                            <div className="card">
                                <div className="card-body">
                                    <table id="basic-datatable" className="table table-striped dt-responsive align-middle nowrap w-100">
                                        <thead>
                                            <tr>
                                                <th>Name</th>
                                                <th>User ID</th>
                                                <th>Category</th>
                                                <th>Username</th>
                                                <th className="sorting_disabled action-datatable">Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td className="table-user">
                                                    <img src="../assets/img/avatar-1.jpg" alt="table-user" className="me-2 rounded-circle" />
                                                    Lion Nixon
                                                </td>
                                                <td>674658ty</td>
                                                <td>Student</td>
                                                <td>lionnixon</td>
                                                <td>
                                                    <div className="dropdown">
                                                        <a href="#" className="dropdown-toggle arrow-none card-drop p-0" data-bs-toggle="dropdown" aria-expanded="false">
                                                            <i className="mdi mdi-dots-vertical" />
                                                        </a>
                                                        <div className="dropdown-menu dropdown-menu-animated">
                                                            <a href="email.html" className="dropdown-item">Email</a>
                                                            <a href="assign.html" className="dropdown-item">Assign</a>
                                                            <a href="#" className="dropdown-item">Info</a>
                                                            <a href="#" className="dropdown-item">Import definition</a>
                                                            <a href="#" className="dropdown-item">Export definition</a>
                                                            <a href="edit-user.html" className="dropdown-item">Edit</a>
                                                            <a href="#delete" data-bs-toggle="modal" role="button" className="dropdown-item text-danger">Delete</a>
                                                        </div>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td className="table-user">
                                                    <img src="../assets/img/avatar-1.jpg" alt="table-user" className="me-2 rounded-circle" />
                                                    Tiger Nixon
                                                </td>
                                                <td>674658ty</td>
                                                <td>Student</td>
                                                <td>tigernixon</td>
                                                <td>
                                                    <div className="dropdown">
                                                        <a href="#" className="dropdown-toggle arrow-none card-drop p-0" data-bs-toggle="dropdown" aria-expanded="false">
                                                            <i className="mdi mdi-dots-vertical" />
                                                        </a>
                                                        <div className="dropdown-menu dropdown-menu-animated">
                                                            <a href="email.html" className="dropdown-item">Email</a>
                                                            <a href="assign.html" className="dropdown-item">Assign</a>
                                                            <a href="#" className="dropdown-item">Info</a>
                                                            <a href="#" className="dropdown-item">Import definition</a>
                                                            <a href="#" className="dropdown-item">Export definition</a>
                                                            <a href="edit-user.html" className="dropdown-item">Edit</a>
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
            </div>        </div>

    )
};

export default UserManager;
