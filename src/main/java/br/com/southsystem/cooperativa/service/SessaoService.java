package br.com.southsystem.cooperativa.service;

import br.com.southsystem.cooperativa.dto.request.SessaoRequestDTO;
import br.com.southsystem.cooperativa.dto.response.SessaoResponseDTO;

public interface SessaoService {
    SessaoResponseDTO criarSessao(SessaoRequestDTO sessao);
    SessaoResponseDTO buscarPorId(Long id);
}
