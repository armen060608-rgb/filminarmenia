package com.registration.service;

import com.registration.request.PhotoRequest;
import com.registration.request.QuestionRequest;

import java.util.List;
import java.util.UUID;

public interface QuestionService {
    List<QuestionRequest> getQuestions();
    QuestionRequest getQuestionById(UUID id);
    QuestionRequest createQuestion(QuestionRequest questionRequest);
    void changeStatus(UUID id, String status);
    void deleteQuestion(UUID id);
}