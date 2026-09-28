package com.registration.service;

import com.registration.request.ServiceRequest;

import java.util.List;
import java.util.UUID;

public interface ServiceService {

    List<ServiceRequest> getAllServices();

    ServiceRequest getServiceById(UUID id);

    List<ServiceRequest> getAllServicess();

    ServiceRequest createService(ServiceRequest serviceRequest);

    ServiceRequest updateService(UUID id, ServiceRequest serviceRequest);

    void deleteService(UUID id);
}