import React, { createContext, useContext, useState, useEffect } from "react";

const API_BASE = "/registration/api/v1/place_types";

const PlaceTypeContext = createContext({
    placeTypes: [],
    loading: false,
    error: null,
    addPlaceType: () => {},
    deletePlaceType: () => {},
    updatePlaceType: () => {},
    refetch: () => {},
});

export function PlaceTypeProvider({ children }) {
    const [placeTypes, setPlaceTypes] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const fetchPlaceTypes = async () => {
        setLoading(true);
        setError(null);
        try {
            const res = await fetch(`${API_BASE}/getAllPlaceTypess`, {
                headers: { "accept": "*/*", "Accept-Language": "en" },
            });
            if (!res.ok) throw new Error(`Server error: ${res.status}`);
            const data = await res.json();
            const mapped = data.map(r => ({
                id: r.id,
                name_en: r.name ?? "",
                name_hy: r.nameHy ?? "",
                name_ru: r.nameRu ?? "",
                description_en: r.description ?? "",
                description_hy: r.descriptionHy ?? "",
                description_ru: r.descriptionRu ?? "",
            }));
            setPlaceTypes(mapped);
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchPlaceTypes();
    }, []);

    const addPlaceType = async (placeType) => {
        try {
            const res = await fetch(`${API_BASE}/createPlaceType`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Accept-Language": "en",
                },
                body: JSON.stringify({
                    name: placeType.name_en,
                    nameHy: placeType.name_hy,
                    nameRu: placeType.name_ru,
                    description: placeType.description_en,
                    descriptionHy: placeType.description_hy,
                    descriptionRu: placeType.description_ru,
                }),
            });
            if (!res.ok) throw new Error(`Server error: ${res.status}`);
            await fetchPlaceTypes();
        } catch (err) {
            setError(err.message);
        }
    };

    const deletePlaceType = async (id) => {
        try {
            const res = await fetch(`${API_BASE}/deletePlaceType/${id}`, {
                method: "DELETE",
                headers: { "Accept-Language": "en" },
            });
            if (!res.ok) throw new Error(`Server error: ${res.status}`);
            setPlaceTypes(prev => prev.filter(r => r.id !== id));
        } catch (err) {
            setError(err.message);
        }
    };

    const updatePlaceType = async (updated) => {
        try {
            const res = await fetch(`${API_BASE}/updatePlaceType/${updated.id}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                    "Accept-Language": "en",
                },
                body: JSON.stringify({
                    name: updated.name_en,
                    nameHy: updated.name_hy,
                    nameRu: updated.name_ru,
                    description: updated.description_en,
                    descriptionHy: updated.description_hy,
                    descriptionRu: updated.description_ru,
                }),
            });
            if (!res.ok) throw new Error(`Server error: ${res.status}`);
            await fetchPlaceTypes();
        } catch (err) {
            setError(err.message);
        }
    };

    return (
        <PlaceTypeContext.Provider value={{ placeTypes, loading, error, addPlaceType, deletePlaceType, updatePlaceType, refetch: fetchPlaceTypes }}>
            {children}
        </PlaceTypeContext.Provider>
    );
}

export function usePlaceTypes() {
    return useContext(PlaceTypeContext);
}