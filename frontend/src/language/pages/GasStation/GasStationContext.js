import React, { createContext, useContext, useState, useEffect } from "react";

const API_BASE = "/registration/api/v1/gas_station";

const GasStationContext = createContext(null);

export function GasStationProvider({ children }) {
    const [gasStations, setGasStations] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const fetchGasStations = async () => {
        setLoading(true);
        setError(null);
        try {
            const res = await fetch(`${API_BASE}/getGasStationss`, {
                headers: { accept: "*/*", "Accept-Language": "en" },
            });
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            const data = await res.json();
            if (!Array.isArray(data)) { setGasStations([]); return; }
            setGasStations(data.map(item => ({
                id: item.id,
                name_en: item.name ?? "",
                name_hy: item.nameHy ?? "",
                name_ru: item.nameRu ?? "",
                description_en: item.description ?? "",
                description_hy: item.descriptionHy ?? "",
                description_ru: item.descriptionRu ?? "",
                regionId: item.regionId ?? "",
                address: item.address ?? "",
                phone: item.phone ?? "",
            })));
        } catch (err) { setError("Could not load gas stations: " + err.message); }
        finally { setLoading(false); }
    };

    useEffect(() => { fetchGasStations(); }, []);

    const addGasStation = async (gs) => {
        try {
            const res = await fetch(`${API_BASE}/createGasStation`, {
                method: "POST",
                headers: { "Content-Type": "application/json", accept: "*/*" },
                body: JSON.stringify({
                    name: gs.name_en, nameHy: gs.name_hy, nameRu: gs.name_ru,
                    description: gs.description_en, descriptionHy: gs.description_hy, descriptionRu: gs.description_ru,
                    address: gs.address, phone: gs.phone,
                }),
            });
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            await fetchGasStations();
        } catch (err) { setError("Could not create gas station: " + err.message); }
    };

    const deleteGasStation = async (id) => {
        try {
            const res = await fetch(`${API_BASE}/deleteGasStation/${id}`, { method: "DELETE" });
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            setGasStations(prev => prev.filter(g => g.id !== id));
        } catch (err) { setError("Could not delete gas station: " + err.message); }
    };

    const updateGasStation = async (updated) => {
        try {
            const res = await fetch(`${API_BASE}/updateGasStation/${updated.id}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    name: updated.name_en, nameHy: updated.name_hy, nameRu: updated.name_ru,
                    description: updated.description_en, descriptionHy: updated.description_hy, descriptionRu: updated.description_ru,
                    address: updated.address, phone: updated.phone,
                }),
            });
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            await fetchGasStations();
        } catch (err) { setError("Could not update gas station: " + err.message); }
    };

    return (
        <GasStationContext.Provider value={{ gasStations, loading, error, addGasStation, deleteGasStation, updateGasStation, refetch: fetchGasStations }}>
            {children}
        </GasStationContext.Provider>
    );
}

export function useGasStations() { return useContext(GasStationContext); }