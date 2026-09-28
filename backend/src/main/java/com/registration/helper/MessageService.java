package com.registration.helper;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Service for retrieving localized messages.
 */
@Service
public class MessageService {

    private final MessageSource messageSource;

    public MessageService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Get message for current locale
     */
    public String getMessage(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    /**
     * Get message with arguments for current locale
     */
    public String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    /**
     * Get message for specific locale
     */
    public String getMessage(String code, Locale locale) {
        return messageSource.getMessage(code, null, locale);
    }

    /**
     * Get message with arguments for specific locale
     */
    public String getMessage(String code, Object[] args, Locale locale) {
        return messageSource.getMessage(code, args, locale);
    }

    /**
     * Get message with default value if not found
     */
    public String getMessage(String code, String defaultMessage) {
        return messageSource.getMessage(code, null, defaultMessage, LocaleContextHolder.getLocale());
    }
}
