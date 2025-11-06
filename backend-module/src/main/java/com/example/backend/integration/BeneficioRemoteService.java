package com.example.backend.integration;

import com.example.backend.application.dto.TransferRequest;
import com.example.backend.application.exception.NotFoundException;
import com.example.ejb.domain.service.LockingMode;
import com.example.ejb.infrastructure.BeneficioEjbServiceRemote;
import org.springframework.stereotype.Service;

import javax.naming.InitialContext;

@Service
public class BeneficioRemoteService {

  private final InitialContext ctx;
  private static final String JNDI_NAME =
          "ejb:/ejb-module-1.0.0-SNAPSHOT/BeneficioEjbService!com.example.ejb.infrastructure.BeneficioEjbServiceRemote";

  public BeneficioRemoteService(InitialContext ctx) {
    this.ctx = ctx;
  }

  BeneficioEjbServiceRemote lookup() throws Exception {
    return (BeneficioEjbServiceRemote) ctx.lookup(JNDI_NAME);
  }

  public void transferir(TransferRequest req) {
    try {
      var ejbReq = com.example.ejb.application.dto.TransferRequest.builder()
              .fromId(req.getFromId())
              .toId(req.getToId())
              .amount(req.getAmount())
              .lockingMode(req.getLockingMode())
              .build();

      lookup().transfer(
              ejbReq,
              ejbReq.getLockingMode()!= null ? ejbReq.getLockingMode() : LockingMode.NONE
      );

    } catch (Exception e) {
      System.out.println(req.toString());
      System.out.println(e);
      throw new NotFoundException(e.getMessage());
    }
  }
}

