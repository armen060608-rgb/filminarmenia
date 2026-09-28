import { useState } from "react";
import { Link } from "react-router-dom";
import { useLanguage } from "../../language";
import { getTranslations } from "../../translations";
import "./index.css";

/* ─── Sub-Components ─── */
function ChevronDown({ open }) {
    return (
        <svg
            width="18"
            height="18"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            strokeWidth="2"
            strokeLinecap="round"
            strokeLinejoin="round"
            style={{
                transition: "transform 0.2s ease",
                transform: open ? "rotate(180deg)" : "rotate(0deg)",
                flexShrink: 0,
                color: "#888",
            }}
            aria-hidden="true"
        >
            <polyline points="6 9 12 15 18 9" />
        </svg>
    );
}

function ArrowLeft() {
    return (
        <svg
            width="14"
            height="14"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            strokeWidth="2"
            strokeLinecap="round"
            strokeLinejoin="round"
            aria-hidden="true"
        >
            <line x1="19" y1="12" x2="5" y2="12" />
            <polyline points="12 19 5 12 12 5" />
        </svg>
    );
}

function HeadphonesIcon() {
    return (
        <svg
            width="22"
            height="22"
            viewBox="0 0 24 24"
            fill="none"
            stroke="#666"
            strokeWidth="1.5"
            strokeLinecap="round"
            strokeLinejoin="round"
            aria-hidden="true"
        >
            <path d="M3 18v-6a9 9 0 0 1 18 0v6" />
            <path d="M21 19a2 2 0 0 1-2 2h-1a2 2 0 0 1-2-2v-3a2 2 0 0 1 2-2h3z" />
            <path d="M3 19a2 2 0 0 0 2 2h1a2 2 0 0 0 2-2v-3a2 2 0 0 0-2-2H3z" />
        </svg>
    );
}

function FAQItem({ faq, isOpen, onToggle }) {
    return (
        <div
            style={{
                background: "#fff",
                border: "0.5px solid #e0e0e8",
                borderRadius: 12,
                marginBottom: 10,
                overflow: "hidden",
            }}
        >
            <button
                onClick={onToggle}
                style={{
                    width: "100%",
                    display: "flex",
                    alignItems: "center",
                    gap: 16,
                    padding: "18px 20px",
                    background: "none",
                    border: "none",
                    cursor: "pointer",
                    textAlign: "left",
                    fontFamily: "inherit",
                }}
                aria-expanded={isOpen}
            >
                <span
                    style={{
                        width: 28,
                        height: 28,
                        borderRadius: "50%",
                        background: "#eeeef6",
                        display: "flex",
                        alignItems: "center",
                        justifyContent: "center",
                        fontSize: 13,
                        fontWeight: 500,
                        color: "#333",
                        flexShrink: 0,
                    }}
                >
                    {faq.id}
                </span>
                <span
                    style={{
                        flex: 1,
                        fontSize: 15,
                        color: "#1a1a2e",
                        fontWeight: 400,
                    }}
                >
                    {faq.question}
                </span>
                <ChevronDown open={isOpen} />
            </button>

            {isOpen && (
                <div
                    style={{
                        padding: "0 20px 18px 64px",
                        fontSize: 13.5,
                        color: "#666",
                        lineHeight: 1.7,
                    }}
                >
                    {faq.answer}
                </div>
            )}
        </div>
    );
}

/* ─── Main Component ─── */
export default function FilmingFAQ() {
    const [openId, setOpenId] = useState(1);

    // Fetch active app language context
    const { language } = useLanguage();
    const t = getTranslations(language);

    const localizedFaqs = t.faqs || [];

    const toggle = (id) => setOpenId(openId === id ? null : id);

    return (
        <div className="faq-page">
            <div className="faq-content" style={{ maxWidth: 880, margin: "0 auto", padding: "0 1.5rem" }}>
                {/* Back button */}
                <Link to="/"
                      style={{
                          display: "inline-flex",
                          alignItems: "center",
                          gap: 6,
                          color: "#53599A",
                          background: "#868CCE40",
                          border: "0.5px solid #ddd",
                          borderRadius: 12,
                          padding: "6px 14px",
                          marginTop: "1.5rem",
                          cursor: "pointer",
                          fontFamily: "inherit",
                          fontSize: 15,
                          textDecoration: "none"
                      }}
                >
                    <ArrowLeft />
                    {t.faq_back_home || "Back to Home"}
                </Link>

                <div>
                    <h1 className="faq-heading"
                        style={{
                            fontSize: 48,
                            fontWeight: 400,
                            color: "#3A3C5B",
                            lineHeight: 1.1,
                            letterSpacing: "-1px",
                            marginBottom: "1rem",
                            marginTop: "1.5rem",
                        }}
                    >
                        {t.faq_main_title}
                    </h1>
                    <p style={{ fontSize: 14, color: "#000000", lineHeight: 1.7 }}>
                        {t.faq_subtitle_1}
                        <br />
                        {t.faq_subtitle_2}
                    </p>
                </div>

                <div style={{ paddingTop: "50px"}}>
                    {localizedFaqs.map((faq) => (
                        <FAQItem
                            key={faq.id}
                            faq={faq}
                            isOpen={openId === faq.id}
                            onToggle={() => toggle(faq.id)}
                        />
                    ))}
                </div>

                {/* Contact card */}
                <div className="howitworks">
                    <div
                        style={{
                            width: 52,
                            height: 52,
                            borderRadius: "50%",
                            background: "#eeeef6",
                            display: "flex",
                            alignItems: "center",
                            justifyContent: "center",
                            flexShrink: 0,
                        }}
                    >
                        <HeadphonesIcon />
                    </div>
                    <div style={{ flex: 1 }}>
                        <h3
                            style={{
                                fontSize: 16,
                                fontWeight: 500,
                                color: "#1a1a2e",
                                marginBottom: 4,
                            }}
                        >
                            {t.faq_more_questions}
                        </h3>
                        <p style={{ fontSize: 13, color: "#888" }}>
                            {t.faq_team_help}
                        </p>
                    </div>
                    <Link to="/contactus"
                          style={{
                              background: "#1a1a2e",
                              color: "#fff",
                              border: "none",
                              borderRadius: 8,
                              padding: "12px 22px",
                              fontSize: 14,
                              cursor: "pointer",
                              whiteSpace: "nowrap",
                              fontFamily: "inherit",
                              textDecoration: "none"
                          }}
                    >
                        {t.faq_contact_us}
                    </Link>
                </div>
            </div>
        </div>
    );
}