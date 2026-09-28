package com.registration.request;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequest {
    @Hidden
    private UUID id;
    private String message;
    private String email;
    @Hidden
    private String status;
}