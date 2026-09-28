import "../../../App.css";
import "../../../hover.css";
import "../../../fonts.css";
import React, { useState, useRef, useEffect, useMemo } from "react";
import "./index.css";
import { Link } from "react-router-dom";
import { useLocations } from "../Locations/LocationContext";
import { usePlaceTypes } from "../PlaceType/PlaceTypeContext";
import { useLanguage } from "../../language";
import { getTranslations } from "../../translations";
import { usePhotos } from "../Photo/PhotoContext";
import { useRegions } from "../Regions/RegionContext";

const LANG_KEY = { hy: "name_hy", en: "name_en", ru: "name_ru" };

/* ─── "All" pill icon ─── */
const IcoAll = () => (
    <svg width="20" height="20" viewBox="0 0 20 20" fill="none">
        <rect x="0" y="0" width="8" height="8" rx="1.5" fill="currentColor"/>
        <rect x="12" y="0" width="8" height="8" rx="1.5" fill="currentColor"/>
        <rect x="0" y="12" width="8" height="8" rx="1.5" fill="currentColor"/>
        <rect x="12" y="12" width="8" height="8" rx="1.5" fill="currentColor"/>
    </svg>
);

/* ─── HeartButton ─── */
function HeartButton({ id }) {
    const [liked, setLiked] = useState(() => localStorage.getItem(`fav_${id}`) === "true");
    const handleLike = (e) => {
        e.preventDefault();
        const next = !liked;
        setLiked(next);
        localStorage.setItem(`fav_${id}`, next);
    };
    return (
        <button onClick={handleLike} style={{
            position: "absolute", top: 12, right: 12,
            width: 34, height: 34, borderRadius: "50%",
            background: "white", border: "none", cursor: "pointer",
            display: "flex", alignItems: "center", justifyContent: "center",
            boxShadow: "0 2px 8px rgba(0,0,0,0.15)", zIndex: 2,
        }}>
            <svg width="16" height="15" viewBox="0 0 16 15" fill={liked ? "#e74c3c" : "none"}>
                <path d="M8 13.5C8 13.5 1 9 1 4.5C1 2.567 2.567 1 4.5 1C5.8 1 6.95 1.7 7.6 2.75C7.8 3.05 8.2 3.05 8.4 2.75C9.05 1.7 10.2 1 11.5 1C13.433 1 15 2.567 15 4.5C15 9 8 13.5 8 13.5Z"
                      stroke={liked ? "#e74c3c" : "#555"} strokeWidth="1.4"/>
            </svg>
        </button>
    );
}

/* ─── LocationCard ─── */
function LocationCard({ loc, langKey, t }) {
    const { getPhotosForLocation, photos } = usePhotos();
    const { regions } = useRegions();

    const [currentImageIndex, setCurrentImageIndex] = useState(0);
    const [imgErrors, setImgErrors]                 = useState({});
    const [isHovering, setIsHovering]               = useState(false);
    const slideIntervalRef                          = useRef(null);

    const name   = loc[langKey] || loc.name_en || t.location;
    const region = loc.region   || loc.country  || "Armenia";

    const regionEntity = regions.find(r => r.id === loc.placeRegionId);
    const regionName   = regionEntity?.name_en ?? loc.region ?? "";

    const sliderImages = useMemo(() => {
        const matched = getPhotosForLocation(regionName, loc.name_en);
        if (matched.length > 0) return matched;
        if (loc.sliderImages?.length > 0) return loc.sliderImages;
        if (loc.galleryImages?.length > 0) return loc.galleryImages;
        if (loc.coverImage) return [loc.coverImage];
        return [];
    }, [photos, loc, regionName]);

    const hasMultipleImages = sliderImages.length > 1;

    useEffect(() => {
        if (isHovering && hasMultipleImages) {
            slideIntervalRef.current = setInterval(() => {
                setCurrentImageIndex(prev => (prev + 1) % sliderImages.length);
            }, 2000);
        } else {
            clearInterval(slideIntervalRef.current);
        }
        return () => clearInterval(slideIntervalRef.current);
    }, [isHovering, hasMultipleImages, sliderImages.length]);

    // ⛔️ FIX 1: removed the `document.body.className = "search"` effect from here.
    // It lived in the card, so when there were 0 results no card mounted and
    // the last card's cleanup wiped body.className -> background image vanished.

    useEffect(() => { setCurrentImageIndex(0); }, [loc.id]);

    const currentImage       = sliderImages[currentImageIndex];
    const currentImageFailed = imgErrors[currentImageIndex];

    const goTo = (dir, e) => {
        e.preventDefault(); e.stopPropagation();
        setCurrentImageIndex(prev =>
            dir === "prev"
                ? (prev - 1 + sliderImages.length) % sliderImages.length
                : (prev + 1) % sliderImages.length
        );
    };

    return (
        <Link to={`/location/${loc.id}`} style={{ textDecoration: "none", color: "inherit" }}>
            <div
                style={{
                    borderRadius: 14, overflow: "hidden", background: "white",
                    boxShadow: "0 2px 12px rgba(0,0,0,0.08)",
                    transition: "transform 0.2s, box-shadow 0.2s",
                    cursor: "pointer", height: "100%", display: "flex", flexDirection: "column",
                }}
                onMouseEnter={() => setIsHovering(true)}
                onMouseLeave={() => setIsHovering(false)}
            >
                <div style={{ position: "relative", width: "100%", height: 210, background: "linear-gradient(135deg,#667eea,#764ba2)", overflow: "hidden" }}>
                    {sliderImages.length > 0 && currentImage && !currentImageFailed ? (
                        <>
                            <img src={currentImage} alt={name}
                                 onError={() => setImgErrors(p => ({ ...p, [currentImageIndex]: true }))}
                                 style={{ width: "100%", height: "100%", objectFit: "cover", display: "block" }} />
                            {hasMultipleImages && (
                                <>
                                    <span style={{
                                        position: "absolute", bottom: 10, right: 10,
                                        background: "rgba(0,0,0,0.6)", color: "white",
                                        fontSize: 11, padding: "2px 8px", borderRadius: 12, zIndex: 3,
                                    }}>{currentImageIndex + 1} / {sliderImages.length}</span>
                                    {isHovering && <>
                                        <button onClick={e => goTo("prev", e)} style={{ position: "absolute", left: 8, top: "50%", transform: "translateY(-50%)", background: "rgba(0,0,0,0.5)", color: "white", border: "none", borderRadius: "50%", width: 28, height: 28, cursor: "pointer", fontSize: 18, zIndex: 3 }}>‹</button>
                                        <button onClick={e => goTo("next", e)} style={{ position: "absolute", right: 8, top: "50%", transform: "translateY(-50%)", background: "rgba(0,0,0,0.5)", color: "white", border: "none", borderRadius: "50%", width: 28, height: 28, cursor: "pointer", fontSize: 18, zIndex: 3 }}>›</button>
                                    </>}
                                </>
                            )}
                        </>
                    ) : (
                        <div style={{ width: "100%", height: "100%", background: "linear-gradient(135deg,#667eea,#764ba2)", display: "flex", alignItems: "center", justifyContent: "center", flexDirection: "column", gap: 10 }}>
                            <svg width="64" height="64" viewBox="0 0 48 48" fill="none">
                                <path d="M24 4C14.058 4 6 12.058 6 22C6 32 24 44 24 44C24 44 42 32 42 22C42 12.058 33.942 4 24 4ZM24 28C19.582 28 16 24.418 16 20C16 15.582 19.582 12 24 12C28.418 12 32 15.582 32 20C32 24.418 28.418 28 24 28Z" fill="white" opacity="0.8"/>
                            </svg>
                            <span style={{ color: "white", fontSize: 14, opacity: 0.8 }}>{name}</span>
                        </div>
                    )}
                    <HeartButton id={loc.id} />
                </div>

                <div style={{ padding: "14px 16px 16px", flex: 1 }}>
                    <div style={{ fontWeight: 600, fontSize: 16, color: "#1a1a2e", marginBottom: 8, display: "-webkit-box", WebkitLineClamp: 2, WebkitBoxOrient: "vertical", overflow: "hidden", lineHeight: "1.4", minHeight: 44 }}>
                        {name}
                    </div>
                    <div style={{ display: "flex", alignItems: "center", justifyContent: "space-between" }}>
                        <div style={{ display: "flex", alignItems: "center", gap: 5, color: "#666", fontSize: 13 }}>
                            <svg width="11" height="14" viewBox="0 0 11 14" fill="none">
                                <path d="M5.5 0C2.46 0 0 2.46 0 5.5C0 9.625 5.5 14 5.5 14C5.5 14 11 9.625 11 5.5C11 2.46 8.54 0 5.5 0ZM5.5 7.5C4.395 7.5 3.5 6.605 3.5 5.5C3.5 4.395 4.395 3.5 5.5 3.5C6.605 3.5 7.5 4.395 7.5 5.5C7.5 6.605 6.605 7.5 5.5 7.5Z" fill="#888"/>
                            </svg>
                            <span style={{ whiteSpace: "nowrap", overflow: "hidden", textOverflow: "ellipsis", maxWidth: 130 }}>{region}</span>
                        </div>
                        <span style={{ color: "#1a1a2e", fontSize: 13, fontWeight: 500, display: "flex", alignItems: "center", gap: 4 }}>
                            {t.viewDetails}
                            <svg width="14" height="14" viewBox="0 0 14 14" fill="none">
                                <path d="M2 7H12M12 7L7.5 2.5M12 7L7.5 11.5" stroke="#1a1a2e" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round"/>
                            </svg>
                        </span>
                    </div>
                </div>
            </div>
        </Link>
    );
}

/* ─── Main Explore Component ─── */
function Explore() {
    const { locations: contextLocations } = useLocations();
    const { placeTypes }                  = usePlaceTypes();
    const { language }                    = useLanguage();
    const t                               = getTranslations(language);
    const langKey                         = LANG_KEY[language] ?? "name_en";

    const ITEMS_PER_PAGE = 8;

    const [currentPage,             setCurrentPage]             = useState(1);
    const [inputValue,              setInputValue]              = useState("");
    const [activePillId,            setActivePillId]            = useState("all");
    const [suggestions,             setSuggestions]             = useState([]);
    const [selectedSuggestionIndex, setSelectedSuggestionIndex] = useState(-1);
    const [isVisible,               setIsVisible]               = useState(false);

    // ✅ FIX 1: keep the background image alive for the whole Explore page,
    // regardless of how many cards are rendered (even zero).
    useEffect(() => {
        document.body.className = "search";
        return () => { document.body.className = ""; };
    }, []);

    // ✅ FIX 3: flag so that selecting a suggestion doesn't immediately
    // re-trigger the dropdown from the inputValue effect below.
    const suppressSuggestionsRef = useRef(false);

    /* ─── Suggestions ─── */
    const getAllKeywords = () => {
        const all = new Set();
        contextLocations.forEach(loc => {
            (loc.keywords || []).forEach(kw => all.add(kw.toLowerCase()));
            if (loc.name_en) all.add(loc.name_en.toLowerCase());
            if (loc.name_hy) all.add(loc.name_hy.toLowerCase());
            if (loc.name_ru) all.add(loc.name_ru.toLowerCase());
        });
        return Array.from(all).sort();
    };

    useEffect(() => {
        if (suppressSuggestionsRef.current) {
            suppressSuggestionsRef.current = false;
            setSuggestions([]);
            return;
        }
        if (inputValue.length > 0) {
            const term = inputValue.toLowerCase();
            // ✅ FIX 2: startsWith instead of includes
            setSuggestions(getAllKeywords().filter(k => k.startsWith(term)).slice(0, 8));
            setSelectedSuggestionIndex(-1);
        } else {
            setSuggestions([]);
        }
    }, [inputValue, contextLocations]);

    useEffect(() => { setCurrentPage(1); }, [activePillId, inputValue]);

    /* ─── Filtering ─── */
    const byType = activePillId === "all"
        ? contextLocations
        : contextLocations.filter(loc => loc.locationType === activePillId);

    const filteredLocations = !inputValue
        ? byType
        : byType.filter(loc => {
            const term = inputValue.toLowerCase();
            // ✅ FIX 2: match places whose names/keywords START with the term
            return (
                (loc.keywords || []).some(k => k.toLowerCase().startsWith(term)) ||
                (loc[langKey]       || "").toLowerCase().startsWith(term) ||
                (loc.name_en        || "").toLowerCase().startsWith(term) ||
                (loc.name_hy        || "").toLowerCase().startsWith(term) ||
                (loc.name_ru        || "").toLowerCase().startsWith(term)
            );
        });

    /* ─── Pagination ─── */
    const totalPages  = Math.max(1, Math.ceil(filteredLocations.length / ITEMS_PER_PAGE));
    const startIndex  = (currentPage - 1) * ITEMS_PER_PAGE;
    const visibleData = filteredLocations.slice(startIndex, startIndex + ITEMS_PER_PAGE);

    useEffect(() => { if (currentPage > totalPages) setCurrentPage(totalPages); }, [currentPage, totalPages]);

    /* ─── Handlers ─── */
    const handlePillClick = (id) => { setActivePillId(id); setCurrentPage(1); };

    const handleInputChange = (e) => { setInputValue(e.target.value); };

    const handleKeyDown = (e) => {
        if (e.key === "ArrowDown") { e.preventDefault(); setSelectedSuggestionIndex(p => Math.min(p + 1, suggestions.length - 1)); }
        else if (e.key === "ArrowUp") { e.preventDefault(); setSelectedSuggestionIndex(p => Math.max(p - 1, -1)); }
        else if (e.key === "Enter" && selectedSuggestionIndex >= 0) {
            e.preventDefault();
            suppressSuggestionsRef.current = true;     // ✅ FIX 3
            setInputValue(suggestions[selectedSuggestionIndex]);
            setSuggestions([]);
        }
    };

    const handleClear             = () => { setInputValue(""); setSuggestions([]); };
    const handleSuggestionClick   = (s) => {
        suppressSuggestionsRef.current = true;          // ✅ FIX 3: close on first click
        setInputValue(s);
        setSuggestions([]);
    };
    const handleDragOver          = (e) => e.preventDefault();
    const handleDrop              = (e) => { e.preventDefault(); };
    const handleFileChange        = () => {};
    const handleSubmit            = (e) => e.preventDefault();

    return (
        <div className="search">
            {/* ── Hero search bar ── */}
            <div className="search__bar">
                <div className="search__bar-header">{t.inputHeader}</div>
                <div className="search__bar-subheader">{t.inputsubheader}</div>

                <div className="search__input-box">
                    <div className="search__input-wrapper pr">
                        <div className="pr" style={{ position: "relative" }}>
                            <svg className="form__search-svg pa" width="24" height="24" viewBox="0 0 24 24" fill="none">
                                <path d="M22.1333 24L13.7333 15.6C13.0667 16.1333 12.3 16.5556 11.4333 16.8667C10.5667 17.1778 9.64445 17.3333 8.66667 17.3333C6.24445 17.3333 4.19467 16.4942 2.51733 14.816C0.840001 13.1378 0.000889594 11.088 7.05467e-07 8.66667C-0.000888183 6.24533 0.838223 4.19556 2.51733 2.51733C4.19645 0.839111 6.24622 0 8.66667 0C11.0871 0 13.1373 0.839111 14.8173 2.51733C16.4973 4.19556 17.336 6.24533 17.3333 8.66667C17.3333 9.64444 17.1778 10.5667 16.8667 11.4333C16.5556 12.3 16.1333 13.0667 15.6 13.7333L24 22.1333L22.1333 24ZM8.66667 14.6667C10.3333 14.6667 11.7502 14.0836 12.9173 12.9173C14.0844 11.7511 14.6676 10.3342 14.6667 8.66667C14.6658 6.99911 14.0827 5.58267 12.9173 4.41733C11.752 3.252 10.3351 2.66844 8.66667 2.66667C6.99822 2.66489 5.58178 3.24844 4.41733 4.41733C3.25289 5.58622 2.66933 7.00267 2.66667 8.66667C2.664 10.3307 3.24756 11.7476 4.41733 12.9173C5.58711 14.0871 7.00356 14.6702 8.66667 14.6667Z"
                                      fill="#53599A"/>
                            </svg>
                            <input
                                className="search__input"
                                type="text"
                                placeholder={t.searchPlaceholder}
                                value={inputValue}
                                onChange={handleInputChange}
                                onKeyDown={handleKeyDown}
                                autoComplete="off"
                            />
                            {inputValue.length > 0 && (
                                <button className="search__input-clear-btn pa" type="button" onClick={handleClear}>
                                    <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" viewBox="0 0 22 22" fill="none">
                                        <rect width="22" height="22" rx="11" fill="#323232"/>
                                        <path d="M7.05 16.5L6 15.45L10.2 11.25L6 7.05L7.05 6L11.25 10.2L15.45 6L16.5 7.05L12.3 11.25L16.5 15.45L15.45 16.5L11.25 12.3L7.05 16.5Z" fill="white"/>
                                    </svg>
                                </button>
                            )}

                            {/* Suggestions dropdown */}
                            {suggestions.length > 0 && (
                                <div style={{
                                    position: "absolute", top: "100%", left: 0, right: 0,
                                    background: "white", borderRadius: 12,
                                    boxShadow: "0 4px 20px rgba(0,0,0,0.15)",
                                    zIndex: 1000, marginTop: 8, overflow: "hidden",
                                    border: "1px solid #e0e0e0",
                                }}>
                                    {suggestions.map((s, i) => (
                                        <div key={i} onClick={() => handleSuggestionClick(s)}
                                             onMouseEnter={() => setSelectedSuggestionIndex(i)}
                                             style={{
                                                 padding: "12px 16px", cursor: "pointer",
                                                 display: "flex", alignItems: "center", gap: 10,
                                                 background: i === selectedSuggestionIndex ? "#f0f0f0" : "white",
                                                 borderBottom: i < suggestions.length - 1 ? "1px solid #eee" : "none",
                                             }}>
                                            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="#999" strokeWidth="2">
                                                <circle cx="10" cy="10" r="7"/>
                                                <line x1="15" y1="15" x2="21" y2="21"/>
                                            </svg>
                                            <span style={{ flex: 1, color: "#333" }}>{s}</span>
                                            <span style={{ fontSize: 11, color: "#999" }}>{t.pressEnter}</span>
                                        </div>
                                    ))}
                                </div>
                            )}
                        </div>
                    </div>

                    {/* Upload box */}
                    <div className={`upload-box-wrapper ${isVisible ? "show" : ""}`} onClick={() => setIsVisible(p => !p)}>
                        {isVisible && (
                            <div className="upload-box" onClick={e => e.stopPropagation()}>
                                <button className="upload-box__close-btn" type="button" onClick={() => setIsVisible(false)}>
                                    <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" viewBox="0 0 22 22" fill="none">
                                        <rect width="22" height="22" rx="11" fill="#323232"/>
                                        <path d="M7.05 16.5L6 15.45L10.2 11.25L6 7.05L7.05 6L11.25 10.2L15.45 6L16.5 7.05L12.3 11.25L16.5 15.45L15.45 16.5L11.25 12.3L7.05 16.5Z" fill="white"/>
                                    </svg>
                                </button>
                                <div className="upload-box__content">
                                    <span className="upload-box__title">{t.uploadImage}</span>
                                    <form onSubmit={handleSubmit} className="upload-box__form">
                                        <div className="upload-box__form-content">
                                            <div className="upload-box__image-container" id="upload-area"
                                                 onDragOver={handleDragOver} onDrop={handleDrop}/>
                                            <span className="upload-box__drag-info">{t.dragAndDrop}</span>
                                            <label htmlFor="file" className="upload-box__label">{t.selectFile}</label>
                                            <input className="dn" id="file" type="file" accept="image/*"
                                                   onChange={handleFileChange} required/>
                                        </div>
                                        <button className="upload-box__submit-btn" type="submit">{t.searchSimilar}</button>
                                    </form>
                                </div>
                            </div>
                        )}
                    </div>
                </div>
            </div>

            {/* ── Filter pills + grid ── */}
            <div className="search__layout pr">
                <div className="center search-container">

                    <div style={{display: "flex", alignItems: "center", gap: 10, flexWrap: "wrap", marginBottom: 24}}>

                        <button
                            onClick={() => handlePillClick("all")}
                            style={{
                                display: "flex", alignItems: "center", gap: 7,
                                padding: "8px 16px", borderRadius: 30,
                                border: activePillId === "all" ? "2px solid #1e2a4a" : "1.5px solid #d0d5e0",
                                background: activePillId === "all" ? "#1e2a4a" : "white",
                                color: activePillId === "all" ? "white" : "#333",
                                fontSize: 14, fontWeight: 500, cursor: "pointer",
                                transition: "all 0.18s", whiteSpace: "nowrap",
                            }}
                        >
                            <span style={{display: "flex", alignItems: "center"}}><IcoAll/></span>
                            {t.pill_all || "All"}
                        </button>

                        {placeTypes.map(pt => {
                            const label = pt[langKey] ?? pt.name_en;
                            const active = activePillId === pt.id;
                            return (
                                <button
                                    key={pt.id}
                                    onClick={() => handlePillClick(pt.id)}
                                    style={{
                                        display: "flex", alignItems: "center", gap: 7,
                                        padding: "8px 16px", borderRadius: 30,
                                        border: active ? "2px solid #1e2a4a" : "1.5px solid #d0d5e0",
                                        background: active ? "#1e2a4a" : "white",
                                        color: active ? "white" : "#333",
                                        fontSize: 14, fontWeight: 500, cursor: "pointer",
                                        transition: "all 0.18s", whiteSpace: "nowrap",
                                    }}
                                >
                                    {label}
                                </button>
                            );
                        })}

                        <span style={{marginLeft: "auto", color: "#666", fontSize: 14, fontWeight: 500}}>
                            {filteredLocations.length} {t.results}
                        </span>
                    </div>

                    <div
                        style={{
                            display: "grid",
                            gridTemplateColumns: "repeat(auto-fill, minmax(min(280px, 100%), 1fr))",
                            maxWidth: "100%",
                            gap: 24,
                            marginBottom: 40
                        }}
                    >
                        {visibleData.map(loc => (
                            <LocationCard key={loc.id} loc={loc} langKey={langKey} t={t}/>
                        ))}
                        {visibleData.length === 0 && (
                            <div style={{
                                gridColumn: "1/-1",
                                textAlign: "center",
                                padding: "60px 0",
                                color: "#aaa",
                                fontSize: 16
                            }}>
                                {t.noLocationsFound}
                            </div>
                        )}
                    </div>

                    {filteredLocations.length > ITEMS_PER_PAGE && (
                        <div style={{
                            display: "flex",
                            justifyContent: "center",
                            alignItems: "center",
                            gap: 8,
                            marginBottom: 24
                        }}>
                            <button type="button" onClick={() => setCurrentPage(p => Math.max(p - 1, 1))}
                                    disabled={currentPage === 1}
                                    style={{
                                        width: 32, height: 32, border: "none", background: "transparent",
                                        color: currentPage === 1 ? "#b8b8c7" : "#2b2f42",
                                        cursor: currentPage === 1 ? "default" : "pointer",
                                        fontSize: 22, lineHeight: 1
                                    }}
                                    aria-label="Previous page">‹
                            </button>

                            {(() => {
                                const last = totalPages;
                                const anchor = currentPage <= 2 ? 2 : Math.min(currentPage, last - 4);
                                const core = new Set([1, anchor, anchor+1, anchor+2, anchor+3, last]
                                    .filter(p => p >= 1 && p <= last));
                                const sorted = Array.from(core).sort((a, b) => a - b);

                                return sorted.map(page => (
                                    <button key={page} type="button" onClick={() => setCurrentPage(page)}
                                            style={{
                                                minWidth: 32, height: 32, borderRadius: 8,
                                                border: "1px solid #d9d9e5",
                                                background: currentPage === page ? "#eef0ff" : "#fff",
                                                color: "#2b2f42", fontSize: 14,
                                                fontWeight: currentPage === page ? 700 : 500,
                                                cursor: "pointer",
                                            }}>
                                        {page}
                                    </button>
                                ));
                            })()}

                            <button type="button" onClick={() => setCurrentPage(p => Math.min(p + 1, totalPages))}
                                    disabled={currentPage === totalPages}
                                    style={{
                                        width: 32, height: 32, border: "none", background: "transparent",
                                        color: currentPage === totalPages ? "#b8b8c7" : "#2b2f42",
                                        cursor: currentPage === totalPages ? "default" : "pointer",
                                        fontSize: 22, lineHeight: 1
                                    }}
                                    aria-label="Next page">›
                            </button>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
}

export default Explore;
