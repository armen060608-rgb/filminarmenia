package com.registration.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class LanguageUtil {

    /**
     * Gets the language from the Accept-Language header
     * @return Language code (en, hy, ru). Defaults to "en" if header is missing or invalid.
     */
    public static String getLanguage() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                String language = request.getHeader("Accept-Language");

                if (language != null && !language.isEmpty()) {
                    language = language.toLowerCase().trim();
                    if (language.equals("hy") || language.equals("ru")) {
                        return language;
                    }
                }
            }
        } catch (Exception e) {
            // If any error occurs, return default language
        }
        return "en";
    }

    /**
     * Selects the appropriate language value from the three available options
     * @param en English value
     * @param ru Russian value
     * @param hy Armenian value
     * @return The value in the requested language
     */
    public static String getLocalizedValue(String en, String ru, String hy) {
        String language = getLanguage();
        switch (language) {
            case "ru":
                return ru != null ? ru : en;
            case "hy":
                return hy != null ? hy : en;
            default:
                return en;
        }
    }
}