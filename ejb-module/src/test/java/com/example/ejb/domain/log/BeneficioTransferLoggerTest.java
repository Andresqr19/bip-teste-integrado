package com.example.ejb.domain.log;

import com.example.ejb.domain.model.BeneficioTransferLog;
import com.example.ejb.domain.repository.BeneficioTransferLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;

class BeneficioTransferLoggerTest {

  @InjectMocks
  BeneficioTransferLogger service;

  @Mock
  BeneficioTransferLogRepository repo;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void deveSalvarLog() {
    BeneficioTransferLog log = new BeneficioTransferLog();

    service.save(log);

    verify(repo, times(1)).save(log);
  }
}
