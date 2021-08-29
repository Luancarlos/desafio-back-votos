package br.com.southsystem.cooperativa.service;

import br.com.southsystem.cooperativa.dto.request.PautaRequestDTO;
import br.com.southsystem.cooperativa.dto.response.PautaResponseDTO;
import br.com.southsystem.cooperativa.dto.response.PautaResultadoDTO;

public interface IPautaService {
    PautaResponseDTO criarPauta(PautaRequestDTO pauta);
    PautaResponseDTO buscarPautaPorSessao(Long sessaoId);
    PautaResponseDTO buscarPautaPorId(Long id);
    PautaResultadoDTO resultado(Long id);
}
