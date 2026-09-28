package com.registration.service;

import com.registration.entity.placefinder.Photo;
import com.registration.entity.placefinder.QuestionEntity;
import com.registration.ex.photoexception.PhotoApiException;
import com.registration.ex.photoexception.PhotoBadRequestException;
import com.registration.ex.photoexception.PhotoNotFoundException;
import com.registration.ex.questionexception.QuestionApiException;
import com.registration.ex.questionexception.QuestionNotFoundException;
import com.registration.helper.MessageService;
import com.registration.repository.PhotoRepository;
import com.registration.repository.QuestionRepository;
import com.registration.request.PhotoRequest;
import com.registration.request.QuestionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class QuestionSpringJpa implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private MessageService messageService;

    @Override
    public List<QuestionRequest> getQuestions() {
        try {
            return questionRepository.findAll()
                    .stream()
                    .map(QuestionEntity::toQuestionRequest)
                    .toList();
        } catch (Exception e) {
            throw new PhotoApiException(messageService.getMessage("question.getting.all.error"), e);
        }
    }
    @Override
    public QuestionRequest getQuestionById(UUID id) {
        QuestionEntity question = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(messageService.getMessage("photo.not.found")));
        return question.toQuestionRequest();
    }
    @Override
    public QuestionRequest createQuestion(QuestionRequest questionRequest) {

        if (questionRequest.getId() != null) {
            throw new PhotoBadRequestException(messageService.getMessage("question.id.must.null"));
        }
        questionRequest.setMessage(questionRequest.getMessage());
        questionRequest.setEmail(questionRequest.getEmail());
        questionRequest.setStatus("active");

        QuestionEntity question = new QuestionEntity(questionRequest);

        try {
            question = questionRepository.save(question);
        } catch (Exception e) {
            throw new QuestionApiException(messageService.getMessage("question.creating.error"), e);
        }

        QuestionRequest newQuestion = question.toQuestionRequest();

        return newQuestion;
    }
    @Override
    public void changeStatus(UUID id, String status) {
        try {
            Optional<QuestionEntity> questionEntity = questionRepository.findById(id);
            QuestionEntity question = questionEntity.get();
            question.setStatus(status);
            questionRepository.save(question);
        }catch (Exception e) {
            throw new QuestionApiException(messageService.getMessage("question.status.update.error"), e);
        }

    }
    @Override
    public void deleteQuestion(UUID id) {
        questionRepository.findById(id).orElseThrow(() ->
                new QuestionNotFoundException(messageService.getMessage("question.not.found")));
        try {
            questionRepository.deleteById(id);
        } catch (Exception e) {
            throw new QuestionApiException(messageService.getMessage("question.deleting.error"), e);
        }
    }


}