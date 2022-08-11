package com.mysite.sbb;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class HelloLombok {
    private final String hello;
    private final int lombok;

    public static void main(String[] args) {
        HelloLombok helloLombok = new HelloLombok("hello",2); // 생성자 자동 생성

        System.out.println(helloLombok.getHello());
        System.out.println(helloLombok.getLombok());
    }
}
