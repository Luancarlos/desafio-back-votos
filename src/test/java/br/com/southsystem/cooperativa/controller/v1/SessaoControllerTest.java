package br.com.southsystem.cooperativa.controller.v1;

import br.com.southsystem.cooperativa.dto.request.SessaoRequestDTO;
import br.com.southsystem.cooperativa.dto.response.SessaoResponseDTO;
import br.com.southsystem.cooperativa.exceptions.BadRequestException;
import br.com.southsystem.cooperativa.exceptions.ResourceNotFoundException;
import br.com.southsystem.cooperativa.service.impl.SessaoService;
import br.com.southsystem.cooperativa.util.SessaoUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = SessaoController.class)
public class SessaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SessaoService sessaoService;

    @Test
    @DisplayName("Teste de sucesso salvar sessao")
    void deveRetornarSucessoQuandoSalvarUmaSessao() throws Exception {
        SessaoRequestDTO sessaoRequestDTO = SessaoUtil.criarSessaoRequest();
        SessaoResponseDTO sessaoResponseDTO = SessaoUtil.criarSessaoRenponse();

        when(sessaoService.criarSessao(sessaoRequestDTO)).thenReturn(sessaoResponseDTO);

        mockMvc.perform(post("/v1/sessao")
                .content(objectMapper.writeValueAsString(sessaoRequestDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }


    @Test
    @DisplayName("Teste de sucesso buscar uma sessão")
    void deveRetornarSucessoQuandoBuscarUmaSessao() throws Exception {
        Long id = 1L;
        SessaoResponseDTO sessaoResponseDTO = SessaoUtil.criarSessaoRenponse();

        when(sessaoService.buscarPorId(id)).thenReturn(sessaoResponseDTO);

        var result = mockMvc.perform(get("/v1/sessao/{id}", id)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk()).andReturn();

        String json = result.getResponse().getContentAsString();
        SessaoResponseDTO resultSessaoResponseDTO = objectMapper.readValue(json, SessaoResponseDTO.class);

        assertEquals(sessaoResponseDTO.getId(), resultSessaoResponseDTO.getId());
        assertEquals(sessaoResponseDTO.getIdPauta(), resultSessaoResponseDTO.getIdPauta());

   }

    @Test
    @DisplayName("Teste de erro quando não encontrar sessao")
    void deveRetornarNotFoundQuandoNaoEncontrarUmaSessao() throws Exception {
        Long id = 1L;

        when(sessaoService.buscarPorId(id)).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get("/v1/sessao/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Teste de erro quando não encontrar uma pauta")
    void deveRetornarNotFoundQuandoNaoEncontrarUmaPauta() throws Exception {
        SessaoRequestDTO sessaoRequestDTO = SessaoUtil.criarSessaoRequest();

        when(sessaoService.criarSessao(sessaoRequestDTO)).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(post("/v1/sessao")
                .content(objectMapper.writeValueAsString(sessaoRequestDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Teste de erro quando tentar adicionar sessão em uma pauta que já tenha uma sessão")
    void deveRetornarBadRequestQuandoTentarAdicionarSessaoEmPautaComSessao() throws Exception {
        SessaoRequestDTO sessaoRequestDTO = SessaoUtil.criarSessaoRequest();

        when(sessaoService.criarSessao(sessaoRequestDTO)).thenThrow(BadRequestException.class);

        mockMvc.perform(post("/v1/sessao")
                .content(objectMapper.writeValueAsString(sessaoRequestDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
