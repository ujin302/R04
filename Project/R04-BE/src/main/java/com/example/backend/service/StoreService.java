package com.example.backend.service;

import java.util.ArrayList;
import java.util.List;

import com.example.backend.dto.StoreRequestDto;
import com.example.backend.entity.StoreEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.dto.GMoneyJsonDto;
import com.example.backend.repository.StoreRepository;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    public void saveStoreInfo() {
        boolean isSave = false;
        OpenAPIService openAPIService = new OpenAPIService();

        // 1. 지역화폐
        List<GMoneyJsonDto> list = openAPIService.getGmoneyData();
        // Dto -> StoreDto -> Entity -> DB 저장
        List<StoreEntity> entityList = new ArrayList<>();
        for(GMoneyJsonDto dto : list) {
            StoreRequestDto.StoreSaveDto saveDto = GMoneyJsonDto.toStoreSaveDto(dto);
            StoreEntity entity = StoreRequestDto.StoreSaveDto.toStoreEntity(saveDto);

            entityList.add(entity);
        }
        storeRepository.saveAll(entityList);

        // 2. 온누리상품권

    }

}
