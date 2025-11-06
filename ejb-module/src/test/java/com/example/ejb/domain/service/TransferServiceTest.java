package com.example.ejb.domain.service;

import com.example.ejb.application.dto.TransferRequest;
import com.example.ejb.application.exception.ConcurrencyConflictException;
import com.example.ejb.application.exception.SaldoInsuficienteException;
import com.example.ejb.domain.log.BeneficioTransferLogFactory;
import com.example.ejb.domain.log.BeneficioTransferLogger;
import com.example.ejb.domain.model.Beneficio;
import com.example.ejb.domain.repository.BeneficioRepository;
import jakarta.persistence.OptimisticLockException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransferServiceTest {

  @InjectMocks
  TransferService service;

  @Mock
  BeneficioRepository beneficioRepository;

  @Mock
  BeneficioTransferLogger logger;

  @Mock
  BeneficioTransferLogFactory logFactory;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  private Beneficio mockBeneficio(Long id, BigDecimal saldo) {
    Beneficio b = new Beneficio();
    b.setId(id);
    b.setAtivo(true);
    b.setValor(saldo);
    return b;
  }

  @Test
  void deveTransferirComSucesso() {
    TransferRequest req = new TransferRequest(1L, 2L, new BigDecimal("100"), LockingMode.PESSIMISTIC_WRITE);

    Beneficio from = mockBeneficio(1L, new BigDecimal("500"));
    Beneficio to = mockBeneficio(2L, new BigDecimal("200"));

    when(beneficioRepository.findById(eq(1L), any())).thenReturn(java.util.Optional.of(from));
    when(beneficioRepository.findById(eq(2L), any())).thenReturn(java.util.Optional.of(to));

    service.transfer(req, LockingMode.PESSIMISTIC_WRITE);

    verify(beneficioRepository, times(2)).save(any());
    verify(logger, times(1)).save(any());
    assertEquals(new BigDecimal("400.00"), from.getValor());
    assertEquals(new BigDecimal("300.00"), to.getValor());
  }

  @Test
  void deveFalharSaldoInsuficiente() {
    TransferRequest req = new TransferRequest(1L, 2L, new BigDecimal("600"), LockingMode.PESSIMISTIC_WRITE);

    Beneficio from = mockBeneficio(1L, new BigDecimal("500"));
    Beneficio to = mockBeneficio(2L, new BigDecimal("200"));

    when(beneficioRepository.findById(eq(1L), any())).thenReturn(java.util.Optional.of(from));
    when(beneficioRepository.findById(eq(2L), any())).thenReturn(java.util.Optional.of(to));

    assertThrows(SaldoInsuficienteException.class,
            () -> service.transfer(req, LockingMode.PESSIMISTIC_WRITE)
    );

    verify(logger, never()).save(any());
  }

  @Test
  void deveTratarOptimisticLock() {
    TransferRequest req = new TransferRequest(1L, 2L, new BigDecimal("100"), LockingMode.OPTIMISTIC);

    Beneficio from = mockBeneficio(1L, new BigDecimal("500"));
    Beneficio to = mockBeneficio(2L, new BigDecimal("200"));

    when(beneficioRepository.findById(eq(1L), any())).thenReturn(java.util.Optional.of(from));
    when(beneficioRepository.findById(eq(2L), any())).thenReturn(java.util.Optional.of(to));

    doThrow(new OptimisticLockException()).when(beneficioRepository).save(from);

    assertThrows(ConcurrencyConflictException.class,
            () -> service.transfer(req, LockingMode.OPTIMISTIC)
    );

    verify(logger, times(1)).save(any());
  }
}
