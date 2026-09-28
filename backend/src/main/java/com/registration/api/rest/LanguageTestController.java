package com.registration.api.rest;

import com.registration.constants.RoutConstants;
import com.registration.helper.MessageService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(RoutConstants.BASE_URL + "${user.service.version}/test")
public class LanguageTestController {

    @Autowired
    private MessageService messageService;

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @GetMapping("/locale")
    public Map<String, Object> testLocale(HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();

        log.info("=== LOCALE TEST ===");
        log.info("Detected Locale: {}", locale);
        log.info("Accept-Language Header: {}", request.getHeader("Accept-Language"));

        Map<String, Object> result = new HashMap<>();
        result.put("detectedLocale", locale.toString());
        result.put("language", locale.getLanguage());
        result.put("country", locale.getCountry());
        result.put("acceptLanguageHeader", request.getHeader("Accept-Language"));

        // Test messages in different languages
        result.put("user.not.found", messageService.getMessage("user.not.found"));
        result.put("transporttype.not.found", messageService.getMessage("transporttype.not.found"));
        result.put("country.not.found", messageService.getMessage("country.not.found"));

        return result;
    }
}