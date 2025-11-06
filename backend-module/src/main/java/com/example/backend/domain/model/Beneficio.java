package com.example.backend.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Beneficio {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(length = 100, nullable = false)
  private String nome;
  @Column(length = 255)
  private String descricao;
  @Column(nullable = false, precision = 15, scale = 2)
  private BigDecimal valor;
  @Column(nullable = false)
  private Boolean ativo = true;
  @Version
  private Long version;
}
