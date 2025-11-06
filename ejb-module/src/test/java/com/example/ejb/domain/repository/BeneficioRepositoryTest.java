package com.example.ejb.domain.repository;

import com.example.ejb.domain.model.Beneficio;
import com.example.ejb.domain.service.LockingMode;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BeneficioRepositoryTest {

  @InjectMocks
  BeneficioRepository repo;

  @Mock
  EntityManager em;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void deveEncontrarBeneficio() {
    Beneficio b = new Beneficio();
    when(em.find(Beneficio.class, 1L)).thenReturn(b);

    Optional<Beneficio> result = repo.findById(1L, LockingMode.NONE);

    assertTrue(result.isPresent());
  }

  @Test
  void devePersistirNovoBeneficio() {
    Beneficio b = new Beneficio();

    repo.save(b);

    verify(em, times(1)).persist(b);
  }
}
