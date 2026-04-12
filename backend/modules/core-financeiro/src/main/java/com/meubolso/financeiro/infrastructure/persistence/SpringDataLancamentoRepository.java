package com.meubolso.financeiro.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataLancamentoRepository extends JpaRepository<LancamentoEntity, UUID> {

    List<LancamentoEntity> findByUserId(String userId);

    Optional<LancamentoEntity> findByIdAndUserId(UUID id, String userId);

    @Modifying
    @Transactional
    void deleteByIdAndUserId(UUID id, String userId);
}
