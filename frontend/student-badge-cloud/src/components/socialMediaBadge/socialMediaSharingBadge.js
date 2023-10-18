import { makeStyles } from "@material-ui/core";
import React, { useState } from "react";
import { useParams } from 'react-router-dom';
import { socialMediaBadgeApi } from "../../api/socialMediaBadgeApi";

const useStyles = makeStyles((theme) => ({
    badgeHolder: {
        padding: '1.3rem 0px !important',
    },
    img: {
        width: '80px !important',
        marginRight: "32px",
        height: '80px !important',
        borderRadius: "50%",
    },
    badgeHolderTitle: {
        margin: "0px !important",
    }
}));


const SocialMediaSharingBadge = () => {
    const imgBaseUrl = process.env.REACT_APP_PUBLIC_BADGE_URL;
    const baseUrl = window.location.protocol + "//" + window.location.host;
    const { id } = useParams();
    const classes = useStyles();
    const [badgeInfo, setBadgeInfo] = useState();
    const getSocialMediaBadgeInfo = async () => {
        try {
            const res = await socialMediaBadgeApi.getSocialMediaBadgeUrl(id)
            setBadgeInfo(res?.data);
        } catch (err) {

            console.log(err?.response?.data?.message || err?.message);
        }
    }
    React.useEffect(() => {
        getSocialMediaBadgeInfo();
    }, [])

    console.log("id", id, baseUrl)
    return (
        <div>
            <div className="" id="preview" tabIndex={-1} aria-labelledby="previewLabel" aria-hidden="true">
                <div className="modal-dialog modal-dialog-centered modal-full-width">
                    <div className="modal-content">
                        <div className="modal-body">
                            <header className="preview-header">
                                <div className="container px-xxl-5">
                                    <nav className="navbar navbar-expand-sm navbar-light py-3">
                                        <div className="container-fluid px-0">
                                            <a className="navbar-brand nav-logo" href="#">
                                                <img src="../assets/img/logo.png" className="img-fluid" alt="Company logo" style={{ width: 200 }} />
                                            </a>
                                            <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                                                <i className="dripicons-menu" />
                                            </button>
                                            <div className="collapse navbar-collapse menu" id="navbarSupportedContent">
                                                <ul className="navbar-nav ms-auto" style={{ marginRight: 0 }}>
                                                    <li className="nav-item ms-sm-2 mt-sm-0 mt-2">
                                                        <a href="#" className="btn btn-primary" target="_blank">Create Account</a>
                                                    </li>
                                                    <li className="nav-item ms-sm-2 mt-sm-0 mt-2">
                                                        <a href={baseUrl} className="btn btn-outline-primary" target="_blank">Sign In</a>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>
                                    </nav>
                                </div>
                            </header>
                            <section className="badge-holder bg-light">
                                <div className="container px-xxl-5">
                                    <div className="row">
                                        <div className="col-md-12">
                                            <div className={`${classes.badgeHolder} d-sm-flex justify-content-sm-between align-items-center`}>
                                                <div className="badge-holder-info d-flex justify-content-between align-items-center">
                                                    <img src={badgeInfo?.studentDTO?.photo ? `${imgBaseUrl + badgeInfo?.studentDTO?.photo}` : "../assets/img/logo.png"} className={classes.img} alt="Photo" />
                                                    <p className={classes.badgeHolderTitle}>This badge was issued to <a href="#" target="_blank">{badgeInfo?.studentDTO?.name}</a></p>
                                                </div>
                                                <button type="button" className="btn btn-primary">Validate</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </section>
                            <section className="badge-info">
                                <div className="container px-xxl-5">
                                    <div className="row">
                                        <div className="col-md-12">
                                            <div className="row">
                                                <div className="col-md-4 pe-4 text-center">
                                                    <img src={imgBaseUrl + badgeInfo?.badgeDTO?.image} className="img-fluid badge-img" alt="Badge" />
                                                    <a href="#" target="_blank" className="d-md-block d-none">Additional Details</a>
                                                    <a href="#" target="_blank">{badgeInfo?.additionalDetailsDTO?.name}</a>
                                                    <p className="mt-2">{badgeInfo?.additionalDetailsDTO?.description}</p>
                                                </div>
                                                <div className="col-md-8 ps-md-4">
                                                    <h1>{badgeInfo?.programName}</h1>
                                                    <div className="row">
                                                        <div className="col-md-12">
                                                            <div className="contents-preview mb-4">
                                                                <h5>Issued by <a href="#" target="_blank">{badgeInfo?.issuedBy}</a></h5>
                                                                <p>{badgeInfo?.description}</p>
                                                                <a href="#" target="_blank" className="d-md-none mt-2">Additional Details</a>
                                                            </div>
                                                            <div className="contents-preview mb-4">
                                                                <h5>Competencies</h5>
                                                                {badgeInfo?.competencyDTOS.map((competency) => {
                                                                    return <a href="#" className="btn btn-outline-primary" target="_blank">{competency?.name}</a>
                                                                })}
                                                            </div>
                                                            <div className="contents-preview mb-4">
                                                                <h5>Earning Requirements</h5>
                                                                <div className="earning-requirements d-flex justify-content-start">
                                                                    <p>{badgeInfo?.earningRequirementDTO?.description}</p>
                                                                </div>
                                                            </div>
                                                            <div className="contents-preview mb-4">
                                                                <h5>Standards</h5>
                                                                <a href="#" target="_blank">{badgeInfo?.standardDTO?.name}</a>
                                                                <p className="mt-2">{badgeInfo?.standardDTO?.description}</p>
                                                            </div>
                                                            <div className="contents-preview">
                                                                <h5>Evidence</h5>
                                                                <a href="#" target="_blank">{badgeInfo?.evidenceDTO?.name}</a>
                                                                <p className="mt-2">{badgeInfo?.evidenceDTO?.description}</p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </section>
                            <footer className="preview-footer">
                                <div className="container">
                                    <div className="row">
                                        <div className="col-md-12 text-center">
                                            <p className="mb-0">Powered by <a href="https://vizzibl.io" target="_blank"><strong>Vizzibl.io</strong></a></p>
                                        </div>
                                    </div>
                                </div>
                            </footer>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default SocialMediaSharingBadge;