package com.registration.service;

import com.registration.request.RegionRequest;

import java.util.List;
import java.util.UUID;

public interface RegionService {

    List<RegionRequest> getAllRegions();
    RegionRequest getRegionById(UUID id);
    RegionRequest createRegion(RegionRequest regionRequest);
    RegionRequest updateRegion(UUID id, RegionRequest regionRequest);
    void deleteRegion(UUID id);
    List<RegionRequest> getAllRegionss();

}
