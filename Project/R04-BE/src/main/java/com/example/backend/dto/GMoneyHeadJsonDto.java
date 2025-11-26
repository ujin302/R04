package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GMoneyHeadJsonDto {

    @JsonProperty("list_total_count")
    private Long count;

    @JsonProperty("RESULT")
    private GMoneyReultJsonDto reultJsonDto;

    @JsonProperty("api_version")
    private String version;
}
