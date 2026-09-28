import React, { createContext, useContext, useState, useEffect } from "react";

const API_BASE = "/registration/api/v1/food_places";

const RestaurantContext = createContext(null);

export function RestaurantProvider({ children }) {
    const [restaurants, setRestaurants] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const fetchRestaurants = async () => {
        setLoading(true);
        setError(null);
        try {
            const res = await fetch(`${API_BASE}/getFoodPlacess`, {
                headers: { accept: "*/*", "Accept-Language": "en" },
            });
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            const data = await res.json();
            if (!Array.isArray(data)) { setRestaurants([]); return; }
            setRestaurants(data.map(item => ({
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
                cuisine: item.cuisine ?? "",
            })));
        } catch (err) { setError("Could not load food places: " + err.message); }
        finally { setLoading(false); }
    };

    useEffect(() => { fetchRestaurants(); }, []);

    const addRestaurant = async (restaurant) => {
        try {
            const res = await fetch(`${API_BASE}/createFoodPlace`, {
                method: "POST",
                headers: { "Content-Type": "application/json", accept: "*/*" },
                body: JSON.stringify({
                    name: restaurant.name_en, nameHy: restaurant.name_hy, nameRu: restaurant.name_ru,
                    description: restaurant.description_en, descriptionHy: restaurant.description_hy, descriptionRu: restaurant.description_ru,
                    address: restaurant.address, phone: restaurant.phone, cuisine: restaurant.cuisine,
                }),
            });
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            await fetchRestaurants();
        } catch (err) { setError("Could not create food place: " + err.message); }
    };

    const deleteRestaurant = async (id) => {
        try {
            const res = await fetch(`${API_BASE}/deleteFoodPlace/${id}`, { method: "DELETE" });
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            setRestaurants(prev => prev.filter(r => r.id !== id));
        } catch (err) { setError("Could not delete food place: " + err.message); }
    };

    const updateRestaurant = async (updated) => {
        try {
            const res = await fetch(`${API_BASE}/updateFoodPlace/${updated.id}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    name: updated.name_en, nameHy: updated.name_hy, nameRu: updated.name_ru,
                    description: updated.description_en, descriptionHy: updated.description_hy, descriptionRu: updated.description_ru,
                    address: updated.address, phone: updated.phone, cuisine: updated.cuisine,
                }),
            });
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            await fetchRestaurants();
        } catch (err) { setError("Could not update food place: " + err.message); }
    };

    return (
        <RestaurantContext.Provider value={{ restaurants, loading, error, addRestaurant, deleteRestaurant, updateRestaurant, refetch: fetchRestaurants }}>
            {children}
        </RestaurantContext.Provider>
    );
}

export function useRestaurants() { return useContext(RestaurantContext); }