import { useState } from "react";
import emailjs from "@emailjs/browser";
import "./index.css";

const EMAILJS_SERVICE_ID  = "service_unzp0yq";
const EMAILJS_TEMPLATE_ID = "template_8nuvz3p";
const EMAILJS_PUBLIC_KEY  = "Rsok4PcZQxznWiINt";

export default function ContactUs() {
    const [form, setForm] = useState({ name: "", email: "", message: "" });
    const [status, setStatus] = useState(null); // null | 'success' | 'error'
    const [loading, setLoading] = useState(false);

    const handleChange = (e) => {
        setForm((prev) => ({ ...prev, [e.target.name]: e.target.value }));
    };

    const validate = () => {
        if (!form.name.trim() || !form.email.trim() || !form.message.trim()) {
            return "Please fill in all fields before sending.";
        }
        if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(form.email)) {
            return "Please enter a valid email address.";
        }
        return null;
    };

    const handleSubmit = async () => {
        setStatus(null);
        const error = validate();
        if (error) {
            setStatus({ type: "error", text: error });
            return;
        }

        setLoading(true);
        try {
            await emailjs.send(
                EMAILJS_SERVICE_ID,
                EMAILJS_TEMPLATE_ID,
                {
                    from_name:  form.name,
                    from_email: form.email,
                    message:    form.message,
                    to_email:   "webmaster.aybschool@gmail.com",
                },
                EMAILJS_PUBLIC_KEY
            );

            setStatus({
                type: "success",
                text: "Your message has been sent! We'll get back to you within 24 hours.",
            });
            setForm({ name: "", email: "", message: "" });
        } catch {
            setStatus({
                type: "error",
                text: "Something went wrong. Please try again or email us directly.",
            });
        }
        setLoading(false);
    };

    return (
        <section className="cu-root">
            <div className="cu-inner">
                <h1 className="cu-title">Contact Us</h1>
                <p className="cu-subtitle">
                    Have a question, suggestion, or need help finding the perfect
                    location?
                    <br />
                    We'd love to hear from you.
                </p>

                <div className="cu-cols">
                    {/* ── Left column ── */}
                    <div className="cu-left">
                        <div className="cu-info-cards">
                            <InfoCard
                                icon="✉"
                                label="Email Us"
                                value="hello@flens.am"
                                note="We typically reply within 24 hours."
                            />
                            <InfoCard
                                icon="🎧"
                                label="Support"
                                value="+374 11 123 456"
                                note="Mon – Fri, 9:00 AM – 6:00 PM (AMT)"
                            />
                            <InfoCard
                                icon="📍"
                                label="Our Office"
                                value="1 Tumanyan St., Yerevan 0001"
                                note="Republic of Armenia"
                            />
                        </div>
                    </div>

                    {/* ── Right column: form ── */}
                    <div className="cu-form-card">
                        <h2 className="cu-form-title">Send us a message</h2>
                        <p className="cu-form-desc">
                            Fill out the form below and we'll get back to you as soon as
                            possible.
                        </p>

                        <div className="cu-field">
                            <label htmlFor="cu-name">Full name</label>
                            <input
                                id="cu-name"
                                name="name"
                                type="text"
                                placeholder="Your name"
                                value={form.name}
                                onChange={handleChange}
                            />
                        </div>

                        <div className="cu-field">
                            <label htmlFor="cu-email">Email address</label>
                            <input
                                id="cu-email"
                                name="email"
                                type="email"
                                placeholder="Your email"
                                value={form.email}
                                onChange={handleChange}
                            />
                        </div>

                        <div className="cu-field">
                            <label htmlFor="cu-message">Message</label>
                            <textarea
                                id="cu-message"
                                name="message"
                                placeholder="How can we help you?"
                                value={form.message}
                                onChange={handleChange}
                            />
                        </div>

                        <button
                            className="cu-send-btn"
                            onClick={handleSubmit}
                            disabled={loading}
                        >
                            {loading ? "Sending…" : "Send message"}
                            {!loading && <SendIcon />}
                        </button>

                        {status && (
                            <div className={`cu-toast cu-toast--${status.type}`}>
                                {status.type === "success" ? "✓ " : "⚠ "}
                                {status.text}
                            </div>
                        )}
                    </div>
                </div>
            </div>
        </section>
    );
}

function InfoCard({ icon, label, value, note }) {
    return (
        <div className="cu-info-card">
            <div className="cu-info-icon" aria-hidden="true">
                {icon}
            </div>
            <div>
                <div className="cu-info-label">{label}</div>
                <div className="cu-info-value">{value}</div>
                <div className="cu-info-note">{note}</div>
            </div>
        </div>
    );
}

function SocialBtn({ label, href, children }) {
    return (
        <a
            href={href}
            className="cu-social-btn"
            aria-label={label}
            target="_blank"
            rel="noopener noreferrer"
        >
            {children}
        </a>
    );
}

function SendIcon() {
    return (
        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" aria-hidden="true">
            <line x1="22" y1="2" x2="11" y2="13" />
            <polygon points="22 2 15 22 11 13 2 9 22 2" />
        </svg>
    );
}

function InstagramIcon() {
    return (
        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" aria-hidden="true">
            <rect x="2" y="2" width="20" height="20" rx="5" ry="5" />
            <circle cx="12" cy="12" r="4" />
            <circle cx="17.5" cy="6.5" r="0.5" fill="currentColor" />
        </svg>
    );
}

function FacebookIcon() {
    return (
        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" aria-hidden="true">
            <path d="M18 2h-3a5 5 0 0 0-5 5v3H7v4h3v8h4v-8h3l1-4h-4V7a1 1 0 0 1 1-1h3z" />
        </svg>
    );
}

function YouTubeIcon() {
    return (
        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" aria-hidden="true">
            <path d="M22.54 6.42a2.78 2.78 0 0 0-1.95-1.96C18.88 4 12 4 12 4s-6.88 0-8.59.46A2.78 2.78 0 0 0 1.46 6.42 29 29 0 0 0 1 12a29 29 0 0 0 .46 5.58 2.78 2.78 0 0 0 1.95 1.96C5.12 20 12 20 12 20s6.88 0 8.59-.46a2.78 2.78 0 0 0 1.95-1.96A29 29 0 0 0 23 12a29 29 0 0 0-.46-5.58z" />
            <polygon points="9.75 15.02 15.5 12 9.75 8.98 9.75 15.02" fill="currentColor" stroke="none" />
        </svg>
    );
}