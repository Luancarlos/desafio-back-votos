package br.com.southsystem.cooperativa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessaoResponseDTO {

    private Long id;
    private LocalDateTime dataAbertura;
    private LocalDateTime dataFechamento;
    private Long pautaId;
}
