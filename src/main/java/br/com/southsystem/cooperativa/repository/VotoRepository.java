package br.com.southsystem.cooperativa.repository;

import br.com.southsystem.cooperativa.entity.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VotoRepository extends JpaRepository<Voto, Long> {
    Optional<Voto> findByCpf(String cpf);
}
