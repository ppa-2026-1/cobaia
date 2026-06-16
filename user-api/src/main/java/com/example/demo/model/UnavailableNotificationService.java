package com.example.demo.model;

import org.springframework.stereotype.Component;

import com.example.demo.model.exceptions.NotificationSubsystemUnavailableException;

@Component("unavailable-notification")
class UnavailableNotificationService implements NotificationService {

    @Override
    public void sendNotification(String destination, 
        String title, String body) throws NotificationSubsystemUnavailableException {

       throw new NotificationSubsystemUnavailableException();
    }
}
