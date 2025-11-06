package com.example.ejb.domain.log;

import com.example.ejb.application.dto.TransferRequest;
import com.example.ejb.domain.service.LockingMode;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BeneficioTransferLogFactoryTest {

  BeneficioTransferLogFactory factory = new BeneficioTransferLogFactory();

  @Test
  void deveCriarLog() {
    TransferRequest req = new TransferRequest(1L, 2L, new BigDecimal("100"), LockingMode.PESSIMISTIC_WRITE);

    var log = factory.buildLog(req,
            new BigDecimal("100"),
            new BigDecimal("500"),
            new BigDecimal("400"),
            new BigDecimal("200"),
            new BigDecimal("300"),
            true,
            "OK"
    );

    assertEquals(1L, log.getFromId());
    assertEquals(2L, log.getToId());
    assertEquals(new BigDecimal("100"), log.getAmount());
    assertEquals("OK", log.getMessage());
    assertTrue(log.getSuccess());
  }
}
