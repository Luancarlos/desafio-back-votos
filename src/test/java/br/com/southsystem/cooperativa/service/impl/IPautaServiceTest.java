package br.com.southsystem.cooperativa.service.impl;

import br.com.southsystem.cooperativa.dto.request.PautaRequestDTO;
import br.com.southsystem.cooperativa.dto.response.PautaResponseDTO;
import br.com.southsystem.cooperativa.entity.Pauta;
import br.com.southsystem.cooperativa.exceptions.ResourceNotFoundException;
import br.com.southsystem.cooperativa.mapper.PautaMapper;
import br.com.southsystem.cooperativa.repository.PautaRepository;
import br.com.southsystem.cooperativa.util.PautaUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import br.com.southsystem.cooperativa.exceptions.BadRequestException;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
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
        PautaRequestDTO pautaDTO = PautaUtil.criarPautaRequestDTO();
        Pauta pautaSalva = PautaUtil.criarPauta();
        PautaResponseDTO pautaResponseDTO = PautaUtil.criarPautaResponsDTO();

        when(pautaMapper.pautaRequestDTOToPauta(pautaDTO)).thenReturn(pautaSalva);
        when(pautaMapper.pautaToPautaResponse(any(Pauta.class))).thenReturn(pautaResponseDTO);
        when(pautaRepository.save(any(Pauta.class))).thenReturn(pautaSalva);

        PautaResponseDTO novaPauta = iPautaService.criarPauta(pautaDTO);

        assertNotNull(novaPauta.getDataCadastro());
        assertNotNull(novaPauta.getId());
        assertEquals(novaPauta.getDescricao(), pautaDTO.getDescricao());
    }

    @Test
    @DisplayName("Teste lançamento de exceção quando não houver descrição na pauta")
    void deveLancarUmaExcecaoQuandoNaoHouverDescricao() {
        PautaRequestDTO pautaDTO = PautaUtil.criarPautaRequestDTO();

        when(pautaMapper.pautaRequestDTOToPauta(pautaDTO)).thenReturn(new Pauta());

        var erro = Assertions.assertThrows(BadRequestException.class, () -> {
            iPautaService.criarPauta(pautaDTO);
        });

        String expectedMessage = "A descrição da pauta é obrigatória";
        String actualMessage = erro.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Teste busca de pauta por ID")
    void deveRetornarUmaPautaPorId() {
        Pauta pauta = PautaUtil.criarPauta();
        PautaResponseDTO pautaResponseDTO = PautaUtil.criarPautaResponsDTO();
        Long Id = 1L;

        when(pautaMapper.pautaToPautaResponse(any(Pauta.class))).thenReturn(pautaResponseDTO);
        when(pautaRepository.findById(Id)).thenReturn(Optional.of(pauta));

        PautaResponseDTO pautaPesquisada = iPautaService.buscarPautaPorId(Id);

        assertNotNull(pautaPesquisada.getDataCadastro());
        assertNotNull(pautaPesquisada.getId());
        assertEquals(pautaPesquisada.getDescricao(), pauta.getDescricao());
        assertEquals(pautaPesquisada.getId(), pauta.getId());
    }

    @Test
    @DisplayName("Teste busca de pauta por ID da sessão")
    void deveRetornarUmaPautaPorIdSessao() {
        Pauta pauta = PautaUtil.criarPauta();
        PautaResponseDTO pautaResponseDTO = PautaUtil.criarPautaResponsDTO();
        Long Id = 1L;

        when(pautaRepository.findBySessaoId(Id)).thenReturn(Optional.of(pauta));
        when(pautaMapper.pautaToPautaResponse(any(Pauta.class))).thenReturn(pautaResponseDTO);

        PautaResponseDTO pautaPesquisada = iPautaService.buscarPautaPorSessao(Id);

        assertNotNull(pautaPesquisada.getDataCadastro());
        assertNotNull(pautaPesquisada.getId());
        assertEquals(pautaPesquisada.getDescricao(), pauta.getDescricao());
        assertEquals(pautaPesquisada.getId(), pauta.getId());
    }
}
