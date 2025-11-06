package com.example.backend.infrastructure.repository;

import com.example.backend.domain.model.Beneficio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeneficioJpaRepository extends JpaRepository<Beneficio, Long> {
}
