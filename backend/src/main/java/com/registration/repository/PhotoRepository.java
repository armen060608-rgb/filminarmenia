package com.registration.repository;

import com.registration.entity.placefinder.Photo;
import com.registration.entity.placefinder.Photo360;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, UUID> {

    @Query("SELECT cz.id FROM Photo cz WHERE cz.name = :name")
    UUID findIdByName(@Param("name") String name);

    String findNameById(UUID id);

    Optional<Photo> findByName(String name);

    boolean existsByName(String name);
}
