package com.example.ejb.domain.service;

public enum LockingMode {
  NONE,
  OPTIMISTIC,
  OPTIMISTIC_FORCE_INCREMENT,
  PESSIMISTIC_READ,
  PESSIMISTIC_WRITE
}