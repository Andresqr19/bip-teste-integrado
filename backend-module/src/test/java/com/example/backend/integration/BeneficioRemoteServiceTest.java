package com.example.backend.integration;

import com.example.backend.application.dto.TransferRequest;
import com.example.ejb.domain.service.LockingMode;
import com.example.ejb.infrastructure.BeneficioEjbServiceRemote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.naming.InitialContext;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BeneficioRemoteServiceTest {

  private InitialContext ctx;
  private BeneficioEjbServiceRemote ejbMock;
  private BeneficioRemoteService service;

  @BeforeEach
  void setup() throws Exception {
    ctx = Mockito.mock(InitialContext.class);
    ejbMock = Mockito.mock(BeneficioEjbServiceRemote.class);

    service = new BeneficioRemoteService(ctx);

    when(ctx.lookup(any(String.class))).thenReturn(ejbMock);
  }

  @Test
  void deveChamarEjbTransferComSucesso() throws Exception {
    TransferRequest req = new TransferRequest();
    req.setFromId(1L);
    req.setToId(2L);
    req.setAmount(BigDecimal.valueOf(100));
    req.setLockingMode(LockingMode.NONE);

    service.transferir(req);

    verify(ejbMock, times(1))
            .transfer(any(), eq(LockingMode.NONE));
  }

  @Test
  void deveLancarRuntimeExceptionQuandoLookupFalha() throws Exception {
    when(ctx.lookup(any(String.class))).thenThrow(new RuntimeException("Lookup error"));

    TransferRequest req = new TransferRequest();
    req.setFromId(1L);
    req.setToId(2L);
    req.setAmount(BigDecimal.valueOf(100));

    assertThrows(RuntimeException.class, () -> service.transferir(req));
  }
}
