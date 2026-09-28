import React, { useState, useMemo } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { useLocations } from "../Locations/LocationContext";
import { useHotels } from "../Hotel/HotelContext";
import { usePharmacies } from "../Pharmacy/PharmacyContext";
import { useHospitals } from "../Hospital/HospitalContext";
import { useGasStations } from "../GasStation/GasStationContext";
import { useRestaurants } from "../Restaurant/RestaurantContext";
import { getTranslations } from "../../translations";
import { useLanguage } from "../../language";
import "./NearbyServices.css";

const LANG_KEY = { hy: "name_hy", en: "name_en", ru: "name_ru" };
const ITEMS_PER_PAGE = 3;
const PHOTO_BASE = "/registration/api/v1/photos";

/* ── Default placeholder images per category (royalty-free Unsplash) ── */
const CATEGORY_DEFAULTS = {
    Hotel:         "https://images.unsplash.com/photo-1566073771259-6a8506099945?w=440&q=70&auto=format",
    Pharmacy:      "https://images.unsplash.com/photo-1584308666744-24d5c474f2ae?w=440&q=70&auto=format",
    Hospital:      "https://images.unsplash.com/photo-1519494026892-80bbd2d6fd0d?w=440&q=70&auto=format",
    "Gas Station": "https://images.unsplash.com/photo-1592435145666-091581d15f9e?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "Food Place":  "https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=440&q=70&auto=format",
};

const CATEGORY_META = {
    Hotel:         { color: "#f3e5f5", text: "#6a1b9a" },
    Pharmacy:      { color: "#e8f5e9", text: "#2e7d32" },
    Hospital:      { color: "#fce4ec", text: "#b71c1c" },
    "Gas Station": { color: "#fff3e0", text: "#e65100" },
    "Food Place":  { color: "#e3f2fd", text: "#1565c0" },
};

function getCategories(t) {
    return [
        { key: "Hotel", label: typeof t.category_hotel === "string" ? t.category_hotel : "Hotel" },
        { key: "Pharmacy", label: typeof t.category_pharmacy === "string" ? t.category_pharmacy : "Pharmacy" },
        { key: "Hospital", label: typeof t.category_hospital === "string" ? t.category_hospital : "Hospital" },
        { key: "Gas Station", label: typeof t.category_gas === "string" ? t.category_gas : "Gas Station" },
        { key: "Food Place", label: typeof t.category_food === "string" ? t.category_food : "Food Place" },
    ];
}

/* ─── Icons ─── */
function IcoSearch()  { return <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><circle cx="11" cy="11" r="8"/><line x1="21" y1="21" x2="16.65" y2="16.65"/></svg>; }
function IcoPin()     { return <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="#5b8cff" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.87-3.13-7-7-7z"/><circle cx="12" cy="9" r="2.5"/></svg>; }
function IcoPhone()   { return <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="#888" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M22 16.92v3a2 2 0 01-2.18 2 19.79 19.79 0 01-8.63-3.07A19.5 19.5 0 013.86 10.8a19.79 19.79 0 01-3.07-8.68A2 2 0 012.77 0h3a2 2 0 012 1.72c.127.96.361 1.903.7 2.81a2 2 0 01-.45 2.11L6.91 7.91a16 16 0 006.17 6.17l1.27-1.27a2 2 0 012.11-.45c.907.339 1.85.573 2.81.7A2 2 0 0122 16.92z"/></svg>; }
function IcoSort()    { return <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M7 16V4m0 0L3 8m4-4l4 4M17 8v12m0 0l4-4m-4 4l-4-4"/></svg>; }
function IcoRefresh() { return <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M23 4v6h-6M1 20v-6h6"/><path d="M3.51 9a9 9 0 0114.85-3.36L23 10M1 14l4.64 4.36A9 9 0 0020.49 15"/></svg>; }
function IcoChevronLeft()  { return <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.2" strokeLinecap="round" strokeLinejoin="round"><path d="M15 18l-6-6 6-6"/></svg>; }
function IcoChevronRight() { return <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.2" strokeLinecap="round" strokeLinejoin="round"><path d="M9 18l6-6-6-6"/></svg>; }
function IcoArrowRight()   { return <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.2" strokeLinecap="round" strokeLinejoin="round"><path d="M5 12h14M12 5l7 7-7 7"/></svg>; }
function IcoHeart({ filled }) { return <svg width="17" height="17" viewBox="0 0 24 24" fill={filled ? "#e74c3c" : "none"} stroke={filled ? "#e74c3c" : "#bbb"} strokeWidth="1.8"><path d="M20.84 4.61a5.5 5.5 0 00-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 00-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 000-7.78z"/></svg>; }
function IcoStar({ filled }) { return <svg width="13" height="13" viewBox="0 0 24 24" fill={filled ? "#f59e0b" : "none"} stroke="#f59e0b" strokeWidth="1.8"><polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2"/></svg>; }
function IcoFilter()  { return <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><polygon points="22 3 2 3 10 12.46 10 19 14 21 14 12.46 22 3"/></svg>; }
/* Fixed broken X path coordinates */
function IcoClose()   { return <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.2" strokeLinecap="round" strokeLinejoin="round"><line x1="18" y1="6" x2="6" y2="18"/><line x1="6" y1="6" x2="18" y2="18"/></svg>; }

function IcoCat({ type }) {
    switch (type) {
        case "Hotel":        return <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round"><path d="M3 9l9-7 9 7v11a2 2 0 01-2 2H5a2 2 0 01-2-2z"/><polyline points="9 22 9 12 15 12 15 22"/></svg>;
        case "Pharmacy":     return <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round"><path d="M12 2a10 10 0 100 20A10 10 0 0012 2zM8 12h8M12 8v8"/></svg>;
        case "Hospital":     return <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round"><rect x="3" y="3" width="18" height="18" rx="2"/><path d="M12 8v8M8 12h8"/></svg>;
        case "Gas Station":  return <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round"><path d="M3 22V8l6-6h6l2 2v4h1a2 2 0 012 2v8a2 2 0 01-2 2H5a2 2 0 01-2-2z"/><path d="M9 22V12h6v10"/></svg>;
        case "Food Place":   return <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round"><path d="M18 8h1a4 4 0 010 8h-1M2 8h16v9a4 4 0 01-4 4H6a4 4 0 01-4-4V8zM6 1v3M10 1v3M14 1v3"/></svg>;
        default:             return null;
    }
}

function ServicePhoto({ photoId, name, category }) {
    const [src, setSrc] = React.useState(
        photoId ? `${PHOTO_BASE}/${photoId}` : (CATEGORY_DEFAULTS[category] || null)
    );
    const [triedDefault, setTriedDefault] = React.useState(!photoId);

    const handleError = () => {
        if (!triedDefault && CATEGORY_DEFAULTS[category]) {
            setSrc(CATEGORY_DEFAULTS[category]);
            setTriedDefault(true);
        } else {
            setSrc(null);
        }
    };

    if (!src) {
        return (
            <div className="ns-card__img ns-card__img--empty">
                <svg width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="#ccc" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round"><rect x="3" y="3" width="18" height="18" rx="2"/><circle cx="8.5" cy="8.5" r="1.5"/><polyline points="21 15 16 10 5 21"/></svg>
            </div>
        );
    }

    return (
        <img
            className="ns-card__img"
            src={src}
            alt={name}
            onError={handleError}
        />
    );
}

function SidebarContent({
                            t,
                            categories,
                            search, setSearch, setPage,
                            activeCategory, handleCategoryClick, categoryCounts,
                            maxDistance, setMaxDistance,
                            sortBy, setSortBy, sortAsc, setSortAsc,
                            clearAll, onClose,
                        }) {
    const filterLabel = typeof t.filters === "string" ? t.filters : "Filters";
    const searchPlaceholder = typeof t.searchNearbyServices === "string" ? t.searchNearbyServices : "Search...";
    const categoryLabel = typeof t.categories === "string" ? t.categories : "Categories";
    const distanceLabel = typeof t.distance === "string" ? t.distance : "Distance";
    const sortByLabel = typeof t.sortBy === "string" ? t.sortBy : "Sort By";
    const nearestLabel = typeof t.nearestFirst === "string" ? t.nearestFirst : "Nearest First";
    const nameLabel = typeof t.nameAZ === "string" ? t.nameAZ : "Name A-Z";
    const clearLabel = typeof t.clearAllFilters === "string" ? t.clearAllFilters : "Clear All Filters";

    return (
        <>
            {onClose && (
                <div className="ns-drawer-header">
                    <span className="ns-drawer-title">{filterLabel}</span>
                    <button className="ns-drawer-close" onClick={onClose} aria-label="Close filters">
                        <IcoClose/>
                    </button>
                </div>
            )}

            <div className="ns-search">
                <IcoSearch/>
                <input
                    type="text"
                    placeholder={searchPlaceholder}
                    value={search}
                    onChange={e => {
                        setSearch(e.target.value);
                        setPage(1);
                    }}
                />
            </div>

            <div className="ns-section-title">{categoryLabel}</div>
            <ul className="ns-cats">
                {categories.map(({key, label}) => (
                    <li key={key}
                        className={`ns-cat${activeCategory === key ? " ns-cat--active" : ""}`}
                        onClick={() => {
                            handleCategoryClick(key);
                            onClose?.();
                        }}>
                        <span className="ns-cat__icon"><IcoCat type={key}/></span>
                        <span className="ns-cat__name">{label}</span>
                        <span className="ns-cat__count">{categoryCounts[key] ?? 0}</span>
                    </li>
                ))}
            </ul>

            <div className="ns-section-title ns-section-title--mt">{distanceLabel}</div>
            <div className="ns-slider-wrap">
                <input type="range" min="0" max="10" step="0.5"
                       value={maxDistance}
                       onChange={e => {
                           setMaxDistance(+e.target.value);
                           setPage(1);
                       }}
                       className="ns-slider"/>
                <div className="ns-slider-labels">
                    <span>0 km</span>
                    <span>{maxDistance} km</span>
                </div>
            </div>

            <div className="ns-section-title ns-section-title--mt">{sortByLabel}</div>
            <div className="ns-sort-row">
                <div className="ns-sort-select-wrap">
                    <select className="ns-sort-select" value={sortBy} onChange={e => setSortBy(e.target.value)}>
                        <option value="distance">{nearestLabel}</option>
                        <option value="name">{nameLabel}</option>
                    </select>
                    <span className="ns-sort-arrow">▾</span>
                </div>
                <button className="ns-sort-toggle" onClick={() => setSortAsc(p => !p)}>
                    <IcoSort/>
                </button>
            </div>

            <button className="ns-clear" onClick={() => {
                clearAll();
                onClose?.();
            }}>
                <IcoRefresh /> {clearLabel}
            </button>
        </>
    );
}

export default function NearbyServices() {
    const { language } = useLanguage();
    const t = getTranslations(language) || {};
    const langKey = LANG_KEY[language] || "name_en";

    const categories = getCategories(t);
    const { id } = useParams();
    const navigate = useNavigate();
    const { locations }   = useLocations();
    const { hotels }      = useHotels();
    const { pharmacies }  = usePharmacies();
    const { hospitals }   = useHospitals();
    const { gasStations } = useGasStations();
    const { restaurants } = useRestaurants();

    const [search,         setSearch]         = useState("");
    const [activeCategory, setActiveCategory] = useState(null);
    const [maxDistance,    setMaxDistance]    = useState(10);
    const [sortBy,         setSortBy]         = useState("distance");
    const [sortAsc,        setSortAsc]        = useState(true);
    const [page,           setPage]           = useState(1);
    const [saved,          setSaved]          = useState(new Set());
    const [drawerOpen,     setDrawerOpen]     = useState(false);

    const toggleSaved = (uid) =>
        setSaved(prev => { const n = new Set(prev); n.has(uid) ? n.delete(uid) : n.add(uid); return n; });

    const location = useMemo(
        () => (locations || []).find(l => String(l.id) === String(id)),
        [locations, id]
    );

    const allServices = useMemo(() => {
        if (!location) return [];
        const list = [];
        const resolve = (arr, sid) => (arr || []).find(s => String(s.id) === String(sid));

        (location.nearbyHotels ?? []).forEach(({ hotelId, distance }) => {
            const h = resolve(hotels, hotelId);
            if (!h) return;
            list.push({ _uid: `hotel-${hotelId}`, category: "Hotel", name: h[langKey] || h.name_en || "", address: h.address || "", phone: h.phone || "", stars: h.stars || 0, photoId: h.photoId || null, distance: distance ?? 0 });
        });

        (location.nearbyPharmacies ?? []).forEach(({ pharmacyId, distance }) => {
            const p = resolve(pharmacies, pharmacyId);
            if (!p) return;
            list.push({ _uid: `pharmacy-${pharmacyId}`, category: "Pharmacy", name: p[langKey] || p.name_en || "", address: p.address || "", phone: p.phone || "", photoId: p.photoId || null, distance: distance ?? 0 });
        });

        (location.nearbyHospitals ?? []).forEach(({ hospitalId, distance }) => {
            const h = resolve(hospitals, hospitalId);
            if (!h) return;
            list.push({ _uid: `hospital-${hospitalId}`, category: "Hospital", name: h[langKey] || h.name_en || "", address: h.address || "", phone: h.phone || "", photoId: h.photoId || null, distance: distance ?? 0 });
        });

        (location.gasStations ?? []).forEach(({ gasStationId, distance }) => {
            const g = resolve(gasStations, gasStationId);
            if (!g) return;
            list.push({ _uid: `gas-${gasStationId}`, category: "Gas Station", name: g[langKey] || g.name_en || "", address: g.address || "", phone: g.phone || "", photoId: g.photoId || null, distance: distance ?? 0 });
        });

        (location.foodPlaces ?? []).forEach(({ foodPlaceId, distance }) => {
            const f = resolve(restaurants, foodPlaceId);
            if (!f) return;
            list.push({ _uid: `food-${foodPlaceId}`, category: "Food Place", name: f[langKey] || f.name_en || "", address: f.address || "", phone: f.phone || "", cuisine: f.cuisine || "", photoId: f.photoId || null, distance: distance ?? 0 });
        });

        return list;
    }, [location, hotels, pharmacies, hospitals, gasStations, restaurants, langKey]);

    const filtered = useMemo(() => {
        let list = allServices.filter(s => {
            if (activeCategory && s.category !== activeCategory) return false;
            if (s.distance > maxDistance) return false;
            if (search) {
                const q = search.toLowerCase();
                if (!s.name.toLowerCase().includes(q) && !s.category.toLowerCase().includes(q) && !(s.address || "").toLowerCase().includes(q)) return false;
            }
            return true;
        });
        list.sort((a, b) => {
            const diff = sortBy === "distance" ? a.distance - b.distance : a.name.localeCompare(b.name);
            return sortAsc ? diff : -diff;
        });
        return list;
    }, [allServices, activeCategory, maxDistance, search, sortBy, sortAsc]);

    const totalPages = Math.max(1, Math.ceil(filtered.length / ITEMS_PER_PAGE));
    const paginated  = filtered.slice((page - 1) * ITEMS_PER_PAGE, page * ITEMS_PER_PAGE);

    const categoryCounts = useMemo(() => {
        const c = {};
        allServices.forEach(s => { c[s.category] = (c[s.category] || 0) + 1; });
        return c;
    }, [allServices]);

    const handleCategoryClick = (key) => {
        setActiveCategory(p => p === key ? null : key);
        setPage(1);
    };

    const clearAll = () => {
        setSearch(""); setActiveCategory(null);
        setMaxDistance(10); setSortBy("distance");
        setSortAsc(true); setPage(1);
    };

    const activeFilterCount = [
        !!activeCategory,
        maxDistance !== 10,
        sortBy !== "distance",
        !!search,
    ].filter(Boolean).length;

    const pageNums = () => {
        if (totalPages <= 5) return Array.from({ length: totalPages }, (_, i) => i + 1);
        if (page <= 3) return [1, 2, 3, "...", totalPages];
        if (page >= totalPages - 2) return [1, "...", totalPages - 2, totalPages - 1, totalPages];
        return [1, "...", page, "...", totalPages];
    };

    const notFoundText = typeof t.locationNotFound === "string" ? t.locationNotFound : "Location not found";
    if (!location) return <div className="ns"><p style={{ padding: 32 }}>{notFoundText}</p></div>;

    const sidebarProps = {
        t,
        categories,
        search,
        setSearch,
        setPage,
        activeCategory,
        handleCategoryClick,
        categoryCounts,
        maxDistance,
        setMaxDistance,
        sortBy,
        setSortBy,
        sortAsc,
        setSortAsc,
        clearAll
    };

    // Safe fallback resolutions for header labels
    const nearbyServicesLabel = typeof t.nearbyServices === "string" ? t.nearbyServices : "Nearby Services";
    const showingResultsLabel = typeof t.showingResults === "string" ? t.showingResults : "Showing results";
    const resultLabel = filtered.length === 1
        ? (typeof t.result === "string" ? t.result : "result")
        : (typeof t.resultsPlural === "string" ? t.resultsPlural : "results");
    const withinLabel = typeof t.within === "string" ? t.within : "within";
    const filterBtnLabel = typeof t.filters === "string" ? t.filters : "Filters";
    const emptyLabel = typeof t.noServicesFound === "string" ? t.noServicesFound : "No services found";

    return (
        <div className="ns">
            {drawerOpen && (
                <div className="ns-overlay" onClick={() => setDrawerOpen(false)} aria-hidden="true" />
            )}
            <div className={`ns-drawer${drawerOpen ? " ns-drawer--open" : ""}`} role="dialog" aria-modal="true" aria-label="Filters">
                <SidebarContent {...sidebarProps} onClose={() => setDrawerOpen(false)} />
            </div>

            <aside className="ns-sidebar">
                <SidebarContent {...sidebarProps} />
            </aside>

            <main className="ns-main">
                <div className="ns-header">
                    <div className="ns-header__top">
                        <div>
                            <h1 className="ns-title">{nearbyServicesLabel}</h1>
                            <p className="ns-subtitle">
                                {showingResultsLabel} {filtered.length} {resultLabel} {withinLabel} {maxDistance} km
                            </p>
                        </div>
                        <button
                            className="ns-filter-btn"
                            onClick={() => setDrawerOpen(true)}
                            aria-label="Open filters"
                        >
                            <IcoFilter />
                            <span>{filterBtnLabel}</span>
                            {activeFilterCount > 0 && (
                                <span className="ns-filter-btn__badge">{activeFilterCount}</span>
                            )}
                        </button>
                    </div>
                </div>

                <div className="ns-list">
                    {paginated.length === 0 ? (
                        <div className="ns-empty">{emptyLabel}</div>
                    ) : paginated.map(s => {
                        const meta = CATEGORY_META[s.category] || { color: "#f0f0f0", text: "#333" };
                        const viewDetailsLabel = typeof t.viewDetails === "string" ? t.viewDetails : "View Details";

                        return (
                            <div key={s._uid} className="ns-card">
                                <ServicePhoto photoId={s.photoId} name={s.name} category={s.category} />

                                <div className="ns-card__body">
                                    <span className="ns-card__badge" style={{ background: meta.color, color: meta.text }}>
                                        {s.category}
                                    </span>

                                    <h3 className="ns-card__name">{s.name || "—"}</h3>

                                    {s.category === "Hotel" && s.stars > 0 && (
                                        <div className="ns-card__stars">
                                            {[1,2,3,4,5].map(n => <IcoStar key={n} filled={n <= s.stars} />)}
                                        </div>
                                    )}

                                    <p className="ns-card__location">
                                        <IcoPin />
                                        <span>{s.distance} km{s.address ? ` • ${s.address}` : ""}</span>
                                    </p>

                                    {s.cuisine && <p className="ns-card__meta">🍽 {s.cuisine}</p>}
                                    {s.phone   && <p className="ns-card__meta"><IcoPhone /> {s.phone}</p>}
                                </div>

                                <div className="ns-card__divider" />

                                <div className="ns-card__right">
                                    <button className="ns-card__btn" onClick={() => navigate(`/nearby/${s._uid}`)}>
                                        {viewDetailsLabel} <IcoArrowRight />
                                    </button>
                                </div>

                                <button className="ns-card__heart" onClick={() => toggleSaved(s._uid)} aria-label={saved.has(s._uid) ? (typeof t.unsave === "string" ? t.unsave : "Unsave") : (typeof t.save === "string" ? t.save : "Save")}>
                                    <IcoHeart filled={saved.has(s._uid)} />
                                </button>
                            </div>
                        );
                    })}
                </div>

                {totalPages > 1 && (
                    <div className="ns-pagination">
                        <button className="ns-page-btn ns-page-btn--arrow" disabled={page === 1} onClick={() => setPage(p => p - 1)}>
                            <IcoChevronLeft />
                        </button>
                        {pageNums().map((n, i) =>
                            n === "..." ? (
                                <span key={`e-${i}`} className="ns-page-ellipsis">…</span>
                            ) : (
                                <button key={n} className={`ns-page-btn${page === n ? " ns-page-btn--active" : ""}`} onClick={() => setPage(n)}>
                                    {n}
                                </button>
                            )
                        )}
                        <button className="ns-page-btn ns-page-btn--arrow" disabled={page === totalPages} onClick={() => setPage(p => p + 1)}>
                            <IcoChevronRight />
                        </button>
                    </div>
                )}
            </main>
        </div>
    );
}