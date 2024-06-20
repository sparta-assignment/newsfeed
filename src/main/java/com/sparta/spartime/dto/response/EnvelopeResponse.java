package com.sparta.spartime.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnvelopeResponse<T> {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    private int status;
    private String message;
}
