package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
public class GMoneyJsonDto {

    @JsonProperty("CMPNM_NM")
    private String cmpnmNm; // 상호명

    @JsonProperty("INDUTYPE_NM")
    private String indutypeNm; // 업종명

    @JsonProperty("REFINE_LOTNO_ADDR")
    private String refineLotnoAddr; // 정제 지번 주소

    @JsonProperty("REFINE_ROADNM_ADDR")
    private String refineRoadnmAddr; // 정제 도로명 주소

    @JsonProperty("REFINE_ZIPNO")
    private String refineZipno; // 정제 우편번호

    @JsonProperty("REFINE_WGS84_LOGT")
    private Double refineWgs84Logt; // 정제 WGS84 경도

    @JsonProperty("REFINE_WGS84_LAT")
    private Double refineWgs84Lat; // 정제 WGS84 위도

    @JsonProperty("SIGUN_NM")
    private String sigunNm; // 시군명

    @JsonProperty("BIZREGNO")
    private String bizregno; // 사업자등록번호

    @JsonProperty("INDUTYPE_CD")
    private String indutypeCd; // 업종 코드

    @JsonProperty("FRCS_NO")
    private String frcsNo; // 가맹점 번호

    @JsonProperty("LEAD_TAX_MAN_STATE")
    private String leadTaxManState; // 휴폐업 상태

    @JsonProperty("CLSBIZ_DAY")
    private String clsbizDay; // 폐업일자 (null 가능)

    @JsonProperty("LEAD_TAX_MAN_STATE_CD")
    private String leadTaxManStateCd; // 휴폐업 상태 코드

    public static StoreRequestDto.StoreSaveDto toStoreSaveDto(GMoneyJsonDto dto) {
        StoreRequestDto.StoreSaveDto storeDto = new StoreRequestDto.StoreSaveDto();

        storeDto.setName(dto.getCmpnmNm()); // 상호명
        storeDto.setCategoryName(dto.getIndutypeNm()); // 업종명
        storeDto.setMarketName(null);                  // 온누리 시장명 없음

        // 결제 수단
        storeDto.setPayOnnuri(false);  // 온누리 아님
        storeDto.setPayGmoney(true);   // 지역화폐 데이터이므로 true

        // 주소
        storeDto.setAddrRoad(dto.getRefineRoadnmAddr()); // 도로명 주소
        storeDto.setAddrLot(dto.getRefineLotnoAddr());   // 지번 주소
        storeDto.setZipcode(dto.getRefineZipno());       // 우편번호

        // 시도/시군구
        storeDto.setSido("경기도");            // 데이터에 시도 정보 없음
        storeDto.setSigungu(dto.getSigunNm()); // 시군구 정보
        storeDto.setEupmyeon(null);            // 읍면동 정보 없음

        // 좌표
        storeDto.setLat(dto.getRefineWgs84Lat()); // 위도
        storeDto.setLng(dto.getRefineWgs84Logt()); // 경도

        return  storeDto;
    }

}
