package com.example.demo.model;

interface INotificationService {
    
    void sendNotification(String destination,
                          String title,
                          String body);
}
