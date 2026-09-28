package com.registration.entity.placefinder;

import com.registration.constants.DatabaseConstants;
import com.registration.request.CountryRequest;
import com.registration.request.QuestionRequest;
import com.registration.request.RegionRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = DatabaseConstants.SCHEMA_NAME, name = DatabaseConstants.QUESTIONS_TABLE_NAME)
public class QuestionEntity {
    @Id
    @Column(name = "id")
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;

    @Column(name = "message")
    private String message;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private String status;

    public QuestionEntity(QuestionRequest request) {
        id = request.getId();
        message = request.getMessage();
        email = request.getEmail();
        status = request.getStatus();

    }
    public QuestionRequest toQuestionRequest() {
        return new QuestionRequest(id, message, email, status);

    }
}