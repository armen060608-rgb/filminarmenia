import { useState, useEffect } from "react";
import { useLanguage } from "../language/language"

const BASE_URL = "http://localhost:8080/registration";

export function useLabels() {
    const { language } = useLanguage();
    const [labels, setLabels] = useState({});
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        setLoading(true);
        fetch(`${BASE_URL}/api/labels/${language}`)
            .then(res => res.json())
            .then(data => {
                setLabels(data);
                setLoading(false);
            })
            .catch(err => {
                console.error("Labels fetch failed:", err);
                setLoading(false);
            });
    }, [language]); // Re-fetches whenever language changes

    const t = (key) => labels[key] ?? key;

    return { t, labels, loading };
}