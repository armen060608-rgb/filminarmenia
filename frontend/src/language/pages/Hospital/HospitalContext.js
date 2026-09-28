import React, { createContext, useContext, useState, useEffect } from "react";

const API_BASE = "/registration/api/v1/nearby_hospitals";

const HospitalContext = createContext(null);

export function HospitalProvider({ children }) {
    const [hospitals, setHospitals] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const fetchHospitals = async () => {
        setLoading(true);
        setError(null);
        try {
            const res = await fetch(`${API_BASE}/getNearbyHospitals`, {
                headers: { accept: "*/*", "Accept-Language": "en" },
            });
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            const data = await res.json();
            if (!Array.isArray(data)) { setHospitals([]); return; }
            setHospitals(data.map(item => ({
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
        } catch (err) { setError("Could not load hospitals: " + err.message); }
        finally { setLoading(false); }
    };

    useEffect(() => { fetchHospitals(); }, []);

    const addHospital = async (hospital) => {
        try {
            const res = await fetch(`${API_BASE}/createNearbyHospital`, {
                method: "POST",
                headers: { "Content-Type": "application/json", accept: "*/*" },
                body: JSON.stringify({
                    name: hospital.name_en, nameHy: hospital.name_hy, nameRu: hospital.name_ru,
                    description: hospital.description_en, descriptionHy: hospital.description_hy, descriptionRu: hospital.description_ru,
                    address: hospital.address, phone: hospital.phone,
                }),
            });
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            await fetchHospitals();
        } catch (err) { setError("Could not create hospital: " + err.message); }
    };

    const deleteHospital = async (id) => {
        try {
            const res = await fetch(`${API_BASE}/deleteNearbyHospital/${id}`, { method: "DELETE" });
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            setHospitals(prev => prev.filter(h => h.id !== id));
        } catch (err) { setError("Could not delete hospital: " + err.message); }
    };

    const updateHospital = async (updated) => {
        try {
            const res = await fetch(`${API_BASE}/updateNearbyHospital/${updated.id}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    name: updated.name_en, nameHy: updated.name_hy, nameRu: updated.name_ru,
                    description: updated.description_en, descriptionHy: updated.description_hy, descriptionRu: updated.description_ru,
                    address: updated.address, phone: updated.phone,
                }),
            });
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            await fetchHospitals();
        } catch (err) { setError("Could not update hospital: " + err.message); }
    };

    return (
        <HospitalContext.Provider value={{ hospitals, loading, error, addHospital, deleteHospital, updateHospital, refetch: fetchHospitals }}>
            {children}
        </HospitalContext.Provider>
    );
}

export function useHospitals() { return useContext(HospitalContext); }