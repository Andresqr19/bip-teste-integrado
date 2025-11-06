package com.example.ejb.application.dto;

import com.example.ejb.domain.service.LockingMode;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest implements Serializable {
  @NotNull
  private Long fromId;

  @NotNull
  private Long toId;

  @NotNull @Positive
  private BigDecimal amount;

  @NotNull
  private LockingMode lockingMode;
}
