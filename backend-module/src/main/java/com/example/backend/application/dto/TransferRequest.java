package com.example.backend.application.dto;

import com.example.ejb.domain.service.LockingMode;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest implements Serializable {
  @NonNull
  private Long fromId;

  @NonNull
  private Long toId;

  @NonNull
  private BigDecimal amount;

  @NonNull
  private LockingMode lockingMode;
}
