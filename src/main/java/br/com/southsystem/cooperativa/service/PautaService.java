package br.com.southsystem.cooperativa.service;

import br.com.southsystem.cooperativa.entity.Pauta;

public interface PautaService {
    Pauta criarPauta(Pauta pauta);
    Pauta buscarPautaPorSessao(Long sessaoId);
    Pauta buscarPautaPorId(Long id);
}
