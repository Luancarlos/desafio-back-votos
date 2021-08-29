package br.com.southsystem.cooperativa.service.impl;

import br.com.southsystem.cooperativa.dto.request.PautaRequestDTO;
import br.com.southsystem.cooperativa.dto.response.PautaResponseDTO;
import br.com.southsystem.cooperativa.dto.response.PautaResultadoDTO;
import br.com.southsystem.cooperativa.entity.Pauta;
import br.com.southsystem.cooperativa.exceptions.ResourceNotFoundException;
import br.com.southsystem.cooperativa.mapper.PautaMapper;
import br.com.southsystem.cooperativa.repository.PautaRepository;
import br.com.southsystem.cooperativa.util.PautaUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import br.com.southsystem.cooperativa.exceptions.BadRequestException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PautaServiceTest {

    @Mock
    private PautaRepository pautaRepository;

    @Mock
    private PautaMapper pautaMapper;

    @InjectMocks
    private PautaService pautaService;

    @Test
    void deveCriarUmaPauta() {
        PautaRequestDTO pautaDTO = PautaUtil.criarPautaRequestDTO();
        Pauta pautaSalva = PautaUtil.criarPauta();
        PautaResponseDTO pautaResponseDTO = PautaUtil.criarPautaResponsDTO();

        when(pautaMapper.pautaRequestDTOToPauta(pautaDTO)).thenReturn(pautaSalva);
        when(pautaMapper.pautaToPautaResponse(any(Pauta.class))).thenReturn(pautaResponseDTO);
        when(pautaRepository.save(any(Pauta.class))).thenReturn(pautaSalva);

        PautaResponseDTO novaPauta = pautaService.criarPauta(pautaDTO);

        assertNotNull(novaPauta.getDataCadastro());
        assertNotNull(novaPauta.getId());
        assertEquals(novaPauta.getDescricao(), pautaDTO.getDescricao());
    }


    @Test
    void deveRetornarUmaPautaPorId() {
        Pauta pauta = PautaUtil.criarPauta();
        PautaResponseDTO pautaResponseDTO = PautaUtil.criarPautaResponsDTO();
        Long Id = 1L;

        when(pautaMapper.pautaToPautaResponse(any(Pauta.class))).thenReturn(pautaResponseDTO);
        when(pautaRepository.findById(Id)).thenReturn(Optional.of(pauta));

        PautaResponseDTO pautaPesquisada = pautaService.buscarPautaPorId(Id);

        assertNotNull(pautaPesquisada.getDataCadastro());
        assertNotNull(pautaPesquisada.getId());
        assertEquals(pautaPesquisada.getDescricao(), pauta.getDescricao());
        assertEquals(pautaPesquisada.getId(), pauta.getId());
    }

    @Test
    void deveRetornarUmaPautaPorIdSessao() {
        Pauta pauta = PautaUtil.criarPauta();
        PautaResponseDTO pautaResponseDTO = PautaUtil.criarPautaResponsDTO();
        Long Id = 1L;

        when(pautaRepository.findBySessaoId(Id)).thenReturn(Optional.of(pauta));
        when(pautaMapper.pautaToPautaResponse(any(Pauta.class))).thenReturn(pautaResponseDTO);

        PautaResponseDTO pautaPesquisada = pautaService.buscarPautaPorSessao(Id);

        assertNotNull(pautaPesquisada.getDataCadastro());
        assertNotNull(pautaPesquisada.getId());
        assertEquals(pautaPesquisada.getDescricao(), pauta.getDescricao());
        assertEquals(pautaPesquisada.getId(), pauta.getId());
    }

    @Test
    void deveRetornarOResultado() {
        Long id = 1L;
        PautaResultadoDTO pautaResultadoDTO1 = PautaUtil.criarPautaResultadoDTO();
        Pauta pauta = PautaUtil.criarPauta();

        when(pautaRepository.findById(id)).thenReturn(Optional.of(pauta));
        when(pautaMapper.pautaToPautaResultado(any(Pauta.class))).thenReturn(pautaResultadoDTO1);
        PautaResultadoDTO pautaResultadoDTO = pautaService.resultado(id);

        assertEquals(0, pautaResultadoDTO.getQuantidadevotosNao());
        assertEquals(0, pautaResultadoDTO.getQuantidadeVotosSim());
        assertEquals(pautaResultadoDTO.getDescricao(), pauta.getDescricao());
        assertEquals(pautaResultadoDTO.getId(), pauta.getId());
    }


    @Test
    void deveLancarUmaExcecaoQuandoNaoHouverDescricao() {
        PautaRequestDTO pautaDTO = PautaUtil.criarPautaRequestDTO();

        when(pautaMapper.pautaRequestDTOToPauta(pautaDTO)).thenReturn(new Pauta());

        var erro = Assertions.assertThrows(BadRequestException.class, () -> {
            pautaService.criarPauta(pautaDTO);
        });

        String expectedMessage = "A descrição da pauta é obrigatória";
        String actualMessage = erro.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void deveLancarUmaExcecaoQuandoNaoEncontrarPauta() {
        Long id = 1L;

        when(pautaRepository.findById(id)).thenReturn(Optional.empty());
        when(pautaRepository.findBySessaoId(id)).thenReturn(Optional.empty());

        var erro = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            pautaService.buscarPautaPorId(id);
        });

        var erroSessao = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            pautaService.buscarPautaPorSessao(id);
        });

        String expectedMessage = "Não foi possível encontrar a Pauta";
        String actualMessage = erro.getMessage();
        String actualMessageSessao = erroSessao.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
        Assertions.assertTrue(actualMessageSessao.contains(expectedMessage));
    }

    @Test
    void deveLancarUmaExcecaoQuandoNaoHouverSessaoNaBuscaResultado() {
        Long id = 1L;
        Pauta pauta = PautaUtil.criarPauta();
        pauta.setSessao(null);

        when(pautaRepository.findById(id)).thenReturn(Optional.of(pauta));

        var erro = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            pautaService.resultado(id);
        });

        String expectedMessage = "Não existe sessão para essa pauta";
        String actualMessage = erro.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void deveLancarUmaExcecaoQuandoSessaoNaoEncerradaNaBuscaResultado() {
        Long id = 1L;
        Pauta pauta = PautaUtil.criarPauta();
        pauta.getSessao().setDataFechamento(LocalDateTime.now().plusHours(1));

        when(pautaRepository.findById(id)).thenReturn(Optional.of(pauta));

        var erro = Assertions.assertThrows(BadRequestException.class, () -> {
            pautaService.resultado(id);
        });

        String expectedMessage = "A sessão desta pauta ainda está aberta";
        String actualMessage = erro.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }



}
