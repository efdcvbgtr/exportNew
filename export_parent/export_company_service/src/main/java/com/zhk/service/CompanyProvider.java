package com.zhk.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class CompanyProvider {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-*.xml");

        context.start();

        System.in.read();
    }
}
