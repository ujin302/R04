package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CoordinatesJusoJsonDto {

    @JsonProperty("buldMnnm")
    private String buldMnnm;

    @JsonProperty("rnMgtSn")
    private String rnMgtSn;

    @JsonProperty("bdNm")
    private String bdNm;

    @JsonProperty("entX")
    private String entX;

    @JsonProperty("entY")
    private String entY;

    @JsonProperty("admCd")
    private String admCd;

    @JsonProperty("bdMgtSn")
    private String bdMgtSn;

    @JsonProperty("buldSlno")
    private String buldSlno;

    @JsonProperty("udrtYn")
    private String udrtYn;

}