package com.registration.service;

import com.registration.entity.ServiceTypeEntity;
import com.registration.ex.servicetypeexception.ServiceTypeAlreadyExistException;
import com.registration.ex.servicetypeexception.ServiceTypeApiException;
import com.registration.ex.servicetypeexception.ServiceTypeBadRequestException;
import com.registration.ex.servicetypeexception.ServiceTypeNotFoundException;
import com.registration.helper.MessageService;
import com.registration.repository.ServiceTypeRepository;
import com.registration.request.ServiceTypeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServiceTypeSpringJpa implements ServiceTypeService {

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    @Autowired
    private MessageService messageService;

    @Override
    public List<ServiceTypeRequest> getAllServiceTypes() {
        try {
            return serviceTypeRepository.findAll()
                    .stream()
                    .map(ServiceTypeEntity::toServiceTypeRequest)
                    .toList();
        } catch (Exception e) {
            throw new ServiceTypeApiException(messageService.getMessage("servicetype.getting.all.error"), e);
        }
    }

    @Override
    public ServiceTypeRequest getServiceTypeById(UUID id) {
        ServiceTypeEntity serviceType = serviceTypeRepository.findById(id)
                .orElseThrow(() -> new ServiceTypeNotFoundException(messageService.getMessage("servicetype.not.found")));
        return serviceType.toServiceTypeRequest();
    }

    @Override
    public List<ServiceTypeRequest> getAllServiceTypess() {
        try {
            return serviceTypeRepository.findAll()
                    .stream()
                    .map(ServiceTypeEntity::toServiceTypeRequestFull)
                    .toList();
        } catch (Exception e) {
            throw new ServiceTypeApiException(messageService.getMessage("servicetype.getting.all.error"), e);
        }
    }

    @Override
    public ServiceTypeRequest createServiceType(ServiceTypeRequest serviceTypeRequest) {
        if (serviceTypeRequest.getId() != null) {
            throw new ServiceTypeBadRequestException(messageService.getMessage("servicetype.id.must.null"));
        }

        validateDuplicate(null, serviceTypeRequest.getName());

        serviceTypeRequest.setName(serviceTypeRequest.getName());
        serviceTypeRequest.setNameRu(serviceTypeRequest.getNameRu());
        serviceTypeRequest.setNameHy(serviceTypeRequest.getNameHy());
        serviceTypeRequest.setDescription(serviceTypeRequest.getDescription());
        serviceTypeRequest.setDescriptionRu(serviceTypeRequest.getDescriptionRu());
        serviceTypeRequest.setDescriptionHy(serviceTypeRequest.getDescriptionHy());

        ServiceTypeEntity serviceType = new ServiceTypeEntity(serviceTypeRequest);

        try {
            serviceType = serviceTypeRepository.save(serviceType);
        } catch (Exception e) {
            throw new ServiceTypeApiException(messageService.getMessage("servicetype.creating.error"), e);
        }

        return serviceType.toServiceTypeRequest();
    }

    @Override
    public ServiceTypeRequest updateServiceType(UUID id, ServiceTypeRequest serviceTypeRequest) {
        ServiceTypeEntity serviceType = serviceTypeRepository.findById(id)
                .orElseThrow(() -> new ServiceTypeNotFoundException(messageService.getMessage("servicetype.not.found")));

        serviceType.setName(serviceTypeRequest.getName());
        serviceType.setNameRu(serviceTypeRequest.getNameRu());
        serviceType.setNameHy(serviceTypeRequest.getNameHy());
        serviceType.setDescription(serviceTypeRequest.getDescription());
        serviceType.setDescriptionRu(serviceTypeRequest.getDescriptionRu());
        serviceType.setDescriptionHy(serviceTypeRequest.getDescriptionHy());

        try {
            ServiceTypeEntity updated = serviceTypeRepository.save(serviceType);
            return updated.toServiceTypeRequest();
        } catch (Exception e) {
            throw new ServiceTypeApiException(messageService.getMessage("servicetype.updating.error"), e);
        }
    }

    @Override
    public void deleteServiceType(UUID id) {
        serviceTypeRepository.findById(id).orElseThrow(() ->
                new ServiceTypeNotFoundException(messageService.getMessage("servicetype.not.found")));
        try {
            serviceTypeRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceTypeApiException(messageService.getMessage("servicetype.deleting.error"), e);
        }
    }

    private void validateDuplicate(UUID id, String name) {
        Optional<ServiceTypeEntity> existing = serviceTypeRepository.findByName(name);

        if (existing.isPresent()) {
            ServiceTypeEntity serviceType = existing.get();

            if (!serviceType.getId().equals(id)) {
                throw new ServiceTypeAlreadyExistException(
                        messageService.getMessage("servicetype.already.exists")
                );
            }
        }
    }
}