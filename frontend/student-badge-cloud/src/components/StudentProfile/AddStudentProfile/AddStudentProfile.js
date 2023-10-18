import { ErrorMessage, Field, Form, Formik } from 'formik';
import * as Yup from 'yup';
import { useDispatch, useSelector } from 'react-redux';
import { toastActions } from '../../../Redux/store/Slices/toast';
import { studentApi } from '../../../api/userApi';
import { useEffect, useState } from 'react';
import { ToastContainer } from 'react-toastify';
import { makeStyles } from '@material-ui/core';


const useStyles = makeStyles((theme) => ({
  img: {
    width: '150px !important',
    margin: "20px 0px",
    height: '150px !important',
  },
}));

const AddStudentProfile = () => {
  const classes = useStyles();
  const imgBaseUrl = process.env.REACT_APP_PUBLIC_BADGE_URL;

  const [photo, setPhoto] = useState('');
  const { userId } = useSelector((state) => state.jwt);
  const dispatch = useDispatch();
  const [studentProfile, setStudentProfile] = useState();



  const getSingleStudentProfile = async () => {
    try {
      const res = await studentApi.getSingleStudentProfile(userId)

      setStudentProfile(res?.data);
      setPhoto(imgBaseUrl + res?.data?.photo);
    } catch (err) {
      console.log(err?.response?.data?.message || err?.message);
    }
  }


  useEffect(() => {
    getSingleStudentProfile();
  }, [])

  const initialValues = {
    photo: '',
    userId: studentProfile?.userId || '',
    firstName: studentProfile?.firstName || '',
    email: studentProfile?.email || '',
    mobileNumber: studentProfile?.userId || '',
    bio: studentProfile?.bio || '',
    currentEmployer: studentProfile?.currentEmployer || '',
    current_position: studentProfile?.currentEmployer || '',
    website_url: studentProfile?.websiteURL || '',
    state: studentProfile?.state || '',
    badgeCloudURL: studentProfile?.badgeCloudURL || '',
    birthYear: studentProfile?.birthYear || '',
    country: studentProfile?.country || '',
    city: studentProfile?.city || '',
    zipCode: studentProfile?.zipCode || '',
  };

  const validationSchema = Yup.object().shape({
    // firstName: Yup.string()
    //   .max(255)
    //   .required('Student Name is required!'),
    userId: Yup.string().max(255).required('Student ID is required!'),
    mobileNumber: Yup.string().max(255).required('Mobile Number is required!'),
  });

  const renderError = message => (
    <p className="help is-danger" style={{ color: 'red' }}>
      {message}
    </p>
  );

  return (
    <Formik
      enableReinitialize
      initialValues={initialValues}
      validationSchema={validationSchema}
      onSubmit={async (
        values,
        { setSubmitting, resetForm, setErrors, setStatus }
      ) => {
        try {
          setSubmitting(true);
          let formData = new FormData();
          for (let value in values) {
            if (['photo'].includes(value) && typeof values[value] === 'string') {
              continue;
            }
            if (values[value] === '') {
              continue;
            }
            else {
              formData.append(value, values[value]);
            }
          }

          for (let property of formData.entries()) {
            console.log(property[0], property[1]);
          }
          const respon = await studentApi.updateSingleStudentProfile(formData, userId);
          console.log("data", respon)
          dispatch(
            toastActions.succesMessage({
              message: 'Student profile updated!',
            })
          );
          setStatus({ success: true });
          setSubmitting(false);
          resetForm();
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
        handleChange,
        handleSubmit,
        touched,
        values,
        setFieldValue,
      }) => (
        <Form>
          <div className="content-page">

            <div class="container-fluid">

              {/* <!-- start page title --> */}
              <div class="row">
                <div class="col-12">
                  <div class="page-title-box">
                    <h4 class="page-title">Student Profile</h4>
                  </div>
                </div>
              </div>
              {/* <!-- end page title --> */}

              <div class="row">
                <div class="col-lg-12">
                  <div class="card">
                    <div class="card-body">

                      <div>

                        {/* <!-- Profile picture --> */}
                        <div class="row mb-4">
                          <div class="col-lg-12">
                            <h4 class="header-title mb-3">Profile Picture</h4>
                            <p class="fw-semibold mb-2">
                              Choose an image from your device
                            </p>
                            <p>Required image ratio is 1:1 (ex. 100 X 100px)</p>
                            <div class="tower-file">
                              <input
                                type="file"
                                id="photo"
                                accept="image/png, image/jpeg"
                                name="photo"
                                onChange={(event) => {
                                  handleChange(event);
                                  setPhoto(URL.createObjectURL(event.currentTarget.files[0]));
                                  setFieldValue("photo", event.currentTarget.files[0])
                                }}
                              />
                              {console.log("Files Values", values?.photo)}
                              {/* <label
                                for="photo"
                                class="btn btn-outline-primary mb-2 me-2"
                              >
                                Select a File
                              </label> */}
                              <button
                                type="button"
                                class="tower-file-clear btn btn-outline-danger mb-2"
                              >
                                Clear
                              </button>
                            </div>
                            {photo && <img src={photo && photo} className={classes.img} />}
                          </div>
                        </div>
                        {/* <!-- Personal Details --> */}
                        <h4 class="header-title mb-3">Personal Details</h4>
                        <div class="row mb-4">
                          <div class="col-lg-6">
                            <div class="mb-3">
                              <label class="form-label" for="userId">
                                Student ID
                              </label>
                              <Field
                                type="text"
                                className={`form-control ${touched.password && errors.password ? 'is-invalid' : ''}`}
                                error={Boolean(
                                  touched.userId && errors.userId
                                )}
                                onChange={handleChange}
                                helperText={
                                  touched.userId && errors.userId
                                }
                                name="userId"
                                placeholder="Student ID"
                                disabled="true"
                              />
                              <ErrorMessage
                                name="userId"
                                render={renderError}
                              />
                            </div>
                          </div>
                          <div class="col-lg-6">
                            <div class="mb-3">
                              <label class="form-label" for="student-name">
                                Student Name
                              </label>
                              <Field
                                type="text"
                                className={`form-control ${touched.firstName && errors.firstName ? 'is-invalid' : ''}`}
                                error={Boolean(
                                  touched.firstName && errors.firstName
                                )}
                                onChange={handleChange}
                                helperText={
                                  touched.firstName && errors.firstName
                                }
                                name="firstName"
                                placeholder="Student Name"
                              />
                              <ErrorMessage
                                name="firstName"
                                render={renderError}
                              />
                            </div>
                          </div>
                          <div class="col-lg-6">
                            <div class="mb-3">
                              <label class="form-label" for="email">
                                Email Address
                              </label>
                              <Field
                                type="text"
                                className={`form-control ${touched.email && errors.email ? 'is-invalid' : ''}`}
                                error={Boolean(
                                  touched.email && errors.email
                                )}
                                onChange={handleChange}
                                helperText={
                                  touched.email && errors.email
                                }
                                name="email"
                                placeholder="Email"
                              />
                              <ErrorMessage
                                name="email"
                                render={renderError}
                              />
                            </div>
                          </div>
                          <div class="col-lg-6">
                            <div class="mb-3">
                              <label class="form-label" for="mobileNumber">
                                Mobile Number
                              </label>

                              <Field
                                type="text"
                                className={`form-control ${touched.mobileNumber && errors.mobileNumber ? 'is-invalid' : ''}`}
                                error={Boolean(
                                  touched.mobileNumber && errors.mobileNumber
                                )}
                                onChange={handleChange}
                                helperText={touched.mobileNumber && errors.mobileNumber}
                                name="mobileNumber"
                                placeholder="Mobile Number"
                              />
                              <ErrorMessage
                                name="mobileNumber"
                                render={renderError}
                              />
                            </div>
                          </div>
                          <div class="col-lg-12">
                            <div class="mb-3">
                              <label for="bio" class="form-label">Bio</label>
                              <Field
                                type="text"
                                className={`form-control ${touched.bio && errors.bio ? 'is-invalid' : ''}`}
                                error={Boolean(touched.bio && errors.bio)}
                                onChange={handleChange}
                                helperText={touched.bio && errors.bio}
                                name="bio"
                                placeholder="Bio"
                                rows={5}
                                as="textarea"
                              />
                            </div>
                          </div>
                          <div class="col-lg-6">
                            <div class="mb-3">
                              <label class="form-label" for="currentEmployer">
                                Current Employer
                              </label>
                              <Field
                                type="text"
                                className={`form-control ${touched.currentEmployer && errors.currentEmployer ? 'is-invalid' : ''}`}
                                error={Boolean(
                                  touched.currentEmployer &&
                                  errors.currentEmployer
                                )}
                                onChange={handleChange}
                                helperText={
                                  touched.currentEmployer &&
                                  errors.currentEmployer
                                }
                                name="currentEmployer"
                                placeholder="Current Employer"
                              />
                            </div>
                          </div>
                          <div class="col-lg-6">
                            <div class="mb-3">
                              <label class="form-label" for="current_position">
                                Current Position
                              </label>
                              <Field
                                type="text"
                                className={`form-control ${touched.current_position && errors.current_position ? 'is-invalid' : ''}`}
                                error={Boolean(
                                  touched.current_position &&
                                  errors.current_position
                                )}
                                onChange={handleChange}
                                helperText={
                                  touched.current_position &&
                                  errors.current_position
                                }
                                name="current_position"
                                placeholder="Current Position"
                              />
                            </div>
                          </div>
                          <div class="col-lg-6">
                            <div class="mb-3">
                              <label class="form-label" for="website_url">
                                Website URL
                              </label>

                              <Field
                                type="text"
                                className={`form-control ${touched.website_url && errors.website_url ? 'is-invalid' : ''}`}
                                error={Boolean(
                                  touched.website_url && errors.website_url
                                )}
                                onChange={handleChange}
                                helperText={
                                  touched.website_url && errors.website_url
                                }
                                name="website_url"
                                placeholder="Webiste URL"
                              />
                            </div>
                          </div>
                          <div class="col-lg-6">
                            <div class="mb-3">
                              <label class="form-label" for="badgeCloudURL">
                                Badge Cloud URL
                              </label>
                              <Field
                                type="text"
                                className={`form-control ${touched.badgeCloudURL && errors.badgeCloudURL ? 'is-invalid' : ''}`}
                                error={Boolean(
                                  touched.badgeCloudURL &&
                                  errors.badgeCloudURL
                                )}
                                onChange={handleChange}
                                helperText={
                                  touched.badgeCloudURL &&
                                  errors.badgeCloudURL
                                }
                                name="badgeCloudURL"
                                placeholder="Badge Cloud URL"
                              />
                            </div>
                          </div>
                          <div class="col-lg-6">
                            <div class="mb-3">
                              <label for="birthYear" class="form-label">
                                Birth Year
                              </label>

                              <Field
                                type="text"
                                className={`form-control ${touched.birthYear && errors.birthYear ? 'is-invalid' : ''}`}
                                error={Boolean(
                                  touched.birthYear && errors.birthYear
                                )}
                                onChange={handleChange}
                                helperText={
                                  touched.birthYear && errors.birthYear
                                }
                                name="birthYear"
                                placeholder="1999"
                              />
                            </div>
                          </div>
                        </div>
                      </div>

                      {/* <!-- Location --> */}
                      <h4 class="header-title mb-3">Location</h4>
                      <div class="row">
                        <div class="col-lg-6">
                          <div class="mb-3">
                            <label for="country" class="form-label">
                              Country
                            </label>

                            <Field
                              type="text"
                              className={`form-control ${touched.country && errors.country ? 'is-invalid' : ''}`}
                              error={Boolean(
                                touched.country && errors.country
                              )}
                              onChange={handleChange}
                              helperText={touched.country && errors.country}
                              name="country"
                              placeholder="Country"
                            />
                          </div>
                        </div>
                        <div class="col-lg-6">
                          <div class="mb-3">
                            <label for="state" class="form-label">
                              State
                            </label>

                            <Field
                              type="text"
                              className={`form-control ${touched.state && errors.state ? 'is-invalid' : ''}`}
                              error={Boolean(touched.state && errors.state)}
                              onChange={handleChange}
                              helperText={touched.state && errors.state}
                              name="state"
                              placeholder="State"
                            />
                          </div>
                        </div>
                      </div>
                      <div class="row">
                        <div class="col-lg-6">
                          <div class="mb-3">
                            <label class="form-label" for="city">City</label>
                            <Field
                              type="text"
                              className={`form-control ${touched.city && errors.city ? 'is-invalid' : ''}`}
                              error={Boolean(touched.city && errors.city)}
                              onChange={handleChange}
                              helperText={touched.city && errors.city}
                              name="city"
                              placeholder="City"
                            />
                          </div>
                        </div>
                        <div class="col-lg-6">
                          <div class="mb-3">
                            <label class="form-label" for="zipCode">
                              Zip Code
                            </label>
                            <Field
                              type="text"
                              className={`form-control ${touched.zipCode && errors.zipCode ? 'is-invalid' : ''}`}
                              error={Boolean(
                                touched.zipCode && errors.zipCode
                              )}
                              onChange={handleChange}
                              helperText={touched.zipCode && errors.zipCode}
                              name="zipCode"
                              placeholder="Zip Code"
                            />
                          </div>
                        </div>
                      </div>
                    </div>

                    <button class="btn btn-primary me-2" type="submit">
                      Submit
                    </button>
                  </div>

                </div>
                {/* <!-- end card-body--> */}
              </div>
              {/* <!-- end card--> */}
            </div>
            {/* <!-- end col--> */}
          </div>
          {/* <!-- end row --> */}

          {/* <!-- container --> */}
          <ToastContainer />
        </Form>
      )}
    </Formik>
  );
};

export default AddStudentProfile;
