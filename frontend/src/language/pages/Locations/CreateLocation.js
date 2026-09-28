import "../../../App.css";
import "../../../hover.css";
import "./index.css";
import "../../../fonts.css";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useLocations } from "./LocationContext";
import { useRegions } from "../Regions/RegionContext";
import { useHotels } from "../Hotel/HotelContext";
import { usePharmacies } from "../Pharmacy/PharmacyContext";
import { useGasStations } from "../GasStation/GasStationContext";
import { useRestaurants } from "../Restaurant/RestaurantContext";

const REGION_KEY = { hy: "name_hy", en: "name_en", ru: "name_ru" };

const LOCATION_TYPES = [
    { value: "", label: "— Select type —" },
    { value: "mountains", label: "Mountains" },
    { value: "bodiesOfWater", label: "Bodies of Water" },
    { value: "historicalPlace", label: "Historical Place" },
    { value: "cities", label: "Cities" },
    { value: "forests", label: "Forests" },
    { value: "publicPlaces", label: "Public Places" },
    { value: "venue", label: "Venue / Hotel" },
    { value: "viewPoint", label: "View Point" },
    { value: "deserts", label: "Deserts" },
];

const CLIMATES = [
    { value: "", label: "— Select climate —" },
    { value: "temperate", label: "Temperate" },
    { value: "continental", label: "Continental" },
    { value: "arid", label: "Arid" },
    { value: "alpine", label: "Alpine" },
];

const ROAD_QUALITIES = [
    { value: "", label: "— Select —" },
    { value: "good", label: "Good" },
    { value: "moderate", label: "Moderate" },
    { value: "difficult", label: "Difficult" },
    { value: "rocky", label: "Rocky" },
];

function CreateLocation() {
    const [activeTab, setActiveTab] = useState("hy");
    const { addLocation } = useLocations(); // ID is handled by backend or nextId in context
    const { regions } = useRegions();
    const { hotels } = useHotels();
    const { pharmacies } = usePharmacies();
    const {gasStations} = useGasStations();
    const { restaurants } = useRestaurants();
    const navigate = useNavigate();

    // Per-tab: name and description
    const [tabStates, setTabStates] = useState({
        hy: { name: "", description: "", coverImage: null, draggingCover: false, galleryImages: [], draggingGallery: false, panoramaImages: [], draggingPanorama: false },
        en: { name: "", description: "", coverImage: null, draggingCover: false, galleryImages: [], draggingGallery: false, panoramaImages: [], draggingPanorama: false },
        ru: { name: "", description: "", coverImage: null, draggingCover: false, galleryImages: [], draggingGallery: false, panoramaImages: [], draggingPanorama: false },
    });

    // Shared fields - these will now be visible on all tabs
    const [regionId, setRegionId] = useState("");
    const [locationType, setLocationType] = useState("");
    const [climate, setClimate] = useState("");
    const [coordinates, setCoordinates] = useState("");
    const [country, setCountry] = useState("");
    const [roadQuality, setRoadQuality] = useState("");
    const [recommended, setRecommended] = useState(false);
    const [keywords, setKeywords] = useState("");
    const [nearbyHotels, setNearbyHotels] = useState([{ id: "", distance: "" }]);
    const [nearbyRestaurants, setNearbyRestaurants] = useState([{ id: "", distance: "" }]);
    const [nearbyCities, setNearbyCities] = useState([{ name: "", distance: "" }]);
    const [nearbyGasStations, setNearbyGasStations] = useState([{ name: "", distance: "" }]);
    const [nearbyHospitals, setNearbyHospitals] = useState([{ name: "", distance: "" }]);
    const [nearbyPharmacies, setNearbyPharmacies] = useState([{ name: "", distance: "" }]);
    const [countryId, setCountryId] = useState("");
    const [nearbyFoodPlaces, setNearbyFoodPlaces] = useState([{ id: "", distance: "" }]);

    const updateTab = (tab, updates) =>
        setTabStates(prev => ({ ...prev, [tab]: { ...prev[tab], ...updates } }));

    const handleImageFile = (tab, field, file) => {
        if (file) {
            const reader = new FileReader();
            reader.onloadend = () => updateTab(tab, { [field]: reader.result });
            reader.readAsDataURL(file);
        }
    };

    const handleGalleryFile = (tab, field, file) => {
        if (file) {
            const reader = new FileReader();
            reader.onloadend = () => setTabStates(prev => ({
                ...prev,
                [tab]: { ...prev[tab], [field]: [...prev[tab][field], reader.result] }
            }));
            reader.readAsDataURL(file);
        }
    };

    const updateNearby = (setter, index, key, value) =>
        setter(prev => prev.map((item, i) => i === index ? { ...item, [key]: value } : item));

    const handleSubmit = (e) => {
        e.preventDefault();

        // Data must match the JSON schema keys exactly
        const locationData = {
            name: tabStates[activeTab].name, // Usually the backend handles localization or you send the active one
            coordinates: coordinates,
            placeTypeId: locationType,       // Ensure state stores the GUID
            countryId: countryId,           // Ensure you have a state for country GUID
            climateZoneId: climate,          // Ensure state stores the GUID

            // Nearby IDs (Single GUIDs based on your schema)
            nearbyHotelsId: nearbyHotels[0]?.id || null,
            nearbyHospitalsId: nearbyHospitals[0]?.id || null,
            nearbyPharmaciesId: nearbyPharmacies[0]?.id || null,
            gasStationsId: nearbyGasStations[0]?.id || null,
            foodPlacesId: nearbyFoodPlaces[0]?.id || null,

            // Distances
            distanceFromHotel: parseFloat(nearbyHotels[0]?.distance) || 0,
            distanceFromHospital: parseFloat(nearbyHospitals[0]?.distance) || 0,
            distanceFromPharmacy: parseFloat(nearbyPharmacies[0]?.distance) || 0,
            distanceFromGasStation: parseFloat(nearbyGasStations[0]?.distance) || 0,
            distanceFromFoodPlace: parseFloat(nearbyFoodPlaces[0]?.distance) || 0,

            // Media and Meta
            photosId: tabStates[activeTab].galleryImages, // Needs to be array of GUIDs
            photo360Id: tabStates[activeTab].panoramaImages[0] || null,
            description: tabStates[activeTab].description,
            roadQualityId: roadQuality,      // Ensure state stores the GUID
            placeRegionId: regionId,         // Ensure state stores the GUID
            recommended: recommended,

            // Arrays
            transportTypeId: [] // Must be an array of GUIDs
        };

        addLocation(locationData);
        navigate("/Locations");
    };

    const tabs = [
        { key: "hy", label: "Armenian" },
        { key: "en", label: "English" },
        { key: "ru", label: "Russian" },
    ];

    const UploadIcon = () => (
        <svg width="110" height="110" viewBox="0 0 110 110" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M22.9167 91.6667H87.0834V82.5H22.9167V91.6667ZM22.9167 45.8333H41.2501V73.3333H68.7501V45.8333H87.0834L55.0001 13.75L22.9167 45.8333Z" fill="black"/>
        </svg>
    );

    const renderForm = (tab) => {
        const state = tabStates[tab];
        const regionKey = REGION_KEY[tab];

        return (
            <div>
                {/* Photos */}
                <div className="create-hotel__photos" style={{ gap: 40 }}>
                    <div className="create-hotel__photo-block">
                        <span className="create-hotel__photo-label">Main cover photo *</span>
                        <label htmlFor={`cover-${tab}`}
                               className={`create-hotel__upload-area ${state.draggingCover ? "dragging" : ""}`}
                               onDragOver={(e) => { e.preventDefault(); updateTab(tab, { draggingCover: true }); }}
                               onDragLeave={() => updateTab(tab, { draggingCover: false })}
                               onDrop={(e) => { e.preventDefault(); updateTab(tab, { draggingCover: false }); handleImageFile(tab, "coverImage", e.dataTransfer.files[0]); }}>
                            {state.coverImage
                                ? <img src={state.coverImage} alt="cover" className="create-hotel__preview-img" />
                                : <UploadIcon />}
                        </label>
                        <input className="dn" id={`cover-${tab}`} type="file" accept="image/*"
                               onChange={e => handleImageFile(tab, "coverImage", e.target.files[0])} />
                    </div>

                    <div className="create-hotel__photo-block">
                        <span className="create-hotel__photo-label">Photo gallery</span>
                        <label htmlFor={`gallery-${tab}`}
                               className={`create-hotel__upload-area ${state.draggingGallery ? "dragging" : ""}`}
                               onDragOver={(e) => { e.preventDefault(); updateTab(tab, { draggingGallery: true }); }}
                               onDragLeave={() => updateTab(tab, { draggingGallery: false })}
                               onDrop={(e) => { e.preventDefault(); updateTab(tab, { draggingGallery: false }); Array.from(e.dataTransfer.files).forEach(f => handleGalleryFile(tab, "galleryImages", f)); }}>
                            {state.galleryImages.length > 0
                                ? <div className="create-hotel__gallery-grid">{state.galleryImages.map((img, i) => <img key={i} src={img} alt="" className="create-hotel__gallery-thumb" />)}</div>
                                : <UploadIcon />}
                        </label>
                        <input className="dn" id={`gallery-${tab}`} type="file" accept="image/*" multiple
                               onChange={e => Array.from(e.target.files).forEach(f => handleGalleryFile(tab, "galleryImages", f))} />
                    </div>
                </div>

                <div className="create-hotel__fields">
                    <div className="create-hotel__field-row">
                        <label className="create-hotel__field-label" htmlFor={`name-${tab}`}>Name *</label>
                        <input className="create-hotel__field-input" id={`name-${tab}`} type="text"
                               value={state.name} onChange={e => updateTab(tab, {name: e.target.value})}/>
                    </div>

                    <div className="create-hotel__field-row">
                        <label className="create-hotel__field-label" htmlFor={`region-${tab}`}>Region</label>
                        <select className="create-hotel__field-input" id={`region-${tab}`}
                                value={regionId} onChange={e => setRegionId(e.target.value)}>
                            <option value="">— Select region —</option>
                            {regions.map(r => <option key={r.id} value={String(r.id)}>{r[regionKey]}</option>)}
                        </select>
                    </div>

                    <div className="create-hotel__field-row">
                        <label className="create-hotel__field-label" htmlFor={`desc-${tab}`}>Description</label>
                        <input className="create-hotel__field-input" id={`desc-${tab}`} type="text"
                               value={state.description} onChange={e => updateTab(tab, {description: e.target.value})}/>
                    </div>

                    {/* Shared fields - Removed the Armenian-only check so they show on all tabs */}
                    <div className="create-hotel__field-row">
                        <label className="create-hotel__field-label">Loc. type</label>
                        <select className="create-hotel__field-input" value={locationType}
                                onChange={e => setLocationType(e.target.value)}>
                            {LOCATION_TYPES.map(t => <option key={t.value} value={t.value}>{t.label}</option>)}
                        </select>
                    </div>
                    <div className="create-hotel__field-row">
                        <label className="create-hotel__field-label">Climate</label>
                        <select className="create-hotel__field-input" value={climate}
                                onChange={e => setClimate(e.target.value)}>
                            {CLIMATES.map(c => <option key={c.value} value={c.value}>{c.label}</option>)}
                        </select>
                    </div>
                    <div className="create-hotel__field-row">
                        <label className="create-hotel__field-label">Coordinates</label>
                        <input className="create-hotel__field-input" type="text" placeholder="lat, lng"
                               value={coordinates} onChange={e => setCoordinates(e.target.value)}/>
                    </div>
                    <div className="create-hotel__field-row">
                        <label className="create-hotel__field-label">Country</label>
                        <input className="create-hotel__field-input" type="text"
                               value={country} onChange={e => setCountry(e.target.value)}/>
                    </div>
                    <div className="create-hotel__field-row">
                        <label className="create-hotel__field-label">Road quality</label>
                        <select className="create-hotel__field-input" value={roadQuality}
                                onChange={e => setRoadQuality(e.target.value)}>
                            {ROAD_QUALITIES.map(r => <option key={r.value} value={r.value}>{r.label}</option>)}
                        </select>
                    </div>
                    <div className="create-hotel__field-row">
                        <label className="create-hotel__field-label">Keywords</label>
                        <input className="create-hotel__field-input" type="text" placeholder="comma separated"
                               value={keywords} onChange={e => setKeywords(e.target.value)}/>
                    </div>
                    <div className="create-hotel__field-row">
                        <label className="create-hotel__field-label">Recommended</label>
                        <div className="create-hotel__field-input location__checkbox-row">
                            <input type="checkbox" id="recommended" checked={recommended}
                                   onChange={e => setRecommended(e.target.checked)}
                                   style={{width: 24, height: 24, cursor: "pointer"}}/>
                            <label htmlFor="recommended" style={{fontSize: 18, cursor: "pointer"}}>Show in
                                Recommendations</label>
                        </div>
                    </div>

                    {/* Nearby services */}
                    <div className="create-hotel__field-row location__nearby-row">
                        <label className="create-hotel__field-label">Nearby cities</label>
                        <div className="location__nearby-inputs">
                            {nearbyCities.map((item, i) => (
                                <div key={i} className="location__nearby-item">
                                    <input className="create-hotel__field-input" type="text" placeholder="City name"
                                           value={item.name}
                                           onChange={e => updateNearby(setNearbyCities, i, "name", e.target.value)}/>
                                    <input className="create-hotel__field-input location__distance-input" type="text"
                                           placeholder="km"
                                           value={item.distance}
                                           onChange={e => updateNearby(setNearbyCities, i, "distance", e.target.value)}/>
                                </div>
                            ))}
                            <button type="button" className="location__add-btn"
                                    onClick={() => setNearbyCities(prev => [...prev, {name: "", distance: ""}])}>+
                            </button>
                        </div>
                    </div>

                    <div className="create-hotel__field-row location__nearby-row">
                        <label className="create-hotel__field-label">Nearby hotels</label>
                        <div className="location__nearby-inputs">
                            {nearbyHotels.map((item, i) => (
                                <div key={i} className="location__nearby-item">
                                    <select className="create-hotel__field-input" value={item.id}
                                            onChange={e => updateNearby(setNearbyHotels, i, "id", e.target.value)}>
                                        <option value="">— choose —</option>
                                        {hotels.map(h => <option key={h.id} value={h.id}>{h.name_en}</option>)}
                                    </select>
                                    <input className="create-hotel__field-input location__distance-input" type="text"
                                           placeholder="km"
                                           value={item.distance}
                                           onChange={e => updateNearby(setNearbyHotels, i, "distance", e.target.value)}/>
                                </div>
                            ))}
                            <button type="button" className="location__add-btn"
                                    onClick={() => setNearbyHotels(prev => [...prev, {id: "", distance: ""}])}>+
                            </button>
                        </div>
                    </div>

                    <div className="create-hotel__field-row location__nearby-row">
                        <label className="create-hotel__field-label">Gas stations</label>
                        <div className="location__nearby-inputs">
                            {nearbyGasStations.map((item, i) => (
                                <div key={i} className="location__nearby-item">
                                    <select className="create-hotel__field-input" value={item.id}
                                            onChange={e => updateNearby(setNearbyGasStations, i, "id", e.target.value)}>
                                        <option value="">— choose —</option>
                                        {gasStations.map(g => <option key={g.id} value={g.id}>{g.name_en}</option>)}
                                    </select>
                                    <input className="create-hotel__field-input location__distance-input" type="text"
                                           placeholder="km"
                                           value={item.distance}
                                           onChange={e => updateNearby(setNearbyGasStations, i, "distance", e.target.value)}/>
                                </div>
                            ))}
                            <button type="button" className="location__add-btn"
                                    onClick={() => setNearbyGasStations(prev => [...prev, {id: "", distance: ""}])}>+
                            </button>
                        </div>
                    </div>
                    <div className="create-hotel__field-row location__nearby-row">
                        <label className="create-hotel__field-label">Nearby pharmacies</label>
                        <div className="location__nearby-inputs">
                            {nearbyPharmacies.map((item, i) => (
                                <div key={i} className="location__nearby-item">
                                    <select className="create-hotel__field-input" value={item.id}
                                            onChange={e => updateNearby(setNearbyPharmacies(), i, "id", e.target.value)}>
                                        <option value="">— choose —</option>
                                        {pharmacies.map(h => <option key={h.id} value={h.id}>{h.name_en}</option>)}
                                    </select>
                                    <input className="create-hotel__field-input location__distance-input" type="text"
                                           placeholder="km"
                                           value={item.distance}
                                           onChange={e => updateNearby(setNearbyPharmacies(), i, "distance", e.target.value)}/>
                                </div>
                            ))}
                            <button type="button" className="location__add-btn"
                                    onClick={() => setNearbyPharmacies(prev => [...prev, {id: "", distance: ""}])}>+
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        );
    };

    return (
        <div>
            <div className="container__locations">
                <div className="heading__locations">Create Location</div>
                <div className="tabs">
                    {tabs.map(({key, label}) => (
                        <button key={key} className={activeTab === key ? "active" : ""}
                                onClick={() => setActiveTab(key)}>
                            {label}
                        </button>
                    ))}
                </div>
                <form onSubmit={handleSubmit}>
                    <div className="tab-content">{renderForm(activeTab)}</div>
                    <div className="create-hotel__submit-row">
                        <button type="submit" className="tab-content__more-btn">Save</button>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default CreateLocation;