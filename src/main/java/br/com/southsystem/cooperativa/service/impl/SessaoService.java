package br.com.southsystem.cooperativa.service.impl;

import br.com.southsystem.cooperativa.dto.request.SessaoRequestDTO;
import br.com.southsystem.cooperativa.dto.response.SessaoResponseDTO;
import br.com.southsystem.cooperativa.entity.Pauta;
import br.com.southsystem.cooperativa.entity.Sessao;
import br.com.southsystem.cooperativa.exceptions.BadRequestException;
import br.com.southsystem.cooperativa.exceptions.ResourceNotFoundException;
import br.com.southsystem.cooperativa.mapper.PautaMapper;
import br.com.southsystem.cooperativa.mapper.SessaoMapper;
import br.com.southsystem.cooperativa.repository.SessaoRepository;
import br.com.southsystem.cooperativa.service.IPautaService;
import br.com.southsystem.cooperativa.service.ISessaoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SessaoService implements ISessaoService {

    private final SessaoRepository sessaoRepository;
    private final SessaoMapper sessaoMapper;
    private final IPautaService pautaService;
    private final PautaMapper pautaMapper;

    public SessaoService(SessaoRepository sessaoRepository, SessaoMapper sessaoMapper, IPautaService pautaService, PautaMapper pautaMapper) {
        this.sessaoRepository = sessaoRepository;
        this.sessaoMapper = sessaoMapper;
        this.pautaService = pautaService;
        this.pautaMapper = pautaMapper;
    }

    @Override
    public SessaoResponseDTO criarSessao(SessaoRequestDTO sessaoRequestDTO) {
        validarDataFechamento(sessaoRequestDTO.getDataFechamento());

        Sessao sessao = sessaoMapper.sessaoRequestDTOToSessao(sessaoRequestDTO);
        sessao.setPauta(getPauta(sessaoRequestDTO.getIdPauta()));
        sessao.setDataAbertura(LocalDateTime.now());
        addDataFechamento(sessao);
        Sessao novaSessao = null;

        try {
            novaSessao = sessaoRepository.save(sessao);
        } catch (Exception e) {
            throw new BadRequestException("A pauta já está associada a uma sessão");
        }

        return sessaoMapper.sessaoToSessaoResponseDTO(novaSessao);
    }

    @Override
    public SessaoResponseDTO buscarPorId(Long id) {
        Sessao sessao = sessaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi possível encontrar a Sessão"));
        return sessaoMapper.sessaoToSessaoResponseDTO(sessao);
    }

    private void addDataFechamento(Sessao sessao) {
        if (sessao.getDataFechamento() == null) {
            sessao.setDataFechamento(LocalDateTime.now().plusMinutes(1));
        }
    }

    private void validarDataFechamento(LocalDateTime localDateTime) {
        LocalDateTime now = LocalDateTime.now();
        if (localDateTime != null && now.isAfter(localDateTime)) {
            throw new BadRequestException("A data de fechamento é menor que a data atual");
        }
    }

    private Pauta getPauta(Long id) {
        return pautaMapper.pautaResponseDTOToPauta(this.pautaService.buscarPautaPorId(id));
    }
}
