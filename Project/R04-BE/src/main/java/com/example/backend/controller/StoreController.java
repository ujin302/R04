package com.example.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.service.StoreService;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@Slf4j
@RequestMapping("/api/store")
public class StoreController {
    @Autowired
    private StoreService storeService;

    @PostMapping("/info")
    public ResponseEntity<Map> info() {
        log.info("info 호출");
        storeService.saveStoreInfo();

        return null;
    }

    @PostMapping("/juso")
    public ResponseEntity<Map> juso() {
        log.info("juso 호출");
        storeService.updateJuso();

        return null;
    }

    @PostMapping("/thread/juso")
    public ResponseEntity<Map> threadJuso() {
        log.info("thread juso 호출");
        storeService.threadUpdateJuso();

        return null;
    }

    @PostMapping("/callable/juso")
    public ResponseEntity<Map> callableJuso() {
        log.info("callable juso 호출");
        storeService.callableUpdateJuso();

        return null;
    }

    @PostMapping("/callable/juso/asnyc")
    public ResponseEntity<Map> asyncJuso() {
        log.info("callable Async juso 호출");
        storeService.asyncUpdateJuso();

        return null;
    }

}
