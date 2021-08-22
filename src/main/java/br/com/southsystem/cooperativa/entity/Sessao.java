package br.com.southsystem.cooperativa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column
    private Long id;

    @Column
    private String situacao;

    @Column(name = "data_abertura")
    private LocalDateTime dataAbertura;

    @Column(name = "data_fechamento")
    private LocalDateTime dataFechamento;

    @OneToOne()
    @JoinColumn(name = "pauta_id", referencedColumnName = "id")
    private Pauta pauta;

    @OneToMany(mappedBy = "sessao")
    private List<Voto> votos;
}
