package com.registration.service;

import com.registration.entity.placefinder.CountryEntity;
import com.registration.ex.countryexception.CountryAlreadyExistException;
import com.registration.ex.countryexception.CountryApiException;
import com.registration.ex.countryexception.CountryBadRequestException;
import com.registration.ex.countryexception.CountryNotFoundException;
import com.registration.helper.MessageService;
import com.registration.repository.CountryRepository;
import com.registration.request.CountryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CountrySpringJpa implements CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private RegionService regionService;

    @Autowired
    private MessageService messageService;

    @Override
    public List<CountryRequest> getAllCountries() {
        try {
            return countryRepository.findAll()
                    .stream()
                    .map(CountryEntity::toCountryRequest)
                    .toList();
        } catch (Exception e) {
            throw new CountryApiException(messageService.getMessage("country.getting.all.error"), e);
        }
    }


    @Override
    public List<CountryRequest> getAllCountriess() {
        try {
            return countryRepository.findAll()
                    .stream()
                    .map(CountryEntity::toCountryRequestFull)
                    .toList();
        } catch (Exception e) {
            throw new CountryApiException(messageService.getMessage("country.getting.all.error"), e);
        }
    }

    @Override
    public CountryRequest getCountryById(UUID id) {
        CountryEntity countryEntity = countryRepository.findById(id)
                .orElseThrow(() -> new CountryNotFoundException(messageService.getMessage("country.not.found")));
        return countryEntity.toCountryRequest();
    }

    @Override
    public CountryRequest createCountry(CountryRequest countryRequest) {

        if (countryRequest.getId() != null) {
            throw new CountryBadRequestException(messageService.getMessage("country.id.must.null"));
        }

        validateDuplicate(null, countryRequest.getName());
        countryRequest.setName(countryRequest.getName());
        countryRequest.setNameRu(countryRequest.getNameRu());
        countryRequest.setNameHy(countryRequest.getNameHy());

        CountryEntity country = new CountryEntity(countryRequest);

        try {
            country = countryRepository.save(country);
        } catch (Exception e) {
            throw new CountryApiException(messageService.getMessage("country.creating.error"), e);
        }

        CountryRequest newCountry = country.toCountryRequest();

        return newCountry;
    }

    @Override
    public CountryRequest updateCountry(UUID id, CountryRequest countryRequest) {
        CountryEntity countryEntity = countryRepository.findById(id)
                .orElseThrow(() -> new CountryNotFoundException(messageService.getMessage("country.not.found")));

        countryEntity.setName(countryRequest.getName());
        countryEntity.setNameRu(countryRequest.getNameRu());
        countryEntity.setNameHy(countryRequest.getNameHy());

        try {
            CountryEntity updated = countryRepository.save(countryEntity);
            return updated.toCountryRequest();
        } catch (Exception e) {
            throw new CountryApiException(messageService.getMessage("country.updating.error"), e);
        }
    }

    @Override
    public void deleteCountry(UUID id) {
        countryRepository.findById(id).orElseThrow(() ->
                new CountryNotFoundException(messageService.getMessage("country.not.found")));
        try {
            countryRepository.deleteById(id);
        } catch (Exception e) {
            throw new CountryApiException(messageService.getMessage("country.deleting.error"), e);
        }
    }

    private void validateDuplicate(UUID id, String name) {
        Optional<CountryEntity> existing = countryRepository.findByName(name);

        if (existing.isPresent()) {
            CountryEntity countryEntity = existing.get();

            if (!countryEntity.getId().equals(id)) {
                throw new CountryAlreadyExistException(
                        messageService.getMessage("country.already.exists")
                );
            }
        }
    }
}