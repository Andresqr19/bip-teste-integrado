package com.example.ejb.infrastructure;

import com.example.ejb.application.dto.TransferRequest;
import com.example.ejb.domain.service.LockingMode;
import com.example.ejb.domain.service.TransferService;
import jakarta.annotation.security.PermitAll;
import jakarta.ejb.*;
import org.jboss.ejb3.annotation.TransactionTimeout;

import java.util.concurrent.TimeUnit;

@Stateless
@Remote(BeneficioEjbServiceRemote.class)
@PermitAll
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionTimeout(value = 30, unit = TimeUnit.SECONDS)
public class BeneficioEjbService implements BeneficioEjbServiceRemote {

  @EJB
  TransferService transferService;

  @Override
  public void transfer(TransferRequest request, LockingMode mode) {
    transferService.transfer(request, mode);
  }
}
