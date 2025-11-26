package com.example.backend.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_store")
public class StoreEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // PK

    // 기본
    @Column
    private String name; // 가맹점 이름

    @Column(name = "category_name", length = 100)
    private String categoryName; // 취급품목/업종명

    @Column(name = "market_name", length = 150)
    private String marketName; // 소속 시장명(온누리)

    // 결제 수단
    @Column(name = "pay_onnuri", nullable = false)
    private boolean payOnnuri = false; // 온누리 상품권

    @Column(name = "pay_local", nullable = false)
    private boolean payGmoney = false; // 지역 화폐

    // 주소
    @Column(name = "addr_road", length = 250)
    private String addrRoad; // 도로명 주소

    @Column(name = "addr_lot", length = 250)
    private String addrLot; // 지번주소

    @Column(name = "zipcode", length = 10)
    private String zipcode; // 우편번호

    @Column(name = "sido", length = 30)
    private String sido; // 시도 Ex. 서울시 or 경기도

    @Column(name = "sigungu", length = 50)
    private String sigungu; // 시군구 Ex. 강남구 or 부천시 오정구

    @Column(name = "eupmyeon", length = 50)
    private String eupmyeon; // 읍면동 Ex. 서초동 or 오정동

    // 좌표
    @Column(name = "lat")
    private Double lat; // 위도

    @Column(name = "lng")
    private Double lng; // 경도

    // 그 외 정보
    @Column(name = "close_day")
    private LocalDate closeDay; // 폐업일자

}
