package com.meubolso.financeiro.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface SpringDataContaRepository extends JpaRepository<ContaEntity, UUID> {

    List<ContaEntity> findByUserId(String userId);

    @Modifying
    @Transactional
    void deleteByIdAndUserId(UUID id, String userId);
}
