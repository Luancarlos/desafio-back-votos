package br.com.southsystem.cooperativa.service.impl;

import br.com.southsystem.cooperativa.dto.request.PautaRequestDTO;
import br.com.southsystem.cooperativa.entity.Pauta;
import br.com.southsystem.cooperativa.exceptions.BadRequestException;
import br.com.southsystem.cooperativa.mapper.PautaMapper;
import br.com.southsystem.cooperativa.repository.PautaRepository;
import br.com.southsystem.cooperativa.service.PautaService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class IPautaService implements PautaService {

    private final PautaRepository pautaRepository;

    private final PautaMapper pautaMapper;

    public IPautaService(PautaRepository pautaRepository, PautaMapper pautaMapper) {
        this.pautaRepository = pautaRepository;
        this.pautaMapper = pautaMapper;
    }

    @Override
    public Pauta criarPauta(PautaRequestDTO pautaRequestDTO) {

        Pauta pauta = pautaMapper.pautaRequestDTOToPauta(pautaRequestDTO);

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
