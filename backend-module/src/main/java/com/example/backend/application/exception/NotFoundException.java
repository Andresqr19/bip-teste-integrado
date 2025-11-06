package com.example.backend.application.exception;

public class NotFoundException extends RuntimeException {
  public NotFoundException(String msg) { super(msg); }
}