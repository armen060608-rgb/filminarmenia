import React from "react";
import "./index.css";
import { NavLink, Link } from "react-router";



function NotFound() {
    return (
        <div className="register-container2">
            <div className="register-container2__title">Create an account</div>
            <div className="register-container2__subtitle">Enter your email to sign in for this app</div>
            <div className="register-container2__inputs">
                <input className="register-container2__input" placeholder="Name*"></input>
                <input className="register-container2__input" placeholder="Surname*"></input>
                <input className="register-container2__input" placeholder="Email@domain.com*"></input>
                <input className="register-container2__input" placeholder="Password*"></input>
                <button>
                    <svg className="register-container2__image1" xmlns="http://www.w3.org/2000/svg" width="24"
                         height="24" viewBox="0 0 24 24" fill="none">
                        <path d="M1 12C1 12 5 4 12 4C19 4 23 12 23 12C23 12 19 20 12 20C5 20 1 12 1 12Z"
                              stroke="#898989"
                              stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        <path
                            d="M12 15C13.6569 15 15 13.6569 15 12C15 10.3431 13.6569 9 12 9C10.3431 9 9 10.3431 9 12C9 13.6569 10.3431 15 12 15Z"
                            stroke="#898989" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                </button>
                <button>
                    <svg className="register-container2__image2" xmlns="http://www.w3.org/2000/svg" width="24"
                         height="24" viewBox="0 0 24 24" fill="none">
                        <path d="M1 12C1 12 5 4 12 4C19 4 23 12 23 12C23 12 19 20 12 20C5 20 1 12 1 12Z"
                              stroke="#898989"
                              stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        <path
                            d="M12 15C13.6569 15 15 13.6569 15 12C15 10.3431 13.6569 9 12 9C10.3431 9 9 10.3431 9 12C9 13.6569 10.3431 15 12 15Z"
                            stroke="#898989" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                </button>
                <input className="register-container2__input" placeholder="Confirm password*"></input>
                <button className="register-container__btn-continue">Continue</button>
                <div className="register-container2__have-account">
                    <span className="register-container2__have-account__white">Already have an account?</span>
                    <Link className="register-container2__have-account__green" to="/login">LogIn</Link>
                </div>
                <div className="register-container2__clicking">
                    <span>By clicking continue, you agree to our</span>
                    <Link className="register-container2__clicking-green" to="/">Terms of Service</Link>
                    <span>and</span>
                    <Link className="register-container2__clicking-green" to="/">Privacy Policy</Link>
                </div>
            </div>
        </div>
    );
}

export default NotFound;