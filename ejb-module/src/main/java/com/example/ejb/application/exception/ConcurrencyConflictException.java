package com.example.ejb.application.exception;

public class ConcurrencyConflictException extends DomainException {
  public ConcurrencyConflictException(String message, Throwable cause) {
    super(message + " | cause: " + (cause != null ? cause.getClass().getSimpleName() : "null"));
  }
}
