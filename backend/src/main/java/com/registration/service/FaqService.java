package com.registration.service;

import com.registration.request.FaqRequest;
import com.registration.request.PhotoRequest;
import com.registration.request.QuestionRequest;

import java.util.List;
import java.util.UUID;

public interface FaqService {
    List<FaqRequest> getFaqs();
    FaqRequest getFaqById(UUID id);
    FaqRequest createFaq(FaqRequest faqRequest);
    void changeStatus(UUID id, String status);
    void deleteFaq(UUID id);
}