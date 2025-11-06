package com.example.ejb.application.mapper;

import com.example.ejb.application.dto.TransferRequest;

import java.math.BigDecimal;

public class TransferMapper {
  public static void validate(TransferRequest r) {
    if (r == null) throw new IllegalArgumentException("TransferRequest é nulo");
    if (r.getFromId() == null || r.getToId() == null) throw new IllegalArgumentException("IDs from/to obrigatórios");
    if (r.getAmount() == null) throw new IllegalArgumentException("amount é obrigatório");
    if (r.getAmount().signum() <= 0) throw new IllegalArgumentException("amount deve ser positivo");
    if (r.getFromId().equals(r.getToId())) throw new IllegalArgumentException("fromId e toId não podem ser iguais");
  }

  public static BigDecimal normalize(BigDecimal v) {
    return v.setScale(2);
  }
}