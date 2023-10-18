import { Box, Grid, makeStyles, } from "@material-ui/core";
import { useEffect, useRef } from "react";
import { Field, Form, Formik, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { jwtActions, loginUser } from "../../Redux/store/Slices/userSlice";
import { Alert } from '@material-ui/lab';

const useStyles = makeStyles((theme) => ({
    container: {
        left: "50%",
        top: "50%",
        position: "absolute",
        transform: "translate(-50%, -50%)",
        backgroundColor: "#ffffff",
        padding: "48px !important"
    },
    h6: {
        margin: "0 0 8px o !important",
        fontWeight: "300 !important",
        fontSize: ".9375rem;"
    },
    h4: {
        marginBottom: 8,
        fontSize: "1.125rem",
        fontWeight: 500,
        lineHeight: 1,
    },
    img: {
        width: '150px !important',
        marginBottom: 20,
    },
    '@img': {
        width: '150px !important',
        marginBottom: 20,
    },
    inputForm: {
        padding: "0.94rem 1.94rem",
        width: "100%",
        fontSize: 14,
        outline: "none",
        borderWidth: "1px",
        marginBottom: "1.2rem",
        "&:focus": {
            outline: "none",
            borderColor: "blue",
            borderWidth: "1px",
            fontSize: 14,
            marginBottom: "1.2rem",
        },
    },
    inputFormErrors: {
        padding: "0.94rem 1.94rem",
        width: "100%",
        fontSize: 14,
        outline: "none",
        borderWidth: "1px",
        marginBottom: "0px !important",
        "&:focus": {
            outline: "none",
            borderColor: "blue",
            borderWidth: "1px",
            fontSize: 14,
            marginBottom: "0px !important",
        },
    },
    btn: {
        width: "100%",
        padding: "1rem 3rem",
        fontSize: "0.875rem",
        fontWeight: 400,
        borderRadius: "15px",
        color: "#fff",
        backgroundColor: "#4B49AC",
        borderColor: "#4B49AC",
    },
    error: {
        borderRadius: 4,
        marginBottom: "16px !important"
    },
}));

const renderError = (message) => (
    <p className='help is-danger' style={{ color: 'red' }}>
        {message}
    </p>
);

const LoginPage = () => {
    const classes = useStyles();
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const { error, isSelected, isFailed, isAuthenticated } = useSelector(state => state.jwt);
    const userRef = useRef();
    const initialValues = {
        username: '',
        password: '',
    };
    const validationSchema = Yup.object().shape({
        username: Yup.string().max(255).required('Username is required!'),
        password: Yup.string().max(255).required('Password is required!'),
    });

    useEffect(() => {
        userRef?.current?.focus();
        dispatch(jwtActions.clearJWT());
    }, []);

    return (
        <Formik
            initialValues={initialValues}
            validationSchema={validationSchema}
            onSubmit={async (
                values,
                { setSubmitting, resetForm, setErrors, setStatus }
            ) => {
                const { username, password } = values;
                try {
                    setSubmitting(true);
                    await dispatch(
                        loginUser({ username, password })
                    );

                } catch (error) {
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
                <Form
                    noValidate
                    onSubmit={handleSubmit}
                >
                    <div className="container-scroller" style={{ background: "#F5F7FF" }}>
                        <div className="container-fluid page-body-wrapper full-page-wrapper">
                            <div className="content-wrapper d-flex align-items-center auth px-0">
                                <div className="row w-100 mx-0">
                                    <div className={`${classes.container} col-lg-4`}>
                                        <div className={`auth-form-light text-left`}>
                                            <div className="brand-logo">
                                                <img src="/assets/img/logo.png" alt="logo" className={classes.img} />
                                            </div>
                                            <h4 className={classes.h4}>Hello! let's get started</h4>
                                            <div className={classes.h6}>Sign in to continue.</div>
                                            <div className="form-group">
                                                <Field
                                                    type='text'
                                                    className={`${errors.username && touched.username ? classes.inputFormErrors : ""} ${classes.inputForm} form-control ${touched.username && errors.username
                                                        ? 'is-invalid'
                                                        : ''
                                                        }`}
                                                    error={Boolean(touched.username && errors.username)}
                                                    onChange={(event) => {
                                                        dispatch(jwtActions.clearJWT());
                                                        handleChange(event);
                                                    }}
                                                    helperText={touched.username && errors.username}
                                                    name='username'
                                                    placeholder='Username'
                                                />
                                                <ErrorMessage name='username' render={renderError} />
                                            </div>
                                            <div className="form-group">
                                                <Field
                                                    type='password'
                                                    className={`${errors.password && touched.password ? classes.inputFormErrors : ""}  ${classes.inputForm} form-control ${touched.password && errors.password
                                                        ? 'is-invalid'
                                                        : ''
                                                        }`}
                                                    error={Boolean(touched.password && errors.password)}
                                                    onChange={(event) => {
                                                        dispatch(jwtActions.clearJWT());
                                                        handleChange(event);
                                                    }}
                                                    helperText={touched.password && errors.password}
                                                    name='password'
                                                    placeholder='Password'
                                                />
                                                <ErrorMessage name='password' render={renderError} />
                                            </div>
                                            {(!isAuthenticated && !isSelected) && <Grid
                                                item
                                                xs={12}
                                                sm={12}
                                                style={{ color: "rgb(97, 26, 21)" }}
                                            >
                                                <Box mt={2}>
                                                    <Alert
                                                        severity="error"
                                                        className={classes.error}
                                                    >
                                                        <div>
                                                            <strong>
                                                                {error}
                                                            </strong>
                                                        </div>
                                                    </Alert>
                                                </Box>
                                            </Grid>
                                            }
                                            <div className="mt-1">
                                                <button disabled={isFailed} className={`${classes.btn} btn btn-block btn-primary btn-lg`} type="submit">
                                                    {isFailed ?
                                                        <div class="d-flex justify-content-center"  >
                                                            <div class="spinner-border spinner-border-sm" role="status" style={{ size: "15px" }} />
                                                        </div>
                                                        :
                                                        "SIGN IN"}
                                                </button>
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
}

export default LoginPage;