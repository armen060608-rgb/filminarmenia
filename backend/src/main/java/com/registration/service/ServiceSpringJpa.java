package com.registration.service;

import com.registration.entity.ServiceEntity;
import com.registration.ex.serviceexception.ServiceAlreadyExistException;
import com.registration.ex.serviceexception.ServiceApiException;
import com.registration.ex.serviceexception.ServiceBadRequestException;
import com.registration.ex.serviceexception.ServiceNotFoundException;
import com.registration.helper.MessageService;
import com.registration.repository.ServiceRepository;
import com.registration.repository.ServiceTypeRepository;
import com.registration.request.ServiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServiceSpringJpa implements ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    @Autowired
    private MessageService messageService;

    @Override
    public List<ServiceRequest> getAllServices() {
        try {
            return serviceRepository.findAll()
                    .stream()
                    .map(ServiceEntity::toServiceRequest)
                    .toList();
        } catch (Exception e) {
            throw new ServiceApiException(messageService.getMessage("service.getting.all.error"), e);
        }
    }

    @Override
    public ServiceRequest getServiceById(UUID id) {
        ServiceEntity service = serviceRepository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException(messageService.getMessage("service.not.found")));
        return service.toServiceRequest();
    }

    @Override
    public List<ServiceRequest> getAllServicess() {
        try {
            return serviceRepository.findAll()
                    .stream()
                    .map(ServiceEntity::toServiceRequestFull)
                    .toList();
        } catch (Exception e) {
            throw new ServiceApiException(messageService.getMessage("service.getting.all.error"), e);
        }
    }

    @Override
    public ServiceRequest createService(ServiceRequest serviceRequest) {
        if (serviceRequest.getId() != null) {
            throw new ServiceBadRequestException(messageService.getMessage("service.id.must.null"));
        }

        if (serviceRequest.getServiceTypeId() == null) {
            throw new ServiceBadRequestException(messageService.getMessage("service.servicetype.required"));
        }

        serviceTypeRepository.findById(serviceRequest.getServiceTypeId())
                .orElseThrow(() -> new ServiceBadRequestException(
                        messageService.getMessage("servicetype.not.found")));

        validateDuplicate(null, serviceRequest.getName());

        ServiceEntity service = new ServiceEntity(serviceRequest);

        try {
            service = serviceRepository.save(service);
        } catch (Exception e) {
            throw new ServiceApiException(messageService.getMessage("service.creating.error"), e);
        }

        return service.toServiceRequest();
    }

    @Override
    public ServiceRequest updateService(UUID id, ServiceRequest serviceRequest) {
        ServiceEntity service = serviceRepository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException(messageService.getMessage("service.not.found")));

        if (serviceRequest.getServiceTypeId() != null) {
            serviceTypeRepository.findById(serviceRequest.getServiceTypeId())
                    .orElseThrow(() -> new ServiceNotFoundException(
                            messageService.getMessage("servicetype.not.found")));
            service.setServiceTypeId(serviceRequest.getServiceTypeId());
        }

        service.setName(serviceRequest.getName());
        service.setNameRu(serviceRequest.getNameRu());
        service.setNameHy(serviceRequest.getNameHy());
        service.setDescription(serviceRequest.getDescription());
        service.setDescriptionRu(serviceRequest.getDescriptionRu());
        service.setDescriptionHy(serviceRequest.getDescriptionHy());

        try {
            ServiceEntity updated = serviceRepository.save(service);
            return updated.toServiceRequest();
        } catch (Exception e) {
            throw new ServiceApiException(messageService.getMessage("service.updating.error"), e);
        }
    }

    @Override
    public void deleteService(UUID id) {
        serviceRepository.findById(id).orElseThrow(() ->
                new ServiceNotFoundException(messageService.getMessage("service.not.found")));
        try {
            serviceRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceApiException(messageService.getMessage("service.deleting.error"), e);
        }
    }

    private void validateDuplicate(UUID id, String name) {
        Optional<ServiceEntity> existing = serviceRepository.findByName(name);

        if (existing.isPresent()) {
            ServiceEntity service = existing.get();

            if (!service.getId().equals(id)) {
                throw new ServiceAlreadyExistException(
                        messageService.getMessage("service.already.exists")
                );
            }
        }
    }
}