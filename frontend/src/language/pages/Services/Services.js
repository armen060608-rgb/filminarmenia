import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import "./Services.css";
import { useLanguage } from "../../language";
import { getTranslations } from "../../translations";

/* ─── Icons ─── */
function IcoCamera() {
    return (
        <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.6" strokeLinecap="round" strokeLinejoin="round">
            <path d="M23 19a2 2 0 01-2 2H3a2 2 0 01-2-2V8a2 2 0 012-2h4l2-3h6l2 3h4a2 2 0 012 2z"/>
            <circle cx="12" cy="13" r="4"/>
        </svg>
    );
}
function IcoTransport() {
    return (
        <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.6" strokeLinecap="round" strokeLinejoin="round">
            <rect x="1" y="3" width="15" height="13" rx="2"/>
            <path d="M16 8h4l3 4v4h-7V8z"/>
            <circle cx="5.5" cy="18.5" r="2.5"/>
            <circle cx="18.5" cy="18.5" r="2.5"/>
        </svg>
    );
}
function IcoBed() {
    return (
        <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.6" strokeLinecap="round" strokeLinejoin="round">
            <path d="M2 4v16M2 8h20v12H2M2 12h20"/>
            <path d="M7 8v4"/>
        </svg>
    );
}
function IcoCatering() {
    return (
        <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.6" strokeLinecap="round" strokeLinejoin="round">
            <path d="M18 8h1a4 4 0 010 8h-1M2 8h16v9a4 4 0 01-4 4H6a4 4 0 01-4-4V8zM6 1v3M10 1v3M14 1v3"/>
        </svg>
    );
}
function IcoPermits() {
    return (
        <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.6" strokeLinecap="round" strokeLinejoin="round">
            <path d="M14 2H6a2 2 0 00-2 2v16a2 2 0 002 2h12a2 2 0 002-2V8z"/>
            <polyline points="14 2 14 8 20 8"/>
            <line x1="9" y1="13" x2="15" y2="13"/>
            <line x1="9" y1="17" x2="15" y2="17"/>
            <polyline points="9 9 10 9 11 9"/>
        </svg>
    );
}
function IcoCrew() {
    return (
        <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.6" strokeLinecap="round" strokeLinejoin="round">
            <path d="M17 21v-2a4 4 0 00-4-4H5a4 4 0 00-4 4v2"/>
            <circle cx="9" cy="7" r="4"/>
            <path d="M23 21v-2a4 4 0 00-3-3.87M16 3.13a4 4 0 010 7.75"/>
        </svg>
    );
}
function IcoShield() {
    return (
        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
            <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"/>
        </svg>
    );
}
function IcoClock() {
    return (
        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
            <circle cx="12" cy="12" r="10"/>
            <polyline points="12 6 12 12 16 14"/>
        </svg>
    );
}
function IcoSupport() {
    return (
        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
            <path d="M21 15a2 2 0 01-2 2H7l-4 4V5a2 2 0 012-2h14a2 2 0 012 2z"/>
        </svg>
    );
}
function IcoArrow() {
    return (
        <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.2" strokeLinecap="round" strokeLinejoin="round">
            <path d="M5 12h14M12 5l7 7-7 7"/>
        </svg>
    );
}

/* ─── Main component ─── */
export default function Services() {
    const [hovered, setHovered] = useState(null);

    // 1. Get current language context inside the functional component
    const { language } = useLanguage();
    const t = getTranslations(language);

    // 2. Generate dynamic mapped data structure based on the current translation language
    const TRUST_ITEMS = [
        { icon: <IcoShield />, label: t.trust_verified || "Verified local providers" },
        { icon: <IcoClock />,  label: t.trust_pricing || "Transparent pricing" },
        { icon: <IcoSupport />, label: t.trust_support || "Fast quotes & reliable support" },
    ];

    const SERVICES = [
        {
            id: "equipment",
            title: t.service_items?.equipment_title || "Equipment Rental",
            desc: t.service_items?.equipment_desc || "High-quality film and production equipment for any scale.",
            icon: <IcoCamera />,
        },
        {
            id: "transport",
            title: t.service_items?.transport_title || "Transport",
            desc: t.service_items?.transport_desc || "Reliable vehicles and logistics to keep your crew and gear moving.",
            icon: <IcoTransport />,
        },
        {
            id: "accommodation",
            title: t.service_items?.accommodation_title || "Accommodation",
            desc: t.service_items?.accommodation_desc || "Comfortable stays for crews in convenient locations.",
            icon: <IcoBed />,
        },
        {
            id: "catering",
            title: t.service_items?.catering_title || "Catering",
            desc: t.service_items?.catering_desc || "Delicious, flexible catering options for any crew size.",
            icon: <IcoCatering />,
        },
        {
            id: "permits",
            title: t.service_items?.permits_title || "Permits & Approvals",
            desc: t.service_items?.permits_desc || "We handle paperwork and local approvals to keep you compliant.",
            icon: <IcoPermits />,
        },
        {
            id: "crew",
            title: t.service_items?.crew_title || "Local Crew",
            desc: t.service_items?.crew_desc || "Experienced local professionals to support your production.",
            icon: <IcoCrew />,
        },
    ];

    useEffect(() => {
        document.body.className = "sv";
        return () => { document.body.className = ""; };
    }, []);

    return (
        <div className="sv">
            {/* ══════════ HERO ══════════ */}
            <section className="sv-hero">
                <div className="sv-hero__bg">
                    <div className="sv-hero__mountain" />
                    <div className="sv-hero__church" />
                    <div className="sv-hero__mist" />
                    <div className="sv-hero__robot">
                        <svg width="70" height="70" viewBox="0 0 80 80" fill="none">
                            <rect x="18" y="32" width="44" height="34" rx="8" fill="#3a4c6e" opacity="0.85"/>
                            <rect x="24" y="14" width="32" height="26" rx="6" fill="#3a4c6e" opacity="0.9"/>
                            <circle cx="33" cy="26" r="5" fill="#e8edf8"/>
                            <circle cx="47" cy="26" r="5" fill="#e8edf8"/>
                            <circle cx="33" cy="26" r="2.5" fill="#1a2e4a"/>
                            <circle cx="47" cy="26" r="2.5" fill="#1a2e4a"/>
                            <line x1="40" y1="14" x2="40" y2="6" stroke="#3a4c6e" strokeWidth="2.5" strokeLinecap="round"/>
                            <circle cx="40" cy="5" r="3" fill="#3a4c6e"/>
                            <rect x="4" y="36" width="14" height="8" rx="4" fill="#3a4c6e" opacity="0.8"/>
                            <rect x="62" y="36" width="14" height="8" rx="4" fill="#3a4c6e" opacity="0.8"/>
                            <rect x="24" y="64" width="12" height="12" rx="4" fill="#3a4c6e" opacity="0.8"/>
                            <rect x="44" y="64" width="12" height="12" rx="4" fill="#3a4c6e" opacity="0.8"/>
                            <circle cx="58" cy="52" r="10" stroke="#e8edf8" strokeWidth="2.5" fill="none"/>
                            <line x1="65" y1="59" x2="72" y2="66" stroke="#e8edf8" strokeWidth="2.5" strokeLinecap="round"/>
                            <rect x="33" y="42" width="14" height="8" rx="2" fill="#e8edf8" opacity="0.3"/>
                        </svg>
                    </div>
                </div>

                <div className="sv-hero__content">
                    <h1 className="sv-hero__title">
                        {t.services_main_title || <>All services you need<br />for filming, in one place</>}
                    </h1>
                    <p className="sv-hero__sub">
                        {t.services_sub_title || "From planning to wrap — FLENS connects you with trusted local professionals and resources so you can focus on storytelling."}
                    </p>

                    <div className="sv-hero__trust">
                        {TRUST_ITEMS.map(({ icon, label }) => (
                            <div key={label} className="sv-trust-item">
                                <span className="sv-trust-item__icon">{icon}</span>
                                <span className="sv-trust-item__label">{label}</span>
                            </div>
                        ))}
                    </div>
                </div>
            </section>

            {/* ══════════ SERVICES GRID ══════════ */}
            <section className="sv-section">
                <div className="sv-section__head">
                    <h2 className="sv-section__title">{t.services_explore_title || "Explore our services"}</h2>
                    <Link to="/faq" className="sv-section__link">
                        {t.services_how_it_works || "How it works"} <IcoArrow />
                    </Link>
                </div>

                <div className="sv-grid">
                    {SERVICES.map((s, i) => (
                        <div
                            key={s.id}
                            className={`sv-card${hovered === s.id ? " sv-card--hovered" : ""}`}
                            style={{ animationDelay: `${i * 0.07}s` }}
                            onMouseEnter={() => setHovered(s.id)}
                            onMouseLeave={() => setHovered(null)}
                        >
                            <div className="sv-card__icon-wrap">
                                {s.icon}
                            </div>
                            <div className="sv-card__body">
                                <h3 className="sv-card__title">{s.title}</h3>
                                <p className="sv-card__desc">{s.desc}</p>
                                <Link to={`/services/${s.id}`} className="sv-card__link">
                                    {t.services_learn_more || "Learn more"} <IcoArrow />
                                </Link>
                            </div>
                        </div>
                    ))}
                </div>
            </section>
        </div>
    );
}