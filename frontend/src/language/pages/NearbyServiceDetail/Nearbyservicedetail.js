import React, { useMemo } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { useLocations } from "../Locations/LocationContext";
import { useHotels } from "../Hotel/HotelContext";
import { usePharmacies } from "../Pharmacy/PharmacyContext";
import { useHospitals } from "../Hospital/HospitalContext";
import { useGasStations } from "../GasStation/GasStationContext";
import { useRestaurants } from "../Restaurant/RestaurantContext";
import { useLanguage } from "../../language";
import "./Nearbyservicedetail.css";
import { getTranslations } from "../../translations";

const LANG_KEY = { hy: "name_hy", en: "name_en", ru: "name_ru" };
const PHOTO_BASE = "/registration/api/v1/photos";


const CATEGORY_META = {
    Hotel:         { color: "#f3e5f5", text: "#6a1b9a", labelKey: "hotel" },
    Pharmacy:      { color: "#e8f5e9", text: "#2e7d32", labelKey: "pharmacy" },
    Hospital:      { color: "#fce4ec", text: "#b71c1c", labelKey: "hospital" },
    "Gas Station": { color: "#fff3e0", text: "#e65100", labelKey: "gasStation" },
    "Food Place":  { color: "#e3f2fd", text: "#1565c0", labelKey: "foodPlace" },
};

const PAGE_META = {
    Hotel: {
        titleKey: "nearbyHotel",
        subtitleKey: "hotelSubtitle",
    },
    Pharmacy: {
        titleKey: "nearbyPharmacy",
        subtitleKey: "pharmacySubtitle",
    },
    Hospital: {
        titleKey: "nearbyHospital",
        subtitleKey: "hospitalSubtitle",
    },
    "Gas Station": {
        titleKey: "nearbyGasStation",
        subtitleKey: "gasStationSubtitle",
    },
    "Food Place": {
        titleKey: "nearbyFoodPlace",
        subtitleKey: "foodPlaceSubtitle",
    },
};

const DEFAULT_IMAGES = {
    Hotel:         "https://images.unsplash.com/photo-1566073771259-6a8506099945?w=440&q=70&auto=format",
    Pharmacy:      "https://images.unsplash.com/photo-1584308666744-24d5c474f2ae?w=440&q=70&auto=format",
    Hospital:      "https://images.unsplash.com/photo-1519494026892-80bbd2d6fd0d?w=440&q=70&auto=format",
    "Gas Station": "https://images.unsplash.com/photo-1592435145666-091581d15f9e?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "Food Place":  "https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=440&q=70&auto=format",
};

/* ── Icons ── */
function IcoArrowLeft()  { return <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.2" strokeLinecap="round" strokeLinejoin="round"><path d="M19 12H5M12 5l-7 7 7 7"/></svg>; }
function IcoPhone()      { return <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round"><path d="M22 16.92v3a2 2 0 01-2.18 2 19.79 19.79 0 01-8.63-3.07A19.5 19.5 0 013.86 10.8a19.79 19.79 0 01-3.07-8.68A2 2 0 012.77 0h3a2 2 0 012 1.72c.127.96.361 1.903.7 2.81a2 2 0 01-.45 2.11L6.91 7.91a16 16 0 006.17 6.17l1.27-1.27a2 2 0 012.11-.45c.907.339 1.85.573 2.81.7A2 2 0 0122 16.92z"/></svg>; }
function IcoClock()      { return <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round"><circle cx="12" cy="12" r="10"/><polyline points="12 6 12 12 16 14"/></svg>; }
function IcoGlobe()      { return <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round"><circle cx="12" cy="12" r="10"/><line x1="2" y1="12" x2="22" y2="12"/><path d="M12 2a15.3 15.3 0 010 20M12 2a15.3 15.3 0 000 20"/></svg>; }
function IcoPin()        { return <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round"><path d="M12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.87-3.13-7-7-7z"/><circle cx="12" cy="9" r="2.5"/></svg>; }
function IcoStar({ filled }) { return <svg width="14" height="14" viewBox="0 0 24 24" fill={filled ? "#f59e0b" : "none"} stroke="#f59e0b" strokeWidth="1.8"><polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2"/></svg>; }
function IcoNavigation() { return <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><polygon points="3 11 22 2 13 21 11 13 3 11"/></svg>; }
function IcoImage()      { return <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="#ccc" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round"><rect x="3" y="3" width="18" height="18" rx="2"/><circle cx="8.5" cy="8.5" r="1.5"/><polyline points="21 15 16 10 5 21"/></svg>; }
function IcoCuisine()    { return <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round"><path d="M18 8h1a4 4 0 010 8h-1M2 8h16v9a4 4 0 01-4 4H6a4 4 0 01-4-4V8zM6 1v3M10 1v3M14 1v3"/></svg>; }

/* ── Photo ── */
function ServicePhoto({ photoId, name, category }) {
    const [errored, setErrored] = React.useState(false);


    const defaultImage =
        DEFAULT_IMAGES[category] || "/images/default-place.jpg";

    const src =
        !photoId || errored
            ? defaultImage
            : `${PHOTO_BASE}/${photoId}`;

    return (
        <img
            className="nsd-photo"
            src={src}
            alt={name}
            onError={() => setErrored(true)}
        />
    );
}

/* ── Resolve a service from the uid (e.g. "pharmacy-5") ── */
function useServiceByUid(uid) {
    const { language }    = useLanguage();
    const langKey         = LANG_KEY[language] ?? "name_en";

    const { locations }   = useLocations();
    const { hotels }      = useHotels();
    const { pharmacies }  = usePharmacies();
    const { hospitals }   = useHospitals();
    const { gasStations } = useGasStations();
    const { restaurants } = useRestaurants();

    return useMemo(() => {
        if (!uid) return { service: null, location: null };

        // indexOf so type="gas" and rawId="5" even if future ids contain hyphens
        const dashIdx = uid.indexOf("-");
        if (dashIdx === -1) return { service: null, location: null };
        const type  = uid.slice(0, dashIdx);
        const rawId = uid.slice(dashIdx + 1);
        const resolvedId = rawId;

        const resolve = (arr) => arr.find(x => String(x.id) === resolvedId);

        let raw = null;
        let category = null;

        switch (type) {
            case "hotel":    raw = resolve(hotels);      category = "Hotel";       break;
            case "pharmacy": raw = resolve(pharmacies);  category = "Pharmacy";    break;
            case "hospital": raw = resolve(hospitals);   category = "Hospital";    break;
            case "gas":      raw = resolve(gasStations); category = "Gas Station"; break;
            case "food":     raw = resolve(restaurants); category = "Food Place";  break;
            default:         return { service: null, location: null };
        }

        if (!raw) return { service: null, location: null };

        /* Find which location references this service and the stored distance */
        let distance = null;
        let parentLocation = null;

        for (const loc of locations) {
            let entry = null;
            switch (type) {
                case "hotel":    entry = (loc.nearbyHotels    ?? []).find(e => String(e.hotelId)      === resolvedId); break;
                case "pharmacy": entry = (loc.nearbyPharmacies ?? []).find(e => String(e.pharmacyId)  === resolvedId); break;
                case "hospital": entry = (loc.nearbyHospitals  ?? []).find(e => String(e.hospitalId)  === resolvedId); break;
                case "gas":      entry = (loc.gasStations       ?? []).find(e => String(e.gasStationId)=== resolvedId); break;
                case "food":     entry = (loc.foodPlaces         ?? []).find(e => String(e.foodPlaceId) === resolvedId); break;
            }
            if (entry) { distance = entry.distance ?? null; parentLocation = loc; break; }
        }

        const service = {
            _uid:        uid,
            category,
            name:        raw[langKey] || raw.name_en || "",
            address:     raw.address  || "",
            phone:       raw.phone    || "",
            website:     raw.website  || "",
            description: raw[`description_${language}`] || raw.description_en || raw.description || "",
            openHours:   raw.openHours   || raw.hours || "",
            isOpen:      raw.isOpen      ?? null,
            photoId:     raw.photoId     || null,
            stars:       raw.stars       || 0,
            cuisine:     raw.cuisine     || "",
            tags:        Array.isArray(raw.tags) ? raw.tags : [],
            distance,
        };

        return { service, location: parentLocation };
    }, [uid, hotels, pharmacies, hospitals, gasStations, restaurants, locations, langKey]);
}

/* ── Main ── */
export default function NearbyServiceDetail() {
    // useParams key must match your route definition exactly.
    // NearbyServices.jsx navigates to `/nearby/${s._uid}` where _uid is e.g. "pharmacy-5".
    // If your route is <Route path="/nearby/:id"> use { id }; if it's :uid use { uid }.
    const params = useParams();
    const uid = params.uid ?? params.id ?? params.serviceId ?? Object.values(params)[0] ?? null;

    const navigate = useNavigate();

    const { service, location } = useServiceByUid(uid);
    const { language } = useLanguage();
    const t = getTranslations(language);

    if (!service) {
        return (
            <div className="nsd">
                <p className="nsd-not-found">
                    {t.serviceNotFound}
                    {process.env.NODE_ENV === "development" && (
                        <span style={{ display: "block", fontSize: 12, color: "#999", marginTop: 6 }}>
                            Resolved uid: "{uid}" — check that your route param name matches and
                            that the id exists in your context data.
                        </span>
                    )}
                </p>
            </div>
        );
    }

    const meta     = CATEGORY_META[service.category] || { color: "#f0f0f0", text: "#333" };
    const pageMeta = PAGE_META[service.category]     || { title: service.category, subtitle: "" };

    const mapsQuery = [service.name, service.address].filter(Boolean).join(" ");
    const mapsUrl   = `https://www.google.com/maps/search/${encodeURIComponent(mapsQuery)}`;

    return (
        <div className="nsd">
            {/* Back button */}
            <button className="nsd-back" onClick={() => navigate(-1)}>
                <IcoArrowLeft /> {t.backToResults}
            </button>

            {/* Page heading */}
            <h1 className="nsd-page-title">
                {t[pageMeta.titleKey]}
            </h1>
            {pageMeta.subtitle && (
                <p className="nsd-page-subtitle">
                    {t[pageMeta.subtitleKey]}
                </p>
            )}

            {/* Card */}
            <div className="nsd-card">

            {/* Photo */}
                <ServicePhoto
                    photoId={service.photoId}
                    name={service.name}
                    category={service.category}
                />

                {/* Middle body */}
                <div className="nsd-body">

                    {/* Open/closed status — only shown if the API provides isOpen */}
                    {service.isOpen !== null && (
                        <div className={`nsd-status ${service.isOpen ? "nsd-status--open" : "nsd-status--closed"}`}>
                            <span className="nsd-status__dot" />
                            {service.isOpen ? "Open now" : "Closed"}
                            {service.openHours && ` • ${service.openHours}`}
                        </div>
                    )}

                    <h2 className="nsd-name">{service.name || "—"}</h2>

                    {/* Description — localised, shown directly under the name */}
                    {service.description && (
                        <p className="nsd-desc">{service.description}</p>
                    )}

                    <span className="nsd-badge" style={{ background: meta.color, color: meta.text }}>
                        {meta.label}
                    </span>

                    {/* Star rating — hotels only */}
                    {service.category === "Hotel" && service.stars > 0 && (
                        <div className="nsd-stars">
                            {[1, 2, 3, 4, 5].map(n => <IcoStar key={n} filled={n <= service.stars} />)}
                        </div>
                    )}

                    {/* Cuisine — food places */}
                    {service.cuisine && (
                        <p className="nsd-meta-row">
                            <IcoCuisine /> {service.cuisine}
                        </p>
                    )}

                    {/* Tags */}
                    {service.tags.length > 0 && (
                        <div className="nsd-tags">
                            {service.tags.map(tag => (
                                <span key={tag} className="nsd-tag">{tag}</span>
                            ))}
                        </div>
                    )}
                </div>

                {/* Vertical divider */}
                <div className="nsd-divider" />

                {/* Right panel */}
                <div className="nsd-right">

                    {(service.phone || service.openHours || service.website) && (
                        <>
                            <p className="nsd-section-label">Contact</p>

                            {service.phone && (
                                <div className="nsd-info-row">
                                    <IcoPhone />
                                    <a href={`tel:${service.phone}`}>{service.phone}</a>
                                </div>
                            )}

                            {service.openHours && (
                                <div className="nsd-info-row">
                                    <IcoClock />
                                    <span>{service.openHours}</span>
                                </div>
                            )}

                            {service.website && (
                                <div className="nsd-info-row">
                                    <IcoGlobe />
                                    <a href={service.website.startsWith("http") ? service.website : `https://${service.website}`}
                                       target="_blank" rel="noreferrer">
                                        {service.website.replace(/^https?:\/\//, "")}
                                    </a>
                                </div>
                            )}

                            <div className="nsd-hr" />
                        </>
                    )}

                    {service.address && (
                        <>
                            <p className="nsd-section-label">{t.address}</p>
                            <div className="nsd-info-row">
                                <IcoPin />
                                <span>{service.address}</span>
                            </div>
                            <div className="nsd-hr" />
                        </>
                    )}

                    {service.distance !== null && (
                        <p className="nsd-distance">
                            <IcoPin /> {service.distance} {t.kmAway}
                        </p>
                    )}

                    <a
                        className="nsd-directions-btn"
                        href={mapsUrl}
                        target="_blank"
                        rel="noreferrer"
                    >
                        <IcoNavigation /> {t.getDirections}
                    </a>
                </div>
            </div>
        </div>
    );
}