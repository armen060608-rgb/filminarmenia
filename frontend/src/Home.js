import React from "react";
import "./App.css";
import image1 from "./images/image-2.png";
import SecondFirst from "./images/second-first.svg";
import SecondSecond from "./images/second-second.svg";
import SecondThird from "./images/second-third.svg";
import "./hover.css";
import "./fonts.css";
import Image7_1 from "./images/Image7_1.jpg"
import Image15_1 from "./images/Image15_1.jpg"
import Image2_1 from "./images/Image2_1.jpg"

function Home() {
    return (
        <>
            <section className="top-box">
                <div className="top-box__left">
                    <h1 className="top-box__title">Փնտրեք ձեր կատարյալ վայրը</h1>
                    <p className="top-box__description">Կյանքի կոչեք ձեր երազանքի ֆիլմը կամ ֆոտոշարքը հենց հիմա
                        որոնել, զտել տեղանքը կամ ցուցադրել նկար</p>
                    <button className="top-box__btn">Սկսեք</button>
                </div>
                <div className="top-box__right">
                    <img src={image1} alt="Illustration of a chicken with a camera" className="hero-icon"/>
                </div>
            </section>

            <section className="choose">
                <div className="center">
                    <h2 className="choose__title montserrat">Ինչու՞ ընտրել մեզ:</h2>
                    <div className="choose__features">
                        <div className="feature-box pr">
                            <img className="choose__img pa" src={Image2_1} alt="img"/>
                            <img className="image1" src={SecondFirst} alt="Find by Keywords"/>

                            <div className="choose__text-box pr">
                                <h3 className="feature-box__title poppins-medium">Գտեք ըստ բանալի բառերի</h3>
                                <p className="feature-box__text poppins-regular">Գտեք ձեր երազանքների վայրը
                                    պարզապես
                                    նկարագրելով այն.</p>
                            </div>

                        </div>
                        <div className="feature-box pr">
                            <img className="choose__img pa" src={Image15_1} alt="img"/>
                            <img className="image2" src={SecondSecond} alt="Find by Uploading Photo"/>
                            <div className="choose__text-box pr">
                                <h3 className="feature-box__title poppins-medium">Գտեք լուսանկարը ներբեռնելով</h3>
                                <p className="feature-box__text poppins-regular">Ունե՞ք նկար, թե ինչ եք փնտրում:
                                    Դե, լուսանկարը ձեզանից, գտնվելու վայրը
                                    մեզ։</p>
                            </div>
                        </div>
                        <div className="feature-box pr">
                            <img className="choose__img pa" src={Image7_1} alt="img"/>
                            <img className="image3" src={SecondThird} alt="Filter System"/>
                            <div className="choose__text-box pr">
                                <h3 className="feature-box__title poppins-medium">Զտիչ համակարգ</h3>
                                <p className="feature-box__text poppins-regular">Պարզապես զտեք ձեր գտնվելու վայրի համար
                                    ընտրություն.</p>
                            </div>
                        </div>
                    </div>
                </div>

            </section>

        </>
    );
}

export default Home;
