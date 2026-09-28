
package com.registration.repository;

import com.registration.entity.placefinder.Photo;
import com.registration.entity.placefinder.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, UUID> {
    @Query("SELECT cz FROM QuestionEntity cz WHERE cz.id = :id")
    Optional<QuestionEntity> findById(@Param("id") UUID id);
}