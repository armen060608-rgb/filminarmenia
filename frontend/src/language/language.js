import React, { createContext, useContext, useState } from "react";

export const languages = {
    EN: "en",
    HY: "hy",
    RU: "ru",
};

const LanguageContext = createContext();

export function LanguageProvider({ children }) {
    const [language, setLanguage] = useState(languages.HY); // Default to Armenian since your backend has Armenian labels
    const changeLanguage = (lang) => setLanguage(lang);

    return (
        <LanguageContext.Provider value={{ language, changeLanguage }}>
            {children}
        </LanguageContext.Provider>
    );
}

export function useLanguage() {
    const context = useContext(LanguageContext);
    if (!context) throw new Error("useLanguage must be used within a LanguageProvider");
    return context;
}