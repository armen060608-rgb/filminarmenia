package com.registration.service;

import com.registration.entity.placefinder.TransportType;
import com.registration.ex.transporttypeexcpetion.TransportTypeAlreadyExistsException;
import com.registration.ex.transporttypeexcpetion.TransportTypeApiException;
import com.registration.ex.transporttypeexcpetion.TransportTypeNotFoundException;
import com.registration.ex.userexceptions.UserBadRequestException;
import com.registration.helper.MessageService;
import com.registration.repository.TransportTypeRepository;
import com.registration.request.TransportTypeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransportTypeSpringJpa implements TransportTypeService {

    @Autowired
    private TransportTypeRepository transportTypeRepository;

    @Autowired
    private MessageService messageService;

    @Override
    public List<TransportTypeRequest> getAllTransportTypes() {
        try {
            return transportTypeRepository.findAll()
                    .stream()
                    .map(TransportType::toTransportTypeRequest)
                    .toList();
        } catch (Exception e) {
            throw new TransportTypeApiException(messageService.getMessage("transporttype.getting.all.error"), e);
        }
    }

    @Override
    public TransportTypeRequest getTransportTypeById(UUID id) {
        TransportType transportType = transportTypeRepository.findById(id)
                .orElseThrow(() -> new TransportTypeNotFoundException(messageService.getMessage("transporttype.not.found")));
        return transportType.toTransportTypeRequest();
    }

    @Override
    public TransportTypeRequest createTransportType(TransportTypeRequest transportTypeRequest) {

        if (transportTypeRequest.getId() != null) {
            throw new UserBadRequestException(messageService.getMessage("transporttype.id.must.null"));
        }

        validateDuplicate(null, transportTypeRequest.getName());
        transportTypeRequest.setName(transportTypeRequest.getName());
        transportTypeRequest.setNameRu(transportTypeRequest.getNameRu());
        transportTypeRequest.setNameHy(transportTypeRequest.getNameHy());
        transportTypeRequest.setDescription(transportTypeRequest.getDescription());
        transportTypeRequest.setDescriptionRu(transportTypeRequest.getDescriptionRu());
        transportTypeRequest.setDescriptionHy(transportTypeRequest.getDescriptionHy());

        TransportType transportType = new TransportType(transportTypeRequest);

        try {
            transportType = transportTypeRepository.save(transportType);
        } catch (Exception e) {
            throw new TransportTypeApiException(messageService.getMessage("transporttype.creating.error"), e);
        }

        TransportTypeRequest newTransportType = transportType.toTransportTypeRequest();

        return newTransportType;
    }

    @Override
    public TransportTypeRequest updateTransportType(UUID id, TransportTypeRequest transportTypeRequest) {
        TransportType transportType = transportTypeRepository.findById(id)
                .orElseThrow(() -> new TransportTypeNotFoundException(messageService.getMessage("transporttype.not.found")));

        transportType.setName(transportTypeRequest.getName());
        transportType.setNameRu(transportTypeRequest.getNameRu());
        transportType.setNameHy(transportTypeRequest.getNameHy());
        transportType.setDescription(transportTypeRequest.getDescription());
        transportType.setDescriptionRu(transportTypeRequest.getDescriptionRu());
        transportType.setDescriptionHy(transportTypeRequest.getDescriptionHy());

        try {
            TransportType updated = transportTypeRepository.save(transportType);
            return updated.toTransportTypeRequest();
        } catch (Exception e) {
            throw new TransportTypeApiException(messageService.getMessage("transporttype.updating.error"), e);
        }
    }

    @Override
    public void deleteTransportType(UUID id) {
        transportTypeRepository.findById(id).orElseThrow(() ->
                new TransportTypeNotFoundException(messageService.getMessage("transporttype.not.found")));
        try {
            transportTypeRepository.deleteById(id);
        } catch (Exception e) {
            throw new TransportTypeApiException(messageService.getMessage("transporttype.deleting.error"), e);
        }
    }

    private void validateDuplicate(UUID id, String name) {
        Optional<TransportType> existing = transportTypeRepository.findByName(name);

        if (existing.isPresent()) {
            TransportType transportType = existing.get();

            if (!transportType.getId().equals(id)) {
                throw new TransportTypeAlreadyExistsException(
                        messageService.getMessage("transporttype.already.exists")
                );
            }
        }
    }
}