package com.example.backend.infrastructure.adapter;

import com.example.backend.domain.model.Beneficio;
import com.example.backend.domain.repository.BeneficioRepositoryPort;
import com.example.backend.infrastructure.repository.BeneficioJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BeneficioRepositoryAdapter implements BeneficioRepositoryPort {
  private final BeneficioJpaRepository jpa;

  public Beneficio save(Beneficio b) { return jpa.save(b); }
  public List<Beneficio> findAll() { return jpa.findAll(); }
  public Optional<Beneficio> findById(Long id) { return jpa.findById(id); }
  public void deleteById(Long id) { jpa.deleteById(id); }
}
