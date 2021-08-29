package br.com.southsystem.cooperativa.service.impl;

import br.com.southsystem.cooperativa.dto.request.SessaoRequestDTO;
import br.com.southsystem.cooperativa.dto.response.PautaResponseDTO;
import br.com.southsystem.cooperativa.dto.response.SessaoResponseDTO;
import br.com.southsystem.cooperativa.entity.Pauta;
import br.com.southsystem.cooperativa.entity.Sessao;
import br.com.southsystem.cooperativa.exceptions.ResourceNotFoundException;
import br.com.southsystem.cooperativa.mapper.PautaMapper;
import br.com.southsystem.cooperativa.mapper.SessaoMapper;
import br.com.southsystem.cooperativa.repository.SessaoRepository;
import br.com.southsystem.cooperativa.service.IPautaService;
import br.com.southsystem.cooperativa.util.PautaUtil;
import br.com.southsystem.cooperativa.util.SessaoUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class SessaoServiceTest {

    @Mock
    private SessaoRepository sessaoRepository;

    @Mock
    private IPautaService pautaService;

    @Mock
    private PautaMapper pautaMapper;

    @Mock
    private SessaoMapper sessaoMapper;

    @InjectMocks
    private SessaoService sessaoService;


    @Test
    void deveAbrirUmaSessao() {
        SessaoRequestDTO sessaoRequestDTO = SessaoUtil.criarSessaoRequest();
        SessaoResponseDTO sessaoResponseDTO = SessaoUtil.criarSessaoRenponse();
        Sessao sessao = SessaoUtil.criarSessao();
        PautaResponseDTO pautaResponseDTO = PautaUtil.criarPautaResponsDTO();
        Pauta pauta = PautaUtil.criarPauta();

        when(sessaoRepository.save(any(Sessao.class))).thenReturn(sessao);
        when(pautaService.buscarPautaPorId(1L)).thenReturn(pautaResponseDTO);
        when(sessaoMapper.sessaoToSessaoResponseDTO(any(Sessao.class))).thenReturn(sessaoResponseDTO);
        when(sessaoMapper.sessaoRequestDTOToSessao(any(SessaoRequestDTO.class))).thenReturn(sessao);
        when(pautaMapper.pautaToPautaResponse(any(Pauta.class))).thenReturn(pautaResponseDTO);
        when(pautaMapper.pautaResponseDTOToPauta(any(PautaResponseDTO.class))).thenReturn(pauta);

        SessaoResponseDTO novaSessaoResponseDTO = sessaoService.criarSessao(sessaoRequestDTO);

        assertNotNull(novaSessaoResponseDTO.getId());
        assertNotNull(novaSessaoResponseDTO.getDataAbertura());
        assertNotNull(novaSessaoResponseDTO.getDataFechamento());
    }

    @Test
    void deveRetornarUmaSessao() {
        Long id = 1L;
        SessaoResponseDTO sessaoResponseDTO = SessaoUtil.criarSessaoRenponse();
        Sessao sessao = SessaoUtil.criarSessao();

        when(sessaoRepository.findById(id)).thenReturn(Optional.of(sessao));
        when(sessaoMapper.sessaoToSessaoResponseDTO(any(Sessao.class))).thenReturn(sessaoResponseDTO);
        SessaoResponseDTO novaSessaoResponseDTO = sessaoService.buscarPorId(id);

        assertSame(novaSessaoResponseDTO, sessaoResponseDTO);
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrarIdSessao() {
        Long id = 1L;
        SessaoResponseDTO sessaoResponseDTO = SessaoUtil.criarSessaoRenponse();

        when(sessaoRepository.findById(id)).thenReturn(Optional.empty());
        when(sessaoMapper.sessaoToSessaoResponseDTO(any(Sessao.class))).thenReturn(sessaoResponseDTO);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            sessaoService.buscarPorId(id);
        });
    }

}
