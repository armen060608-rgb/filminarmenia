import React from "react";
import {
    MapPin,
    Info,
    Users,
    CalendarDays,
    ClipboardCheck,
    Handshake,
    Clock,
    Globe,
    Camera,
    Film,
    Heart,
    Sparkles,
    ArrowRight,
} from "lucide-react";
import { useLanguage } from "../../language";
import { getTranslations } from "../../translations";
import "./AboutUs.css";
import {Link} from "react-router";

const IMG_HERO_BG =
    "https://images.unsplash.com/photo-1677864109159-34eb97228c65?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";
const IMG_GORGE =
    "https://plus.unsplash.com/premium_photo-1666863909125-3a01f038e71f?q=80&w=1572&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";
const IMG_TEMPLE =
    "https://images.unsplash.com/photo-1629761905941-a3801e2874e2?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";
const IMG_LAKE =
    "https://images.unsplash.com/photo-1505765050516-f72dcac9c60e?auto=format&fit=crop&w=900&q=80";
const IMG_CITY =
    "https://images.unsplash.com/photo-1589537368054-6a9c11ba1eac?q=80&w=1740&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG290by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";
const IMG_CTA_BG =
    "https://images.unsplash.com/photo-1597128395237-5e6e3f6f8c41?auto=format&fit=crop&w=2000&q=80";

export default function AboutUs() {
    const { language } = useLanguage();
    const t = getTranslations(language).aboutUs;

    const features = [
        { Icon: MapPin, title: t.features.search_title, desc: t.features.search_desc },
        { Icon: Info, title: t.features.info_title, desc: t.features.info_desc },
        { Icon: Users, title: t.features.support_title, desc: t.features.support_desc },
        { Icon: CalendarDays, title: t.features.plan_title, desc: t.features.plan_desc },
    ];

    const collabPoints = [
        { Icon: Sparkles, title: t.collab_points.cinema_title, desc: t.collab_points.cinema_desc },
        { Icon: Heart, title: t.collab_points.heritage_title, desc: t.collab_points.heritage_desc },
        { Icon: Globe, title: t.collab_points.global_title, desc: t.collab_points.global_desc },
    ];

    const whyItems = [
        { Icon: MapPin, title: t.why_items.discover_title, desc: t.why_items.discover_desc },
        { Icon: ClipboardCheck, title: t.why_items.access_title, desc: t.why_items.access_desc },
        { Icon: Handshake, title: t.why_items.partners_title, desc: t.why_items.partners_desc },
        { Icon: Clock, title: t.why_items.save_title, desc: t.why_items.save_desc },
        { Icon: Globe, title: t.why_items.confidence_title, desc: t.why_items.confidence_desc },
        { Icon: Camera, title: t.why_items.showcase_title, desc: t.why_items.showcase_desc },
    ];

    return (
        <div className="au">
            <section
                className="au-hero"
                style={{ backgroundImage: `url(${IMG_HERO_BG})` }}
            >
                <div className="au-hero__overlay" />
                <div className="au-hero__inner">
                    <h1 className="au-hero__title">{t.hero_title}</h1>
                    <span className="au-hero__rule" />
                    <p className="au-hero__sub">{t.hero_sub_1}</p>
                    <p className="au-hero__sub">{t.hero_sub_2}</p>
                </div>
            </section>

            <section className="au-wrap">
                <div className="au-card au-card--overlap">
                    <div className="au-about">
                        <div className="au-about__text">
                            <span className="au-eyebrow">{t.eyebrow_about}</span>
                            <h2 className="au-h2">{t.about_title}</h2>
                            <p className="au-lead">{t.about_lead}</p>

                            <div className="au-features">
                                {features.map(({ Icon, title, desc }) => (
                                    <div className="au-feature" key={title}>
                    <span className="au-feature__icon">
                      <Icon size={18} strokeWidth={1.6} />
                    </span>
                                        <h4 className="au-feature__title">{title}</h4>
                                        <p className="au-feature__desc">{desc}</p>
                                    </div>
                                ))}
                            </div>
                        </div>

                        <div className="au-about__gallery">
                            <div className="au-gallery__row au-gallery__row--one">
                                <img src={IMG_GORGE} alt="Armenian mountain gorge" />
                            </div>
                            <div className="au-gallery__row au-gallery__row--two">
                                <img src={IMG_TEMPLE} alt="Garni temple" />
                                <img src={IMG_LAKE} alt="Sevan lake" />
                            </div>
                            <div className="au-gallery__row au-gallery__row--three">
                                <img src={IMG_CITY} alt="Yerevan with Ararat" />
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <section className="au-wrap">
                <div className="au-card au-card--tinted">
                    <div className="au-collab">
                        <div className="au-collab__intro">
                            <span className="au-eyebrow">{t.eyebrow_collab}</span>
                            <h2 className="au-h2">
                                {t.collab_title_1}
                                {t.collab_title_2 && (
                                    <>
                                        <br />
                                        {t.collab_title_2}
                                    </>
                                )}
                            </h2>
                            <p className="au-lead">{t.collab_lead_1}</p>
                            <p className="au-lead">{t.collab_lead_2}</p>
                            <a className="au-btn" href="https://ncca.am/hy/" target="_blank">
                                {t.collab_btn} <ArrowRight size={16} strokeWidth={2} />
                            </a>
                        </div>

                        <div className="au-collab__logo" aria-hidden>
                            <div className="au-logo-mark">
                                <Film size={64} strokeWidth={1.3} />
                            </div>
                            <div className="au-logo-text">
                                {t.logo_text_1}
                                <br />
                                {t.logo_text_2}
                                {t.logo_text_3 && (
                                    <>
                                        <br />
                                        {t.logo_text_3}
                                    </>
                                )}
                            </div>
                        </div>

                        <ul className="au-collab__points">
                            {collabPoints.map(({ Icon, title, desc }) => (
                                <li key={title} className="au-point">
                  <span className="au-point__icon">
                    <Icon size={18} strokeWidth={1.6} />
                  </span>
                                    <div>
                                        <h4 className="au-point__title">{title}</h4>
                                        <p className="au-point__desc">{desc}</p>
                                    </div>
                                </li>
                            ))}
                        </ul>
                    </div>
                </div>
            </section>

            <section className="au-wrap">
                <div className="au-why-head">
                    <span className="au-eyebrow au-eyebrow--center">{t.why_eyebrow}</span>
                    <h2 className="au-h2 au-h2--center">{t.why_title}</h2>
                </div>

                <div className="au-why-grid">
                    {whyItems.map(({ Icon, title, desc }) => (
                        <div className="au-why" key={title}>
              <span className="au-why__icon">
                <Icon size={28} strokeWidth={1.4} />
              </span>
                            <h3 className="au-why__title">{title}</h3>
                            <p className="au-why__desc">{desc}</p>
                        </div>
                    ))}
                </div>
            </section>

            <section className="au-wrap au-wrap--flush">
                <div
                    className="au-cta"
                    style={{ backgroundImage: `url(${IMG_CTA_BG})` }}
                >
                    <div className="au-cta__overlay" />
                    <div className="au-cta__inner">
                        <div className="au-cta__text">
                            <p>{t.cta_line1}</p>
                            <p>{t.cta_line2}</p>
                            <p>{t.cta_line3}</p>
                        </div>
                        <Link to="/explore" className="au-btn au-btn--dark" href="#">
                            {t.cta_btn} <ArrowRight size={16} strokeWidth={2} />
                        </Link>
                    </div>
                </div>
            </section>
        </div>
    );
}