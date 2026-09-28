package com.registration.service;

import com.registration.entity.placefinder.RegionEntity;
import com.registration.ex.regionexception.RegionAlreadyExistsException;
import com.registration.ex.regionexception.RegionApiException;
import com.registration.ex.regionexception.RegionBadRequestException;
import com.registration.ex.regionexception.RegionNotFoundException;
import com.registration.helper.MessageService;
import com.registration.repository.RegionRepository;
import com.registration.request.RegionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RegionSpringJpa implements RegionService {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private MessageService messageService;

    @Override
    public List<RegionRequest> getAllRegions() {
        try {
            return regionRepository.findAll()
                    .stream()
                    .map(RegionEntity::toRegionRequest)
                    .toList();
        } catch (Exception e) {
            throw new RegionApiException(messageService.getMessage("region.getting.all.error"), e);
        }
    }


    @Override
    public List<RegionRequest> getAllRegionss() {
        try {
            return regionRepository.findAll()
                    .stream()
                    .map(RegionEntity::toRegionRequestFull)
                    .toList();
        } catch (Exception e) {
            throw new RegionApiException(messageService.getMessage("region.getting.all.error"), e);
        }
    }

    @Override
    public RegionRequest getRegionById(UUID id) {
        RegionEntity regionEntity = regionRepository.findById(id)
                .orElseThrow(() -> new RegionNotFoundException(messageService.getMessage("region.not.found")));
        return regionEntity.toRegionRequest();
    }

    @Override
    public RegionRequest createRegion(RegionRequest regionRequest) {

        if (regionRequest.getId() != null) {
            throw new RegionBadRequestException(messageService.getMessage("region.id.must.null"));
        }

        validateDuplicate(null, regionRequest.getName());
        regionRequest.setName(regionRequest.getName());
        regionRequest.setCountryId(regionRequest.getCountryId());
        regionRequest.setNameRu(regionRequest.getNameRu());
        regionRequest.setNameHy(regionRequest.getNameHy());

        RegionEntity region = new RegionEntity(regionRequest);

        try {
            region = regionRepository.save(region);
        } catch (Exception e) {
            throw new RegionApiException(messageService.getMessage("region.creating.error"), e);
        }

        RegionRequest newRegion = region.toRegionRequest();

        return newRegion;
    }

    @Override
    public RegionRequest updateRegion(UUID id, RegionRequest regionRequest) {
        RegionEntity regionEntity = regionRepository.findById(id)
                .orElseThrow(() -> new RegionNotFoundException(messageService.getMessage("region.not.found")));

        regionEntity.setName(regionRequest.getName());
        regionEntity.setCountryId(regionRequest.getCountryId());
        regionEntity.setNameRu(regionRequest.getNameRu());
        regionEntity.setNameHy(regionRequest.getNameHy());

        try {
            RegionEntity updated = regionRepository.save(regionEntity);
            return updated.toRegionRequest();
        } catch (Exception e) {
            throw new RegionApiException(messageService.getMessage("region.updating.error"), e);
        }
    }

    @Override
    public void deleteRegion(UUID id) {
        regionRepository.findById(id).orElseThrow(() ->
                new RegionNotFoundException(messageService.getMessage("region.not.found")));
        try {
            regionRepository.deleteById(id);
        } catch (Exception e) {
            throw new RegionApiException(messageService.getMessage("region.deleting.error"), e);
        }
    }

    private void validateDuplicate(UUID id, String name) {
        Optional<RegionEntity> existing = regionRepository.findByName(name);

        if (existing.isPresent()) {
            RegionEntity regionEntity = existing.get();

            if (!regionEntity.getId().equals(id)) {
                throw new RegionAlreadyExistsException(
                        messageService.getMessage("region.already.exists")
                );
            }
        }
    }
}