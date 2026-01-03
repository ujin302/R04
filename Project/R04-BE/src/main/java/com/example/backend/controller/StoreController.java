package com.example.backend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.backend.service.StoreService;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/store")
public class StoreController {
    @Autowired
    private StoreService storeService;

    @PostMapping("/info")
    public ResponseEntity<Map> info() {
        System.out.println("info 호출");
        storeService.saveStoreInfo();

        return null;
    }

    @PostMapping("/juso")
    public ResponseEntity<Map> juso() {
        System.out.println("juso 호출");
        storeService.updateJuso();

        return null;
    }

    @PostMapping("/thread/juso")
    public ResponseEntity<Map> threadJuso() {
        System.out.println("thread juso 호출");
        storeService.threadUpdateJuso();

        return null;
    }

    @PostMapping("/callable/juso")
    public ResponseEntity<Map> callableJuso() {
        System.out.println("callable juso 호출");
        storeService.callableUpdateJuso();

        return null;
    }

//    @PostMapping("/test")
//    public ResponseEntity<Map> jusotest() {
//        System.out.println("jusotest 호출");
//        storeService.test();
//
//        return null;
//    }

}
