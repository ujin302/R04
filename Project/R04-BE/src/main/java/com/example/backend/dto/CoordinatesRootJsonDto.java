package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CoordinatesRootJsonDto {

    @JsonProperty("results")
    private CoordinatesResultsJsonDto resultsJsonDto;

}