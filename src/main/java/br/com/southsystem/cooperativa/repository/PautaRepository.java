package br.com.southsystem.cooperativa.repository;

import br.com.southsystem.cooperativa.entity.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {
    Pauta getBySessaoId(Long id);
}
