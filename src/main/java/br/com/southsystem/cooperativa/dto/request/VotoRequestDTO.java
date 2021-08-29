package br.com.southsystem.cooperativa.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;;
import javax.validation.constraints.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VotoRequestDTO {
    @NotNull
    private Long idSessao;

    @NotNull
    @Size(min = 11, max = 14, message = "O CPF precisa ter no mínino 11 dígitos e com mascara no máximo 14 dígitos")
    private String cpf;

    @NotNull
    @Pattern(regexp = "Sim|Não", message = "Para o voto só é aceito 'Sim' ou 'Não'")
    private String voto;

}
