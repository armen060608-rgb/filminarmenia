import React, { createContext, useContext, useState, useEffect } from "react";

const API_BASE = "/registration/api/v1/climate_zones";

const ClimateZoneContext = createContext({
    climateZones: [],
    loading: false,
    error: null,
    addClimateZone: () => {},
    deleteClimateZone: () => {},
    updateClimateZone: () => {},
    refetch: () => {},
});

export function ClimateZoneProvider({ children }) {
    const [climateZones, setClimateZones] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const fetchClimateZones = async () => {
        setLoading(true);
        setError(null);
        try {
            const res = await fetch(`${API_BASE}/getAllClimateZoness`, {
                headers: { "accept": "*/*", "Accept-Language": "en" },
            });
            if (!res.ok) throw new Error(`Server error: ${res.status}`);
            const data = await res.json();
            const mapped = data.map(r => ({
                id: r.id,
                name_en: r.name ?? "",
                name_hy: r.nameHy ?? "",
                name_ru: r.nameRu ?? "",
            }));
            setClimateZones(mapped);
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchClimateZones();
    }, []);

    const addClimateZone = async (zone) => {
        try {
            const res = await fetch(`${API_BASE}/createClimateZone`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Accept-Language": "en",
                },
                body: JSON.stringify({
                    name: zone.name_en,
                    nameHy: zone.name_hy,
                    nameRu: zone.name_ru,
                }),
            });
            if (!res.ok) throw new Error(`Server error: ${res.status}`);
            await fetchClimateZones();
        } catch (err) {
            setError(err.message);
        }
    };

    const deleteClimateZone = async (id) => {
        try {
            const res = await fetch(`${API_BASE}/deleteClimateZone/${id}`, {
                method: "DELETE",
                headers: { "Accept-Language": "en" },
            });
            if (!res.ok) throw new Error(`Server error: ${res.status}`);
            setClimateZones(prev => prev.filter(r => r.id !== id));
        } catch (err) {
            setError(err.message);
        }
    };

    const updateClimateZone = async (updated) => {
        try {
            const res = await fetch(`${API_BASE}/updateClimateZone/${updated.id}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                    "Accept-Language": "en",
                },
                body: JSON.stringify({
                    name: updated.name_en,
                    nameHy: updated.name_hy,
                    nameRu: updated.name_ru,
                }),
            });
            if (!res.ok) throw new Error(`Server error: ${res.status}`);
            await fetchClimateZones();
        } catch (err) {
            setError(err.message);
        }
    };

    return (
        <ClimateZoneContext.Provider value={{ climateZones, loading, error, addClimateZone, deleteClimateZone, updateClimateZone, refetch: fetchClimateZones }}>
            {children}
        </ClimateZoneContext.Provider>
    );
}

export function useClimateZones() {
    return useContext(ClimateZoneContext);
}