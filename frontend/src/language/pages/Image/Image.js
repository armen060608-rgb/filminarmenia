import React, { useState, useEffect } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import "./index.css";
import "../../../fonts.css";
import HotelImg from "../../images/Hotel_img.png";
import ImageTrial from "../../images/image.png";
import { useLanguage } from "../../language";
import { useLocations } from "../Locations/LocationContext";
import { useRegions } from "../Regions/RegionContext";
import { usePlaceTypes } from "../PlaceType/PlaceTypeContext";
import { useClimateZones } from "../ClimateZone/ClimateZoneContext";
import { useHotels } from "../Hotel/HotelContext";
import { useRestaurants } from "../Restaurant/RestaurantContext";
import { usePharmacies } from "../Pharmacy/PharmacyContext";
import { useGasStations } from "../GasStation/GasStationContext";
import { useHospitals } from "../Hospital/HospitalContext";
import { usePhotos } from "../Photo/PhotoContext"; // adjust path
/* ─────────────────────────────────────────────────────────────
   UI label translations
   ───────────────────────────────────────────────────────────── */
const UI = {
    hy: {
        home:           "Գլխավոր",
        locations:      "Վայրեր",
        nearby:         "Մոտակա",
        region:         "Մարզ",
        bestSeason:     "Լավագույն եղանակ",
        landscapeType:  "Լանդշաֆտի տեսակ",
        nearbyServices: "Հարակից ծառայություններ",
        accessibility:  "Հասանելիություն",
        estimatedVisit: "Այցելության տևողություն",
        climate:        "Կլիմա",
    },
    en: {
        home:           "Home",
        locations:      "Locations",
        nearby:         "Nearby",
        region:         "Region",
        bestSeason:     "Best season",
        landscapeType:  "Landscape type",
        nearbyServices: "Nearby services",
        accessibility:  "Accessibility",
        estimatedVisit: "Estimated visit",
        climate:        "Climate",
    },
    ru: {
        home:           "Главная",
        locations:      "Места",
        nearby:         "Рядом",
        region:         "Регион",
        bestSeason:     "Лучший сезон",
        landscapeType:  "Тип ландшафта",
        nearbyServices: "Ближайшие сервисы",
        accessibility:  "Доступность",
        estimatedVisit: "Время посещения",
        climate:        "Климат",
    },
};

/* ─── Thumbnail nav arrows ─── */
const ThumbNext = ({ onClick }) => (
    <button className="lp-tnav lp-tnav--next" onClick={onClick} aria-label="Next">
        <svg width="7" height="13" viewBox="0 0 7 13" fill="none">
            <path d="M1 1.5L6 6.5L1 11.5" stroke="#333" strokeWidth="1.6" strokeLinecap="round" strokeLinejoin="round"/>
        </svg>
    </button>
);
const ThumbPrev = ({ onClick }) => (
    <button className="lp-tnav lp-tnav--prev" onClick={onClick} aria-label="Previous">
        <svg width="7" height="13" viewBox="0 0 7 13" fill="none">
            <path d="M6 1.5L1 6.5L6 11.5" stroke="#333" strokeWidth="1.6" strokeLinecap="round" strokeLinejoin="round"/>
        </svg>
    </button>
);


/* ─── Main slider nav arrows ─── */
const MainNext = ({ onClick }) => (
    <button className="lp-mnav lp-mnav--next" onClick={onClick} aria-label="Next photo">
        <svg width="8" height="14" viewBox="0 0 8 14" fill="none">
            <path d="M1 1L7 7L1 13" stroke="#1a2e4a" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round"/>
        </svg>
    </button>
);
const MainPrev = ({ onClick }) => (
    <button className="lp-mnav lp-mnav--prev" onClick={onClick} aria-label="Previous photo">
        <svg width="8" height="14" viewBox="0 0 8 14" fill="none">
            <path d="M7 1L1 7L7 13" stroke="#1a2e4a" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round"/>
        </svg>
    </button>
);

/* ─── SVG icon set ─── */
const IcoPin   = () => <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round"><path d="M12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.87-3.13-7-7-7z"/><circle cx="12" cy="9" r="2.5"/></svg>;
const IcoCal   = () => <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round"><rect x="3" y="4" width="18" height="18" rx="2"/><line x1="16" y1="2" x2="16" y2="6"/><line x1="8" y1="2" x2="8" y2="6"/><line x1="3" y1="10" x2="21" y2="10"/></svg>;
const IcoMtn   = () => <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round"><path d="M3 17l5-9 4 6 3-4 6 7H3z"/></svg>;
const IcoFork  = () => <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round"><path d="M18 8h1a4 4 0 010 8h-1M2 8h16v9a4 4 0 01-4 4H6a4 4 0 01-4-4V8zM6 1v3M10 1v3M14 1v3"/></svg>;
const IcoCar   = () => <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round"><rect x="1" y="3" width="15" height="13" rx="2"/><path d="M16 8h4l3 4v4h-7V8z"/><circle cx="5.5" cy="18.5" r="2.5"/><circle cx="18.5" cy="18.5" r="2.5"/></svg>;
const IcoClock = () => <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round"><circle cx="12" cy="12" r="10"/><polyline points="12 6 12 12 16 14"/></svg>;
const IcoCloud = () => <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round"><path d="M18 10h-1.26A8 8 0 109 20h9a5 5 0 000-10z"/></svg>;

/* ─── Nearby SVG icons ─── */
const IcoHotel = () => (
    <svg xmlns="http://www.w3.org/2000/svg" width="22" height="15" viewBox="0 0 34 23" fill="none">
        <path d="M0.5 22.5V0H3.5V15H15.5V3H27.5C29.15 3 30.5625 3.5875 31.7375 4.7625C32.9125 5.9375 33.5 7.35 33.5 9V22.5H30.5V18H3.5V22.5H0.5ZM9.5 13.5C8.25 13.5 7.1875 13.0625 6.3125 12.1875C5.4375 11.3125 5 10.25 5 9C5 7.75 5.4375 6.6875 6.3125 5.8125C7.1875 4.9375 8.25 4.5 9.5 4.5C10.75 4.5 11.8125 4.9375 12.6875 5.8125C13.5625 6.6875 14 7.75 14 9C14 10.25 13.5625 11.3125 12.6875 12.1875C11.8125 13.0625 10.75 13.5 9.5 13.5Z" fill="currentColor"/>
    </svg>
);
const IcoGas = () => (
    <svg xmlns="http://www.w3.org/2000/svg" width="18" height="20" viewBox="0 0 29 30" fill="none">
        <path d="M14.0606 11.6667H3.51515V3.33333H14.0606M0 3.33333V30H17.5758V17.5H20.2121V25.8333C20.2121 28.1345 22.1579 30 24.6061 30C27.0543 30 29 28.1345 29 25.8333V10C29 8.85 28.5079 7.8 27.717 7.05L21.1964 0.833333L19.3333 2.6L23.0418 6.11667C21.3897 6.66667 20.2121 8.21667 20.2121 10C20.2121 12.3012 22.1579 14.1667 24.6061 14.1667V25.8333C24.6061 26.75 23.7121 27.5 22.8485 27.5C21.9848 27.5 22.8485 26.75 22.8485 25.8333V18.3333C22.8485 16.4679 21.2804 15 19.3333 15H17.5758V3.33333C17.5758 1.49167 16.0114 0 14.0606 0H3.51515C1.56424 0 0 1.48333 0 3.33333Z" fill="currentColor"/>
    </svg>
);
const IcoHospital = () => (
    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 44 44" fill="none">
        <path d="M33 25.6667H25.6667V33H18.3333V25.6667H11V18.3333H18.3333V11H25.6667V18.3333H33" fill="currentColor"/>
    </svg>
);
const IcoRest = () => (
    <svg xmlns="http://www.w3.org/2000/svg" width="22" height="24" viewBox="0 0 36 37" fill="none">
        <path d="M10.5 33.9167V19.8104C9.225 19.4507 8.1565 18.7313 7.2945 17.6521C6.4325 16.5729 6.001 15.3139 6 13.875V3.08334H9V13.875H10.5V3.08334H13.5V13.875H15V3.08334H18V13.875C18 15.3139 17.569 16.5729 16.707 17.6521C15.845 18.7313 14.776 19.4507 13.5 19.8104V33.9167H10.5ZM25.5 33.9167V21.5833H21V10.7917C21 8.65904 21.7315 6.84141 23.1945 5.3388C24.6575 3.83619 26.426 3.08437 28.5 3.08334V33.9167H25.5Z" fill="currentColor"/>
    </svg>
);
const IcoPharm = () => (
    <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" viewBox="0 0 37 37" fill="none">
        <path d="M14.1102 10.0208H8.47922C8.05295 10.0208 7.70376 10.3677 7.75233 10.7901C8.22639 14.9372 12.5007 18.2233 17.8179 18.4831V24.6667H19.3595V18.4738C24.0251 18.1997 27.7778 14.5592 29.2478 10.7909C29.2963 10.3685 28.9464 10.0216 28.5209 10.0216L14.1102 10.0208ZM17.7739 4.62501C16.536 4.62501 15.5362 3.35236 14.7499 3.85649C13.9614 4.36293 13.4472 5.0667 13.1204 5.83445C12.5515 7.1703 12.5238 8.76207 12.5669 10.0208H14.1102C14.0662 8.7798 14.1017 7.46553 14.5387 6.43878C14.7692 5.8992 15.1014 5.46291 15.5824 5.1538C16.0658 4.84393 16.7595 4.62501 17.7739 4.62501Z" fill="currentColor"/>
        <path d="M23.1252 30.0625C23.1252 28.8716 22.638 27.8734 21.7708 27.1919C20.9237 26.5267 19.7751 26.2084 18.5002 26.2084C17.357 26.2084 16.3426 26.1081 15.6334 25.7929C15.0576 25.4552 14.646 24.7245 14.646 24.2813C14.646 23.5397 15.2935 22.9207 15.6334 22.7697C16.0011 22.6062 16.4513 22.5006 16.9585 22.4367V20.885C16.2694 20.9582 15.5934 21.1 15.0075 21.3606C13.6408 22.4421 13.1043 23.5683 13.1043 24.2813C13.1043 25.6117 13.6408 26.9676 15.0075 27.202C16.0335 27.6575 17.3308 27.7493 18.5002 27.7493C19.5369 27.7493 20.317 28.0098 20.8181 28.4045C21.2998 28.7829 21.5835 29.3264 21.5835 30.0625C21.5835 30.7987 21.2998 31.3429 20.8181 31.7206C20.4666 31.9973 19.9778 32.2078 19.3596 32.3087V29.2917H17.818V32.375H12.3335V33.9167H24.6668V32.375H22.3397C22.8477 31.7491 23.1252 30.9605 23.1252 30.0625Z" fill="currentColor"/>
    </svg>
);

/* ─────────────────────────────────────────────
   Helper: read a multilingual field from a
   looked-up context entity (name_en / name_hy / name_ru).
   ───────────────────────────────────────────── */
function pick(entity, langKey) {
    if (!entity) return "";
    return entity[langKey] ?? entity.name_en ?? "";
}

/* ─────────────────────────────────────────────
   Helper: read a field from the location object
   which stores flat multilingual keys.
   ───────────────────────────────────────────── */
function gl(item, field, langKey) {
    if (!item) return "";
    return item[`${field}_${langKey.replace("name_", "")}`]
        ?? item[`${field}_en`]
        ?? item[`${field}_hy`]
        ?? item[`${field}_ru`]
        ?? item[field]
        ?? "";
}

export default function LocationDetail() {
    const { id } = useParams();
    const navigate = useNavigate();

    const { getPhotosForLocation } = usePhotos();


    const { locations }    = useLocations();
    const { regions }      = useRegions();
    const { placeTypes }   = usePlaceTypes();
    const { climateZones } = useClimateZones();
    const { hotels }       = useHotels();
    const { restaurants }  = useRestaurants();
    const { pharmacies }   = usePharmacies();
    const { gasStations }  = useGasStations();
    const { hospitals }    = useHospitals();
    const { language }     = useLanguage();

    const [mainSlider, setMainSlider]     = useState(null);
    const [thumbSlider, setThumbSlider]   = useState(null);
    const [currentSlide, setCurrentSlide] = useState(0);
    const [modalItem, setModalItem]       = useState(null);

    useEffect(() => {
        document.body.className = "lp";
        return () => { document.body.className = ""; };
    }, []);

    useEffect(() => {
        document.body.style.overflow = modalItem ? "hidden" : "";
        return () => { document.body.style.overflow = ""; };
    }, [modalItem]);

    const location = locations.find(item => item.id === id);
    if (!location) return <div className="lp-loading"><div className="lp-spin" /></div>;

    /* active language key used by context entities */
    const langKey = language === "hy" ? "name_hy" : language === "ru" ? "name_ru" : "name_en";

    /* UI labels */
    const u = UI[language] || UI.en;

    /* ── Resolve UUID references via contexts ── */
    const regionEntity      = regions.find(r => r.id === location.placeRegionId);
    const placeTypeEntity   = placeTypes.find(t => t.id === location.locationType);
    const climateZoneEntity = climateZones.find(c => c.id === location.climate);

    const regionName      = pick(regionEntity, langKey);
    const placeTypeName   = pick(placeTypeEntity, langKey);
    const climateZoneName = pick(climateZoneEntity, langKey);

    /* ── Direct multilingual fields from location ── */
    const name        = location[langKey.replace("name_", "name_")] ?? location.name_en ?? "";
    const description = language === "hy" ? location.description_hy
        : language === "ru" ? location.description_ru
            : location.description_en;

    /* ── Other flat fields ── */
    const bestSeason     = location.bestSeason     ?? "";
    const nearbyServices = location.nearbyServices ?? "";
    const roadQuality    = location.roadQuality    ?? "";
    const estimatedVisit = location.estimatedVisit ?? "";
    const country        = location.country        ?? "";

    const matchedPhotos = getPhotosForLocation(regionEntity?.name_en, location.name_en);
    const fallback = [ImageTrial];
    const images = matchedPhotos.length > 0 ? matchedPhotos : fallback;

    /* ── Nearby entities ── */
    const nearbyHotel      = hotels.find(h => h.locationId === id)      || null;
    const nearbyRestaurant = restaurants.find(r => r.locationId === id) || null;
    const nearbyPharmacy   = pharmacies.find(p => p.locationId === id)  || null;
    const nearbyGasStation = gasStations.find(g => g.locationId === id) || null;
    const nearbyHospital   = hospitals.find(h => h.locationId === id)   || null;

    const nearbyList = [
        { label: "Hotel",       Icon: IcoHotel,    entity: nearbyHotel,      image: nearbyHotel?.images?.[0] || HotelImg, images: nearbyHotel?.images || [],  link: "" },
        { label: "Gas Station", Icon: IcoGas,      entity: nearbyGasStation, image: ImageTrial,                           images: [],                          link: "https://www.maxoil.am/en/oil-stations" },
        { label: "Hospital",    Icon: IcoHospital, entity: nearbyHospital,   image: ImageTrial,                           images: [],                          link: "" },
        { label: "Restaurant",  Icon: IcoRest,     entity: nearbyRestaurant, image: ImageTrial,                           images: [],                          link: "" },
        { label: "Pharmacy",    Icon: IcoPharm,    entity: nearbyPharmacy,   image: ImageTrial,                           images: [],                          link: "" },
    ];

    /* ── Details grid cells — only show if value exists ── */
    const cells = [
        { Icon: IcoPin,   label: u.region,         value: regionName },
        { Icon: IcoCal,   label: u.bestSeason,      value: bestSeason },
        { Icon: IcoMtn,   label: u.landscapeType,   value: placeTypeName },
        { Icon: IcoCloud, label: u.climate,         value: climateZoneName },
        { Icon: IcoFork,  label: u.nearbyServices,  value: nearbyServices },
        { Icon: IcoCar,   label: u.accessibility,   value: roadQuality },
        { Icon: IcoClock, label: u.estimatedVisit,  value: estimatedVisit },
    ].filter(c => c.value);

    const thumbShow = Math.min(images.length, 5);



    return (
        <div className="lp">
            {/* breadcrumb */}
            <nav className="lp-crumb">
                <button className="lp-crumb__btn" onClick={() => navigate("/")}>{u.home}</button>
                <span className="lp-crumb__sep">›</span>
                <button className="lp-crumb__btn" onClick={() => navigate("/explore")}>{u.locations}</button>
                {regionName && (
                    <>
                        <span className="lp-crumb__sep">›</span>
                        <span className="lp-crumb__plain">{regionName}</span>
                    </>
                )}
                <span className="lp-crumb__sep">›</span>
                <span className="lp-crumb__plain lp-crumb__plain--bold">{name}</span>
            </nav>

            {/* two-column layout */}
            <div className="lp-layout">

                {/* ════ LEFT: photo card ════ */}
                <div className="lp-card">
                    {placeTypeName && (
                        <div className="lp-card__badge">
                            <IcoMtn />
                            {placeTypeName}
                        </div>
                    )}

                    <button className="lp-card__heart" aria-label="Save">
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="#555" strokeWidth="1.8">
                            <path d="M20.84 4.61a5.5 5.5 0 00-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 00-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 000-7.78z"/>
                        </svg>
                    </button>

                    {/* main slider */}
                    <div style={{ position: "relative" }}>
                        <MainPrev onClick={() => mainSlider?.slickPrev()} />
                        <Slider
                            className="lp-main-slider"
                            asNavFor={thumbSlider}
                            ref={s => setMainSlider(s)}
                            arrows={false}
                            fade={true}
                            beforeChange={(_, next) => setCurrentSlide(next)}
                        >
                            {images.map((img, i) => (
                                <div key={i}>
                                    <img src={img} alt={`Photo ${i + 1}`} className="lp-main-slider__img" />
                                </div>
                            ))}
                        </Slider>
                        <MainNext onClick={() => mainSlider?.slickNext()} />
                    </div>

                    {/* thumbnail strip */}
                    <div className="lp-thumb-strip">
                        <Slider
                            className="lp-thumb-slider"
                            asNavFor={mainSlider}
                            ref={s => setThumbSlider(s)}
                            slidesToShow={thumbShow}
                            slidesToScroll={1}
                            swipeToSlide={true}
                            focusOnSelect={true}
                            arrows={images.length > thumbShow}
                            nextArrow={<ThumbNext />}
                            prevArrow={<ThumbPrev />}
                            infinite={false}
                            variableWidth={false}
                        >
                            {images.map((img, i) => (
                                <div key={i} className="lp-thumb-slider__item">
                                    <img
                                        src={img}
                                        alt={`Thumb ${i + 1}`}
                                        className={`lp-thumb-slider__img${currentSlide === i ? " lp-thumb-slider__img--active" : ""}`}
                                    />
                                </div>
                            ))}
                        </Slider>
                    </div>
                </div>

                {/* ════ RIGHT: info panel ════ */}
                <div className="lp-info">
                    <h1 className="lp-info__title">{name}</h1>

                    {(regionName || country) && (
                        <p className="lp-info__subtitle">
                            {[regionName, country].filter(Boolean).join(", ")}
                        </p>
                    )}

                    {description && (
                        <p className="lp-info__desc">{description}</p>
                    )}

                    {/* details grid */}
                    {cells.length > 0 && (
                        <div className="lp-details">
                            {cells.map(({ Icon, label, value }, i) => (
                                <div key={i} className="lp-details__cell">
                                    <span className="lp-details__cell-icon"><Icon /></span>
                                    <div>
                                        <div className="lp-details__cell-label">{label}</div>
                                        <div className="lp-details__cell-value">{value}</div>
                                    </div>
                                </div>
                            ))}
                        </div>
                    )}

                    {/* Nearby link */}
                    <Link to={`/location/${id}/nearby`} className="lp-nearby__heading">
                        <span>{u.nearby}</span>
                        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.4" strokeLinecap="round" strokeLinejoin="round">
                            <path d="M9 18l6-6-6-6"/>
                        </svg>
                    </Link>
                </div>
            </div>

            {/* ════ Nearby modal ════ */}
            {modalItem && (
                <div className="lp-modal-bg" onClick={() => setModalItem(null)}>
                    <div className="lp-modal" onClick={e => e.stopPropagation()}>
                        <div className="lp-modal__header">
                            <div className="lp-modal__header-left">
                                <span className="lp-modal__header-icon"><modalItem.Icon /></span>
                                <span className="lp-modal__header-title">{modalItem.label}</span>
                            </div>
                            <button className="lp-modal__close" onClick={() => setModalItem(null)}>
                                <svg width="15" height="15" viewBox="0 0 20 20" fill="none">
                                    <path d="M3 3l14 14M17 3L3 17" stroke="#444" strokeWidth="2" strokeLinecap="round"/>
                                </svg>
                            </button>
                        </div>
                        <div className="lp-modal__body">
                            {modalItem.images?.length > 0 ? (
                                <div className="lp-modal__gallery">
                                    {modalItem.images.map((img, i) => (
                                        <img key={i} src={img} alt="" className="lp-modal__gimg" />
                                    ))}
                                </div>
                            ) : (
                                <img src={modalItem.image} alt={modalItem.label} className="lp-modal__img" />
                            )}
                            <div className="lp-modal__text">
                                <h3 className="lp-modal__name">
                                    {pick(modalItem.entity, langKey) || modalItem.label}
                                </h3>
                                <p className="lp-modal__desc">
                                    {(modalItem.entity
                                            ? (language === "hy" ? modalItem.entity.description_hy
                                                : language === "ru" ? modalItem.entity.description_ru
                                                    : modalItem.entity.description_en)
                                            : null)
                                        || `No ${modalItem.label.toLowerCase()} information available.`}
                                </p>
                                {modalItem.link && (
                                    <Link to={modalItem.link} className="lp-modal__visit">
                                        Visit Website →
                                    </Link>
                                )}
                            </div>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}