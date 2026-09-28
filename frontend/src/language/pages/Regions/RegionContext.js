import React, { createContext, useContext, useState, useEffect } from "react";

const API_BASE = "/registration/api/v1/region";

const defaultValue = {
    regions: [],
    loading: false,
    error: null,
    addRegion: () => {},
    deleteRegion: () => {},
    updateRegion: () => {},
    nextId: () => 1,
    refetch: () => {},
};

const RegionContext = createContext(defaultValue);

export function RegionProvider({ children }) {
    const [regions, setRegions] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const fetchRegions = async () => {
        setLoading(true);
        setError(null);
        try {
            const res = await fetch(`${API_BASE}/getAllRegionss`, {
                headers: { "accept": "*/*", "Accept-Language": "en" },
            });
            if (!res.ok) throw new Error(`Server error: ${res.status}`);
            const data = await res.json();
            // Map API shape → internal shape
            const mapped = data.map(r => ({
                id: r.id,
                name_en: r.name ?? "",
                name_hy: r.nameHy ?? "",
                name_ru: r.nameRu ?? "",
                countryId: r.countryId,
            }));
            setRegions(mapped);
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchRegions();
    }, []);

    const addRegion = async (region) => {
        try {
            const res = await fetch(`${API_BASE}/createRegion`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Accept-Language": "en",
                },
                body: JSON.stringify({
                    name: region.name_en,
                    nameHy: region.name_hy,
                    nameRu: region.name_ru,
                    countryId: region.countryId,
                }),
            });
            if (!res.ok) throw new Error(`Server error: ${res.status}`);
            await fetchRegions();
        } catch (err) {
            setError(err.message);
        }
    };

    const deleteRegion = async (id) => {
        try {
            const res = await fetch(`${API_BASE}/deleteRegion/${id}`, {
                method: "DELETE",
                headers: { "Accept-Language": "en" },
            });
            if (!res.ok) throw new Error(`Server error: ${res.status}`);
            setRegions(prev => prev.filter(r => r.id !== id));
        } catch (err) {
            setError(err.message);
        }
    };

    const updateRegion = async (updated) => {
        try {
            const res = await fetch(`${API_BASE}/updateRegion/${updated.id}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                    "Accept-Language": "en",
                },
                body: JSON.stringify(updated),
            });
            if (!res.ok) throw new Error(`Server error: ${res.status}`);
            await fetchRegions();
        } catch (err) {
            setError(err.message);
        }
    };

    const nextId = () => Math.max(0, ...regions.map(r => r.id)) + 1;

    return (
        <RegionContext.Provider value={{ regions, loading, error, addRegion, deleteRegion, updateRegion, nextId, refetch: fetchRegions }}>
            {children}
        </RegionContext.Provider>
    );
}

export function useRegions() {
    return useContext(RegionContext);
}