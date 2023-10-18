
import type { FC } from 'react';
import { Link } from 'react-router-dom';
const AddEarningRequirements: FC = () => {

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
                                    <Link to="/program/earning-requirements" className="btn btn-primary">Go To Earning Requirement List</Link>
                                </div>
                                <h4 className="page-title">Add Earning Requirement</h4>
                            </div>
                        </div>
                    </div>
                    {/* end page title */}
                    <div className="row">
                        <div className="col-lg-12">
                            <div className="card">
                                <div className="card-body">
                                    <form className="needs-validation" noValidate>
                                        <div className="row">
                                            <div className="col-lg-6">
                                                <div className="mb-3">
                                                    <label htmlFor="name" className="form-label">Name</label>
                                                    <input type="text" className="form-control" id="name" required />
                                                </div>
                                            </div>
                                            <div className="col-lg-6">
                                                <div className="mb-3">
                                                    <label htmlFor="url" className="form-label">URL</label>
                                                    <input type="text" className="form-control" id="url" required />
                                                </div>
                                            </div>
                                            <div className="col-lg-6">
                                                <div className="mb-3">
                                                    <label htmlFor="description" className="form-label">Description</label>
                                                    <textarea className="form-control" id="description" rows={6} required defaultValue={""} />
                                                </div>
                                            </div>
                                            <div className="col-lg-6">
                                                <div className="mb-3">
                                                    <p className="form-label">Type</p>
                                                    <div className="form-check mb-1">
                                                        <input className="form-check-input" type="checkbox" id="DateTime" />
                                                        <label className="form-check-label" htmlFor="DateTime">DateTime</label>
                                                    </div>
                                                    <div className="form-check mb-1">
                                                        <input className="form-check-input" type="checkbox" id="string" />
                                                        <label className="form-check-label" htmlFor="string">String</label>
                                                    </div>
                                                    <div className="form-check mb-1">
                                                        <input className="form-check-input" type="checkbox" id="integer" />
                                                        <label className="form-check-label" htmlFor="integer">Integer</label>
                                                    </div>
                                                    <div className="form-check mb-1">
                                                        <input className="form-check-input" type="checkbox" id="url" />
                                                        <label className="form-check-label" htmlFor="url">URL</label>
                                                    </div>
                                                    <div className="form-check mb-1">
                                                        <input className="form-check-input" type="checkbox" id="file" />
                                                        <label className="form-check-label" htmlFor="file">File</label>
                                                    </div>
                                                    <div className="form-check mb-1">
                                                        <input className="form-check-input" type="checkbox" id="cost" />
                                                        <label className="form-check-label" htmlFor="cost">Cost</label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div className="col-lg-6">
                                                <div className="mb-3">
                                                    <label htmlFor="doc_1" className="form-label">Image / Doc</label>
                                                    <input type="file" className="form-control" id="doc_1" />
                                                    <button
                                                        //  onclick="resetFile1()"
                                                        className="btn btn-outline-danger mt-2" style={{ padding: '5px 10px' }}>Reset file</button>
                                                </div>
                                            </div>
                                            <div className="col-lg-6">
                                                <div className="mb-3">
                                                    <label htmlFor="doc_2" className="form-label">Image / Doc</label>
                                                    <input type="file" className="form-control" id="doc_2" />
                                                    <button
                                                        //  onclick="resetFile2()"
                                                        className="btn btn-outline-danger mt-2" style={{ padding: '5px 10px' }}>Reset file</button>
                                                </div>
                                            </div>
                                            <div className="col-lg-6">
                                                <div className="mb-3">
                                                    <label htmlFor="doc_3" className="form-label">Image / Doc</label>
                                                    <input type="file" className="form-control" id="doc_3" />
                                                    <button
                                                        //  onclick="resetFile3()" 
                                                        className="btn btn-outline-danger mt-2" style={{ padding: '5px 10px' }}>Reset file</button>
                                                </div>
                                            </div>
                                        </div>
                                        <button className="btn btn-primary me-2" type="submit">Submit</button>
                                        <Link to="/program/earning-requirements" className="btn btn-outline-primary">Cancel</Link>
                                    </form>
                                </div> {/* end card-body*/}
                            </div> {/* end card*/}
                        </div> {/* end col*/}
                    </div>
                    {/* end row */}
                </div> {/* container */}
            </div> {/* content */}
            {/* Footer Start */}
            <footer className="footer">
                <div className="container-fluid">
                    <div className="row">
                        <div className="col-md-6">
                            © Vizzibl LLC
                        </div>
                        <div className="col-md-6">
                            <div className="text-md-end footer-links d-none d-md-block">
                                <a href="../terms-and-conditions.html">Terms &amp; Conditions</a>
                                <a href="../privacy-policy.html">Privacy Policy</a>
                            </div>
                        </div>
                    </div>
                </div>
            </footer>
            {/* end Footer */}
        </div>


    )
};

export default AddEarningRequirements;
