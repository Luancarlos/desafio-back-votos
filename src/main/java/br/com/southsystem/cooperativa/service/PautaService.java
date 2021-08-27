package br.com.southsystem.cooperativa.service;

import br.com.southsystem.cooperativa.dto.request.PautaRequestDTO;
import br.com.southsystem.cooperativa.dto.response.PautaResponseDTO;

public interface PautaService {
    PautaResponseDTO criarPauta(PautaRequestDTO pauta);
    PautaResponseDTO buscarPautaPorSessao(Long sessaoId);
    PautaResponseDTO buscarPautaPorId(Long id);
}
