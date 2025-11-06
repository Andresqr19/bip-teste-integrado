package com.example.ejb.domain.repository;

import com.example.ejb.domain.model.Beneficio;
import com.example.ejb.domain.service.LockingMode;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;

import java.util.Optional;

import static jakarta.persistence.LockModeType.*;

@Stateless
public class BeneficioRepository {

  @PersistenceContext
  private EntityManager em;

  public Optional<Beneficio> findById(Long id, LockingMode mode) {
    if (id == null) return Optional.empty();

    MapLock modeMap = switch (mode) {
      case NONE -> null;
      case OPTIMISTIC -> new MapLock(OPTIMISTIC);
      case OPTIMISTIC_FORCE_INCREMENT -> new MapLock(OPTIMISTIC_FORCE_INCREMENT);
      case PESSIMISTIC_READ -> new MapLock(PESSIMISTIC_READ);
      case PESSIMISTIC_WRITE -> new MapLock(LockModeType.PESSIMISTIC_WRITE);
    };

    Beneficio b = (modeMap == null)
            ? em.find(Beneficio.class, id)
            : em.find(Beneficio.class, id, modeMap.lock);

    return Optional.ofNullable(b);
  }

  public Beneficio save(Beneficio b) {
    if (b.getId() == null) {
      em.persist(b);
      return b;
    }
    return em.merge(b);
  }

  private record MapLock(LockModeType lock) {}
}
