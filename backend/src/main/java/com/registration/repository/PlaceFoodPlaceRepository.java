package com.registration.repository;

import com.registration.entity.placefinder.PlaceFoodPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlaceFoodPlaceRepository extends JpaRepository<PlaceFoodPlace, UUID> {

    List<PlaceFoodPlace> findByPlaceId(UUID placeId);

    @Query("SELECT DISTINCT p.place.id FROM PlaceFoodPlace p WHERE p.foodPlace.id = :foodPlaceId")
    List<UUID> findPlaceIdsByFoodPlaceId(@Param("foodPlaceId") UUID foodPlaceId);
}
