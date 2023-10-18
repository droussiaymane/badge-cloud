import { Field, Form, Formik, ErrorMessage } from 'formik';
import { Link, useNavigate } from 'react-router-dom';
import * as Yup from 'yup';
import { useLocation } from 'react-router-dom';
import { useEffect } from 'react';
import { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { toastActions } from '../../../Redux/store/Slices/toast';
import { makeStyles } from '@material-ui/core';
import { BadgeApi } from '../../../api/badgeApi';

const useStyles = makeStyles((theme) => ({
  img: {
    width: '150px !important',
    margin: "20px 0px",
    height: '150px !important',
  },
}));
const EditBadge = () => {
  const imgBaseUrl = process.env.REACT_APP_IMAGE_BASE_URL;
  const [badge, setBadge] = useState('');
  const dispatch = useDispatch();
  const classes = useStyles();
  const location = useLocation();
  const { tenantId } = useSelector((state) => state.jwt);
  const navigate = useNavigate();
  const [records, setRecords] = useState([]);
  const validationSchema = Yup.object().shape({
    badgeName: Yup.string().max(255).required('Name is required!'),
    file: Yup.mixed()
      .required('File is required!'),
  });
  const renderError = (message) => (
    <p className='help is-danger' style={{ color: 'red' }}>
      {message}
    </p>
  );

  const getStandardById = async (id) => {
    try {
      const response = await BadgeApi.getById(id);
      console.log("response", response)
      setRecords({
        badgeName: response?.badgeName || '',
        file: response?.image || ''
      });
      setBadge(imgBaseUrl + response?.image);
    } catch (error) { }
  };

  useEffect(() => {
    getStandardById(location.state.id);
  }, []);
  return (
    <Formik
      initialValues={{
        badgeName: records?.badgeName || '',
        file: records?.file || '',
      }}
      enableReinitialize
      validationSchema={validationSchema}
      onSubmit={async (
        values,
        { setSubmitting, resetForm, setErrors, setStatus }
      ) => {
        try {
          setSubmitting(true);
          let formData = new FormData();
          formData.append("tenantId", tenantId);
          formData.append("id", location.state.id);
          formData.append("userId", 1);

          for (let value in values) {
            if (['file'].includes(value) && typeof values[value] === 'string') {
              continue;
            }
            else {
              formData.append(value, values[value]);
            }
          }

          for (let property of formData.entries()) {
            console.log(property[0], property[1]);
          }
          const res = await BadgeApi.create(formData, tenantId);
          await dispatch(
            toastActions.succesMessage({
              message: res.message,
            })
          );
          setStatus({ success: true });
          setSubmitting(false);
          resetForm();
          navigate('/badge/badge-collection');
        } catch (error) {
          await dispatch(
            toastActions.errorMessage({
              message: error.message,
            })
          );
          setStatus({ success: false });
          setErrors({ submit: error.message });
          setSubmitting(false);
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
          <div className='content-page'>
            <div className='content'>
              <div className='container-fluid'>
                <div className='row'>
                  <div className='col-12'>
                    <div className='page-title-box'>
                      <div className='page-title-right'>
                        <Link
                          to='/badge/badge-collection'
                          className='btn btn-primary'
                        >
                          Go To Badge Collection List
                        </Link>
                      </div>
                      <h4 className='page-title'>Edit Badge</h4>
                    </div>
                  </div>
                </div>
                <div className='row'>
                  <div className='col-lg-12'>
                    <div className='card'>
                      <div className='card-body'>
                        <div className='row'>
                          <div className='col-lg-12'>
                            <div className='mb-3'>
                              <label htmlFor='name' className='form-label'>
                                COLLECTION NAME
                              </label>
                              <Field
                                type='text'
                                className={`form-control ${touched.badgeName && errors.badgeName
                                  ? 'is-invalid'
                                  : ''
                                  }`}
                                error={Boolean(touched.badgeName && errors.badgeName)}
                                onChange={handleChange}
                                helperText={touched.badgeName && errors.badgeName}
                                name='badgeName'
                                placeholder='badgeName'
                              />
                              <ErrorMessage name='badgeName' render={renderError} />
                            </div>
                          </div>
                          <div className="row mb-4">
                            <div className="col-lg-12">
                              <h5 className="header-title mb-2">Badge</h5>
                              <p className="fw-semibold mb-2">Choose an image from your device</p>
                              <p>Required image ratio is 1:1 (ex. 100 X 100px)</p>

                              <div className="tower-file">
                                <input
                                  type='file'
                                  name="file"
                                  id="badge-2" accept="image/png, image/jpeg" style={{ display: "none" }}
                                  onChange={(event) => {
                                    handleChange(event);
                                    setBadge(URL.createObjectURL(event.currentTarget.files[0]));
                                    setFieldValue("file", event.currentTarget.files[0])
                                  }}
                                />
                                <label htmlFor="badge-2" className="btn btn-outline-primary mb-2 me-2">
                                  Select a File
                                </label>
                                <button type="button" className="tower-file-clear btn btn-outline-danger mb-2">
                                  Clear
                                </button>
                                <ErrorMessage
                                  name='file'
                                  render={renderError}
                                />
                              </div>
                              {badge && <img src={badge && badge} className={classes.img} />}
                            </div>
                          </div>
                        </div>
                        <button
                          disabled={isSubmitting}
                          className='btn btn-primary me-2'
                          type='submit'
                        >
                          Submit
                        </button>
                        <Link
                          to='/badge/badge-collection'
                          className='btn btn-outline-primary'
                        >
                          Cancel
                        </Link>
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
  );
};

export default EditBadge;
