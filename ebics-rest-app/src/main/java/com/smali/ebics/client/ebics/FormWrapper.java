package com.smali.ebics.client.ebics;

import org.ebics.client.order.EbicsService;
import org.springframework.web.multipart.MultipartFile;

public class FormWrapper {
    private MultipartFile file;
    private String title;
    private String description;
    private BTFService service;
    public MultipartFile getFile() {
        return file;
    }
    public void setFile(MultipartFile file) {
        this.file = file;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public BTFService getService() {
        return service;
    }
    public void setService(BTFService service) {
        this.service = service;
    }   
    
}
