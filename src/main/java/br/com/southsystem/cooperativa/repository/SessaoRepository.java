package br.com.southsystem.cooperativa.repository;

import br.com.southsystem.cooperativa.entity.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {
    List<Sessao> findAllByFechadoAndDataFechamentoIsLessThanEqual(Boolean isFechado, LocalDateTime dataFechamento);
}
