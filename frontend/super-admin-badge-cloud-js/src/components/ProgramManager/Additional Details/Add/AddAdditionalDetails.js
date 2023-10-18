import { Field, Form, Formik, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
import { AdditionalDetailsApi } from '../../../../api/additonalDetailsApi';
import { toastActions } from '../../../../Redux/store/Slices/toast';

const AddAdditionalDetails = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { tenantId } = useSelector((state) => state.jwt);
  const initialValues = { 
    name: '',
    url: '',
  //  description: ''
   };
  const validationSchema = Yup.object().shape({
    name: Yup.string().max(255).required('Name is required!'),
    // description: Yup.string().max(255).required('Description is required!'),
    url: Yup.string().max(255).required('URL is required!'),
  });
  const renderError = (message) => (
    <p className='help is-danger' style={{ color: 'red' }}>
      {message}
    </p>
  );
  return (
    <Formik
      initialValues={initialValues}
      validationSchema={validationSchema}
      onSubmit={async (
        values,
        { setSubmitting, resetForm, setErrors, setStatus }
      ) => {
        try {
          setSubmitting(true);
          const submittingObject = {
            name: values.name,
            description: values.description,
            url: values.url
          };
          const res = await AdditionalDetailsApi.create(submittingObject,tenantId);
          await dispatch(
            toastActions.succesMessage({
              message: res.message,
            })
          );
          setStatus({ success: true });
          setSubmitting(false);
          resetForm();
          navigate('/program/additional-details');
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
                          to='/program/additional-details'
                          className='btn btn-primary'
                        >
                          Go To Additional Details List
                        </Link>
                      </div>
                      <h4 className='page-title'>Add Additional Details</h4>
                    </div>
                  </div>
                </div>
                <div className='row'>
                  <div className='col-lg-12'>
                    <div className='card'>
                      <div className='card-body'>
                        <div className='row'>
                          <div className='col-lg-6'>
                            <div className='mb-3'>
                              <label htmlFor='name' className='form-label'>
                                Name
                              </label>
                              <Field
                                type='text'
                                className={`form-control ${
                                  touched.password && errors.password
                                    ? 'is-invalid'
                                    : ''
                                }`}
                                error={Boolean(touched.name && errors.name)}
                                onChange={handleChange}
                                helperText={touched.name && errors.name}
                                name='name'
                                placeholder='Name'
                              />
                              <ErrorMessage name='name' render={renderError} />
                            </div>
                          </div>
                          <div className='col-lg-6'>
                            <div className='mb-3'>
                              <label htmlFor='url' className='form-label'>
                                URL
                              </label>
                              <Field
                                type='text'
                                className='form-control'
                                placeholder='URL'
                                error={Boolean(touched.url && errors.url)}
                                onChange={handleChange}
                                value={values.url}
                                helperText={touched.url && errors.url}
                                name='url'
                              />
                              <ErrorMessage name='url' render={renderError} />
                            </div>
                          </div>
                          {/* <div className='col-lg-6'>
                            <div className='mb-3'>
                              <label
                                htmlFor='description'
                                className='form-label'
                              >
                                Description
                              </label>
                              <Field
                                className='form-control'
                                name='description'
                                placeholder='Description'
                                error={Boolean(
                                  touched.description && errors.description
                                )}
                                onChange={handleChange}
                                value={values.description}
                                helperText={
                                  touched.description && errors.description
                                }
                                rows={6}
                                as='textarea'
                              />
                              <ErrorMessage
                                name='description'
                                render={renderError}
                              />
                            </div>
                          </div>
                          <div className='col-lg-6'>
                            <div className='mb-3'>
                              <p className='form-label'>Type</p>
                              <div className='form-check mb-1'>
                                <input
                                  className='form-check-input'
                                  type='checkbox'
                                  id='DateTime'
                                />
                                <label
                                  className='form-check-label'
                                  htmlFor='DateTime'
                                >
                                  DateTime
                                </label>
                              </div>
                              <div className='form-check mb-1'>
                                <input
                                  className='form-check-input'
                                  type='checkbox'
                                  id='string'
                                />
                                <label
                                  className='form-check-label'
                                  htmlFor='string'
                                >
                                  String
                                </label>
                              </div>
                              <div className='form-check mb-1'>
                                <input
                                  className='form-check-input'
                                  type='checkbox'
                                  id='integer'
                                />
                                <label
                                  className='form-check-label'
                                  htmlFor='integer'
                                >
                                  Integer
                                </label>
                              </div>
                              <div className='form-check mb-1'>
                                <input
                                  className='form-check-input'
                                  type='checkbox'
                                  id='url'
                                />
                                <label
                                  className='form-check-label'
                                  htmlFor='url'
                                >
                                  URL
                                </label>
                              </div>
                              <div className='form-check mb-1'>
                                <input
                                  className='form-check-input'
                                  type='checkbox'
                                  id='file'
                                />
                                <label
                                  className='form-check-label'
                                  htmlFor='file'
                                >
                                  File
                                </label>
                              </div>
                              <div className='form-check mb-1'>
                                <input
                                  className='form-check-input'
                                  type='checkbox'
                                  id='cost'
                                />
                                <label
                                  className='form-check-label'
                                  htmlFor='cost'
                                >
                                  Cost
                                </label>
                              </div>
                            </div>
                          </div>
                          <div className='col-lg-6'>
                            <div className='mb-3'>
                              <label htmlFor='doc_1' className='form-label'>
                                Image / Doc
                              </label>
                              <input
                                type='file'
                                className='form-control'
                                id='doc_1'
                              />
                              <button
                                className='btn btn-outline-danger mt-2'
                                style={{ padding: '5px 10px' }}
                              >
                                Reset file
                              </button>
                            </div>
                          </div>
                          <div className='col-lg-6'>
                            <div className='mb-3'>
                              <label htmlFor='doc_2' className='form-label'>
                                Image / Doc
                              </label>
                              <input
                                type='file'
                                className='form-control'
                                id='doc_2'
                              />
                              <button
                                className='btn btn-outline-danger mt-2'
                                style={{ padding: '5px 10px' }}
                              >
                                Reset file
                              </button>
                            </div>
                          </div>
                          <div className='col-lg-6'>
                            <div className='mb-3'>
                              <label htmlFor='doc_3' className='form-label'>
                                Image / Doc
                              </label>
                              <input
                                type='file'
                                className='form-control'
                                id='doc_3'
                              />
                              <button
                                className='btn btn-outline-danger mt-2'
                                style={{ padding: '5px 10px' }}
                              >
                                Reset file
                              </button>
                            </div>
                          </div> */}
                        </div>
                        <button
                          disabled={isSubmitting}
                          className='btn btn-primary me-2'
                          type='submit'
                        >
                          Submit
                        </button>
                        <Link
                          to='/program/additional-details'
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

export default AddAdditionalDetails;
