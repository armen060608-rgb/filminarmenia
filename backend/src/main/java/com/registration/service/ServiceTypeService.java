package com.registration.service;

import com.registration.request.ServiceTypeRequest;

import java.util.List;
import java.util.UUID;

public interface ServiceTypeService {

    List<ServiceTypeRequest> getAllServiceTypes();

    ServiceTypeRequest getServiceTypeById(UUID id);

    List<ServiceTypeRequest> getAllServiceTypess();

    ServiceTypeRequest createServiceType(ServiceTypeRequest serviceTypeRequest);

    ServiceTypeRequest updateServiceType(UUID id, ServiceTypeRequest serviceTypeRequest);

    void deleteServiceType(UUID id);
}