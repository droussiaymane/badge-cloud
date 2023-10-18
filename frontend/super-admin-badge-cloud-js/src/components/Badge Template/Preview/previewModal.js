import { makeStyles } from "@material-ui/core";

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
    },
    badgeImg: {
        margin: '5rem 0px',
    }
}));

const PreviewModal = ({ record }) => {
    const imgBaseUrl = process.env.REACT_APP_IMAGE_BASE_URL;
    const classes = useStyles();
    return (
        <div>
            <div className="modal fade" id="preview" tabIndex={-1} aria-labelledby="previewLabel" aria-hidden="true">
                <div className="modal-dialog modal-dialog-centered modal-full-width">
                    <div className="modal-content">
                        <div className="modal-header modal-colored-header bg-primary">
                            <h5 className="modal-title" id="viewAssociationsLabel">Badge Preview</h5>
                            <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close" />
                        </div>
                        <div className="modal-body">
                            <header className="preview-header">
                                <div className="container px-xxl-5">
                                    <nav className="navbar navbar-expand-sm navbar-light py-3">
                                        <div className="container-fluid px-0">
                                            <a className="navbar-brand nav-logo" href="#">
                                                <img src="../assets/img/icurriculum_logo.png" className="img-fluid" alt="Company logo" style={{ width: 200 }} />
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
                                                        <a href="#" className="btn btn-outline-primary" target="_blank">Sign In</a>
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
                                                    <img src="../assets/img/user.png" className={classes.img} alt="Photo" />
                                                    <p className={classes.badgeHolderTitle}>This badge was issued to <a href="#" target="_blank">Badge holder name</a></p>
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
                                                    <img src={imgBaseUrl + record?.fileName} className={`img-fluid badge-img ${classes.badgeImg}`} alt="Badge" />
                                                    <a href="" target="_blank" className="d-md-block d-none">Additional Details</a>
                                                </div>
                                                {/* ps-4 changed to ps-md-4 */}
                                                <div className="col-md-8 ps-md-4">
                                                    <h1>Program</h1>
                                                    <div className="row">
                                                        <div className="col-md-12">
                                                            <div className="contents-preview mb-4">
                                                                <h5>Issued by <a href="#" target="_blank">Company Name</a></h5>
                                                                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
                                                                <a href="" target="_blank" className="d-md-none mt-2">Additional Details</a>
                                                            </div>
                                                            <div className="contents-preview mb-4">
                                                                <h5>Competencies</h5>
                                                                <a href="" className="btn btn-outline-primary" target="_blank">Competency 1</a>
                                                                <a href="" className="btn btn-outline-primary" target="_blank">Competency 2</a>
                                                            </div>
                                                            <div className="contents-preview mb-4">
                                                                <h5>Earning Requirements</h5>
                                                                <div className="earning-requirements d-flex justify-content-start">
                                                                    <p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.</p>
                                                                </div>
                                                            </div>
                                                            <div className="contents-preview mb-4">
                                                                <h5>Standards</h5>
                                                                <a href="" target="_blank">Standards 1</a>
                                                                <p className="mt-2">Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt.</p>
                                                            </div>
                                                            <div className="contents-preview">
                                                                <h5>Evidence</h5>
                                                                <a href="" target="_blank">Evidence 1</a>
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

export default PreviewModal;