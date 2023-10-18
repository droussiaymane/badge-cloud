
import { makeStyles } from '@material-ui/core';
import { ErrorMessage, Field, Form, Formik } from 'formik';
import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
import * as Yup from 'yup';
import { UserApi } from '../../../api/userApi';
import { toastActions } from '../../../Redux/store/Slices/toast';
const renderError = (message) => (
    <p className='help is-danger' style={{ color: 'red' }}>
        {message}
    </p>
);

const useStyles = makeStyles((theme) => ({
    img: {
        width: '150px !important',
        margin: "20px 0px",
        height: '150px !important',
    },
}));
const AddUser = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const classes = useStyles();
    const [userCatergory, setUserGategory] = useState([]);
    const { tenantId } = useSelector((state) => state.jwt);
    const [image, setImage] = useState('');
    const [passwordVisible, setPasswordVisible] = useState(false);
    let initialValues = { firstName: '', lastName: '', userId: '', userName: '', email: "", password: "", photo: null, categoryName: userCatergory[0]?.category || '' };
    const validationSchema = Yup.object().shape({
        firstName: Yup.string().max(255).required('First Name is required!'),
        categoryName: Yup.string().max(255).required('Category is required!'),
        lastName: Yup.string().max(255).required('Last Name is required!'),
        userId: Yup.string().max(255).required('User Id is required!'),
        userName: Yup.string().max(255).required('User Name is required!'),
        password: Yup.string().max(255).required('Password is required!'),
        email: Yup.string().email().max(255).required('Email is required!'),
        // photo: Yup.mixed().required('File is required!'),
    });

    const UserCategoryByTenantId = async () => {
        try {
            const response = await UserApi.UserCategoryByTenantId();
            if (response.length > 0)
                setUserGategory(response)
            else
                setUserGategory([]);

            console.log('response', response);
        } catch (error) {
            setUserGategory([]);
        }
    }
    useEffect(() => {
        UserCategoryByTenantId();
    }, []);

    return (
        <Formik
            initialValues={initialValues}
            validationSchema={validationSchema}
            enableReinitialize
            onSubmit={async (
                values,
                { setSubmitting, resetForm, setErrors, setStatus }
            ) => {
                try {
                    let formData = new FormData();
                    formData.append("tenantId", tenantId);
                    for (let value in values) {
                        if (['photo'].includes(value) && values[value] === null) {
                            continue;
                        }
                        formData.append(value, values[value]);
                    }
                    setSubmitting(true);

                    const res = await UserApi.create(formData);
                    await dispatch(
                        toastActions.succesMessage({
                            message: res.message,
                        })
                    );
                    setStatus({ success: true });
                    setSubmitting(false);
                    resetForm();
                    navigate('/user-manager');
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
                            <div className="container-fluid">
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
                                <div className="row">
                                    <div className="col-lg-12">
                                        <div className="card">
                                            <div className="card-body">
                                                <div className="row mb-4">
                                                    <div className="col-lg-12">
                                                        <h4 className="header-title mb-3">Profile Picture</h4>
                                                        <p className="fw-semibold mb-2">Choose an image from your device</p>
                                                        <p>Required image ratio is 1:1 (ex. 100 X 100px)</p>
                                                        <div className="tower-file">
                                                            <input
                                                                type="file"
                                                                id="profile-picture"
                                                                accept="image/png, image/jpeg"
                                                                name="photo"
                                                                onChange={(event) => {
                                                                    handleChange(event);
                                                                    setImage(URL.createObjectURL(event.currentTarget.files[0]));
                                                                    setFieldValue("photo", event.currentTarget.files[0])
                                                                }}
                                                                style={{ display: "none" }}
                                                            />
                                                            <label htmlFor="profile-picture" className="btn btn-outline-primary mb-2 me-2">
                                                                Select a File
                                                            </label>
                                                            <button type="button" className="tower-file-clear btn btn-outline-danger mb-2">
                                                                Clear
                                                            </button>
                                                            <ErrorMessage
                                                                name='photo'
                                                                render={renderError}
                                                            />
                                                        </div>
                                                    </div>
                                                    {image && <img src={image && image} className={classes.img} />}
                                                </div>
                                                <div className="row mb-4">
                                                    <div className="col-lg-12">
                                                        <h4 className="header-title mb-3">Personal Details</h4>
                                                        <label htmlFor="category" className="form-label">Category</label>
                                                        <select className="form-select" id="category" name="categoryName" required
                                                            onChange={(e) => {
                                                                setFieldValue('categoryName', e.target.value);
                                                            }}
                                                        >
                                                            {userCatergory.length > 0 && userCatergory.map((category) => {
                                                                return (
                                                                    <option key={category.id} value={category.category} className="administrator">{category?.category}</option>
                                                                )
                                                            })
                                                            }

                                                        </select>
                                                        <ErrorMessage name='categoryName' render={renderError} />
                                                    </div>
                                                </div>
                                                <div className="row mb-3 administrator show_form">
                                                    <div className="col-lg-6">
                                                        <div className="mb-3">
                                                            <label className="form-label" htmlFor="f_name">First Name</label>
                                                            <Field
                                                                type='text'
                                                                className="form-control f-name"
                                                                htmlFor="firstName"
                                                                placeholder='First Name'
                                                                error={Boolean(touched.firstName && errors.firstName)}
                                                                onChange={handleChange}
                                                                value={values.firstName}
                                                                helperText={touched.firstName && errors.firstName}
                                                                name='firstName'
                                                            />
                                                            <ErrorMessage name='firstName' render={renderError} />
                                                        </div>
                                                    </div>
                                                    <div className="col-lg-6">
                                                        <div className="mb-3">
                                                            <label className="form-label" htmlFor="l_name">Last Name</label>
                                                            <Field
                                                                type='text'
                                                                className="form-control l-name"
                                                                htmlFor="lastName"
                                                                placeholder='Last Name'
                                                                error={Boolean(touched.lastName && errors.lastName)}
                                                                onChange={handleChange}
                                                                value={values.lastName}
                                                                helperText={touched.lastName && errors.lastName}
                                                                name='lastName'
                                                            />
                                                            <ErrorMessage name='lastName' render={renderError} />
                                                        </div>
                                                    </div>
                                                    <div className="col-lg-6">
                                                        <div className="mb-3">
                                                            <label className="form-label" htmlFor="user_id">User ID</label>
                                                            <Field
                                                                type='text'
                                                                className="form-control uid"
                                                                htmlFor="userId"
                                                                placeholder='User'
                                                                error={Boolean(touched.userId && errors.userId)}
                                                                onChange={handleChange}
                                                                value={values.userId}
                                                                helperText={touched.userId && errors.userId}
                                                                name='userId'
                                                            />
                                                            <ErrorMessage name='userId' render={renderError} />
                                                        </div>
                                                    </div>
                                                </div>
                                                <h4 className="header-title mb-3">Login Credentials</h4>
                                                <div className="row">
                                                    <div className="col-lg-6">
                                                        <div className="mb-3">
                                                            <label className="form-label" htmlFor="username">Username</label>
                                                            <Field
                                                                type='text'
                                                                className="form-control"
                                                                htmlFor="userName"
                                                                placeholder='User Name'
                                                                error={Boolean(touched.userName && errors.userName)}
                                                                onChange={handleChange}
                                                                value={values.userName}
                                                                helperText={touched.userName && errors.userName}
                                                                name='userName'
                                                            />
                                                            <ErrorMessage name='userName' render={renderError} />
                                                        </div>
                                                    </div>
                                                    <div className="col-lg-6">
                                                        <div className="mb-3">
                                                            <label className="form-label" htmlFor="email_address">Email Address</label>
                                                            <Field
                                                                type='text'
                                                                className="form-control uid"
                                                                htmlFor="email"
                                                                placeholder='Email'
                                                                error={Boolean(touched.email && errors.email)}
                                                                onChange={handleChange}
                                                                value={values.email}
                                                                helperText={touched.email && errors.email}
                                                                name='email'
                                                                id="email_address"
                                                            />
                                                            <ErrorMessage name='email' render={renderError} />
                                                        </div>
                                                    </div>
                                                    <div className="col-lg-6">
                                                        <div className="mb-3">
                                                            <label htmlFor="password" className="form-label">Password</label>
                                                            <div className="input-group input-group-merge">
                                                                <Field
                                                                    type={passwordVisible ? 'text' : 'password'}
                                                                    className="form-control"
                                                                    htmlFor="password"
                                                                    placeholder='Password'
                                                                    error={Boolean(touched.password && errors.password)}
                                                                    onChange={handleChange}
                                                                    value={values.password}
                                                                    helperText={touched.password && errors.password}
                                                                    name='password'
                                                                    id="password"
                                                                />
                                                                <div className={`input-group-text ${passwordVisible ? 'show-password' : ''}`} data-password="true">
                                                                    <span className={`password-eye`} onClick={() => setPasswordVisible(!passwordVisible)} />
                                                                </div>
                                                            </div>
                                                            <ErrorMessage name='password' render={renderError} />
                                                        </div>
                                                    </div>
                                                </div>
                                                <button disabled={isSubmitting} className="btn btn-primary me-2" type="submit">Submit</button>
                                                <Link to="/user-manager" className="btn btn-outline-primary">Cancel</Link>
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

export default AddUser;
