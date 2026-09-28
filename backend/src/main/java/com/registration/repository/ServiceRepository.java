package com.registration.repository;

import com.registration.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, UUID> {

    @Query("SELECT s.id FROM ServiceEntity s WHERE s.name = :name")
    UUID findIdByName(@Param("name") String name);

    Optional<ServiceEntity> findByName(String name);

    boolean existsByName(String name);

    @Query("SELECT s FROM ServiceEntity s WHERE s.serviceTypeId = :serviceTypeId")
    List<ServiceEntity> findByServiceTypeId(@Param("serviceTypeId") UUID serviceTypeId);
}