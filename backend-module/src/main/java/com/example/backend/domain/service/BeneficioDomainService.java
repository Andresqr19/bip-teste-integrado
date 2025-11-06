package com.example.backend.domain.service;

import com.example.backend.application.exception.NotFoundException;
import com.example.backend.domain.model.Beneficio;
import com.example.backend.domain.repository.BeneficioRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BeneficioDomainService {
  private final BeneficioRepositoryPort repository;


  public List<Beneficio> listar() { return repository.findAll(); }
  public Beneficio salvar(Beneficio b) { return repository.save(b); }
  public Beneficio buscar(Long id) {
    return repository.findById(id).orElseThrow(() -> new NotFoundException("Beneficio não encontrado"));
  }
  public void deletar(Long id) { repository.deleteById(id); }
}
