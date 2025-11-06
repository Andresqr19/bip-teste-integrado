package com.example.ejb.domain.log;

import com.example.ejb.application.dto.TransferRequest;
import com.example.ejb.domain.model.BeneficioTransferLog;
import jakarta.ejb.Stateless;

import java.math.BigDecimal;

@Stateless
public class BeneficioTransferLogFactory {

  public BeneficioTransferLog buildLog(TransferRequest req,
                                        BigDecimal amount,
                                        BigDecimal fromBefore,
                                        BigDecimal fromAfter,
                                        BigDecimal toBefore,
                                        BigDecimal toAfter,
                                        boolean success,
                                        String message) {
    BeneficioTransferLog log = new BeneficioTransferLog();
    log.setFromId(req.getFromId());
    log.setToId(req.getToId());
    log.setAmount(amount);
    log.setFromValorBefore(fromBefore);
    log.setFromValorAfter(fromAfter);
    log.setToValorBefore(toBefore);
    log.setToValorAfter(toAfter);
    log.setSuccess(success);
    log.setMessage(message);
    return log;
  }
}
