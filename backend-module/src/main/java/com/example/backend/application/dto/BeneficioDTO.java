package com.example.backend.application.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BeneficioDTO {
  private Long id;
  private String nome;
  private String descricao;
  private BigDecimal valor;
  private Boolean ativo;
  private Long version;
}
