import React, {useEffect} from "react";
import {Link} from "react-router";
import "../../../App.css";
import SecondFirst from "../../images/kerparik photo 1.png";
import SecondSecond from "../../images/kerparik camera 1.png";
import SecondThird from "../../images/kerparik computer 1.png";
import "../../../hover.css";
import "../../../fonts.css";
import "./index.css";
import { getTranslations } from "../../translations.js";
import {useLanguage} from "../../language";

function Home() {

    const { language } = useLanguage();
    const t = getTranslations(language);

    useEffect(() => {
        document.body.classList.add("home-body");

        return () => {
            document.body.classList.remove("home-body");
        };
    }, []);

    return (
        <div className="home-body">
            <section className="top-box">
                <div className="top-box__left">
                    {/*api.getLabel('label.home.title', language)*/}
                    <h1 className="top-box__title">{t.hero_title}</h1>
                    <p className="top-box__description">{t.hero_subtitle}</p>
                    <Link to="/explore" className="top-box__btn">{t.getStarted}</Link>
                </div>
                {/*<div className="top-box__right">*/}
                {/*    <img src={image1} alt="Illustration of a chicken with a camera" className="hero-icon"/>*/}
                {/*</div>*/}
            </section>

            <section className="choose">
                <div className="center">
                    <h2 className="choose__title montserrat">{t.whyChooseUs}</h2>
                    <div className="choose__features">
                        <div className="feature-box pr">
                            <img className="image1" src={SecondFirst} alt="Find by Keywords"/>

                            <div className="choose__text-box pr">
                                <p className="feature-box__text poppins-regular">{t.feature1_text}</p>
                            </div>

                        </div>
                        <div className="feature-box pr">
                            <img className="image2" src={SecondSecond} alt="Find by Uploading Photo"/>
                            <div className="choose__text-box pr">
                                <p className="feature-box__text poppins-regular">{t.feature2_text}</p>
                            </div>
                        </div>
                        <div className="feature-box pr">
                            <img className="image3" src={SecondThird} alt="Filter System"/>
                            <div className="choose__text-box pr">
                                <p className="feature-box__text poppins-regular">{t.feature3_text}</p>
                            </div>
                        </div>
                    </div>
                </div>

            </section>

        </div>
    );
}

export default Home;