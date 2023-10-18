
import type { FC } from 'react';
import { Link } from 'react-router-dom';
const AddProgram: FC = () => {

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
                                     <Link to='/program/program-list'  className="btn btn-primary">Go To Program List</Link>
                                </div>
                                <h4 className="page-title">Add Program</h4>
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
                                                    <label className="form-label" htmlFor="program_name">Program Name</label>
                                                    <input type="text" className="form-control" id="program_name" required />
                                                </div>
                                            </div>
                                            <div className="col-lg-6">
                                                <div className="mb-3">
                                                    <label className="form-label" htmlFor="issued_by">Issued By</label>
                                                    <input type="text" className="form-control" id="issued_by" required />
                                                </div>
                                            </div>
                                            <div className="col-lg-12">
                                                <div className="mb-3">
                                                    <label htmlFor="program_description" className="form-label">Program Description</label>
                                                    <textarea className="form-control" id="program_description" rows={5} required defaultValue={""} />
                                                </div>
                                            </div>
                                            <div className="col-lg-6">
                                                <div className="mb-3 position-relative" id="start_date">
                                                    <label className="form-label">Start Date</label>
                                                    <input type="text" className="form-control" data-provide="datepicker" data-date-container="#start_date" required />
                                                </div>
                                            </div>
                                            <div className="col-lg-6">
                                                <div className="mb-3 position-relative" id="end_date">
                                                    <label className="form-label">Start Date</label>
                                                    <input type="text" className="form-control" data-provide="datepicker" data-date-container="#end_date" required />
                                                </div>
                                            </div>
                                            <div className="col-lg-6">
                                                <div className="mb-3">
                                                    <label htmlFor="select_collection" className="form-label">Select Collection
                                                    </label>
                                                    <small className="mb-1 text-primary d-block">Press <b>ctrl</b> for multiple selection or remove selection</small>
                                                    <select id="select_collection" multiple className="form-control" required>
                                                        <option value={0} className="py-1">IT Security Audit</option>
                                                        <option value={1} className="py-1">Incident Analysis/Response</option>
                                                        <option value={2} className="py-1">Penetration/Vulnerability Testing</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div className="col-lg-6">
                                                <div className="mb-3">
                                                    <label htmlFor="select_instructors" className="form-label">Select Instructor
                                                    </label>
                                                    <small className="mb-1 text-primary d-block">Press <b>ctrl</b> for multiple selection or remove selection</small>
                                                    <select id="select_instructors" multiple className="form-control" required>
                                                        <option value={0} className="py-1">David Jones</option>
                                                        <option value={1} className="py-1">David Jones</option>
                                                        <option value={2} className="py-1">David Jones</option>
                                                        <option value={3} className="py-1">David Jones</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div className="col-lg-6">
                                                <div className="mb-3">
                                                    <label htmlFor="select_students" className="form-label">Select Student
                                                    </label>
                                                    <small className="mb-1 text-primary d-block">Press <b>ctrl</b> for multiple selection or remove selection</small>
                                                    <select id="select_students" multiple className="form-control" required>
                                                        <option value={0} className="py-1">David Jones</option>
                                                        <option value={1} className="py-1">David Jones</option>
                                                        <option value={2} className="py-1">David Jones</option>
                                                        <option value={3} className="py-1">David Jones</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div className="col-lg-6">
                                                <div className="mb-3">
                                                    <label htmlFor="select_competencies" className="form-label">Select Competency
                                                    </label>
                                                    <small className="mb-1 text-primary d-block">Press <b>ctrl</b> for multiple selection or remove selection</small>
                                                    <select id="select_competencies" multiple className="form-control" required>
                                                        <option value={0} className="py-1">IT Security Audit</option>
                                                        <option value={1} className="py-1">IT Security Audit</option>
                                                        <option value={2} className="py-1">Incident Analysis/Response</option>
                                                        <option value={3} className="py-1">Penetration/Vulnerability Testing</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div className="col-lg-6">
                                                <div className="mb-3">
                                                    <label htmlFor="select_earning_requirements" className="form-label">Select Earning Requirement</label>
                                                    <select className="form-select" id="select_earning_requirements" required>
                                                        <option>No item selected</option>
                                                        <option value={1}>1</option>
                                                        <option value={2}>2</option>
                                                        <option value={3}>3</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div className="col-lg-6">
                                                <div className="mb-3">
                                                    <label htmlFor="select_standards" className="form-label">Select Standard</label>
                                                    <select className="form-select" id="select_standards" required>
                                                        <option>No item selected</option>
                                                        <option value={1}>1</option>
                                                        <option value={2}>2</option>
                                                        <option value={3}>3</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div className="col-lg-6">
                                                <div className="mb-3">
                                                    <label htmlFor="select_evidence" className="form-label">Select Evidence
                                                    </label>
                                                    <select className="form-select" id="select_evidence" required>
                                                        <option>No item selected</option>
                                                        <option value={1}>Evidence 1</option>
                                                        <option value={2}>Evidence 2</option>
                                                        <option value={3}>Evidence 3</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div className="col-lg-6">
                                                <div className="mb-3">
                                                    <label htmlFor="select_additional_details" className="form-label large-paragraph">Select Additional Details
                                                    </label>
                                                    <select className="form-select" id="select_additional_details" required>
                                                        <option>No item selected</option>
                                                        <option value={1}>Additional Details 1</option>
                                                        <option value={2}>Additional Details 2</option>
                                                        <option value={3}>Additional Details 3</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <button className="btn btn-primary me-2" type="submit">Submit</button>
                                        <Link to='/program/program-list'  className="btn btn-outline-primary">Cancel </Link>
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

export default AddProgram;
