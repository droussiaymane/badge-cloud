export const ViewCollectionModal = ({ record }) => {
    const imgBaseUrl = process.env.REACT_APP_IMAGE_BASE_URL;
    return (
        <div>
            <div className="modal fade" id="viewCollections" tabIndex={-1} aria-labelledby="viewCollectionsLabel" aria-hidden="true">
                <div className="modal-dialog modal-dialog-centered">
                    <div className="modal-content">
                        <div className="modal-header modal-colored-header bg-primary">
                            <h4 className="modal-title" id="viewCollectionsLabel">{record?.programName}</h4>
                            <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close" />
                        </div>
                        <div className="modal-body">
                            <div className="row justify-content-center">
                                <div className="col-md-8">
                                    <div className="my-3 px-4 py-3 d-flex justify-content-center align-items-center">
                                        <img src={imgBaseUrl + record?.badgeDTO?.image} className="img-fluid" alt="badge" />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    );
}