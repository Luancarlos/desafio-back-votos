package br.com.southsystem.cooperativa.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PautaResponseDTO {
    private Long id;

    @NotNull
    private String descricao;

    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataCadastro;

    private Long sessaoId;

    private int quantidadeVotos;
}
