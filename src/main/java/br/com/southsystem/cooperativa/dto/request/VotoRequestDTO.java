package br.com.southsystem.cooperativa.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VotoRequestDTO {
    @NotNull
    private Long idSessao;

    @NotNull
    @Max(14)
    @Min(11)
    private String cpf;

    @NotNull
    @Pattern(regexp = "Sim|Não")
    private String voto;

}
