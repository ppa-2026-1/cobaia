package com.example.demo.model.exceptions;

public class NotificationSubsystemUnavailableException extends RuntimeException {
  
  public NotificationSubsystemUnavailableException() {
    super("Notification subsystem is currently unavailable.");
  }

  public NotificationSubsystemUnavailableException(String message) {
    super(message);
  }
    
}
