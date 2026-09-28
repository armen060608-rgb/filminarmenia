import React, { createContext, useContext, useState, useEffect } from "react";

const API_BASE = "/registration/api/v1/nearby_pharmacy";

const PharmacyContext = createContext(null);

export function PharmacyProvider({ children }) {
    const [pharmacies, setPharmacies] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const fetchPharmacies = async () => {
        setLoading(true);
        setError(null);
        try {
            const res = await fetch(`${API_BASE}/getNearbyPharmaciess`, {
                headers: { accept: "*/*", "Accept-Language": "en" },
            });
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            const data = await res.json();
            if (!Array.isArray(data)) { setPharmacies([]); return; }
            setPharmacies(data.map(item => ({
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
        } catch (err) { setError("Could not load pharmacies: " + err.message); }
        finally { setLoading(false); }
    };

    useEffect(() => { fetchPharmacies(); }, []);

    const addPharmacy = async (pharmacy) => {
        try {
            const res = await fetch(`${API_BASE}/createNearbyPharmacy`, {
                method: "POST",
                headers: { "Content-Type": "application/json", accept: "*/*" },
                body: JSON.stringify({
                    name: pharmacy.name_en, nameHy: pharmacy.name_hy, nameRu: pharmacy.name_ru,
                    description: pharmacy.description_en, descriptionHy: pharmacy.description_hy, descriptionRu: pharmacy.description_ru,
                    address: pharmacy.address, phone: pharmacy.phone,
                }),
            });
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            await fetchPharmacies();
        } catch (err) { setError("Could not create pharmacy: " + err.message); }
    };

    const deletePharmacy = async (id) => {
        try {
            const res = await fetch(`${API_BASE}/deleteNearbyPharmacy/${id}`, { method: "DELETE" });
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            setPharmacies(prev => prev.filter(p => p.id !== id));
        } catch (err) { setError("Could not delete pharmacy: " + err.message); }
    };

    const updatePharmacy = async (updated) => {
        try {
            const res = await fetch(`${API_BASE}/updateNearbyPharmacy/${updated.id}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    name: updated.name_en, nameHy: updated.name_hy, nameRu: updated.name_ru,
                    description: updated.description_en, descriptionHy: updated.description_hy, descriptionRu: updated.description_ru,
                    address: updated.address, phone: updated.phone,
                }),
            });
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            await fetchPharmacies();
        } catch (err) { setError("Could not update pharmacy: " + err.message); }
    };

    return (
        <PharmacyContext.Provider value={{ pharmacies, loading, error, addPharmacy, deletePharmacy, updatePharmacy, refetch: fetchPharmacies }}>
            {children}
        </PharmacyContext.Provider>
    );
}

export function usePharmacies() { return useContext(PharmacyContext); }