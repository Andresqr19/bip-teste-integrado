package com.example.backend.infrastructure.mapper;

import com.example.backend.application.dto.BeneficioDTO;
import com.example.backend.domain.model.Beneficio;
import org.springframework.stereotype.Component;

@Component
public class BeneficioMapper {
  public BeneficioDTO toDTO(Beneficio b) {
    BeneficioDTO dto = new BeneficioDTO();
    dto.setId(b.getId());
    dto.setNome(b.getNome());
    dto.setDescricao(b.getDescricao());
    dto.setValor(b.getValor());
    dto.setAtivo(b.getAtivo());
    dto.setVersion(b.getVersion());
    return dto;
  }
  public Beneficio toEntity(BeneficioDTO dto) {
    return Beneficio.builder()
            .id(dto.getId())
            .nome(dto.getNome())
            .descricao(dto.getDescricao())
            .valor(dto.getValor())
            .ativo(dto.getAtivo())
            .version(dto.getVersion())
            .build();
  }
}
