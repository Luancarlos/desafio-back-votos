package br.com.southsystem.cooperativa.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String cpf;

    @Column
    private String voto;

    @Column(name = "data_voto")
    private LocalDateTime dataVoto;

    @ManyToOne
    @JoinColumn(name = "sessao_id", referencedColumnName = "id")
    private Sessao sessao;


    @Override
    public String toString() {
        return "Voto{" +
                "id=" + id +
                ", cpf='" + cpf + '\'' +
                ", voto='" + voto + '\'' +
                ", dataVoto=" + dataVoto +
                ", sessao=" + sessao +
                '}';
    }
}
