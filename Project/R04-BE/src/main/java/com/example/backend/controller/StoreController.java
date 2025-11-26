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
    public ResponseEntity<Map> postMethodName() {
        storeService.saveStoreInfo();

        return null;
    }

    @GetMapping("/gettest")
    public boolean getMethodName() {
        return true;
    }

}
