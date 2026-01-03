package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CoordinatesResultsJsonDto {

    @JsonProperty("common")
    private CoordinatesCommonJsonDto commonJsonDto;

    @JsonProperty("juso")
    private List<CoordinatesJusoJsonDto> jusoJsonDto;

}
