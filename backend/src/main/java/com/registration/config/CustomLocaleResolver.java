package com.registration.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CustomLocaleResolver extends AcceptHeaderLocaleResolver {

    private static final List<Locale> SUPPORTED_LOCALES = Arrays.asList(
            Locale.forLanguageTag("en"),
            Locale.forLanguageTag("ru"),
            Locale.forLanguageTag("hy")
    );

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String headerLang = request.getHeader("Accept-Language");

        if (headerLang == null || headerLang.isEmpty()) {
            return Locale.forLanguageTag("en");
        }

        // Handle simple language codes (en, ru, hy)
        String langCode = headerLang.toLowerCase().split("[,;_-]")[0].trim();

        for (Locale locale : SUPPORTED_LOCALES) {
            if (locale.getLanguage().equals(langCode)) {
                return locale;
            }
        }

        return Locale.forLanguageTag("en");
    }
}