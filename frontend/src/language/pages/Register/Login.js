import React from "react";
import "../../../App.css";
import "../../../hover.css";
import "../../../fonts.css";
import "./index.css";
import {Link} from "react-router";


function Login() {
    return (
            <div className="register-container">
                <div className="register-container__title">Welcome back</div>
                <div className="register-container__subtitle">Enter your email to sign in for this app</div>
                <div className="register-container__input">
                    <input className="register-container__email-password email-input"
                           placeholder="Email@domain.com*"></input>
                </div>
                <div className="register-container__input">
                    <input className="register-container__email-password password-input"
                           placeholder="Password*"></input>
                </div>
                <div className="register-container__btn">
                    <button className="register-container__btn-continue">Continue</button>
                </div>
                <div className="register-container__linkto-signup">
                    <span className="register-container__account register-container__account-white">Don't have an account?</span>
                    <Link to="/signup" className="register-container__account register-container__account-green" to="/signup">Sign up</Link>
                </div>
                </div>
    );
}

export default Login;