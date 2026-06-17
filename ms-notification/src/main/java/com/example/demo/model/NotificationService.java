package com.example.demo.model;

interface NotificationService {
    
    void sendNotification(String destination,
                          String title,
                          String body);
}
