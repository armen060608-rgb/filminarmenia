import React, { createContext, useContext, useState, useEffect } from "react";

const API_BASE = "/registration/api/v1/countries";

const CountryContext = createContext(null);

export function CountryProvider({ children }) {
    const [countries, setCountries] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const fetchCountries = async () => {
        setLoading(true);
        setError(null);
        try {
            const res = await fetch(`${API_BASE}/getAllCountriess`, {
                headers: { accept: "*/*", "Accept-Language": "en" },
            });
            if (!res.ok) throw new Error(`Server error: ${res.status}`);
            const data = await res.json();
            setCountries(data.map(r => ({
                id: r.id,
                name_en: r.name ?? "",
                name_hy: r.nameHy ?? "",
                name_ru: r.nameRu ?? "",
            })));
        } catch (err) { setError(err.message); }
        finally { setLoading(false); }
    };

    useEffect(() => { fetchCountries(); }, []);

    const addCountry = async (country) => {
        try {
            const res = await fetch(`${API_BASE}/createCountry`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ name: country.name_en, nameHy: country.name_hy, nameRu: country.name_ru }),
            });
            if (!res.ok) throw new Error(`Server error: ${res.status}`);
            await fetchCountries();
        } catch (err) { setError(err.message); }
    };

    const deleteCountry = async (id) => {
        try {
            const res = await fetch(`${API_BASE}/deleteCountry/${id}`, { method: "DELETE" });
            if (!res.ok) throw new Error(`Server error: ${res.status}`);
            setCountries(prev => prev.filter(r => r.id !== id));
        } catch (err) { setError(err.message); }
    };

    const updateCountry = async (updated) => {
        try {
            const res = await fetch(`${API_BASE}/updateCountry/${updated.id}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ name: updated.name_en, nameHy: updated.name_hy, nameRu: updated.name_ru }),
            });
            if (!res.ok) throw new Error(`Server error: ${res.status}`);
            await fetchCountries();
        } catch (err) { setError(err.message); }
    };

    return (
        <CountryContext.Provider value={{ countries, loading, error, addCountry, deleteCountry, updateCountry, refetch: fetchCountries }}>
            {children}
        </CountryContext.Provider>
    );
}

export function useCountries() { return useContext(CountryContext); }