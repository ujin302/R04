package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CoordinatesCommonJsonDto {

    @JsonProperty("errorMessage")
    private String errorMsg;

    @JsonProperty("totalCount")
    private int totalCount;

    @JsonProperty("errorCode")
    private int errorCode;

}