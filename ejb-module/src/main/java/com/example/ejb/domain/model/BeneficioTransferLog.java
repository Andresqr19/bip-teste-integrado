package com.example.ejb.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "beneficio_transfer_log")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeneficioTransferLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "from_id")
  private Long fromId;

  @Column(name = "to_id")
  private Long toId;

  @Column(precision = 15, scale = 2, nullable = false)
  private BigDecimal amount;

  @Column(name = "from_valor_before", precision = 15, scale = 2)
  private BigDecimal fromValorBefore;

  @Column(name = "from_valor_after", precision = 15, scale = 2)
  private BigDecimal fromValorAfter;

  @Column(name = "to_valor_before", precision = 15, scale = 2)
  private BigDecimal toValorBefore;

  @Column(name = "to_valor_after", precision = 15, scale = 2)
  private BigDecimal toValorAfter;

  @Column(nullable = false)
  private Boolean success;

  @Column(length = 255)
  private String message;

  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt = OffsetDateTime.now();
}
