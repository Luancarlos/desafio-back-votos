package br.com.southsystem.cooperativa.service.impl;

import br.com.southsystem.cooperativa.dto.request.PautaRequestDTO;
import br.com.southsystem.cooperativa.entity.Pauta;
import br.com.southsystem.cooperativa.mapper.PautaMapper;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IPautaServiceTest {

    @Mock
    private PautaRepository pautaRepository;

    @Mock
    private PautaMapper pautaMapper;

    @InjectMocks
    private IPautaService iPautaService;

    @Test
    @DisplayName("Teste criação de uma pauta")
    void deveCriarUmaPauta() {
        PautaRequestDTO pautaDTO = new PautaRequestDTO("Pauta teste");
        Pauta pautaSalva = Pauta.builder()
                .descricao("Pauta teste")
                .id(1L)
                .dataCadastro(LocalDateTime.now())
                .build();

        when(pautaMapper.pautaRequestDTOToPauta(pautaDTO)).thenReturn(pautaSalva);
        when(pautaRepository.save(Mockito.any(Pauta.class))).thenReturn(pautaSalva);

        Pauta novaPauta = iPautaService.criarPauta(pautaDTO);

        assertNotNull(novaPauta.getDataCadastro());
        assertNotNull(novaPauta.getId());
        assertEquals(novaPauta.getDescricao(), pautaDTO.getDescricao());
    }

    @Test
    @DisplayName("Teste lançamento de exceção quando não houver descrição na pauta")
    void deveLancarUmaExcecaoQuandoNaoHouverDescricao() {
        PautaRequestDTO pautaDTO = new PautaRequestDTO(null);

        var erro = Assertions.assertThrows(BadRequestException.class, () -> {
            Pauta novaPauta = iPautaService.criarPauta(pautaDTO);
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

        when(pautaRepository.getById(Id)).thenReturn(pauta);

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

        when(pautaRepository.getBySessaoId(Id)).thenReturn(pauta);

        Pauta pautaPesquisada = iPautaService.buscarPautaPorSessao(Id);

        assertNotNull(pautaPesquisada.getDataCadastro());
        assertNotNull(pautaPesquisada.getId());
        assertEquals(pautaPesquisada.getDescricao(), pauta.getDescricao());
        assertEquals(pautaPesquisada.getId(), pauta.getId());
    }
}
