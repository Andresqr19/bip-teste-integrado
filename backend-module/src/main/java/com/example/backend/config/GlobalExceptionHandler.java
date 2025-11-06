package com.example.backend.config;

import com.example.backend.application.exception.NotFoundException;
import com.example.backend.application.exception.ValidationException;
import jakarta.persistence.OptimisticLockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<?> handleNotFound(NotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Map.of("error", ex.getMessage()));
  }

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<?> handleValidation(ValidationException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Map.of("error", ex.getMessage()));
  }

  @ExceptionHandler(OptimisticLockException.class)
  public ResponseEntity<?> handleOptimistic(OptimisticLockException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(Map.of("error", "Conflito de concorrência"));
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<?> handleGeneric(RuntimeException ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Map.of("error", ex.getMessage()));
  }
}