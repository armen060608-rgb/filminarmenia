package com.registration.repository;

import com.registration.entity.ServiceTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceTypeEntity, UUID> {

    @Query("SELECT st.id FROM ServiceTypeEntity st WHERE st.name = :name")
    UUID findIdByName(@Param("name") String name);

    String findNameById(UUID id);

    Optional<ServiceTypeEntity> findByName(String name);

    boolean existsByName(String name);
}