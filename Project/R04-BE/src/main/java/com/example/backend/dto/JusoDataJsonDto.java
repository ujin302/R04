package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JusoDataJsonDto {

    @JsonProperty("detBdNmList")
    private String detBdNmList;

    @JsonProperty("engAddr")
    private String engAddr;

    @JsonProperty("rn")
    private String rn;

    @JsonProperty("emdNm")
    private String emdNm;

    @JsonProperty("zipNo")
    private String zipNo;

    @JsonProperty("roadAddrPart2")
    private String roadAddrPart2;

    @JsonProperty("emdNo")
    private String emdNo;

    @JsonProperty("sggNm")
    private String sggNm;

    @JsonProperty("jibunAddr")
    private String jibunAddr;

    @JsonProperty("siNm")
    private String siNm;

    @JsonProperty("roadAddrPart1")
    private String roadAddrPart1;

    @JsonProperty("bdNm")
    private String bdNm;

    @JsonProperty("admCd")
    private String admCd;

    @JsonProperty("udrtYn")
    private String udrtYn;

    @JsonProperty("lnbrMnnm")
    private String lnbrMnnm;

    @JsonProperty("roadAddr")
    private String roadAddr;

    @JsonProperty("lnbrSlno")
    private String lnbrSlno;

    @JsonProperty("buldMnnm")
    private String buldMnnm;

    @JsonProperty("bdKdcd")
    private String bdKdcd;

    @JsonProperty("liNm")
    private String liNm;

    @JsonProperty("rnMgtSn")
    private String rnMgtSn;

    @JsonProperty("mtYn")
    private String mtYn;

    @JsonProperty("bdMgtSn")
    private String bdMgtSn;

    @JsonProperty("buldSlno")
    private String buldSlno;
}
