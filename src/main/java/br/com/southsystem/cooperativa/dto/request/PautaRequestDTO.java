package br.com.southsystem.cooperativa.dto.request;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Data
public class PautaRequestDTO {

    @NotBlank
    private String descricao;
}
