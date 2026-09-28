package com.registration.repository;

import com.registration.entity.placefinder.PlaceNearbyHotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlaceNearbyHotelRepository extends JpaRepository<PlaceNearbyHotel, UUID> {

    List<PlaceNearbyHotel> findByPlaceId(UUID placeId);

    @Query("SELECT DISTINCT p.place.id FROM PlaceNearbyHotel p WHERE p.hotel.id = :hotelId")
    List<UUID> findPlaceIdsByHotelId(@Param("hotelId") UUID hotelId);
}
