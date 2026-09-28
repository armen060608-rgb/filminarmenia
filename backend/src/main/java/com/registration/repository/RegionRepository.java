package com.registration.repository;

import com.registration.entity.placefinder.RegionEntity;
import com.registration.ex.regionexception.RegionNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RegionRepository extends JpaRepository<RegionEntity, UUID> {

    @Query("SELECT c.id FROM RegionEntity c WHERE c.name = :name")
    UUID findIdByName(@Param("name") String name);
    String findNameById(UUID id);
    Optional<RegionEntity> findByName(String name);



    boolean existsByName(String name);
}