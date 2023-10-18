import { Field, Form, Formik } from 'formik';
import * as Yup from 'yup';
import React from 'react';
import { studentApi } from '../../api/userApi';
import { useState } from 'react';
import { useSelector } from 'react-redux';
import { makeStyles } from '@material-ui/core';

const useStyles = makeStyles((theme) => ({
  img: {
    aspectRatio: "1100 / 1000"
  },
}));
const Home = () => {
  const classes = useStyles();
  const imgBaseUrl = process.env.REACT_APP_PUBLIC_BADGE_URL;
  const imgBaseUrlSSL = process.env.REACT_APP_PUBLIC_BADGE_URL_SSL;
  const [title, setTitle] = useState('');
  const [subTitle, setSubTitle] = useState('');

  const baseUrl = window.location.protocol + "//" + window.location.host;

  const { userId, tenantId } = useSelector((state) => state.jwt);
  const initialValues = {
    collection: '',
  };


  const [studentData, setStudentData] = useState(null);
  const [badge, setSelectedBadge] = useState(null);
  const [share, setShare] = useState('');


  const getSingleStudent = async () => {
    try {
      const res = await studentApi.getSingleStudent(userId)
      setStudentData(res?.data);
    } catch (err) {
      console.log(err?.response?.data?.message || err?.message);
    }
  }

  const getPublicUrl = async (programId) => {
    try {
      const body = {
        studentId: userId,
        tenantId,
        programId,
      };
      const res = await studentApi.getPublicUrl(body);
      setShare(res?.data?.userBadgeViewUrl || '');
    } catch (err) {
      console.log(err?.response?.data?.message || err?.message);
    }
  }

  React.useEffect(() => {
    getSingleStudent();
  }, [badge])
  const validationSchema = Yup.object().shape({});
  const sharePage = (postClickUrl, imageUrl, successUrl) => {
    const errorUrl = `${baseUrl}/badges/linkdinerror`;
    const url = `https://www.linkedin.com/oauth/v2/authorization?response_type=code&client_id=78slbnekpqfyos&redirect_uri=https%3A%2F%2Falpha.genesisapi.io%2Fshare%2Flinkedin&scope=r_liteprofile%20r_emailaddress%20w_member_social&state=
                {"text":"${title}","subtitle":"${subTitle}",
                "postClickUrl":"${postClickUrl}",
                "imageUrl":[{"image":"${imageUrl}",
                "height":500,"width":500}],
                "successUrl":"${successUrl}",
                "errorUrl":"${errorUrl}"}`;
    window.open(url, '_blank', 'width=600,height=400');
    // window.location.href = '/home'
  }


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
          resetForm();
        } catch (error) {
          setStatus({ success: false });
          setErrors({ submit: error.message });
          setSubmitting(false);
          console.log('Error', error);
        }
      }}
    >
      {({
        values,
      }) => (
        <>
          <Form>
            <div className="content-page">
              <div className="content">
                <div className="container-fluid">
                  <div className="row">
                    <div className="col-12">
                      <div className="page-title-box">
                        <h4 className="page-title">Home</h4>
                      </div>
                    </div>
                  </div>
                  <div className="row">
                    <div className="col-md-12">
                      <div className="card">
                        <div className="card-body">
                          <div className="row">
                            <div className="col-md-2 pb-3 p-md-0">
                              <img
                                src={studentData?.image ? `${imgBaseUrl + studentData?.image}` : 'assets/img/user.png'}
                                className={`${classes.img} img-fluid rounded-circle px-2`}
                                alt="User Photo"
                              />
                            </div>
                            <div className="offset-md-1 col-md-8">
                              <table className="table table-centered mb-0">
                                <tbody>
                                  <tr>
                                    <th>Student ID</th>
                                    <td>{studentData?.studentId}</td>
                                  </tr>
                                  <tr>
                                    <th>Student Name</th>
                                    <td>{studentData?.studentName}</td>
                                  </tr>
                                  <tr>
                                    <th>Programs Completed</th>
                                    <td>
                                      {studentData?.programsBadges && studentData?.programsBadges?.map((item) => {
                                        return (
                                          <p> {item?.programName}</p>
                                        )
                                      })}
                                    </td>
                                  </tr>
                                </tbody>
                              </table>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <>
                    <div className="row">
                      <div className="col-md-12">
                        <div className="card">
                          <div className="card-body">
                            <h4 className="header-title mb-3">Badge Collection</h4>

                            <label className='form-label'>
                              Select Collection{' '}
                              <select
                                name="collection"
                                as="select"
                                component="select"
                                className="form-select mb-3"
                                id="collection"
                                onChange={(e) => setSelectedBadge(JSON.parse(e.target.value))}>
                                <option value="" disabled selected>Select your option</option>
                                {
                                  studentData?.programsBadges && studentData?.programsBadges.map((badge) => (
                                    <option value={JSON.stringify(badge)}>
                                      {badge?.badgeName}
                                    </option>
                                  ))
                                }
                              </select>
                            </label>

                            {badge &&
                              <div className="row align-items-center">
                                <div className="col-md-4">
                                  <div className="card text-center">
                                    <div className="card-body">
                                      <input
                                        type="radio"
                                        name="badge_select"
                                        className="visually-hidden"
                                        data-id={53}
                                      />
                                      <label data-for={53}>
                                        <img
                                          src={imgBaseUrl + badge.image}
                                          // src="assets/img/Cyber Girls Oracle.png"
                                          className="img-fluid badge-img"
                                          alt="Badge"
                                        />
                                      </label>
                                    </div>
                                    <div className="card-body pt-0">
                                      <button
                                        type="button"
                                        className="btn btn-primary"
                                        data-bs-toggle="modal"
                                        data-bs-target="#A53"
                                        onClick={() => getPublicUrl(badge.programId)}
                                      >
                                        Share
                                      </button>
                                    </div>
                                  </div>
                                </div>
                              </div>
                            }
                          </div>
                        </div>
                      </div>
                    </div>
                  </>
                </div>
              </div>
              <div>
                <div
                  className="modal fade"
                  id="A53"
                  tabIndex={-1}
                  aria-labelledby="A53Label"
                  aria-hidden="true"
                >
                  <div className="modal-dialog modal-dialog-centered">
                    <div className="modal-content">
                      <div className="modal-header modal-colored-header bg-primary">
                        <h5 className="modal-title" id="A53Label">
                          Share with friends
                        </h5>
                        <button
                          type="button"
                          className="btn-close"
                          data-bs-dismiss="modal"
                          aria-label="Close"
                        />
                      </div>
                      <div
                        className="modal-body"
                        style={{ padding: '1rem 2rem 2rem' }}
                      >
                        <div className="row align-items-center py-3">
                          <div className="col-12 d-flex justify-content-between">
                            <a href="#AL53" data-bs-toggle="modal" role="button"><i class="uil-linkedin fs-2" aria-hidden="true"></i></a>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div>
                <div
                  className="modal fade"
                  id="AL53"
                  tabIndex={-1}
                  aria-labelledby="AL53Label"
                  aria-hidden="true"
                >
                  <div className="modal-dialog modal-dialog-centered">
                    <div className="modal-content">
                      <div className="modal-header modal-colored-header bg-primary">
                        <a href="#A53" data-bs-toggle="modal" role="button">
                          <i className=" uil-arrow-left fs-2 text-white lh-1" />
                        </a>
                        <h5 className="modal-title ms-2" id="AL53Label">
                          Share with friends
                        </h5>
                        <button
                          type="button"
                          className="btn-close"
                          data-bs-dismiss="modal"
                          aria-label="Close"
                        />
                      </div>
                      <div
                        className="modal-body"
                        style={{ padding: '1rem 2rem 2rem' }}
                      >
                        <div className="row">
                          <div className="col-md-12">
                            <div className="row mt-3">
                              <div className="col-md-12 mb-3">
                                <label className="form-label">Title</label>
                                <input
                                  type="text"
                                  name="title"
                                  className="form-control"
                                  value={title}
                                  onChange={(e) => setTitle(e.target.value)}
                                />
                              </div>
                              <div className="col-md-12 mb-3">
                                <label className="form-label">Sub-Title</label>
                                <input
                                  type="text"
                                  name="subTitle"
                                  className="form-control"
                                  value={subTitle}
                                  onChange={(e) => setSubTitle(e.target.value)}
                                />
                              </div>
                              <div className="col-md-12">
                                <button
                                  className="btn btn-primary"
                                  name="share"
                                  onClick={() => sharePage(`${baseUrl + share}`, `${imgBaseUrlSSL + badge.image}`, `https://www.linkedin.com/`)}
                                >
                                  Submit
                                </button>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </Form>
        </>
      )}
    </Formik>
  );
};

export default Home;
