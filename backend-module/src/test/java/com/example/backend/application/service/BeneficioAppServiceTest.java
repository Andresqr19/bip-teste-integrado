package com.example.backend.application.service;

import com.example.backend.application.dto.BeneficioDTO;
import com.example.backend.domain.model.Beneficio;
import com.example.backend.domain.service.BeneficioDomainService;
import com.example.backend.infrastructure.mapper.BeneficioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BeneficioAppServiceTest {

  private BeneficioDomainService domain;
  private BeneficioMapper mapper;
  private BeneficioAppService service;

  @BeforeEach
  void setup() {
    domain = mock(BeneficioDomainService.class);
    mapper = new BeneficioMapper();
    service = new BeneficioAppService(domain, mapper);
  }

  @Test
  void listar_shouldMapEntitiesToDTOs() {
    Beneficio b = Beneficio.builder().id(1L).valor(BigDecimal.TEN).ativo(true).build();
    when(domain.listar()).thenReturn(List.of(b));

    List<BeneficioDTO> list = service.listar();

    assertEquals(1, list.size());
    assertEquals(1L, list.get(0).getId());
  }

  @Test
  void criar_shouldReturnSavedDTO() {
    BeneficioDTO dto = new BeneficioDTO();
    dto.setValor(BigDecimal.ONE);
    Beneficio entity = mapper.toEntity(dto);

    when(domain.salvar(any())).thenReturn(entity);

    BeneficioDTO result = service.criar(dto);
    assertEquals(BigDecimal.ONE, result.getValor());
  }

  @Test
  void buscar_shouldReturnMappedDTO() {
    Beneficio b = Beneficio.builder().id(10L).build();
    when(domain.buscar(10L)).thenReturn(b);

    assertEquals(10L, service.buscar(10L).getId());
  }

  @Test
  void deletar_shouldCallDomain() {
    service.deletar(3L);
    verify(domain, times(1)).deletar(3L);
  }
}
