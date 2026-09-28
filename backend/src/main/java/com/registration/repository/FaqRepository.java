package com.registration.repository;

import com.registration.entity.placefinder.FaqEntity;
import com.registration.entity.placefinder.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface FaqRepository extends JpaRepository<FaqEntity, UUID> {
    @Query("SELECT cz FROM FaqEntity cz WHERE cz.id = :id")
    Optional<FaqEntity> findById(@Param("id") UUID id);
}