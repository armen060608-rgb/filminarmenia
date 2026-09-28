import React from "react";
import {Link} from "react-router-dom";
import "../../../App.css";
import "../../../hover.css";
import "../../../fonts.css";
import "./index.css";

function Footer() {
    return (
            <footer className="footer">

                <div className="footer__container">
                    <div className="footer-left">
                        <svg width="76" height="77" viewBox="0 0 76 77" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <path
                                d="M6.3627 17.6764C11.9456 14.0417 24.0525 12.6399 36.132 16.9977C14.0425 24.6579 2.40238 42.6841 3.37217 51.8856C0.532562 45.7279 -2.44157 28.8359 6.3627 17.6764Z"
                                fill="white" stroke="white" strokeOpacity="0.692308" strokeWidth="1.00157"/>
                            <path
                                d="M8.26611 14.8251C21.8377 9.41448 39.9933 13.5514 55.0271 24.4634C52.7607 17.8114 49.371 6.67553 37.7636 0.571289C21.4814 1.06201 9.7752 12.7828 8.26611 14.8251Z"
                                fill="white" stroke="white" strokeOpacity="0.692308" strokeWidth="1.00157"/>
                            <path
                                d="M40.4819 0.571289C52.8842 8.21217 62.9032 29.9326 57.4736 46.4551C62.2488 43.0374 71.6152 32.8479 71.0669 20.3909C64.1969 7.27955 51.0731 0.964977 40.4819 0.571289Z"
                                fill="white" stroke="white" strokeOpacity="0.692308" strokeWidth="1.00157"/>
                            <path
                                d="M72.0109 22.5239C72.9406 36.2097 57.1659 53.292 40.3877 58.8083C45.0796 61.0362 59.2056 64.3677 69.9924 58.0403C78.3763 44.4107 75.6147 30.7674 72.0109 22.5239Z"
                                fill="white" stroke="white" strokeOpacity="0.692308" strokeWidth="1.00157"/>
                            <path
                                d="M18.4726 29.3398C10.1318 36.6046 3.72812 45.0044 4.43922 54.1054C8.58177 63.9931 19.3043 73.8557 33.9478 75.1272C25.8982 71.2168 14.687 52.9793 18.4726 29.3398Z"
                                fill="white" stroke="white" strokeOpacity="0.692308" strokeWidth="1.00157"/>
                            <path
                                d="M20.3945 49.7856C34.2383 61.1866 53.707 66.5473 68.1658 60.5366C65.4736 66.0024 52.0861 77.0091 37.2154 75.4151C25.6054 69.3595 21.6871 57.7384 20.3945 49.7856Z"
                                fill="white" stroke="white" strokeOpacity="0.692308" strokeWidth="1.00157"/>
                            <path
                                d="M30.0066 23.8677H48.3653L48.4374 28.3552C47.8044 26.8658 47.9201 24.8275 45.0011 24.5396H36.1582L36.2783 37.9063L40.4835 37.9782C42.0902 38.1146 43.2001 37.5188 43.4632 35.6745H43.9679L43.8958 41.0019L43.0788 40.954C43.0943 39.4561 42.2956 38.9907 40.5316 38.9862L36.2543 38.8902L36.1582 50.457C36.2055 51.2897 36.6558 51.7199 37.6 51.6569V52.3288L30.1988 52.2329V51.5609C30.9207 51.5459 31.7707 51.5887 31.7848 50.3611L31.9289 25.4516C31.5878 24.5901 30.8752 24.3471 29.9585 24.4437L30.0066 23.8677Z"
                                fill="white" stroke="white" strokeOpacity="0.692308" strokeWidth="1.00157"/>
                        </svg>

                        <p className="copyright_2025">©Copyright 2026, All rights reserved</p>
                    </div>
                    <div className="footer-right">
                        <div className="footer-links-box">
                            <h3 className="box-title">Information</h3>
                            <Link className="footer-links" to="#">Support</Link>
                            {/*api.getLabel('label.footer.support', language)*/}
                            <Link className="footer-links" to="#">Terms & Conditions</Link>
                            <Link className="footer-links" to="#">Privacy & Policy</Link>
                        </div>
                        <div className="footer-links-box">
                            <h3 className="box-title">Contact info</h3>
                            <p className="footer-links">Email: info@cfarmenia.am</p>
                            <p className="footer-links">Phone: +374 (10) 52 20 32</p>
                            <p className="footer-links">Location: Տերյան 3ա</p>
                        </div>
                        <div className="footer-links-box">
                            <h3 className="box-title">Follow us on</h3>
                            <div className="social-icons">
                                <Link to="https://www.instagram.com/webmaster_ayb/" className="soc-link soc-link--tg" target="_blank">
                                    <svg width="20" height="20" viewBox="0 0 20 20" fill="none"
                                         xmlns="http://www.w3.org/2000/svg">
                                        <path
                                            d="M5.8 0H14.2C17.4 0 20 2.6 20 5.8V14.2C20 15.7383 19.3889 17.2135 18.3012 18.3012C17.2135 19.3889 15.7383 20 14.2 20H5.8C2.6 20 0 17.4 0 14.2V5.8C0 4.26174 0.61107 2.78649 1.69878 1.69878C2.78649 0.61107 4.26174 0 5.8 0ZM5.6 2C4.64522 2 3.72955 2.37928 3.05442 3.05442C2.37928 3.72955 2 4.64522 2 5.6V14.4C2 16.39 3.61 18 5.6 18H14.4C15.3548 18 16.2705 17.6207 16.9456 16.9456C17.6207 16.2705 18 15.3548 18 14.4V5.6C18 3.61 16.39 2 14.4 2H5.6ZM15.25 3.5C15.5815 3.5 15.8995 3.6317 16.1339 3.86612C16.3683 4.10054 16.5 4.41848 16.5 4.75C16.5 5.08152 16.3683 5.39946 16.1339 5.63388C15.8995 5.8683 15.5815 6 15.25 6C14.9185 6 14.6005 5.8683 14.3661 5.63388C14.1317 5.39946 14 5.08152 14 4.75C14 4.41848 14.1317 4.10054 14.3661 3.86612C14.6005 3.6317 14.9185 3.5 15.25 3.5ZM10 5C11.3261 5 12.5979 5.52678 13.5355 6.46447C14.4732 7.40215 15 8.67392 15 10C15 11.3261 14.4732 12.5979 13.5355 13.5355C12.5979 14.4732 11.3261 15 10 15C8.67392 15 7.40215 14.4732 6.46447 13.5355C5.52678 12.5979 5 11.3261 5 10C5 8.67392 5.52678 7.40215 6.46447 6.46447C7.40215 5.52678 8.67392 5 10 5ZM10 7C9.20435 7 8.44129 7.31607 7.87868 7.87868C7.31607 8.44129 7 9.20435 7 10C7 10.7956 7.31607 11.5587 7.87868 12.1213C8.44129 12.6839 9.20435 13 10 13C10.7956 13 11.5587 12.6839 12.1213 12.1213C12.6839 11.5587 13 10.7956 13 10C13 9.20435 12.6839 8.44129 12.1213 7.87868C11.5587 7.31607 10.7956 7 10 7Z"
                                            fill="white"/>
                                    </svg>

                                </Link>
                                <Link to="facebook.com" target="_blank"
                                      className="soc-link soc-link--inst">
                                    <svg width="20" height="20" viewBox="0 0 20 20" fill="none"
                                         xmlns="http://www.w3.org/2000/svg">
                                        <path
                                            d="M20 10C20 4.48 15.52 0 10 0C4.48 0 0 4.48 0 10C0 14.84 3.44 18.87 8 19.8V13H6V10H8V7.5C8 5.57 9.57 4 11.5 4H14V7H12C11.45 7 11 7.45 11 8V10H14V13H11V19.95C16.05 19.45 20 15.19 20 10Z"
                                            fill="white"/>
                                    </svg>

                                </Link>
                                <Link to="https://t.me/FilmerScouter" target="_blank" className="soc-link soc-link--fb">
                                    <svg width="20" height="20" viewBox="0 0 20 20" fill="none"
                                         xmlns="http://www.w3.org/2000/svg">
                                    <path
                                            d="M10 0C4.48 0 0 4.48 0 10C0 15.52 4.48 20 10 20C15.52 20 20 15.52 20 10C20 4.48 15.52 0 10 0ZM14.64 6.8C14.49 8.38 13.84 12.22 13.51 13.99C13.37 14.74 13.09 14.99 12.83 15.02C12.25 15.07 11.81 14.64 11.25 14.27C10.37 13.69 9.87 13.33 9.02 12.77C8.03 12.12 8.67 11.76 9.24 11.18C9.39 11.03 11.95 8.7 12 8.49C12.0069 8.45819 12.006 8.42517 11.9973 8.3938C11.9886 8.36244 11.9724 8.33367 11.95 8.31C11.89 8.26 11.81 8.28 11.74 8.29C11.65 8.31 10.25 9.24 7.52 11.08C7.12 11.35 6.76 11.49 6.44 11.48C6.08 11.47 5.4 11.28 4.89 11.11C4.26 10.91 3.77 10.8 3.81 10.45C3.83 10.27 4.08 10.09 4.55 9.9C7.47 8.63 9.41 7.79 10.38 7.39C13.16 6.23 13.73 6.03 14.11 6.03C14.19 6.03 14.38 6.05 14.5 6.15C14.6 6.23 14.63 6.34 14.64 6.42C14.63 6.48 14.65 6.66 14.64 6.8Z"
                                            fill="white"/>
                                    </svg>

                                </Link>
                            </div>
                        </div>
                    </div>
                </div>
            </footer>
    );
}

export default Footer;
