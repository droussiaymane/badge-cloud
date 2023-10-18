
import react from 'react'; 


export const Footer = () => {

    return (
        <footer className="footer">
        <div className="container-fluid">
            <div className="row">
                <div className="col-md-6">
                    © Vizzibl LLC
                </div>
                <div className="col-md-6">
                    <div className="text-md-end footer-links d-none d-md-block">
                        <a href="terms-and-conditions.html">Terms &amp; Conditions</a>
                        <a href="privacy-policy.html">Privacy Policy</a>
                    </div>
                </div>
            </div>
        </div>
    </footer>
    );
}