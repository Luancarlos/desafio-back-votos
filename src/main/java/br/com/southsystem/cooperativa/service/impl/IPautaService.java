package br.com.southsystem.cooperativa.service.impl;

import br.com.southsystem.cooperativa.entity.Pauta;
import br.com.southsystem.cooperativa.exceptions.BadRequestException;
import br.com.southsystem.cooperativa.repository.PautaRepository;
import br.com.southsystem.cooperativa.service.PautaService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class IPautaService implements PautaService {

    private final PautaRepository pautaRepository;

    public IPautaService(PautaRepository pautaRepository) {
        this.pautaRepository = pautaRepository;
    }

    @Override
    public Pauta criarPauta(Pauta pauta) {
        if (pauta.getDescricao() == null || pauta.getDescricao().trim().equals("")) {
            throw new BadRequestException("A descrição da pauta é obrigatória");
        }
        pauta.setDataCadastro(LocalDateTime.now());
        return pautaRepository.save(pauta);
    }

    @Override
    public Pauta buscarPautaPorSessao(Long sessaoId) {
        return pautaRepository.getBySessaoId(sessaoId);
    }

    @Override
    public Pauta buscarPautaPorId(Long id) {
        return pautaRepository.getById(id);
    }
}
