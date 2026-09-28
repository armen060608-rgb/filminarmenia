package com.registration.repository;

import com.registration.entity.placefinder.PlacePhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlacePhotoRepository extends JpaRepository<PlacePhoto, UUID> {
    List<PlacePhoto> findByPlaceId(UUID placeId);
}
