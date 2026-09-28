// LocationDetail.js - Fixed version for single location view with slider

import React, { useState, useEffect, useRef } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { useLocations } from "./LocationContext";
import { useLanguage } from "../../language";

const LANG_KEY = { hy: "name_hy", en: "name_en", ru: "name_ru" };
const DESC_KEY = { hy: "description_hy", en: "description_en", ru: "description_ru" };

// Map internal type keys → readable badge labels
const TYPE_LABEL = {
    mountains: "Mountains",
    bodiesOfWater: "Lakes and rivers",
    historicalPlace: "Historical",
    cities: "Urban",
    forests: "Forests",
    publicPlaces: "Urban",
    venue: "Venue",
    viewPoint: "Nature",
    deserts: "Desert",
};

function LocationDetail() {
    const { id } = useParams();
    const navigate = useNavigate();
    const { locations } = useLocations();
    const { language } = useLanguage();
    const [currentImageIndex, setCurrentImageIndex] = useState(0);
    const [isModalOpen, setIsModalOpen] = useState(false);

    const location = locations.find(l => l.id === id);
    const langKey = LANG_KEY[language] || "name_en";
    const descKey = DESC_KEY[language] || "description_en";

    useEffect(() => {
        if (!location) {
            navigate("/Locations");
        }
    }, [location, navigate]);

    if (!location) {
        return <div className="container__locations">Loading...</div>;
    }

    const name = location[langKey] || location.name_en || "Location";
    const description = location[descKey] || location.description_en || "No description available.";

    // Get all images from sliderImages array
    const getSliderImages = () => {
        if (location.sliderImages && Array.isArray(location.sliderImages) && location.sliderImages.length > 0) {
            return location.sliderImages.filter(img => img && typeof img === 'string' && img.startsWith('data:image'));
        }
        if (location.galleryImages && Array.isArray(location.galleryImages) && location.galleryImages.length > 0) {
            return location.galleryImages.filter(img => img && typeof img === 'string' && img.startsWith('data:image'));
        }
        if (location.coverImage && typeof location.coverImage === 'string' && location.coverImage.startsWith('data:image')) {
            return [location.coverImage];
        }
        return [];
    };

    const sliderImages = getSliderImages();
    const hasMultipleImages = sliderImages.length > 1;

    const nextImage = () => {
        setCurrentImageIndex((prev) => (prev + 1) % sliderImages.length);
    };

    const prevImage = () => {
        setCurrentImageIndex((prev) => (prev - 1 + sliderImages.length) % sliderImages.length);
    };

    const openModal = () => setIsModalOpen(true);
    const closeModal = () => setIsModalOpen(false);

    return (
        <div className="container__locations">
            {/* Image Slider Section */}
            {sliderImages.length > 0 && (
                <div className="location-slider" style={{ marginBottom: "30px" }}>
                    <div className="slider-container" style={{ position: "relative" }}>
                        <img
                            src={sliderImages[currentImageIndex]}
                            alt={`${name} - ${currentImageIndex + 1}`}
                            className="slider-main-image"
                            onClick={openModal}
                            style={{
                                width: "100%",
                                height: "500px",
                                objectFit: "cover",
                                cursor: "pointer",
                                borderRadius: "12px",
                            }}
                        />

                        {hasMultipleImages && (
                            <>
                                <button
                                    className="slider-nav prev"
                                    onClick={prevImage}
                                    style={{
                                        position: "absolute",
                                        left: "20px",
                                        top: "50%",
                                        transform: "translateY(-50%)",
                                        background: "rgba(0,0,0,0.5)",
                                        color: "white",
                                        border: "none",
                                        borderRadius: "50%",
                                        width: "40px",
                                        height: "40px",
                                        cursor: "pointer",
                                        fontSize: "24px",
                                        display: "flex",
                                        alignItems: "center",
                                        justifyContent: "center",
                                        zIndex: 10,
                                    }}
                                >‹</button>
                                <button
                                    className="slider-nav next"
                                    onClick={nextImage}
                                    style={{
                                        position: "absolute",
                                        right: "20px",
                                        top: "50%",
                                        transform: "translateY(-50%)",
                                        background: "rgba(0,0,0,0.5)",
                                        color: "white",
                                        border: "none",
                                        borderRadius: "50%",
                                        width: "40px",
                                        height: "40px",
                                        cursor: "pointer",
                                        fontSize: "24px",
                                        display: "flex",
                                        alignItems: "center",
                                        justifyContent: "center",
                                        zIndex: 10,
                                    }}
                                >›</button>
                            </>
                        )}
                    </div>

                    {/* Thumbnail strip */}
                    {hasMultipleImages && (
                        <div className="thumbnail-strip" style={{
                            display: "flex",
                            gap: "10px",
                            marginTop: "15px",
                            overflowX: "auto",
                            padding: "5px",
                        }}>
                            {sliderImages.map((img, idx) => (
                                <img
                                    key={idx}
                                    src={img}
                                    alt={`Thumb ${idx + 1}`}
                                    onClick={() => setCurrentImageIndex(idx)}
                                    style={{
                                        width: "80px",
                                        height: "80px",
                                        objectFit: "cover",
                                        borderRadius: "8px",
                                        cursor: "pointer",
                                        border: idx === currentImageIndex ? "3px solid #1e2a4a" : "2px solid transparent",
                                        opacity: idx === currentImageIndex ? 1 : 0.7,
                                    }}
                                />
                            ))}
                        </div>
                    )}
                </div>
            )}

            {/* Lightbox Modal */}
            {isModalOpen && sliderImages.length > 0 && (
                <div
                    className="lightbox-modal"
                    onClick={closeModal}
                    style={{
                        position: "fixed",
                        top: 0,
                        left: 0,
                        right: 0,
                        bottom: 0,
                        background: "rgba(0,0,0,0.95)",
                        zIndex: 2000,
                        display: "flex",
                        alignItems: "center",
                        justifyContent: "center",
                    }}
                >
                    <div
                        className="lightbox-content"
                        onClick={(e) => e.stopPropagation()}
                        style={{
                            position: "relative",
                            maxWidth: "90vw",
                            maxHeight: "90vh",
                        }}
                    >
                        <img
                            src={sliderImages[currentImageIndex]}
                            alt={name}
                            style={{
                                maxWidth: "90vw",
                                maxHeight: "85vh",
                                objectFit: "contain",
                            }}
                        />
                        {hasMultipleImages && (
                            <>
                                <button
                                    onClick={prevImage}
                                    style={{
                                        position: "absolute",
                                        left: "-50px",
                                        top: "50%",
                                        transform: "translateY(-50%)",
                                        background: "rgba(255,255,255,0.3)",
                                        color: "white",
                                        border: "none",
                                        borderRadius: "50%",
                                        width: "50px",
                                        height: "50px",
                                        cursor: "pointer",
                                        fontSize: "30px",
                                        display: "flex",
                                        alignItems: "center",
                                        justifyContent: "center",
                                    }}
                                >‹</button>
                                <button
                                    onClick={nextImage}
                                    style={{
                                        position: "absolute",
                                        right: "-50px",
                                        top: "50%",
                                        transform: "translateY(-50%)",
                                        background: "rgba(255,255,255,0.3)",
                                        color: "white",
                                        border: "none",
                                        borderRadius: "50%",
                                        width: "50px",
                                        height: "50px",
                                        cursor: "pointer",
                                        fontSize: "30px",
                                        display: "flex",
                                        alignItems: "center",
                                        justifyContent: "center",
                                    }}
                                >›</button>
                            </>
                        )}
                        <button
                            onClick={closeModal}
                            style={{
                                position: "absolute",
                                top: "-50px",
                                right: "-50px",
                                background: "rgba(0,0,0,0.5)",
                                color: "white",
                                border: "none",
                                borderRadius: "50%",
                                width: "40px",
                                height: "40px",
                                cursor: "pointer",
                                fontSize: "20px",
                            }}
                        >×</button>
                        <div className="image-counter" style={{
                            position: "absolute",
                            bottom: "-40px",
                            left: "50%",
                            transform: "translateX(-50%)",
                            color: "white",
                            background: "rgba(0,0,0,0.5)",
                            padding: "5px 15px",
                            borderRadius: "20px",
                        }}>
                            {currentImageIndex + 1} / {sliderImages.length}
                        </div>
                    </div>
                </div>
            )}

            {/* Location Details */}
            <div className="location-details">
                <h1 className="heading__locations">{name}</h1>

                {/* Type badge */}
                {location.locationType && TYPE_LABEL[location.locationType] && (
                    <div style={{ marginBottom: "20px" }}>
                        <span style={{
                            background: "#1e2a4a",
                            color: "white",
                            padding: "6px 16px",
                            borderRadius: "20px",
                            fontSize: "14px",
                            display: "inline-block",
                        }}>
                            {TYPE_LABEL[location.locationType]}
                        </span>
                    </div>
                )}

                <div className="location-info-grid" style={{
                    display: "grid",
                    gridTemplateColumns: "repeat(auto-fit, minmax(250px, 1fr))",
                    gap: "20px",
                    marginTop: "30px",
                }}>
                    {location.country && (
                        <div className="info-card" style={{ background: "#f8f9fa", padding: "12px 16px", borderRadius: "10px" }}>
                            <strong>Country:</strong> {location.country}
                        </div>
                    )}
                    {location.region && (
                        <div className="info-card" style={{ background: "#f8f9fa", padding: "12px 16px", borderRadius: "10px" }}>
                            <strong>Region:</strong> {location.region}
                        </div>
                    )}
                    {location.locationType && (
                        <div className="info-card" style={{ background: "#f8f9fa", padding: "12px 16px", borderRadius: "10px" }}>
                            <strong>Type:</strong> {location.locationType}
                        </div>
                    )}
                    {location.climate && (
                        <div className="info-card" style={{ background: "#f8f9fa", padding: "12px 16px", borderRadius: "10px" }}>
                            <strong>Climate:</strong> {location.climate}
                        </div>
                    )}
                    {location.roadQuality && (
                        <div className="info-card" style={{ background: "#f8f9fa", padding: "12px 16px", borderRadius: "10px" }}>
                            <strong>Road Quality:</strong> {location.roadQuality}
                        </div>
                    )}
                    {location.recommended && (
                        <div className="info-card" style={{ background: "#f8f9fa", padding: "12px 16px", borderRadius: "10px", color: "#e74c3c" }}>
                            <strong>⭐ Recommended</strong>
                        </div>
                    )}
                </div>

                {description && (
                    <div className="location-description" style={{ marginTop: "30px" }}>
                        <h3>Description</h3>
                        <p style={{ lineHeight: "1.6", color: "#444", marginTop: "10px" }}>{description}</p>
                    </div>
                )}

                {location.coordinates && (
                    <div className="location-coordinates" style={{ marginTop: "20px" }}>
                        <strong>Coordinates:</strong> {location.coordinates}
                    </div>
                )}

                {location.keywords && location.keywords.length > 0 && (
                    <div className="location-keywords" style={{ marginTop: "20px" }}>
                        <strong>Keywords:</strong>
                        <div style={{ display: "flex", flexWrap: "wrap", gap: "10px", marginTop: "10px" }}>
                            {location.keywords.map((kw, i) => (
                                <span key={i} style={{
                                    background: "#f0f0f0",
                                    padding: "5px 12px",
                                    borderRadius: "20px",
                                    fontSize: "14px",
                                }}>{kw}</span>
                            ))}
                        </div>
                    </div>
                )}

                {location.nearbyCities && location.nearbyCities.length > 0 && (
                    <div className="nearby-cities" style={{ marginTop: "30px" }}>
                        <h3>Nearby Cities</h3>
                        <ul style={{ marginTop: "10px" }}>
                            {location.nearbyCities.map((city, i) => (
                                <li key={i}>{city.name} - {city.distance} km</li>
                            ))}
                        </ul>
                    </div>
                )}
            </div>
        </div>
    );
}

export default LocationDetail;