package br.com.southsystem.cooperativa.service.impl;

import br.com.southsystem.cooperativa.dto.request.SessaoRequestDTO;
import br.com.southsystem.cooperativa.dto.response.PautaResponseDTO;
import br.com.southsystem.cooperativa.dto.response.SessaoResponseDTO;
import br.com.southsystem.cooperativa.entity.Pauta;
import br.com.southsystem.cooperativa.entity.Sessao;
import br.com.southsystem.cooperativa.exceptions.BadRequestException;
import br.com.southsystem.cooperativa.mapper.PautaMapper;
import br.com.southsystem.cooperativa.mapper.SessaoMapper;
import br.com.southsystem.cooperativa.repository.SessaoRepository;
import br.com.southsystem.cooperativa.service.PautaService;
import br.com.southsystem.cooperativa.service.SessaoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ISessaoService implements SessaoService {

    private final SessaoRepository sessaoRepository;
    private final SessaoMapper sessaoMapper;
    private final PautaService pautaService;

    public ISessaoService(SessaoRepository sessaoRepository, SessaoMapper sessaoMapper, PautaService pautaService) {
        this.sessaoRepository = sessaoRepository;
        this.sessaoMapper = sessaoMapper;
        this.pautaService = pautaService;
    }

    @Override
    public SessaoResponseDTO criarSessao(SessaoRequestDTO sessaoRequestDTO) {
        validarPauta(sessaoRequestDTO.getPautaId());
        validarDataFechamento(sessaoRequestDTO.getDataFechamento());

        Sessao sessao = sessaoMapper.sessaoRequestDTOToSessao(sessaoRequestDTO);
        sessao.setDataAbertura(LocalDateTime.now());
        addDataFechamento(sessao);

        Sessao novaSessao = sessaoRepository.save(sessao);

        return sessaoMapper.sessaoToSessaoResponseDTO(novaSessao);
    }

    private void addDataFechamento(Sessao sessao) {
        if (sessao.getDataFechamento() == null) {
            sessao.setDataFechamento(LocalDateTime.now().plusMinutes(1));
        }
    }

    private void validarDataFechamento(LocalDateTime localDateTime) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(localDateTime)) {
            throw new BadRequestException("A data de fechamento é menor que a data atual");
        }
    }

    private void validarPauta(Long id) {
        this.pautaService.buscarPautaPorId(id);
    }
}
