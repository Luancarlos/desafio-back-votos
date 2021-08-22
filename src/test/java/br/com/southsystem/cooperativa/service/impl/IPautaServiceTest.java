package br.com.southsystem.cooperativa.service.impl;

import br.com.southsystem.cooperativa.entity.Pauta;
import br.com.southsystem.cooperativa.repository.PautaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import br.com.southsystem.cooperativa.exceptions.BadRequestException;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class IPautaServiceTest {

    @Mock
    private PautaRepository pautaRepository;

    @InjectMocks
    private IPautaService iPautaService;

    private Pauta pauta;

    @Test
    @DisplayName("Teste criação de uma pauta")
    void deveCriarUmaPauta() {
        Pauta pauta = Pauta.builder().descricao("Pauta teste").build();
        Pauta pautaSalva = Pauta.builder()
                .descricao("Pauta teste")
                .id(1L)
                .dataCadastro(LocalDateTime.now())
                .build();

        Mockito.when(pautaRepository.save(pauta)).thenReturn(pautaSalva);

        Pauta novaPauta = iPautaService.criarPauta(pauta);

        assertNotNull(novaPauta.getDataCadastro());
        assertNotNull(novaPauta.getId());
        assertEquals(novaPauta.getDescricao(), pauta.getDescricao());
    }

    @Test
    @DisplayName("Teste lançamento de exceção quando não houver descrição na pauta")
    void deveLancarUmaExcecaoQuandoNaoHouverDescricao() {
        Pauta pauta = Pauta.builder().descricao(null).build();

        var erro = Assertions.assertThrows(BadRequestException.class, () -> {
            Pauta novaPauta = iPautaService.criarPauta(pauta);
        });

        String expectedMessage = "A descrição da pauta é obrigatória";
        String actualMessage = erro.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Teste busca de pauta por ID")
    void deveRetornarUmaPautaPorId() {
        Pauta pauta = Pauta.builder()
                .descricao("Pauta teste")
                .id(1L)
                .dataCadastro(LocalDateTime.now())
                .build();

        Long Id = 1L;

        Mockito.when(pautaRepository.getById(Id)).thenReturn(pauta);

        Pauta pautaPesquisada = iPautaService.buscarPautaPorId(Id);

        assertNotNull(pautaPesquisada.getDataCadastro());
        assertNotNull(pautaPesquisada.getId());
        assertEquals(pautaPesquisada.getDescricao(), pauta.getDescricao());
        assertEquals(pautaPesquisada.getId(), pauta.getId());
    }

    @Test
    @DisplayName("Teste busca de pauta por ID da sessão")
    void deveRetornarUmaPautaPorIdSessao() {
        Pauta pauta = Pauta.builder()
                .descricao("Pauta teste")
                .id(1L)
                .dataCadastro(LocalDateTime.now())
                .build();

        Long Id = 1L;

        Mockito.when(pautaRepository.getBySessaoId(Id)).thenReturn(pauta);

        Pauta pautaPesquisada = iPautaService.buscarPautaPorSessao(Id);

        assertNotNull(pautaPesquisada.getDataCadastro());
        assertNotNull(pautaPesquisada.getId());
        assertEquals(pautaPesquisada.getDescricao(), pauta.getDescricao());
        assertEquals(pautaPesquisada.getId(), pauta.getId());
    }
}
