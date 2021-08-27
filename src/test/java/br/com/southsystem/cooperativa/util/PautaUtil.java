package br.com.southsystem.cooperativa.util;

import br.com.southsystem.cooperativa.dto.request.PautaRequestDTO;
import br.com.southsystem.cooperativa.dto.response.PautaResponseDTO;
import br.com.southsystem.cooperativa.entity.Pauta;

import java.time.LocalDateTime;

public class PautaUtil {
    public static Pauta criarPauta()  {
        return Pauta.builder()
                .descricao("Pauta teste")
                .id(1L)
                .dataCadastro(LocalDateTime.now())
                .build();
    }

    public static PautaResponseDTO criarPautaResponsDTO() {
        return PautaResponseDTO.builder()
                .descricao("Pauta teste")
                .id(1L)
                .dataCadastro(LocalDateTime.now())
                .quantidadeVotos(0)
                .sessaoId(1L)
                .build();
    }

    public static PautaRequestDTO criarPautaRequestDTO() {
        return new PautaRequestDTO("Pauta teste");
    }
}
