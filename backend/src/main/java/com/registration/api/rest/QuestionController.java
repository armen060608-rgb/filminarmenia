package com.registration.api.rest;

import com.registration.constants.RoutConstants;
import com.registration.request.FoodPlacesRequest;
import com.registration.request.QuestionRequest;
import com.registration.security.RequiredAdminUser;
import com.registration.service.FoodPlacesService;
import com.registration.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

import static org.reflections.Reflections.log;
@RestController
@RequestMapping(RoutConstants.BASE_URL + "${platform.version}" + RoutConstants.QUESTIONS)
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @PostMapping("/createQuestion")
    public QuestionRequest createQuestion(@RequestBody @Valid QuestionRequest questionRequest) {
        log.info("received request to food place user");
        QuestionRequest question = questionService.createQuestion(questionRequest);
        log.info("food place created successfully");
        return question;
    }
    @PutMapping("/changeStatus/{id}")
    public void changeStatus(@PathVariable UUID id, String status) {
        questionService.changeStatus(id, status);
    }
    @DeleteMapping("/deleteQuestion/{id}")
    @RequiredAdminUser
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuestion(@PathVariable UUID id) {
        questionService.deleteQuestion(id);
    }
}
