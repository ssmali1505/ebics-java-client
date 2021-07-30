package com.smali.ebics.client;

import com.smali.ebics.client.ebics.EbicsConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EbicsClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbicsClientApplication.class, args);
		EbicsConfig.getInstance();
	}

}
