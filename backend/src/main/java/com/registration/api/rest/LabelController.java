package com.registration.api.rest;

import com.registration.helper.MessageService;
import com.registration.localization.UsersLocale;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/labels")
public class LabelController {

    private final MessageService messageService;

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @GetMapping("/search/all")
    public Map<String, Map<String, String>> searchInAllLanguages(@RequestParam String q) {
        Map<String, Map<String, String>> result = new HashMap<>();
        String lang = LocaleContextHolder.getLocale().getLanguage();
        System.out.println(lang);
        Locale locale = UsersLocale.getJavaLocale(lang);
        Map<String, String> labels = new HashMap<>();
        labels.put(q, messageService.getMessage(q, locale));

        Map<String, String> filtered = labels.entrySet()
                .stream()
                .filter(entry ->
                        entry.getKey().toLowerCase().contains(q.toLowerCase())
                                || entry.getValue().toLowerCase().contains(q.toLowerCase())
                )
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));

        result.put(lang, filtered);

        return result;
    }

    public LabelController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/en")
    public Map<String, String> getEnglishLabels() {
        return getLabels(UsersLocale.getJavaLocale("en"));
    }

    @GetMapping("/ru")
    public Map<String, String> getRussianLabels() {
        return getLabels(UsersLocale.getJavaLocale("ru"));
    }

    @GetMapping("/hy")
    public Map<String, String> getArmenianLabels() {
        return getLabels(UsersLocale.getJavaLocale("hy"));
    }

    @GetMapping("/all")
    public Map<String, Map<String, String>> getAllLabels() {
        Map<String, Map<String, String>> allLabels = new HashMap<>();
        allLabels.put("en", getLabels(UsersLocale.getJavaLocale("en")));
        allLabels.put("ru", getLabels(UsersLocale.getJavaLocale("ru")));
        allLabels.put("hy", getLabels(UsersLocale.getJavaLocale("hy")));
        return allLabels;
    }

    private Map<String, String> getLabels(Locale locale) {
        Map<String, String> labels = new HashMap<>();

        // Homepage Labels
        labels.put("home.title", messageService.getMessage("home.title", locale));
        labels.put("home.subtitle", messageService.getMessage("home.subtitle", locale));
        labels.put("home.button", messageService.getMessage("home.button", locale));

        // Why Choose Us Section
        labels.put("home.why.title", messageService.getMessage("home.why.title", locale));
        labels.put("home.why.keywords.title", messageService.getMessage("home.why.keywords.title", locale));
        labels.put("home.why.keywords.description", messageService.getMessage("home.why.keywords.description", locale));
        labels.put("home.why.upload.title", messageService.getMessage("home.why.upload.title", locale));
        labels.put("home.why.upload.description", messageService.getMessage("home.why.upload.description", locale));
        labels.put("home.why.filter.title", messageService.getMessage("home.why.filter.title", locale));
        labels.put("home.why.filter.description", messageService.getMessage("home.why.filter.description", locale));

        // Search Page Labels
        labels.put("search.placeholder", messageService.getMessage("search.placeholder", locale));
        labels.put("search.tab.all", messageService.getMessage("search.tab.all", locale));
        labels.put("search.tab.recommendations", messageService.getMessage("search.tab.recommendations", locale));
        labels.put("search.button.view.more", messageService.getMessage("search.button.view.more", locale));

        // Search Results
        labels.put("search.results.found", messageService.getMessage("search.results.found", locale));

        // Upload Image Modal
        labels.put("upload.modal.title", messageService.getMessage("upload.modal.title", locale));
        labels.put("upload.modal.drag.drop", messageService.getMessage("upload.modal.drag.drop", locale));
        labels.put("upload.modal.select.file", messageService.getMessage("upload.modal.select.file", locale));
        labels.put("upload.modal.button.search", messageService.getMessage("upload.modal.button.search", locale));
        labels.put("upload.modal.image.uploaded", messageService.getMessage("upload.modal.image.uploaded", locale));

        // Image Search Results
        labels.put("search.image.results.title", messageService.getMessage("search.image.results.title", locale));
        labels.put("search.button.new.search", messageService.getMessage("search.button.new.search", locale));
        labels.put("search.button.clear.all", messageService.getMessage("search.button.clear.all", locale));

        // Place Details Page
        labels.put("details.tab.photo", messageService.getMessage("details.tab.photo", locale));
        labels.put("details.tab.panorama", messageService.getMessage("details.tab.panorama", locale));
        labels.put("details.section.nearby", messageService.getMessage("details.section.nearby", locale));
        labels.put("details.nearby.hotel", messageService.getMessage("details.nearby.hotel", locale));
        labels.put("details.nearby.gas.station", messageService.getMessage("details.nearby.gas.station", locale));
        labels.put("details.nearby.hospital", messageService.getMessage("details.nearby.hospital", locale));
        labels.put("details.nearby.restaurant", messageService.getMessage("details.nearby.restaurant", locale));
        labels.put("details.nearby.pharmacy", messageService.getMessage("details.nearby.pharmacy", locale));

        // Footer
        labels.put("footer.copyright", messageService.getMessage("footer.copyright", locale));
        labels.put("footer.information", messageService.getMessage("footer.information", locale));
        labels.put("footer.support", messageService.getMessage("footer.support", locale));
        labels.put("footer.terms", messageService.getMessage("footer.terms", locale));
        labels.put("footer.privacy", messageService.getMessage("footer.privacy", locale));
        labels.put("footer.contact", messageService.getMessage("footer.contact", locale));
        labels.put("footer.email", messageService.getMessage("footer.email", locale));
        labels.put("footer.phone", messageService.getMessage("footer.phone", locale));
        labels.put("footer.location", messageService.getMessage("footer.location", locale));
        labels.put("footer.follow", messageService.getMessage("footer.follow", locale));

        return labels;
    }
}