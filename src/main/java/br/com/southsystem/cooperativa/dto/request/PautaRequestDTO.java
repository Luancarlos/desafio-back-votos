package br.com.southsystem.cooperativa.dto.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PautaRequestDTO {

    @NotBlank
    private String descricao;
}
