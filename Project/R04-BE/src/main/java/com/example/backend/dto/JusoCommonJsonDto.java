package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JusoCommonJsonDto {

    @JsonProperty("errorMessage")
    private String errorMsg;

    @JsonProperty("countPerPage")
    private int countPg;

    @JsonProperty("totalCount")
    private int totalCount;

    @JsonProperty("errorCode")
    private int errorCode;

    @JsonProperty("currentPage")
    private int currentPg;

}
