package com.example.ejb.domain.repository;

import com.example.ejb.domain.model.BeneficioTransferLog;
import jakarta.ejb.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class BeneficioTransferLogRepository {

  @PersistenceContext
  private EntityManager em;

  public void save(BeneficioTransferLog log) {
    em.persist(log);
  }
}
