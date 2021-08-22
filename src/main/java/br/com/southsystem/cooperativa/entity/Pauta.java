package br.com.southsystem.cooperativa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pauta {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)

    @Column
    private Long id;

    @Column
    private String descricao;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @OneToOne
    private Sessao sessao;
}
