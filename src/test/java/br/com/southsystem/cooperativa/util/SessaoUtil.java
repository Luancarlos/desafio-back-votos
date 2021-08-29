package br.com.southsystem.cooperativa.util;

import br.com.southsystem.cooperativa.dto.request.SessaoRequestDTO;
import br.com.southsystem.cooperativa.dto.response.SessaoResponseDTO;
import br.com.southsystem.cooperativa.entity.Pauta;
import br.com.southsystem.cooperativa.entity.Sessao;
import br.com.southsystem.cooperativa.entity.Voto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SessaoUtil {

    public static Sessao criarSessao() {

        List<Voto> votos = new ArrayList<>();

        return Sessao.builder()
                .id(1L)
                .dataAbertura(LocalDateTime.now())
                .dataFechamento(LocalDateTime.now())
                .pauta(new Pauta())
                .votos(votos)
                .build();
    }

    public static SessaoRequestDTO criarSessaoRequest() {
        return SessaoRequestDTO.builder()
                .dataFechamento(LocalDateTime.now().plusHours(1))
                .idPauta(1L).build();
    }

    public static SessaoResponseDTO criarSessaoRenponse() {
        return SessaoResponseDTO.builder()
                .id(1L)
                .dataAbertura(LocalDateTime.now())
                .dataFechamento(LocalDateTime.now())
                .descricaoPauta("Pauta teste")
                .idPauta(1L).build();
    }
}
