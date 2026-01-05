package com.example.backend.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.example.backend.dto.*;
import com.example.backend.entity.StoreEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.backend.repository.StoreRepository;

@Service
@Slf4j
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private AsyncService asyncService;

    // 시간 출력
    private String currentTime() {
        LocalDateTime n = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        String time = n.format(formatter);

        return time;
    }

    // 지역화폐 & 온누리상품권 가맹점 저장
    public void saveStoreInfo() {
        OpenAPIService openAPIService = new OpenAPIService();
        List<StoreEntity> entityList = new ArrayList<>();

        // 1. 지역화폐
        List<GMoneyJsonDto> GMoneylist = openAPIService.getGmoneyData();
        // Dto -> StoreDto -> Entity -> DB 저장
        for(GMoneyJsonDto dto : GMoneylist) {
            StoreRequestDto.StoreSaveDto saveDto = GMoneyJsonDto.toStoreSaveDto(dto);
            StoreEntity entity = StoreRequestDto.StoreSaveDto.toStoreEntity(saveDto);

            entityList.add(entity);

        }

        // 2. 온누리상품권
        List<OnnuriDataJsonDto> onnuriList = openAPIService.getOnnuriData();
        for(OnnuriDataJsonDto dto : onnuriList) {
            StoreRequestDto.StoreSaveDto saveDto = OnnuriDataJsonDto.toStoreSaveDto(dto);
            StoreEntity entity = StoreRequestDto.StoreSaveDto.toStoreEntity(saveDto);

            entityList.add(entity);
        }

        storeRepository.saveAll(entityList);
    }

    // Ver1. 주소 업데이트
    public void updateJuso() {
        OpenAPIService openAPIService = new OpenAPIService();
        List<StoreEntity> entityList = storeRepository.findByLatIsNullAndLngIsNull();
        int c = 0;

        for(StoreEntity entity : entityList) {
            StoreEntity tempEntity = openAPIService.getJusoRootData(entity);

            if(tempEntity != null) {
                c++;
                storeRepository.save(tempEntity);
            }
        }

        log.info("updateJuso 업데이트 대상: " + String.valueOf(c));
    }

    // 주소 업데이트 작업 (Async 구현을 위해 반복되는 부분 함수화)
    private List<StoreEntity> finallyStore(List<StoreEntity> entityList) {
        log.info("finallyStore 시작 " + currentTime());
        OpenAPIService openAPIService = new OpenAPIService();
        List<StoreEntity> resultList = new ArrayList<>();

        for(StoreEntity entity : entityList) {
            log.info("작업 대상 >> " + entity.getId() + " / " + entity.getAddrLot() + " / " + entity.getAddrRoad());
            StoreEntity tempEntity = openAPIService.getJusoRootData(entity);

            if(tempEntity != null) {
                log.info("Update 대상 >> " + tempEntity.getId() + " / " + tempEntity.getLat() + " / " + tempEntity.getLng());
//                storeRepository.save(tempEntity); > 여기서 저장하면 오류 발생 활용 증가
                resultList.add(tempEntity);
            } else {
                resultList.add(entity);
            }
        }

        log.info("finallyStore 종료 " + currentTime());
        return resultList;
    }

    // Ver2. 주소 업데이트 > Thread 클래스 활용
    public void threadUpdateJuso() {
        log.info("threadUpdateJuso 시작 " + currentTime());
        List<StoreEntity> entityList = storeRepository.findByLatIsNullAndLngIsNull();
        int len = entityList.size();

        Thread th1 = new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("thred 1");
                finallyStore(entityList.subList(0, len/2));
            }
        });

        Thread th2 = new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("thread 2");
                finallyStore(entityList.subList(len/2, len));
            }
        });

        th1.start();
        th2.start();
        log.info("threadUpdateJuso 종료 " + currentTime());
    }

    // Ver2. 주소 업데이트 > Callable 인터페이스 활용
    public void callableUpdateJuso() {
        log.info("callableUpdateJuso 시작 " + currentTime());
        // 함수의 목적: Callable 객체를 통해 비동기 구현함. 이를 통해, 처리 속도 감소
        List<StoreEntity> entityList = storeRepository.findByLatIsNullAndLngIsNull();
        int len = entityList.size();

        try {
            // Callable 객체 2개 생성 >
            Callable<List<StoreEntity>> callable1 = new Callable<List<StoreEntity>>() {
                @Override
                public List<StoreEntity> call() throws Exception {
                    log.info("Callable 1");
                    return finallyStore(entityList.subList(0, len/2));
                }
            };

            Callable<List<StoreEntity>> callable2 = new Callable<List<StoreEntity>>() {
                @Override
                public List<StoreEntity> call() throws Exception {
                    log.info("Callable 2");
                    return finallyStore(entityList.subList(len/2, len));
                }
            };

            ExecutorService executorService = Executors.newCachedThreadPool();

            Future<List<StoreEntity>> submit1 = executorService.submit(callable1);
            Future<List<StoreEntity>> submit2 = executorService.submit(callable2);
            List<StoreEntity> result1 = submit1.get();
            List<StoreEntity> result2 = submit2.get();

            executorService.shutdown();

            // 각 결과를 합침 > 한번에 저장
            // 효과: 같은 메모리를 사용할 일이 없어 오류 발생 예방
            List<StoreEntity> finallyResult= new ArrayList<>();
            finallyResult.addAll(result1);
            finallyResult.addAll(result2);

            storeRepository.saveAll(finallyResult);

        } catch (Exception e) {
            System.err.println("callableUpdateJuso >> " + e.getMessage());
        }

        log.info("callableUpdateJuso 종료 " + currentTime());
    }

    // Ver3. 주소 업데이트 > Async 클래스 생성 및 활용
    @Async
    public void asyncUpdateJuso() {
        log.info("callableUpdateJuso Async 시작 " + currentTime());
        List<StoreEntity> entityList = storeRepository.findByLatIsNullAndLngIsNull();
        int len = entityList.size();

        asyncService.asyncFinallyStore(entityList.subList(0, len/2));
        asyncService.asyncFinallyStore(entityList.subList(len/2, len));
        log.info("callableUpdateJuso Async 종료 " + currentTime());

    }

}
