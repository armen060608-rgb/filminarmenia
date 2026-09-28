package com.registration.repository;

import com.registration.entity.placefinder.ClimateZone;
import com.registration.entity.placefinder.Photo360;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface Photo360Repository extends JpaRepository<Photo360, UUID> {

    @Query("SELECT cz.id FROM Photo360 cz WHERE cz.name = :name")
    UUID findIdByName(@Param("name") String name);

    String findNameById(UUID id);

    Optional<Photo360> findByName(String name);

    boolean existsByName(String name);
}
