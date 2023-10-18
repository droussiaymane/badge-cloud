package com.vizzibl.velocityservice.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

public interface MailService {

    public void sendEmail(String emailDestination, String message, String objet, FileSystemResource file);
}
