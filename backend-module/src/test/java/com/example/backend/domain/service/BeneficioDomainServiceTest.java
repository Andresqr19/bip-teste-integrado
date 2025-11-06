package com.example.backend.domain.service;

import com.example.backend.domain.model.Beneficio;
import com.example.backend.domain.repository.BeneficioRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BeneficioDomainServiceTest {

  private BeneficioRepositoryPort repository;
  private BeneficioDomainService service;

  @BeforeEach
  void setup() {
    repository = mock(BeneficioRepositoryPort.class);
    service = new BeneficioDomainService(repository);
  }

  @Test
  void listar_shouldReturnList() {
    when(repository.findAll()).thenReturn(List.of(new Beneficio()));
    assertEquals(1, service.listar().size());
  }

  @Test
  void salvar_shouldReturnSavedEntity() {
    Beneficio b = Beneficio.builder().id(1L).valor(BigDecimal.TEN).build();
    when(repository.save(b)).thenReturn(b);

    Beneficio result = service.salvar(b);
    assertEquals(1L, result.getId());
  }

  @Test
  void buscar_shouldReturnEntity() {
    Beneficio b = Beneficio.builder().id(5L).build();
    when(repository.findById(5L)).thenReturn(Optional.of(b));

    assertEquals(5L, service.buscar(5L).getId());
  }

  @Test
  void buscar_shouldThrowWhenNotFound() {
    when(repository.findById(9L)).thenReturn(Optional.empty());
    assertThrows(RuntimeException.class, () -> service.buscar(9L));
  }

  @Test
  void deletar_shouldCallRepository() {
    service.deletar(7L);
    verify(repository, times(1)).deleteById(7L);
  }
}
