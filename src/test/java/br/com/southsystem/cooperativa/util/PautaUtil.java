package br.com.southsystem.cooperativa.util;

import br.com.southsystem.cooperativa.dto.request.PautaRequestDTO;
import br.com.southsystem.cooperativa.dto.response.PautaResponseDTO;
import br.com.southsystem.cooperativa.dto.response.PautaResultadoDTO;
import br.com.southsystem.cooperativa.entity.Pauta;
import java.time.LocalDateTime;

public class PautaUtil {
    public static Pauta criarPauta()  {
        return Pauta.builder()
                .descricao("Pauta teste")
                .id(1L)
                .sessao(SessaoUtil.criarSessao())
                .dataCadastro(LocalDateTime.now())
                .build();
    }

    public static PautaResponseDTO criarPautaResponsDTO() {
        return PautaResponseDTO.builder()
                .descricao("Pauta teste")
                .id(1L)
                .dataCadastro(LocalDateTime.now())
                .build();
    }

    public static PautaResultadoDTO criarPautaResultadoDTO() {
        return PautaResultadoDTO.builder()
                .id(1L)
                .descricao("Pauta teste")
                .quantidadevotosNao(2)
                .quantidadeVotosSim(2)
                .dataCadastro(LocalDateTime.now())
                .dataAberturaSessao(LocalDateTime.now())
                .dataFechamentoSessao(LocalDateTime.now())
                .build();
    }

    public static PautaRequestDTO criarPautaRequestDTO() {
        return new PautaRequestDTO("Pauta teste");
    }
}
