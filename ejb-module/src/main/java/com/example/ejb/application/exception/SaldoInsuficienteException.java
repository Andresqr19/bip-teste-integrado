package com.example.ejb.application.exception;

public class SaldoInsuficienteException extends DomainException {
  public SaldoInsuficienteException(Long id, String valor, String amount) {
    super("Saldo insuficiente no Beneficio " + id + " (valor=" + valor + ", debito=" + amount + ")");
  }
}
