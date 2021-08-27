package br.com.southsystem.cooperativa.service.impl;

import br.com.southsystem.cooperativa.dto.request.SessaoRequestDTO;
import br.com.southsystem.cooperativa.dto.response.SessaoResponseDTO;
import br.com.southsystem.cooperativa.mapper.SessaoMapper;
import br.com.southsystem.cooperativa.repository.SessaoRepository;
import br.com.southsystem.cooperativa.service.PautaService;
import br.com.southsystem.cooperativa.service.SessaoService;
import org.springframework.stereotype.Service;

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
    public SessaoResponseDTO criarSessao(SessaoRequestDTO sessao) {
        return null;
    }
}
