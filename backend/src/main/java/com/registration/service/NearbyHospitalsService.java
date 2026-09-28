package com.registration.service;

import com.registration.request.NearbyHospitalsRequest;
import com.registration.request.NearbyHotelsRequest;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface NearbyHospitalsService {

    List<NearbyHospitalsRequest> getNearbyHospitals();
    NearbyHospitalsRequest getNearbyHospitalById(UUID id);
    NearbyHospitalsRequest createNearbyHospital(@Valid NearbyHospitalsRequest nearbyHospitalsRequest);
    NearbyHospitalsRequest updateNearbyHospital(UUID id, NearbyHospitalsRequest nearbyHospitalsRequest);

    void deleteNearbyHospital(UUID id);
    List<NearbyHospitalsRequest> getNearbyHospitalsByRegion(UUID regionId);
    List<NearbyHospitalsRequest> getNearbyHospitalss();


}
