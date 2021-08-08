package com.rabkov.musictracks.service;

import com.rabkov.musictracks.exception.ServiceException;

import java.util.List;

public interface MailSenderService {
    void send(String email,String subject,String message) throws ServiceException;
    void sendForSeveral(List<String> emailList, String subject, String message) throws ServiceException;

}