package com.example.backend.service;

import com.example.backend.entity.StoreEntity;
import com.example.backend.repository.StoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AsyncService { // Async 클래스 생성하여 Async 구현
    // 비동기를 구현 규칙
    // 여기서 의미하는 함수: Async 하고자 하는 함수
    // 1. public void OOOO
    // 2. 같은 클래스 함수 호출 시, Async 정상 동작X -> 다른 클래스의 함수 호출

    @Autowired
    private StoreRepository storeRepository;

    private String currentTime() {
        LocalDateTime n = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        String time = n.format(formatter);

        return time;
    }

    @Async
    public void asyncFinallyStore(List<StoreEntity> entityList){
        log.info("async FinallyStore 시작 " + currentTime());
        OpenAPIService openAPIService = new OpenAPIService();
        List<StoreEntity> resultList = new ArrayList<>();

        for(StoreEntity entity : entityList) {
            log.info("작업 대상 >> " + entity.getId() + " / " + entity.getAddrLot() + " / " + entity.getAddrRoad());
            StoreEntity tempEntity = openAPIService.getJusoRootData(entity);

            if(tempEntity != null) {
                log.info("Update 대상 >> " + tempEntity.getId() + " / " + tempEntity.getLat() + " / " + tempEntity.getLng());
                storeRepository.save(tempEntity); // > 여기서 저장하면 오류 발생 활용 증가
//                resultList.add(tempEntity);
            } else {
//                resultList.add(entity);
            }
        }

//        storeRepository.saveAll(resultList);
        log.info("async FinallyStore 종료 " + currentTime());
    }

}
