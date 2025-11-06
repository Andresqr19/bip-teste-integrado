package com.example.backend.infrastructure.mapper;

import com.example.backend.application.dto.BeneficioDTO;
import com.example.backend.domain.model.Beneficio;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BeneficioMapperTest {

  private final BeneficioMapper mapper = new BeneficioMapper();

  @Test
  void toDTO_shouldMapFieldsCorrectly() {
    Beneficio b = Beneficio.builder()
            .id(1L)
            .nome("Beneficio A")
            .descricao("Descrição A")
            .valor(new BigDecimal("1000.00"))
            .ativo(true)
            .version(1L)
            .build();

    BeneficioDTO dto = mapper.toDTO(b);

    assertEquals(1L, dto.getId());
    assertEquals("Beneficio A", dto.getNome());
    assertEquals("Descrição A", dto.getDescricao());
    assertEquals(new BigDecimal("1000.00"), dto.getValor());
    assertTrue(dto.getAtivo());
    assertEquals(1L, dto.getVersion());
  }

  @Test
  void toEntity_shouldMapFieldsCorrectly() {
    BeneficioDTO dto = new BeneficioDTO();
    dto.setId(2L);
    dto.setNome("Beneficio B");
    dto.setDescricao("Descrição B");
    dto.setValor(new BigDecimal("500.00"));
    dto.setAtivo(false);
    dto.setVersion(2L);

    Beneficio b = mapper.toEntity(dto);

    assertEquals(2L, b.getId());
    assertEquals("Beneficio B", b.getNome());
    assertEquals("Descrição B", b.getDescricao());
    assertEquals(new BigDecimal("500.00"), b.getValor());
    assertFalse(b.getAtivo());
    assertEquals(2L, b.getVersion());
  }
}
