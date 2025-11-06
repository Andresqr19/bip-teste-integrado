package com.example.backend.application.service;

import com.example.backend.application.dto.BeneficioDTO;
import com.example.backend.domain.service.BeneficioDomainService;
import com.example.backend.infrastructure.mapper.BeneficioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BeneficioAppService {
  private final BeneficioDomainService domain;
  private final BeneficioMapper mapper;

  public List<BeneficioDTO> listar() {
    return domain.listar().stream().map(mapper::toDTO).toList();
  }
  public BeneficioDTO criar(BeneficioDTO dto) {
    return mapper.toDTO(domain.salvar(mapper.toEntity(dto)));
  }
  public BeneficioDTO buscar(Long id) { return mapper.toDTO(domain.buscar(id)); }
  public void deletar(Long id) { domain.deletar(id); }

  public BeneficioDTO atualizar(Long id, BeneficioDTO dto) {
    var existente = domain.buscar(id);
    existente.setNome(dto.getNome());
    existente.setDescricao(dto.getDescricao());
    existente.setValor(dto.getValor());
    existente.setAtivo(dto.getAtivo());
    return mapper.toDTO(domain.salvar(existente));
  }
}
