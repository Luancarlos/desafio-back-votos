package br.com.southsystem.cooperativa.service;

import br.com.southsystem.cooperativa.dto.request.SessaoRequestDTO;
import br.com.southsystem.cooperativa.dto.response.SessaoResponseDTO;

public interface ISessaoService {
    SessaoResponseDTO criarSessao(SessaoRequestDTO sessao);
    SessaoResponseDTO buscarPorId(Long id);
}
