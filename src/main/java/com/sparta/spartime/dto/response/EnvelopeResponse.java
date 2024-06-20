package com.sparta.spartime.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnvelopeResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;
    private int status;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String path;

    private EnvelopeResponse(Object data, int status, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
    }

    private EnvelopeResponse(int status, String message, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
    }


    public static ResponseEntity<?> wrap(Object data, HttpStatus status, String message) {
        return ResponseEntity
                .status(status == HttpStatus.NO_CONTENT ? HttpStatus.OK : status)
                .body(
                        new EnvelopeResponse(
                                data,
                                status.value(),
                                message
                        )
                );
    }

    public static ResponseEntity<?> wrapError(HttpStatus status, String message, String path) {
        return ResponseEntity
                .status(status == HttpStatus.NO_CONTENT ? HttpStatus.OK : status)
                .body(
                        new EnvelopeResponse(
                                status.value(),
                                message,
                                path
                        )
                );
    }
}
