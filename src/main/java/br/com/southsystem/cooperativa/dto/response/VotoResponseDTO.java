package br.com.southsystem.cooperativa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VotoResponseDTO {
    private Long id;
    private Long idSessao;
    private String cpf;
    private String voto;
}
