import { Link } from 'react-router-dom';
const AddUser = () => {

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
                                    <Link to="/user-manager" className="btn btn-primary">Go To User List</Link>
                                </div>
                                <h4 className="page-title">Add User</h4>
                            </div>
                        </div>
                    </div>
                    {/* end page title */}
                    <div className="row">
                        <div className="col-lg-12">
                            <div className="card">
                                <div className="card-body">
                                    <form className="needs-validation" noValidate>
                                        {/* Profile picture */}
                                        <div className="row mb-4">
                                            <div className="col-lg-12">
                                                <h4 className="header-title mb-3">Profile Picture</h4>
                                                <p className="fw-semibold mb-2">Choose an image from your device</p>
                                                <p>Required image ratio is 1:1 (ex. 100 X 100px)</p>
                                                <div className="tower-file">
                                                    <input type="file" id="profile-picture" accept="image/png, image/jpeg" />
                                                    <label htmlFor="profile-picture" className="btn btn-outline-primary mb-2 me-2">
                                                        Select a File
                                                    </label>
                                                    <button type="button" className="tower-file-clear btn btn-outline-danger mb-2">
                                                        Clear
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                        {/* Category */}
                                        <div className="row mb-4">
                                            <div className="col-lg-12">
                                                <h4 className="header-title mb-3">Personal Details</h4>
                                                <label htmlFor="category" className="form-label">Category</label>
                                                <select className="form-select" id="category" name="categoryId" required>
                                                    <option value={0} className="administrator">Administrator</option>
                                                    <option value={1} className="student">Student</option>
                                                    <option value={2} className="instructor">Instructor</option>
                                                    <option value={3} className="company">Company</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div className="row mb-3 administrator show_form">
                                            <div className="col-lg-6">
                                                <div className="mb-3">
                                                    <label className="form-label" htmlFor="f_name">First Name</label>
                                                    <input type="text" className="form-control f-name" id="f_name" required />
                                                </div>
                                            </div>
                                            <div className="col-lg-6">
                                                <div className="mb-3">
                                                    <label className="form-label" htmlFor="l_name">Last Name</label>
                                                    <input type="text" className="form-control l-name" id="l_name" required />
                                                </div>
                                            </div>
                                            <div className="col-lg-6">
                                                <div className="mb-3">
                                                    <label className="form-label" htmlFor="user_id">User ID</label>
                                                    <input type="text" className="form-control uid" id="user_id" required />
                                                </div>
                                            </div>
                                        </div>
                                        {/* Login credentials */}
                                        <h4 className="header-title mb-3">Login Credentials</h4>
                                        <div className="row">
                                            <div className="col-lg-6">
                                                <div className="mb-3">
                                                    <label className="form-label" htmlFor="username">Username</label>
                                                    <input type="text" className="form-control" id="username" required />
                                                </div>
                                            </div>
                                            <div className="col-lg-6">
                                                <div className="mb-3">
                                                    <label className="form-label" htmlFor="email_address">Email Address</label>
                                                    <input type="email" className="form-control" id="email_address" required />
                                                </div>
                                            </div>
                                            <div className="col-lg-6">
                                                <div className="mb-3">
                                                    <label htmlFor="password" className="form-label">Password</label>
                                                    <div className="input-group input-group-merge">
                                                        <input type="password" id="password" className="form-control" />
                                                        <div className="input-group-text" data-password="false">
                                                            <span className="password-eye" />
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <button className="btn btn-primary me-2" type="submit">Submit</button>
                                        <Link to="/user-manager" className="btn btn-outline-primary">Cancel</Link>
                                    </form>
                                </div> {/* end card-body*/}
                            </div> {/* end card*/}
                        </div> {/* end col*/}
                    </div>
                    {/* end row */}
                </div> {/* container */}
            </div> {/* content */}

        </div>

    )
};

export default AddUser;
