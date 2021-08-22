package br.com.southsystem.cooperativa.repository;

import br.com.southsystem.cooperativa.entity.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessaoRepository extends JpaRepository<Sessao, Long> {
}
