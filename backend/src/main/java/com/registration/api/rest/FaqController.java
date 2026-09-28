package com.registration.api.rest;

import com.registration.constants.RoutConstants;
import com.registration.request.FaqRequest;
import com.registration.security.RequiredAdminUser;
import com.registration.service.FaqService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.reflections.Reflections.log;

@RestController
@RequestMapping(RoutConstants.BASE_URL + "${platform.version}" + RoutConstants.FAQ)
public class FaqController {
    @Autowired
    private FaqService faqService;

    @PostMapping("/createFaq")
    public FaqRequest createFaq(@RequestBody @Valid FaqRequest faqRequest) {
        log.info("received request to food place user");
        FaqRequest faq = faqService.createFaq(faqRequest);
        log.info("food place created successfully");
        return faq;
    }
    @PutMapping("/changeStatus/{id}")
    public void changeStatus(@PathVariable UUID id, String status) {
        faqService.changeStatus(id, status);
    }
    @DeleteMapping("/deleteFaq/{id}")
    @RequiredAdminUser
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFaq(@PathVariable UUID id) {
        faqService.deleteFaq(id);
    }
}
