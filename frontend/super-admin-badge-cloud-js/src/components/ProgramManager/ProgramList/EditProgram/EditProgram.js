import { ErrorMessage, Field, Form, Formik } from 'formik';
import { useDispatch, useSelector } from 'react-redux';
import Select from 'react-select';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import * as Yup from 'yup';
import { ProgramApi } from '../../../../api/ProgramAPI';
import { toastActions } from '../../../../Redux/store/Slices/toast';
import { useEffect, useState } from 'react';
import { CompetencyApi } from '../../../../api/competencyApi';
import { AdditionalDetailsApi } from '../../../../api/additonalDetailsApi';
import { EvidenceApi } from '../../../../api/evidenceApi';
import { StandardsApi } from '../../../../api/standardsAPI';
import { UserApi } from '../../../../api/userApi';
import { EarningRequirementsApi } from '../../../../api/earningRequirementApi';
import { BadgeApi } from '../../../../api/badgeApi';
const renderError = (message) => (
    <p className='help is-danger' style={{ color: 'red' }}>
        {message}
    </p>
);

const categoryIdList = {
    Administrator: 1,
    Student: 2,
    Instructor: 3,
}

const EditProgram = () => {
    const { tenantId } = useSelector((state) => state.jwt);
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const location = useLocation();
    const [records, setRecords] = useState([]);
    const [searchComp, setSearchComp] = useState("");
    const [searchStud, setSearchStud] = useState("");
    const [searchInstructor, setSearchInstructor] = useState("");
    const [searchEarReq, setSearchEarReq] = useState("");
    const [searchStandard, setSearchStandard] = useState("");
    const [searchEvidence, setSearchEvidence] = useState("");
    const [searchAdditionalDetail, setSearchAdditionalDetail] = useState("");
    const [searchCollection, setSearchCollection] = useState("");
    const [instructorOption, SetInstructorOption] = useState([]);
    const [instructorOptions, SetInstructorOptions] = useState([]);
    const [studentOption, SetStudentOption] = useState([]);
    const [studentOptions, SetStudentOptions] = useState([]);
    const [standardOption, SetStandardOption] = useState([]);
    const [standardOptions, SetStandardOptions] = useState([]);
    const [competencyOption, SetCompetencyOption] = useState([]);
    const [competencyOptions, SetCompetencyOptions] = useState([]);

    const [collectionOption, SetCollectionOption] = useState([]);
    const [collectionOptions, SetCollectionOptions] = useState([]);
    const [earningRequirementOption, SetEarningRequirementOption] = useState([]);
    const [earningRequirementOptions, SetEarningRequirementOptions] = useState([]);
    const [evidenceOption, SetEvidenceOption] = useState([]);
    const [evidenceOptions, SetEvidenceOptions] = useState([]);

    const [additionalDetailOption, SetAdditionalDetailOption] = useState([]);
    const [additionalDetailOptions, SetAdditionalDetailsOptions] = useState([]);
    const validationSchema = Yup.object().shape({
        programName: Yup.string().max(255).required('Program Name is required!'),
        issuedBy: Yup.string().max(255).required('Issued By is required!'),
        description: Yup.string().max(255).required('Description is required!'),
        startDate: Yup.string().max(255).required('Start Date is required!'),
        endDate: Yup.string().max(255).required('End Date is required!'),
        studentsId: Yup.array().min(1, 'Student is required!'),
        badgeId: Yup.string().max(255).required("Collection is required!"),
        instructorsId: Yup.array().min(1, 'Instructor is required!'),
        competencies: Yup.array().min(1, 'Competency is required!'),
        earningRequirementId: Yup.string().max(255).required('Earning Requirement is required!'),
        standardId: Yup.string().required('Standard is required!'),
        evidence: Yup.string().required('Evidence is required!'),
        additionalDetails: Yup.string().required('Additional Details is required!'),
    });

    const searchByNameCompetency = async () => {
        try {
            let response = await CompetencyApi.searchByName(searchComp);
            console.log(
                "competencies List", response
            )
            if (response?.length > 0) {
                const data = response.map(item => {
                    return {
                        value: item.id,
                        label: item.name
                    }
                });
                SetCompetencyOptions(data);
            }
            else {
                SetCompetencyOptions([]);
            }
        } catch (error) {
            SetCompetencyOptions([]);
        }
    }

    const searchByNameStudent = async () => {
        try {
            let response = await UserApi.searchByName(searchStud, Object.keys(categoryIdList)[1]);
            if (response?.length > 0) {
                const data = response.map(item => {
                    return {
                        value: item.id,
                        label: item.userName
                    }
                });
                SetStudentOptions(data);
            }
            else {
                SetStudentOptions([]);
            }
        } catch (error) {
            SetStudentOptions([]);
        }
    }

    const searchByNameInstructor = async () => {
        try {
            let response = await UserApi.searchByName(searchInstructor, Object.keys(categoryIdList)[2]);
            if (response?.length > 0) {
                const data = response.map(item => {
                    return {
                        value: item.id,
                        label: item.userName
                    }
                });
                SetInstructorOptions(data);
            }
            else {
                SetInstructorOptions([]);
            }
        } catch (error) {
            SetInstructorOptions([]);
        }
    }

    const searchByNameEarReq = async () => {
        try {
            let response = await EarningRequirementsApi.searchByName(searchEarReq);
            if (response?.length > 0) {
                const data = response.map(item => {
                    return {
                        value: item.id,
                        label: item.name
                    }
                });
                SetEarningRequirementOptions(data);
            }
            else {
                SetEarningRequirementOptions([]);
            }
        } catch (error) {
            SetEarningRequirementOptions([]);
        }
    }

    const searchByNameStandard = async () => {
        try {
            let response = await StandardsApi.searchByName(searchStandard);
            if (response?.length > 0) {
                const data = response.map(item => {
                    return {
                        value: item.id,
                        label: item.name
                    }
                });
                SetStandardOptions(data);
            }
            else {
                SetStandardOptions([]);
            }
        } catch (error) {
            SetStandardOptions([]);
        }
    }

    const searchByNameEvidence = async () => {
        try {
            let response = await EvidenceApi.searchByName(searchEvidence);
            if (response?.length > 0) {
                const data = response.map(item => {
                    return {
                        value: item.id,
                        label: item.name
                    }
                });
                SetEvidenceOptions(data);
            }
            else {
                SetEvidenceOptions([]);
            }
        } catch (error) {
            SetEvidenceOptions([]);
        }
    }

    const searchByNameAdditionalDetails = async () => {
        try {
            let response = await AdditionalDetailsApi.searchByName(searchAdditionalDetail);
            if (response?.length > 0) {
                const data = response.map(item => {
                    return {
                        value: item.id,
                        label: item.name
                    }
                });
                SetAdditionalDetailsOptions(data);
            }
            else {
                SetAdditionalDetailsOptions([]);
            }
        } catch (error) {
            SetAdditionalDetailsOptions([]);
        }
    }
    const searchByNameCollectionBadge = async () => {
        try {
            let response = await BadgeApi.searchByName(searchCollection);
            if (response?.length > 0) {
                const data = response.map(item => {
                    return {
                        value: item.id,
                        label: item.badgeName
                    }
                });
                SetCollectionOptions(data);
            }
            else {
                SetCollectionOptions([]);
            }
        } catch (error) {
            SetCollectionOptions([]);
        }
    }

    const getProgramById = async () => {
        try {
            const response = await ProgramApi.getById(location.state.id);
            setRecords(response);
            SetAdditionalDetailOption({ value: response.additionalDetails.id, label: response.additionalDetails.name });
            SetCollectionOption({ value: response.badgeDTO.id, label: response.badgeDTO.badgeName });
            SetEarningRequirementOption({ value: response.earningRequirements.id, label: response.earningRequirements.name });
            SetEvidenceOption({ value: response.evidence.id, label: response.evidence.name });
            SetStandardOption({ value: response.standard.id, label: response.standard.name });
            const competency = response?.competencies?.map((item) => {
                return {
                    value: item.id,
                    label: item.name,
                }
            })
            SetCompetencyOption(competency);
            const students = response?.students?.map((item) => {
                return {
                    value: item.id,
                    label: item.name,
                }
            })
            SetStudentOption(students);
            const instructors = response?.instructors?.map((item) => {
                return {
                    value: item.id,
                    label: item.name,
                }
            })
            SetInstructorOption(instructors);
        }
        catch (error) {

        }
    }

    useEffect(() => {
        if (searchComp) {
            searchByNameCompetency();
        }
    }, [searchComp]);

    useEffect(() => {
        if (searchStud) {
            searchByNameStudent();
        }
    }, [searchStud]);

    useEffect(() => {
        if (searchInstructor) {
            searchByNameInstructor();
        }
    }, [searchInstructor]);

    useEffect(() => {
        if (searchEarReq) {
            searchByNameEarReq();
        }
    }, [searchEarReq]);


    useEffect(() => {
        if (searchStandard) {
            searchByNameStandard();
        }
    }, [searchStandard]);

    useEffect(() => {
        console.log("searchEvidence")
        if (searchEvidence) {
            searchByNameEvidence();
        }
    }, [searchEvidence]);


    useEffect(() => {
        if (searchAdditionalDetail) {
            searchByNameAdditionalDetails();
        }
    }, [searchAdditionalDetail]);


    useEffect(() => {
        if (searchCollection) {
            searchByNameCollectionBadge();
        }
    }, [searchCollection]);

    useEffect(() => {
        getProgramById();
    }, []);
    return (
        <Formik
            initialValues={{
                programName: records?.programName || '',
                issuedBy: records?.issuedBy || '',
                description: records?.description || '',
                startDate: records?.startDate || '',
                endDate: records?.endDate || '',
                studentsId: records?.students?.flatMap(item => item.id) || [],
                badgeId: records?.badgeDTO?.id || '',
                instructorsId: records?.instructors?.flatMap(item => item.id) || [],
                competencies: records?.competencies?.flatMap(item => item.id) || [],
                earningRequirementId: records?.earningRequirements?.id || '',
                standardId: records?.standard?.id || '',
                evidence: records?.evidence?.id || '',
                additionalDetails: records?.additionalDetails?.id || '',
            }}
            enableReinitialize
            validationSchema={validationSchema}
            onSubmit={async (
                values,
                { setSubmitting, resetForm, setErrors, setStatus }
            ) => {
                try {
                    setSubmitting(true);
                    values['id'] = location.state.id;
                    const res = await ProgramApi.edit(values);
                    await dispatch(
                        toastActions.succesMessage({
                            message: res.message,
                        })
                    );
                    setStatus({ success: true });
                    setSubmitting(false);
                    resetForm();
                    navigate('/program/program-list');
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
                setFieldValue,
                values,
            }) => (
                <Form>
                    <div className="content-page">
                        <div className="content">
                            {/* Start Content*/}
                            <div className="container-fluid">
                                {/* start page title */}
                                <div className="row">
                                    <div className="col-12">
                                        <div className="page-title-box">
                                            <div className="page-title-right">
                                                <Link to='/program/program-list' className="btn btn-primary">Go To Program List</Link>
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
                                                {/* <form className="needs-validation" noValidate> */}
                                                <div className="row">
                                                    <div className="col-lg-6">
                                                        <div className="mb-3">
                                                            <label className="form-label" htmlFor="program_name">Program Name</label>
                                                            <Field
                                                                type='text'
                                                                className="form-control"
                                                                htmlFor="program_name"
                                                                placeholder='programName'
                                                                error={Boolean(touched.programName && errors.programName)}
                                                                onChange={handleChange}
                                                                value={values.programName}
                                                                helperText={touched.programName && errors.programName}
                                                                name='programName'
                                                            />
                                                            <ErrorMessage name='programName' render={renderError} />
                                                        </div>
                                                    </div>
                                                    <div className="col-lg-6">
                                                        <div className="mb-3">
                                                            <label className="form-label" htmlFor="issued_by">Issued By</label>
                                                            <Field
                                                                type='text'
                                                                className="form-control"
                                                                htmlFor="issued_by"
                                                                placeholder='issuedBy'
                                                                error={Boolean(touched.issuedBy && errors.issuedBy)}
                                                                onChange={handleChange}
                                                                value={values.issuedBy}
                                                                helperText={touched.issuedBy && errors.issuedBy}
                                                                name='issuedBy'
                                                            />
                                                            <ErrorMessage name='issuedBy' render={renderError} />
                                                        </div>
                                                    </div>
                                                    <div className="col-lg-12">
                                                        <div className="mb-3">
                                                            <label htmlFor="program_description" className="form-label">Program Description</label>
                                                            <Field
                                                                type='text'
                                                                className="form-control"
                                                                htmlFor="program_description"
                                                                placeholder='description'
                                                                error={Boolean(touched.description && errors.description)}
                                                                onChange={handleChange}
                                                                value={values.description}
                                                                helperText={touched.description && errors.description}
                                                                name='description'
                                                                rows={5}
                                                            />
                                                            <ErrorMessage name='description' render={renderError} />
                                                            {/* <textarea className="form-control" id="program_description" rows={5} required defaultValue={""} /> */}
                                                        </div>
                                                    </div>
                                                    <div className="col-lg-6">
                                                        <div className="mb-3 position-relative" id="start_date">
                                                            <label className="form-label">Start Date</label>
                                                            <Field
                                                                type='text'
                                                                className="form-control"
                                                                htmlFor="start_date"
                                                                placeholder='Start Date'
                                                                error={Boolean(touched.startDate && errors.startDate)}
                                                                onSelectCapture={handleChange}
                                                                value={values.startDate}
                                                                helperText={touched.startDate && errors.startDate}
                                                                name='startDate'
                                                                data-provide="datepicker"
                                                            />
                                                            <ErrorMessage name='startDate' render={renderError} />
                                                        </div>
                                                    </div>
                                                    <div className="col-lg-6">
                                                        <div className="mb-3 position-relative" id="end_date">
                                                            <label className="form-label">End Date</label>
                                                            <Field
                                                                type='text'
                                                                className="form-control"
                                                                htmlFor="end_date"
                                                                placeholder='End Date'
                                                                error={Boolean(touched.endDate && errors.endDate)}
                                                                onSelectCapture={handleChange}
                                                                value={values.endDate}
                                                                helperText={touched.endDate && errors.endDate}
                                                                name='endDate'
                                                                data-provide="datepicker"
                                                            />
                                                            <ErrorMessage name='endDate' render={renderError} />
                                                            {/* <input type="text" className="form-control" data-provide="datepicker" data-date-container="#end_date" required /> */}
                                                        </div>
                                                    </div>
                                                    <div className="col-lg-6">
                                                        <div className="mb-3">
                                                            <label htmlFor="select_collection" className="form-label">Select Collection
                                                            </label>
                                                            <Select
                                                                name="badgeId"
                                                                value={collectionOption}
                                                                onInputChange={(e) => setSearchCollection(e)}
                                                                onChange={(e) => {
                                                                    SetCollectionOption(e);
                                                                    values.badgeId = e.value;
                                                                }}
                                                                options={collectionOptions}
                                                                className="basic-multi-select"
                                                                classNamePrefix="select"
                                                            />
                                                            <ErrorMessage name='badgeId' render={renderError} />
                                                        </div>
                                                    </div>
                                                    <div className="col-lg-6">
                                                        <div className="mb-3">
                                                            <label htmlFor="select_instructors" className="form-label">Select Instructor
                                                            </label>
                                                            <Select
                                                                isMulti
                                                                name="instructorsId"
                                                                value={instructorOption}
                                                                onInputChange={(e) => setSearchInstructor(e)}
                                                                onChange={(e) => {
                                                                    SetInstructorOption(e);
                                                                    const data = e.flatMap(item => item.value);
                                                                    setFieldValue('instructorsId', data);

                                                                }}
                                                                options={instructorOptions}
                                                                className="basic-multi-select"
                                                                classNamePrefix="select"
                                                            />
                                                            <ErrorMessage name='instructorsId' render={renderError} />
                                                        </div>
                                                    </div>
                                                    <div className="col-lg-6">
                                                        <div className="mb-3">
                                                            <label htmlFor="select_students" className="form-label">Select Student
                                                            </label>
                                                            <Select
                                                                isMulti
                                                                name="studentsId"
                                                                value={studentOption}
                                                                onInputChange={(e) => setSearchStud(e)}
                                                                onChange={(e) => {
                                                                    SetStudentOption(e);
                                                                    const data = e.flatMap(item => item.value);
                                                                    setFieldValue('studentsId', data);
                                                                }}
                                                                options={studentOptions}
                                                                className="basic-multi-select"
                                                                classNamePrefix="select"
                                                            />
                                                            <ErrorMessage name='studentsId' render={renderError} />
                                                        </div>
                                                    </div>
                                                    <div className="col-lg-6">
                                                        <div className="mb-3">
                                                            <label htm
                                                                lFor="select_competencies" className="form-label">Select Competency
                                                            </label>
                                                            <Select
                                                                isMulti
                                                                name="competencies"
                                                                value={competencyOption}
                                                                onInputChange={(e) => setSearchComp(e)}
                                                                onChange={(e) => {
                                                                    SetCompetencyOption(e)
                                                                    const data = e.flatMap(item => item.value);
                                                                    setFieldValue('competencies', data);
                                                                }}
                                                                options={competencyOptions}
                                                                className="basic-multi-select"
                                                                classNamePrefix="select"
                                                            />
                                                            <ErrorMessage name='competencies' render={renderError} />
                                                        </div>
                                                    </div>
                                                    <div className="col-lg-6">
                                                        <div className="mb-3">
                                                            <label htmlFor="select_earning_requirements" className="form-label">Select Earning Requirement</label>
                                                            <Select
                                                                name="earningRequirementId"
                                                                value={earningRequirementOption}
                                                                onInputChange={(e) => setSearchEarReq(e)}
                                                                onChange={(e) => {
                                                                    SetEarningRequirementOption(e);
                                                                    values.earningRequirementId = e.value;
                                                                }}
                                                                options={earningRequirementOptions}
                                                                className="basic-multi-select"
                                                                classNamePrefix="select"
                                                            />
                                                            <ErrorMessage name='earningRequirementId' render={renderError} />
                                                        </div>
                                                    </div>
                                                    <div className="col-lg-6">
                                                        <div className="mb-3">
                                                            <label htmlFor="select_standards" className="form-label">Select Standard</label>
                                                            <Select
                                                                name="standardId"
                                                                value={standardOption}
                                                                onInputChange={(e) => setSearchStandard(e)}
                                                                onChange={(e) => {
                                                                    SetStandardOption(e);
                                                                    values.standardId = e.value
                                                                }}
                                                                options={standardOptions}
                                                                className="basic-multi-select"
                                                                classNamePrefix="select"
                                                            />
                                                            <ErrorMessage name='standardId' render={renderError} />
                                                        </div>
                                                    </div>
                                                    <div className="col-lg-6">
                                                        <div className="mb-3">
                                                            <label htmlFor="select_evidence" className="form-label">Select Evidence
                                                            </label>
                                                            <Select
                                                                name="evidence"
                                                                value={evidenceOption}
                                                                onInputChange={(e) => setSearchEvidence(e)}
                                                                onChange={(e) => {
                                                                    SetEvidenceOption(e);
                                                                    values.evidence = e.value;
                                                                }}
                                                                options={evidenceOptions}
                                                                className="basic-multi-select"
                                                                classNamePrefix="select"
                                                            />
                                                            <ErrorMessage name='evidence' render={renderError} />
                                                        </div>
                                                    </div>
                                                    <div className="col-lg-6">
                                                        <div className="mb-3">
                                                            <label htmlFor="select_additional_details" className="form-label large-paragraph">Select Additional Details
                                                            </label>
                                                            <Select
                                                                name="additionalDetails"
                                                                value={additionalDetailOption}
                                                                onInputChange={(e) => setSearchAdditionalDetail(e)}
                                                                onChange={(e) => {
                                                                    SetAdditionalDetailOption(e);
                                                                    values.additionalDetails = e.value
                                                                }}
                                                                options={additionalDetailOptions}
                                                                className="basic-multi-select"
                                                                classNamePrefix="select"
                                                            />
                                                            <ErrorMessage name='additionalDetails' render={renderError} />
                                                        </div>
                                                    </div>
                                                </div>
                                                <button className="btn btn-primary me-2" type="submit">Submit</button>
                                                <Link to='/program/program-list' className="btn btn-outline-primary">Cancel </Link>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </Form>
            )}
        </Formik>
    )
};

export default EditProgram;
