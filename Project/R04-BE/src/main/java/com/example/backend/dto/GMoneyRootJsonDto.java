package com.example.backend.dto;

import java.util.List;
import java.util.Map;

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
public class GMoneyRootJsonDto {

    @JsonProperty("RegionMnyFacltStus")
    private List<GMoneyRegionJsonDto> regionJsonDtos;

}
