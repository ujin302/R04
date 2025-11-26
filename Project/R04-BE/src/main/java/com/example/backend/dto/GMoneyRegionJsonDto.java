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
public class GMoneyRegionJsonDto {

    // head객체 > 배열
    @JsonProperty("head")
    // private List<Map<String, Object>> headJsonDto;\
    private List<GMoneyHeadJsonDto> headJsonDto;

    // row객체 > 배열
    @JsonProperty("row")
    private List<GMoneyJsonDto> row;
    // private List<Map<String, Object>> row;

}
