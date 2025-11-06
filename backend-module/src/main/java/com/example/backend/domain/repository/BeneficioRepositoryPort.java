package com.example.backend.domain.repository;

import com.example.backend.domain.model.Beneficio;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BeneficioRepositoryPort {
  Beneficio save(Beneficio beneficio);
  List<Beneficio> findAll();
  Optional<Beneficio> findById(Long id);
  void deleteById(Long id);
}
