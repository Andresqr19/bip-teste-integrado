package com.example.backend.application.exception;

public class ValidationException extends RuntimeException {
  public ValidationException(String msg) { super(msg); }
}