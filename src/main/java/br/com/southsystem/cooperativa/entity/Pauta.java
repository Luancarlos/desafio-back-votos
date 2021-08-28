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
public class Pauta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
 
    @Column
    private String descricao;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @OneToOne(mappedBy = "pauta")
    private Sessao sessao;
}
