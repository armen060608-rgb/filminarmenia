package com.registration.service;

import com.registration.entity.placefinder.Photo;
import com.registration.entity.placefinder.FaqEntity;
import com.registration.ex.faqexception.FaqApiException;
import com.registration.ex.faqexception.FaqNotFoundException;
import com.registration.ex.photoexception.PhotoApiException;
import com.registration.ex.photoexception.PhotoBadRequestException;
import com.registration.ex.photoexception.PhotoNotFoundException;
import com.registration.ex.faqexception.FaqApiException;
import com.registration.ex.faqexception.FaqNotFoundException;
import com.registration.helper.MessageService;
import com.registration.repository.PhotoRepository;
import com.registration.repository.FaqRepository;
import com.registration.request.PhotoRequest;
import com.registration.request.FaqRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FaqSpringJpa implements FaqService {
    @Autowired
    private FaqRepository faqRepository;

    @Autowired
    private MessageService messageService;

    @Override
    public List<FaqRequest> getFaqs() {
        try {
            return faqRepository.findAll()
                    .stream()
                    .map(FaqEntity::toFaqRequest)
                    .toList();
        } catch (Exception e) {
            throw new PhotoApiException(messageService.getMessage("faq.getting.all.error"), e);
        }
    }
    @Override
    public FaqRequest getFaqById(UUID id) {
        FaqEntity faq = faqRepository.findById(id)
                .orElseThrow(() -> new FaqNotFoundException(messageService.getMessage("photo.not.found")));
        return faq.toFaqRequest();
    }
    @Override
    public FaqRequest createFaq(FaqRequest faqRequest) {

        if (faqRequest.getId() != null) {
            throw new PhotoBadRequestException(messageService.getMessage("faq.id.must.null"));
        }
        faqRequest.setQuestion(faqRequest.getQuestion());
        faqRequest.setAnswer(faqRequest.getAnswer());
        faqRequest.setStatus(faqRequest.getStatus());

        FaqEntity faq = new FaqEntity(faqRequest);

        try {
            faq = faqRepository.save(faq);
        } catch (Exception e) {
            throw new FaqApiException(messageService.getMessage("faq.creating.error"), e);
        }

        FaqRequest newFaq = faq.toFaqRequest();

        return newFaq;
    }
    @Override
    public void changeStatus(UUID id, String status) {
        try {
            Optional<FaqEntity> faqEntity = faqRepository.findById(id);
            FaqEntity faq = faqEntity.get();
            faq.setStatus(status);
            faqRepository.save(faq);
        }catch (Exception e) {
            throw new FaqApiException(messageService.getMessage("faq.status.update.error"), e);
        }

    }
    @Override
    public void deleteFaq(UUID id) {
        faqRepository.findById(id).orElseThrow(() ->
                new FaqNotFoundException(messageService.getMessage("faq.not.found")));
        try {
            faqRepository.deleteById(id);
        } catch (Exception e) {
            throw new FaqApiException(messageService.getMessage("faq.deleting.error"), e);
        }
    }


}
