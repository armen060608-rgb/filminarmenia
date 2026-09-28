import React, { useEffect } from "react";
import { Link, useParams } from "react-router-dom";
import { useLanguage } from "../../language";
import { getTranslations } from "../../translations";
import "./ServiceDetail.css";

function IcoArrow() {
    return (
        <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.2" strokeLinecap="round" strokeLinejoin="round">
            <path d="M5 12h14M12 5l7 7-7 7"/>
        </svg>
    );
}
function IcoPin() {
    return (
        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
            <path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0118 0z"/>
            <circle cx="12" cy="10" r="3"/>
        </svg>
    );
}
function IcoPhone() {
    return (
        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
            <path d="M22 16.92v3a2 2 0 01-2.18 2 19.79 19.79 0 01-8.63-3.07 19.5 19.5 0 01-6-6 19.79 19.79 0 01-3.07-8.67A2 2 0 014.11 2h3a2 2 0 012 1.72c.13.96.37 1.9.72 2.81a2 2 0 01-.45 2.11L8.09 9.91a16 16 0 006 6l1.27-1.27a2 2 0 012.11-.45c.91.35 1.85.59 2.81.72A2 2 0 0122 16.92z"/>
        </svg>
    );
}
function IcoGlobe() {
    return (
        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
            <circle cx="12" cy="12" r="10"/>
            <line x1="2" y1="12" x2="22" y2="12"/>
            <path d="M12 2a15.3 15.3 0 014 10 15.3 15.3 0 01-4 10 15.3 15.3 0 01-4-10 15.3 15.3 0 014-10z"/>
        </svg>
    );
}

export default function ServiceDetail() {
    const { id } = useParams();
    const { language } = useLanguage();
    const t = getTranslations(language);


    useEffect(() => {
        document.body.className = "sd";
        return () => { document.body.className = ""; };
    }, []);

    const detail = t.service_details?.[id];
    const providers = (t.service_providers?.[id]?.length ? t.service_providers[id] : t[id]) || [];


    if (!detail) {
        return (
            <div className="sd">
                <section className="sd-hero">
                    <div className="sd-hero__content">
                        <Link to="/services" className="sd-back">
                            <IcoArrow /> {t.sd_back_services || "Back to services"}
                        </Link>
                        <h1 className="sd-hero__title">{t.sd_not_found || "Service not found"}</h1>
                    </div>
                </section>
            </div>
        );
    }

    return (
        <div className="sd">
            <section className="sd-hero">
                <div className="sd-hero__bg">
                    <div className="sd-hero__mountain" />
                    <div className="sd-hero__mist" />
                </div>
                <div className="sd-hero__content">
                    <Link to="/services" className="sd-back">
                        <span className="sd-back__arrow"><IcoArrow /></span>
                        {t.sd_back_services || "Back to services"}
                    </Link>
                    <h1 className="sd-hero__title">{detail.title}</h1>
                    <p className="sd-hero__sub">{detail.long_desc}</p>
                </div>
            </section>

            <section className="sd-section">
                <div className="sd-section__head">
                    <h2 className="sd-section__title">
                        {t.sd_trusted_partners || "Trusted local partners"}
                    </h2>
                    <span className="sd-section__count">
                        {providers.length} {providers.length === 1 ? (t.sd_partner || "partner") : (t.sd_partners || "partners")}
                    </span>
                </div>

                {providers.length === 0 ? (
                    <div className="sd-empty">
                        {t.sd_coming_soon || "Partners coming soon. Contact us to get matched with the right provider."}
                    </div>
                ) : (
                    <div className="sd-grid">
                        {providers.map((p, i) => (
                            <article key={p.name} className="sd-card" style={{ animationDelay: `${i * 0.08}s` }}>
                                <div className="sd-card__badge">{p.tag}</div>
                                <h3 className="sd-card__title">{p.name}</h3>
                                <p className="sd-card__desc">{p.description}</p>

                                <ul className="sd-card__features">
                                    {p.features?.map((f) => (
                                        <li key={f}>{f}</li>
                                    ))}
                                </ul>

                                <div className="sd-card__meta">
                                    {p.address && (
                                        <div className="sd-card__meta-row">
                                            <IcoPin /> <span>{p.address}</span>
                                        </div>
                                    )}
                                    {p.phone && (
                                        <div className="sd-card__meta-row">
                                            <IcoPhone /> <a href={`tel:${p.phone.replace(/\s/g, "")}`}>{p.phone}</a>
                                        </div>
                                    )}
                                    {p.website && (
                                        <div className="sd-card__meta-row">
                                            <IcoGlobe />
                                            <a href={`https://${p.website}`} target="_blank" rel="noreferrer">
                                                {p.website}
                                            </a>
                                        </div>
                                    )}
                                </div>

                                <Link to="/contactus" className="sd-card__cta">
                                    {t.sd_request_quote || "Request a quote"} <IcoArrow />
                                </Link>
                            </article>
                        ))}
                    </div>
                )}
            </section>
        </div>
    );
}
