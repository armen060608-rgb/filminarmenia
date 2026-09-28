package com.registration.entity.placefinder;

import com.registration.constants.DatabaseConstants;
import com.registration.request.FaqRequest;
import com.registration.request.QuestionRequest;
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
@Table(schema = DatabaseConstants.SCHEMA_NAME, name = DatabaseConstants.FAQS_TABLE_NAME)

public class FaqEntity {
    @Id
    @Column(name = "id")
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;

    @Column(name = "question")
    private String question;

    @Column(name = "answer")
    private String answer;

    @Column(name = "status")
    private String status;

    public FaqEntity(FaqRequest request) {
        id = request.getId();
        question = request.getQuestion();
        answer = request.getAnswer();
        status = request.getStatus();

    }
    public FaqRequest toFaqRequest() {
        return new FaqRequest(id, question, answer, status);
    }
}