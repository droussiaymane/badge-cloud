import { makeStyles } from '@material-ui/core';
import { Field, Form, Formik, ErrorMessage } from 'formik';
import { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
import * as Yup from 'yup';
import { EvidenceApi } from '../../../../api/evidenceApi';
import { toastActions } from '../../../../Redux/store/Slices/toast';


const useStyles = makeStyles((theme) => ({
  img: {
    width: '150px !important',
    margin: "20px 0px",
    height: '150px !important',
  },
}));
const AddEvidence = () => {
  const classes = useStyles();
  const dispatch = useDispatch();
  const { tenantId } = useSelector((state) => state.jwt);
  const navigate = useNavigate();
  const [picture1, setPicture1] = useState('');
  const [picture2, setPicture2] = useState('');
  const [picture3, setPicture3] = useState('');
  const initialValues = { name: '', link: '', description: '', file1: null, file2: null, file3: null };
  const validationSchema = Yup.object().shape({
    name: Yup.string().max(255).required('Name is required!'),
    description: Yup.string().max(255).required('Description is required!'),
    link: Yup.string().max(255).required('URL is required!'),
    // file1: Yup.mixed()
    //   .required('File is required!'),
    // file2: Yup.mixed()
    //   .required('File is required!'),
    // file3: Yup.mixed()
    //   .required('File is required!'),
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
          let formData = new FormData();
          formData.append("tenantId", tenantId);
          formData.append("userId", 2);

          for (let value in values) {
            if (['file1', 'file2', 'file3'].includes(value) && values[value] === null) {
              continue;
            }
            formData.append(value, values[value]);
          }

          for (let property of formData.entries()) {
            console.log(property[0], property[1]);
          }
          const res = await EvidenceApi.create(formData, tenantId);
          await dispatch(
            toastActions.succesMessage({
              message: res.message,
            })
          );
          setStatus({ success: true });
          setSubmitting(false);
          resetForm();
          navigate('/program/evidences');
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
        setFieldValue,
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
                          to='/program/evidences'
                          className='btn btn-primary'
                        >
                          Go To Evidences List
                        </Link>
                      </div>
                      <h4 className='page-title'>Add Evidence</h4>
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
                                className={`form-control ${touched.password && errors.password
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
                                error={Boolean(touched.link && errors.link)}
                                onChange={handleChange}
                                value={values.link}
                                helperText={touched.link && errors.link}
                                name='link'
                              />
                              <ErrorMessage name='link' render={renderError} />
                            </div>
                          </div>
                          <div className='col-lg-6'>
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
                          </div>
                          <div className='col-lg-6'>
                            <div className='mb-3'>
                              <label htmlFor='doc_1' className='form-label'>
                                Image / Doc
                              </label>
                              <input
                                type='file'
                                name="file1"
                                className='form-control'
                                id='doc_1'
                                onChange={(event) => {
                                  handleChange(event);
                                  setPicture1(URL.createObjectURL(event.currentTarget.files[0]));
                                  setFieldValue("file1", event.currentTarget.files[0])
                                }}
                                multiple
                              />
                              <ErrorMessage
                                name='file1'
                                render={renderError}
                              />
                              {picture1 && <img src={picture1 && picture1} className={classes.img} /> }

                              {/* <button
                                className='btn btn-outline-danger mt-2'
                                style={{ padding: '5px 10px' }}
                              >
                                Reset file
                              </button> */}
                            </div>
                          </div>
                          <div className='col-lg-6'>
                            <div className='mb-3'>
                              <label htmlFor='doc_2' className='form-label'>
                                Image / Doc
                              </label>
                              <input
                                type='file'
                                name="file2"
                                className='form-control'
                                id='doc_2'
                                onChange={(event) => {
                                  handleChange(event);
                                  setPicture2(URL.createObjectURL(event.currentTarget.files[0]));
                                  setFieldValue("file2", event.currentTarget.files[0])
                                }}
                                multiple
                              />
                              <ErrorMessage
                                name='file2'
                                render={renderError}
                              />
                              {picture2 && <img src={picture2 && picture2} className={classes.img} /> }
                              {/* <button
                                className='btn btn-outline-danger mt-2'
                                style={{ padding: '5px 10px' }}
                              >
                                Reset file
                              </button> */}
                            </div>
                          </div>
                          <div className='col-lg-6'>
                            <div className='mb-3'>
                              <label htmlFor='doc_3' className='form-label'>
                                Image / Doc
                              </label>
                              <input
                                type='file'
                                name="file3"
                                className='form-control'
                                id='doc_3'
                                onChange={(event) => {
                                  handleChange(event);
                                  setPicture3(URL.createObjectURL(event.currentTarget.files[0]));
                                  setFieldValue("file3", event.currentTarget.files[0])
                                }}
                              />
                              <ErrorMessage
                                name='file3'
                                render={renderError}
                              />
                             {picture3 && <img src={picture3 && picture3} className={classes.img} /> }
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
                          to='/program/evidences'
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

export default AddEvidence;
