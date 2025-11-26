package com.example.backend.dto;

import com.example.backend.entity.StoreEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class StoreRequestDto {

    // 가맹점 정보 저장
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StoreSaveDto {
        private String name; // 가맹점 이름
        private String categoryName; // 취급품목/업종명
        private String marketName; // 소속 시장명(온누리)

        // 결제 수단
        private boolean payOnnuri = false; // 온누리 상품권
        private boolean payGmoney = false; // 지역 화폐

        // 주소
        private String addrRoad; // 도로명 주소
        private String addrLot; // 지번번호
        private String zipcode; // 우편번호
        private String sido; // 시도 Ex. 서울시 or 경기도
        private String sigungu; // 시군구 Ex. 강남구 or 부천시 오정구
        private String eupmyeon; // 읍면동 Ex. 서초동 or 오정동

        private Double lat; // 위도
        private Double lng; // 경도

        public static StoreEntity toStoreEntity(StoreSaveDto dto) {
            StoreEntity entity = new StoreEntity();

            entity.setName(dto.getName());
            entity.setCategoryName(dto.getCategoryName());
            entity.setMarketName(dto.getMarketName());

            // 결제 수단
            entity.setPayOnnuri(dto.isPayOnnuri());
            entity.setPayGmoney(dto.isPayGmoney());

            // 주소
            entity.setAddrRoad(dto.getAddrRoad());
            entity.setAddrLot(dto.getAddrLot());
            entity.setZipcode(dto.getZipcode());
            entity.setSido(dto.getSido());
            entity.setSigungu(dto.getSigungu());
            entity.setEupmyeon(dto.getEupmyeon());

            // 좌표
            entity.setLat(dto.getLat());
            entity.setLng(dto.getLng());
            return  entity;
        }
    }

}
