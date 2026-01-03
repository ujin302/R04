package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OnnuriDataJsonDto {

    @JsonProperty("가맹점명")
    private String nane;

    @JsonProperty("등록년도")
    private int year;

    @JsonProperty("모바일 취급여부")
    private String isMoblie;

    @JsonProperty("소속 시장명(또는 상점가)")
    private String sijang;

    @JsonProperty("소재지")
    private String addr;

    @JsonProperty("지류 취급여부")
    private String isPaper;

    @JsonProperty("충전식 카드 취급여부")
    private String isCard;

    @JsonProperty("취급품목")
    private String categoryName;

    public static StoreRequestDto.StoreSaveDto toStoreSaveDto(OnnuriDataJsonDto dto) {
        StoreRequestDto.StoreSaveDto storeDto = new StoreRequestDto.StoreSaveDto();

        storeDto.setName(dto.getNane()); // 상호명
        storeDto.setCategoryName(dto.getCategoryName()); // 업종명
        storeDto.setMarketName(dto.getSijang()); // 온누리 시장명 없음

        // 결제 수단
        storeDto.setPayOnnuri(true);  // 온누리 아님
        storeDto.setPayGmoney(false);   // 지역화폐 데이터이므로 true

        // 주소
//        storeDto.setAddrRoad(dto.getRefineRoadnmAddr()); // 도로명 주소
        storeDto.setAddrLot(dto.getAddr());   // 지번 주소
//        storeDto.setZipcode(dto.getRefineZipno());       // 우편번호

        // 시도/시군구
//        storeDto.setSido("경기도");            // 데이터에 시도 정보 없음
//        storeDto.setSigungu(dto.getSigunNm()); // 시군구 정보
//        storeDto.setEupmyeon(null);            // 읍면동 정보 없음

        // 좌표
//        storeDto.setLat(dto.getRefineWgs84Lat()); // 위도
//        storeDto.setLng(dto.getRefineWgs84Logt()); // 경도

        return storeDto;
    }
}

