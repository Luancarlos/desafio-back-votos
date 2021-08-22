package br.com.southsystem.cooperativa.service;

import br.com.southsystem.cooperativa.entity.Pauta;
import br.com.southsystem.cooperativa.repository.PautaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

public class PautaServiceTest {

    @Autowired
    private PautaService pautaService;

    @MockBean
    private PautaRepository pautaRepository;

    @Test
    void deveCriarUmaPauta() {
        Pauta pauta = Pauta.builder().descricao("Pauta teste").build();


        //Assertions.assertNotNull();

    }
}
