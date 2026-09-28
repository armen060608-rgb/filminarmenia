import React, { createContext, useContext, useState, useEffect } from "react";

const API_BASE = "/registration/api/v1/nearby_hotels";

const HotelContext = createContext(null);

export function HotelProvider({ children }) {
    const [hotels, setHotels] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const fetchHotels = async () => {
        setLoading(true);
        setError(null);
        try {
            const res = await fetch(`${API_BASE}/getNearbyHotelss`, {
                headers: { accept: "*/*", "Accept-Language": "en" },
            });
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            const data = await res.json();
            if (!Array.isArray(data)) { setHotels([]); return; }
            setHotels(data.map(item => ({
                id: item.id,
                name_en: item.name ?? "",
                name_hy: item.nameHy ?? "",
                name_ru: item.nameRu ?? "",
                description_en: item.description ?? "",
                description_hy: item.descriptionHy ?? "",
                description_ru: item.descriptionRu ?? "",
                regionId: item.regionId ?? "",
                locationId: item.locationId ?? "",
                stars: item.stars ?? 0,
                images: item.images ?? [],
                address: item.address ?? "",
                phone: item.phone ?? "",
            })));
        } catch (err) {
            setError("Could not load hotels: " + err.message);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => { fetchHotels(); }, []);

    const addHotel = async (hotel) => {
        try {
            const res = await fetch(`${API_BASE}/createNearbyHotel`, {
                method: "POST",
                headers: { "Content-Type": "application/json", accept: "*/*" },
                body: JSON.stringify({
                    locationId: hotel.locationId,
                    name: hotel.name_en,
                    nameHy: hotel.name_hy,
                    nameRu: hotel.name_ru,
                    description: hotel.description_en,
                    descriptionHy: hotel.description_hy,
                    descriptionRu: hotel.description_ru,
                    regionId: hotel.regionId,
                    stars: hotel.stars ?? 0,
                    address: hotel.address,
                    phone: hotel.phone,
                }),
            });
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            await fetchHotels();
        } catch (err) {
            setError("Could not create hotel: " + err.message);
        }
    };

    const updateHotel = async (updated) => {
        try {
            const res = await fetch(`${API_BASE}/updateNearbyHotel/${updated.id}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json", accept: "*/*" },
                body: JSON.stringify({
                    locationId: updated.locationId,
                    name: updated.name_en,
                    nameHy: updated.name_hy,
                    nameRu: updated.name_ru,
                    description: updated.description_en,
                    descriptionHy: updated.description_hy,
                    descriptionRu: updated.description_ru,
                    regionId: updated.regionId,
                    stars: updated.stars ?? 0,
                    address: updated.address,
                    phone: updated.phone,
                }),
            });
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            await fetchHotels();
        } catch (err) {
            setError("Could not update hotel: " + err.message);
        }
    };

    const deleteHotel = async (id) => {
        try {
            const res = await fetch(`${API_BASE}/deleteNearbyHotel/${id}`, {
                method: "DELETE",
                headers: { accept: "*/*" },
            });
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            setHotels(prev => prev.filter(h => h.id !== id));
        } catch (err) {
            setError("Could not delete hotel: " + err.message);
        }
    };

    return (
        <HotelContext.Provider value={{ hotels, loading, error, addHotel, updateHotel, deleteHotel, refetch: fetchHotels }}>
            {children}
        </HotelContext.Provider>
    );
}

export function useHotels() { return useContext(HotelContext); }