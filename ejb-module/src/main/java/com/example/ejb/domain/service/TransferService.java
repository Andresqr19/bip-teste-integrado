package com.example.ejb.domain.service;

import com.example.ejb.application.dto.TransferRequest;
import com.example.ejb.application.exception.ConcurrencyConflictException;
import com.example.ejb.application.exception.SaldoInsuficienteException;
import com.example.ejb.application.mapper.TransferMapper;
import com.example.ejb.domain.log.BeneficioTransferLogFactory;
import com.example.ejb.domain.log.BeneficioTransferLogger;
import com.example.ejb.domain.model.Beneficio;
import com.example.ejb.domain.repository.BeneficioRepository;
import jakarta.ejb.*;
import jakarta.persistence.OptimisticLockException;

import java.math.BigDecimal;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TransferService {

  @EJB
  BeneficioRepository beneficioRepository;

  @EJB
  BeneficioTransferLogger logger;

  @EJB
  BeneficioTransferLogFactory logFactory;

  public void transfer(TransferRequest req, LockingMode mode) {
    TransferMapper.validate(req);
    final BigDecimal amount = TransferMapper.normalize(req.getAmount());

    final LockingMode lock = (mode == null) ? LockingMode.PESSIMISTIC_WRITE : mode;

    Beneficio from = beneficioRepository.findById(req.getFromId(), lock)
            .orElseThrow(() -> new IllegalArgumentException("Beneficio FROM não encontrado: " + req.getFromId()));
    Beneficio to = beneficioRepository.findById(req.getToId(), lock)
            .orElseThrow(() -> new IllegalArgumentException("Beneficio TO não encontrado: " + req.getToId()));

    if (!Boolean.TRUE.equals(from.getAtivo()) || !Boolean.TRUE.equals(to.getAtivo())) {
      throw new IllegalArgumentException("Ambos os benefícios devem estar ativos");
    }

    BigDecimal fromBefore = from.getValor();
    BigDecimal toBefore = to.getValor();

    if (fromBefore.compareTo(amount) < 0) {
      throw new SaldoInsuficienteException(from.getId(), fromBefore.toPlainString(), amount.toPlainString());
    }

    try {
      from.debitar(amount);
      to.creditar(amount);

      beneficioRepository.save(from);
      beneficioRepository.save(to);

      logger.save(logFactory.buildLog(req, amount, fromBefore, from.getValor(), toBefore, to.getValor(), true, "OK"));

    } catch (OptimisticLockException ole) {
      logger.save(logFactory.buildLog(req, amount, fromBefore, null, toBefore, null, false, "Optimistic Lock"));
      throw new ConcurrencyConflictException("Conflito de concorrência (optimistic lock)", ole);

    } catch (RuntimeException ex) {
      logger.save(logFactory.buildLog(req, amount, fromBefore, null, toBefore, null, false, ex.getMessage()));
      throw ex;
    }
  }

}
