package com.example.ejb.infrastructure;

import com.example.ejb.application.dto.TransferRequest;
import com.example.ejb.domain.service.LockingMode;
import java.rmi.RemoteException;

public interface BeneficioEjbServiceRemote {
  void transfer(TransferRequest request, LockingMode mode) throws RemoteException;
}
