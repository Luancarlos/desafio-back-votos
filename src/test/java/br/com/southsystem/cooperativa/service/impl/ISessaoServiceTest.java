package br.com.southsystem.cooperativa.service.impl;

import br.com.southsystem.cooperativa.dto.request.SessaoRequestDTO;
import br.com.southsystem.cooperativa.dto.response.SessaoResponseDTO;
import br.com.southsystem.cooperativa.mapper.SessaoMapper;
import br.com.southsystem.cooperativa.repository.SessaoRepository;
import br.com.southsystem.cooperativa.util.SessaoUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ISessaoServiceTest {

    @Mock
    private SessaoRepository sessaoRepository;

    @Mock
    private SessaoMapper sessaoMapper;

    @InjectMocks
    private ISessaoService sessaoService;

    @Test
    void deveAbrirUmaSessao() {
        SessaoResponseDTO sessao = SessaoUtil.criarSessaoRenponse();
        SessaoRequestDTO sessaoRequestDTO = SessaoUtil.criarSessaoRequest();

        when(sessaoService.criarSessao(sessaoRequestDTO)).thenReturn(sessao);

        SessaoResponseDTO sessaoResponseDTO = sessaoService.criarSessao(sessaoRequestDTO);

        assertNotNull(sessaoResponseDTO.getId());
        assertNotNull(sessaoResponseDTO.getDataAbertura());
        assertEquals(sessaoResponseDTO.getDataFechamento(), sessaoRequestDTO.getDataFechamento());
    }


}
