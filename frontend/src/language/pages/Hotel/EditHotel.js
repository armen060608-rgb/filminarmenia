import "../../../App.css"
import "../../../hover.css";
import "./index.css";
import "../../../fonts.css";
import React, { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useHotels } from "./HotelContext";
import { useRegions } from "../Regions/RegionContext";

function EditHotel() {
    const { id } = useParams();
    const { hotels, updateHotel } = useHotels();
    const { regions } = useRegions();
    const navigate = useNavigate();
    const [submitting, setSubmitting] = useState(false);

    const hotel = hotels.find(h => String(h.id) === String(id));
    const [activeTab, setActiveTab] = useState("hy");

    const [tabStates, setTabStates] = useState({
        hy: { name: hotel?.name_hy ?? "", description: hotel?.description_hy ?? "", coverImage: hotel?.images?.[0] ?? null, coverImageFile: null, draggingCover: false, galleryImages: hotel?.images ?? [], draggingGallery: false },
        en: { name: hotel?.name_en ?? "", description: hotel?.description_en ?? "", coverImage: hotel?.images?.[0] ?? null, coverImageFile: null, draggingCover: false, galleryImages: hotel?.images ?? [], draggingGallery: false },
        ru: { name: hotel?.name_ru ?? "", description: hotel?.description_ru ?? "", coverImage: hotel?.images?.[0] ?? null, coverImageFile: null, draggingCover: false, galleryImages: hotel?.images ?? [], draggingGallery: false },
    });

    const [regionId, setRegionId] = useState(hotel?.regionId ?? "");
    const [stars, setStars] = useState(hotel?.stars ?? 0);

    if (!hotel) {
        return <div className="container__hotels"><div className="heading__hotels">Hotel not found</div></div>;
    }

    const updateTab = (tab, updates) =>
        setTabStates(prev => ({ ...prev, [tab]: { ...prev[tab], ...updates } }));

    const handleCoverFile = (tab, file) => {
        if (file) {
            const reader = new FileReader();
            reader.onloadend = () => updateTab(tab, { coverImage: reader.result, coverImageFile: file });
            reader.readAsDataURL(file);
        }
    };

    const handleGalleryFile = (tab, file) => {
        if (file) {
            const reader = new FileReader();
            reader.onloadend = () => setTabStates(prev => ({
                ...prev,
                [tab]: { ...prev[tab], galleryImages: [...prev[tab].galleryImages, reader.result] }
            }));
            reader.readAsDataURL(file);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setSubmitting(true);
        await updateHotel({
            id: hotel.id,
            name_en: tabStates.en.name.trim(),
            name_hy: tabStates.hy.name.trim(),
            name_ru: tabStates.ru.name.trim(),
            description_en: tabStates.en.description.trim(),
            description_hy: tabStates.hy.description.trim(),
            description_ru: tabStates.ru.description.trim(),
            regionId,
            stars: parseInt(stars) || 0,
        });
        setSubmitting(false);
        navigate("/Hotel");
    };

    const tabs = [
        { key: "hy", label: "Armenian" },
        { key: "en", label: "English" },
        { key: "ru", label: "Russian" },
    ];

    const renderForm = (tab) => {
        const state = tabStates[tab];
        return (
            <div className="content">
                <form onSubmit={handleSubmit} className="create-hotel__form">
                    <div className="create-hotel__photos">
                        <div className="create-hotel__photo-block">
                            <span className="create-hotel__photo-label">Main cover photo</span>
                            <label
                                htmlFor={`cover-upload-${tab}`}
                                className={`create-hotel__upload-area ${state.draggingCover ? "dragging" : ""}`}
                                onDragOver={(e) => { e.preventDefault(); updateTab(tab, { draggingCover: true }); }}
                                onDragLeave={() => updateTab(tab, { draggingCover: false })}
                                onDrop={(e) => { e.preventDefault(); updateTab(tab, { draggingCover: false }); handleCoverFile(tab, e.dataTransfer.files[0]); }}
                            >
                                {state.coverImage
                                    ? <img src={state.coverImage} alt="Cover preview" className="create-hotel__preview-img" />
                                    : <svg width="110" height="110" viewBox="0 0 110 110" fill="none" xmlns="http://www.w3.org/2000/svg"><path d="M22.9167 91.6667H87.0834V82.5H22.9167V91.6667ZM22.9167 45.8333H41.2501V73.3333H68.7501V45.8333H87.0834L55.0001 13.75L22.9167 45.8333Z" fill="black"/></svg>
                                }
                            </label>
                            <input className="dn" id={`cover-upload-${tab}`} name={`cover-upload-${tab}`} type="file" accept="image/*" onChange={(e) => handleCoverFile(tab, e.target.files[0])} />
                        </div>

                        <div className="create-hotel__photo-block">
                            <span className="create-hotel__photo-label">Photo gallery</span>
                            <label
                                htmlFor={`gallery-upload-${tab}`}
                                className={`create-hotel__upload-area ${state.draggingGallery ? "dragging" : ""}`}
                                onDragOver={(e) => { e.preventDefault(); updateTab(tab, { draggingGallery: true }); }}
                                onDragLeave={() => updateTab(tab, { draggingGallery: false })}
                                onDrop={(e) => { e.preventDefault(); updateTab(tab, { draggingGallery: false }); Array.from(e.dataTransfer.files).forEach(f => handleGalleryFile(tab, f)); }}
                            >
                                {state.galleryImages.length > 0
                                    ? <div className="create-hotel__gallery-grid">{state.galleryImages.map((img, i) => <img key={i} src={img} alt={`Gallery ${i}`} className="create-hotel__gallery-thumb" />)}</div>
                                    : <svg width="110" height="110" viewBox="0 0 110 110" fill="none" xmlns="http://www.w3.org/2000/svg"><path d="M22.9167 91.6667H87.0834V82.5H22.9167V91.6667ZM22.9167 45.8333H41.2501V73.3333H68.7501V45.8333H87.0834L55.0001 13.75L22.9167 45.8333Z" fill="black"/></svg>
                                }
                            </label>
                            <input className="dn" id={`gallery-upload-${tab}`} name={`gallery-upload-${tab}`} type="file" accept="image/*" multiple onChange={(e) => Array.from(e.target.files).forEach(f => handleGalleryFile(tab, f))} />
                        </div>
                    </div>

                    <div className="create-hotel__fields">
                        <div className="create-hotel__field-row">
                            <label className="create-hotel__field-label" htmlFor={`name-${tab}`}>Name</label>
                            <input
                                className="create-hotel__field-input"
                                id={`name-${tab}`}
                                name={`name-${tab}`}
                                type="text"
                                value={String(state.name ?? "")}
                                onChange={(e) => updateTab(tab, { name: e.target.value })}
                            />
                        </div>

                        <div className="create-hotel__field-row">
                            <label className="create-hotel__field-label" htmlFor={`description-${tab}`}>Description</label>
                            <input
                                className="create-hotel__field-input"
                                id={`description-${tab}`}
                                name={`description-${tab}`}
                                type="text"
                                value={String(state.description ?? "")}
                                onChange={(e) => updateTab(tab, { description: e.target.value })}
                            />
                        </div>

                        {tab === "hy" && (
                            <>
                                <div className="create-hotel__field-row">
                                    <label className="create-hotel__field-label" htmlFor="region-select">Region</label>
                                    <select
                                        className="create-hotel__field-input"
                                        id="region-select"
                                        name="region-select"
                                        value={regionId}
                                        onChange={(e) => setRegionId(e.target.value)}
                                    >
                                        <option value="">— Select region —</option>
                                        {regions.map(r => (
                                            <option key={r.id} value={r.id}>
                                                {r.name_hy || r.name_en}
                                            </option>
                                        ))}
                                    </select>
                                </div>

                                <div className="create-hotel__field-row">
                                    <label className="create-hotel__field-label" htmlFor="stars-input">Stars</label>
                                    <input
                                        className="create-hotel__field-input"
                                        id="stars-input"
                                        name="stars-input"
                                        type="number"
                                        min="0"
                                        max="5"
                                        value={String(stars)}
                                        onChange={(e) => setStars(e.target.value)}
                                    />
                                </div>
                            </>
                        )}
                    </div>

                    <div className="create-hotel__submit-row">
                        <button type="submit" className="tab-content__more-btn" disabled={submitting}>
                            {submitting ? "Saving..." : "Save"}
                        </button>
                    </div>
                </form>
            </div>
        );
    };

    return (
        <div>
            <div className="container__hotels">
                <div className="heading__hotels">Edit Hotel</div>
                <div className="tabs">
                    {tabs.map(({ key, label }) => (
                        <button key={key} className={activeTab === key ? "active" : ""} onClick={() => setActiveTab(key)}>
                            {label}
                        </button>
                    ))}
                </div>
                <div className="tab-content">
                    {renderForm(activeTab)}
                </div>
            </div>
        </div>
    );
}

export default EditHotel;