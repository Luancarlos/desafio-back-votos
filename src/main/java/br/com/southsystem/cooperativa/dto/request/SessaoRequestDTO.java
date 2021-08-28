package br.com.southsystem.cooperativa.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SessaoRequestDTO {
    @NotNull
    private Long idPauta;
    private LocalDateTime dataFechamento;
}
