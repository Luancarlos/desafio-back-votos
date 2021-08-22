package br.com.southsystem.cooperativa.repository;

import br.com.southsystem.cooperativa.entity.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotoRepository extends JpaRepository<Voto, Long> {
}
