import React, { createContext, useContext, useState, useEffect } from "react";

const API_BASE = "/registration/api/v1/places";

const LocationContext = createContext(null);

export function LocationProvider({ children }) {
    const [locations, setLocations] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const fetchLocations = async () => {
        setLoading(true);
        setError(null);
        try {
            const res = await fetch(`${API_BASE}/getAllPlacess`, {
                headers: { accept: "*/*", "Accept-Language": "en" },
            });
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            const data = await res.json();
            if (!Array.isArray(data)) { setLocations([]); return; }

            setLocations(data.map(item => ({
                id:             item.id,
                name_en:        item.name      ?? "",
                name_hy:        item.nameHy    ?? "",
                name_ru:        item.nameRu    ?? "",
                description_en: item.description   ?? "",
                description_hy: item.descriptionHy ?? "",
                description_ru: item.descriptionRu ?? "",
                coordinates:    item.coordinates   ?? "",
                locationType:   item.placeTypeId   ?? "",
                climate:        item.climateZoneId ?? "",
                placeRegionId:  item.placeRegionId ?? null,
                recommended:    item.recommended   ?? false,
                coverImage:     item.coverImageUrl ?? "",
                nearbyHotels:     item.nearbyHotels     ?? [],
                nearbyPharmacies: item.nearbyPharmacies ?? [],
                nearbyHospitals:  item.nearbyHospitals  ?? [],
                gasStations:      item.gasStations      ?? [],
                foodPlaces:       item.foodPlaces       ?? [],
            })));
        } catch (err) {
            setError("Could not load places: " + err.message);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => { fetchLocations(); }, []);

    const addLocation = async (location) => {
        try {
            const res = await fetch(`${API_BASE}/createPlace`, {
                method: "POST",
                headers: { "Content-Type": "application/json", accept: "*/*" },
                body: JSON.stringify({
                    name:          location.name_en,
                    namehy:        location.name_hy,
                    nameRu:        location.name_ru,
                    description:   location.description_en,
                    descriptionHy: location.description_hy,
                    descriptionRu: location.description_ru,
                }),
            });
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            await fetchLocations();
        } catch (err) {
            setError("Could not create place: " + err.message);
        }
    };

    const deleteLocation = async (id) => {
        try {
            const res = await fetch(`${API_BASE}/deletePlace/${id}`, { method: "DELETE" });
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            setLocations(prev => prev.filter(l => l.id !== id));
        } catch (err) {
            setError("Could not delete place: " + err.message);
        }
    };

    const updateLocation = async (updated) => {
        try {
            const res = await fetch(`${API_BASE}/updatePlace/${updated.id}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    name:          updated.name_en,
                    namehy:        updated.name_hy,
                    nameRu:        updated.name_ru,
                    description:   updated.description_en,
                    descriptionHy: updated.description_hy,
                    descriptionRu: updated.description_ru,
                }),
            });
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            await fetchLocations();
        } catch (err) {
            setError("Could not update place: " + err.message);
        }
    };

    return (
        <LocationContext.Provider value={{
            locations,
            loading,
            error,
            addLocation,
            deleteLocation,
            updateLocation,
            refetch: fetchLocations,
        }}>
            {children}
        </LocationContext.Provider>
    );
}

export function useLocations() {
    return useContext(LocationContext);
}