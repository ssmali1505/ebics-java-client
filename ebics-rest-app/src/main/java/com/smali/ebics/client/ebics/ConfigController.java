package com.smali.ebics.client.ebics;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
public class ConfigController {

	@GetMapping(path = "/create")
	public ResponseEntity<String> createSetup() {
		EbicsConfig ebicsConfig =  EbicsConfig.getInstance();
		try {			
			ebicsConfig.createDefaultUser();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("Configuration create fail");
		}
		return ResponseEntity.ok().body(ebicsConfig.getProperties().toString());
	}

	@GetMapping(path = "/getCurrentUser")
	public ResponseEntity<String> getCurrentUser() {
		EbicsConfig ebicsConfig =  EbicsConfig.getInstance();
		try {			
			ebicsConfig.loadDefaultUser();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("Configuration create fail");
		}
		return ResponseEntity.ok().body(ebicsConfig.getDefaultUser().getName());
	}

}
