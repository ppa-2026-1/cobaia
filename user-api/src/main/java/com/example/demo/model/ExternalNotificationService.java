package com.example.demo.model;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ExternalNotificationService implements INotificationService {

  private final RestTemplate rest;

  public ExternalNotificationService(
    RestTemplate rest
  ) {
    this.rest = rest;
  }
  // Client HTTP
  // Padrões para resiliência de serviços:
  //  Exponential Backoff
  //  Circuit-Breaker

  @Override
  public void sendNotification(String destination, String title, String body) {
    System.out.println("Chamando serviço de notificação externo em http://localhost:8081/api/v1/notification");
    // service discovery
    rest.postForEntity(
      "http://localhost:8081/api/v1/notification", 
      Map.of(
        "recipient", destination,
        "title", title,
        "body", body,
        "media", List.of("mail")
      ),
      Void.class
    );
  }
}
