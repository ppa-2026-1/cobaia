package com.example.demo.model;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class StubNotificationService implements INotificationService {

  @Override
  public void sendNotification(String destination, String title, String body) {
    // TODO Auto-generated method stub
    System.out.println("Enviando notificacao para " + destination);
  }
  
}
