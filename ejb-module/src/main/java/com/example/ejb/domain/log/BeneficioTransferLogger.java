package com.example.ejb.domain.log;

import com.example.ejb.domain.model.BeneficioTransferLog;
import com.example.ejb.domain.repository.BeneficioTransferLogRepository;
import jakarta.ejb.*;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BeneficioTransferLogger {

  @EJB
  BeneficioTransferLogRepository logRepository;

  public  void save(BeneficioTransferLog log) {
    logRepository.save(log);
  }

}