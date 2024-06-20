package com.sparta.spartime.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnvelopeResponse<T> {
    private T data;
    private int status;
    private String message;
}
