package br.com.southsystem.cooperativa.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private boolean fechado = false;

    @Column(name = "data_abertura")
    private LocalDateTime dataAbertura;

    @Column(name = "data_fechamento")
    private LocalDateTime dataFechamento;

    @OneToOne()
    @JoinColumn(name = "pauta_id", referencedColumnName = "id", unique = true)
    private Pauta pauta;

    @OneToMany(mappedBy = "sessao")
    private List<Voto> votos;


    @Override
    public String toString() {
        return "Sessao{" +
                "id=" + id +
                ", fechado=" + fechado +
                ", dataAbertura=" + dataAbertura +
                ", dataFechamento=" + dataFechamento +
                ", pauta=" + pauta +
                ", votos=" + votos +
                '}';
    }
}
