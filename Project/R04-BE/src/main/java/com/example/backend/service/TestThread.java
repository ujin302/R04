package com.example.backend.service;

public class TestThread {
    public static void main(String[] args) {
        Thread th1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thred 1");
            }
        });

        Thread th2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread 2");
            }
        });

        th1.start();
        th2.start();
    }
}
