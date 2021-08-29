package br.com.southsystem.cooperativa.util;

import br.com.southsystem.cooperativa.dto.request.VotoRequestDTO;
import br.com.southsystem.cooperativa.dto.response.VotoResponseDTO;
import br.com.southsystem.cooperativa.entity.Sessao;
import br.com.southsystem.cooperativa.entity.Voto;

import java.time.LocalDateTime;

public class VotoUtil {
    public static Voto criarVoto() {

        Sessao sessao = SessaoUtil.criarSessao();
        sessao.setDataFechamento(LocalDateTime.now().plusHours(1));

        return Voto.builder()
                .id(1L)
                .cpf("565.021.290-21")
                .sessao(sessao)
                .voto("Sim")
                .dataVoto(LocalDateTime.now())
                .build();
    }

    public static VotoResponseDTO criarVotoResponseDTO() {
        return VotoResponseDTO.builder()
                .id(1L)
                .cpf("565.021.290-21")
                .voto("Sim")
                .idSessao(1L)
                .build();
    }

    public static VotoRequestDTO criarVotoRequestDTO() {
        return VotoRequestDTO.builder()
                .cpf("565.021.290-21")
                .voto("Sim")
                .idSessao(1L)
                .build();
    }
}
