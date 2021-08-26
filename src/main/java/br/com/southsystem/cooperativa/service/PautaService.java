package br.com.southsystem.cooperativa.service;

import br.com.southsystem.cooperativa.dto.request.PautaRequestDTO;
import br.com.southsystem.cooperativa.entity.Pauta;

public interface PautaService {
    Pauta criarPauta(PautaRequestDTO pauta);
    Pauta buscarPautaPorSessao(Long sessaoId);
    Pauta buscarPautaPorId(Long id);
}
