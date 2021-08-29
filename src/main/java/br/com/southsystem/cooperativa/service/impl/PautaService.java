package br.com.southsystem.cooperativa.service.impl;

import br.com.southsystem.cooperativa.dto.request.PautaRequestDTO;
import br.com.southsystem.cooperativa.dto.response.PautaResponseDTO;
import br.com.southsystem.cooperativa.dto.response.PautaResultadoDTO;
import br.com.southsystem.cooperativa.entity.Pauta;
import br.com.southsystem.cooperativa.exceptions.BadRequestException;
import br.com.southsystem.cooperativa.exceptions.ResourceNotFoundException;
import br.com.southsystem.cooperativa.mapper.PautaMapper;
import br.com.southsystem.cooperativa.repository.PautaRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class PautaService implements br.com.southsystem.cooperativa.service.IPautaService {

    private final PautaRepository pautaRepository;

    private final PautaMapper pautaMapper;

    private static final String NOTFOUND = "Não foi possível encontrar a Pauta";

    public PautaService(PautaRepository pautaRepository, PautaMapper pautaMapper) {
        this.pautaRepository = pautaRepository;
        this.pautaMapper = pautaMapper;
    }

    @Override
    public PautaResponseDTO criarPauta(PautaRequestDTO pautaRequestDTO) {
        Pauta pauta = pautaMapper.pautaRequestDTOToPauta(pautaRequestDTO);
        if (pauta.getDescricao() == null || pauta.getDescricao().trim().equals("")) {
            throw new BadRequestException("A descrição da pauta é obrigatória");
        }
        pauta.setDataCadastro(LocalDateTime.now());
        return pautaMapper.pautaToPautaResponse(pautaRepository.save(pauta));
    }

    @Override
    public PautaResponseDTO buscarPautaPorSessao(Long sessaoId) {
        Pauta pauta = pautaRepository.findBySessaoId(sessaoId)
                .orElseThrow(() -> new ResourceNotFoundException(NOTFOUND));
        return pautaMapper.pautaToPautaResponse(pauta);
    }

    @Override
    public PautaResponseDTO buscarPautaPorId(Long id) {
       Pauta pauta = pautaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NOTFOUND));
       return pautaMapper.pautaToPautaResponse(pauta);
    }

    private Pauta buscarPauta(Long id) {
        return pautaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NOTFOUND));
    }

    @Override
    public PautaResultadoDTO resultado(Long id) {
        Pauta pauta = buscarPauta(id);

        LocalDateTime localDateTime = LocalDateTime.now();

        if (pauta.getSessao() == null) {
            throw new ResourceNotFoundException("Não existe sessão para essa pauta");
        }

        if (pauta.getSessao().getDataFechamento().isAfter(localDateTime)) {
            throw new BadRequestException("A sessão desta pauta ainda está aberta");
        }

        PautaResultadoDTO pautaResultado = pautaMapper.pautaToPautaResultado(pauta);

        long qtdSim = pauta.getSessao().getVotos()
                .stream().filter(item -> item.getVoto().equals("Sim")).count();

        long qtdNao = pauta.getSessao().getVotos()
                .stream().filter(item -> item.getVoto().equals("Não")).count();

        pautaResultado.setQuantidadeVotosSim((int) qtdSim);
        pautaResultado.setQuantidadevotosNao((int) qtdNao);

        return pautaResultado;
    }
}
