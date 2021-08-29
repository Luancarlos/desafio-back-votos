package br.com.southsystem.cooperativa.controller.v1;

import br.com.southsystem.cooperativa.dto.request.PautaRequestDTO;
import br.com.southsystem.cooperativa.dto.response.PautaResponseDTO;
import br.com.southsystem.cooperativa.dto.response.PautaResultadoDTO;
import br.com.southsystem.cooperativa.service.impl.PautaService;
import br.com.southsystem.cooperativa.util.PautaUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PautaController.class)
public class PautaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PautaService pautaService;

    @Test
    void deveRetornarSucessoQuandoCriarUmaPauta() throws Exception {

        PautaResponseDTO pauta = PautaUtil.criarPautaResponsDTO();
        PautaRequestDTO pautaRequestDTO = PautaUtil.criarPautaRequestDTO();

        when(pautaService.criarPauta(any(PautaRequestDTO.class))).thenReturn(pauta);

        mockMvc.perform(post("/v1/pauta")
                .content(objectMapper.writeValueAsString(pautaRequestDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }


    @Test
    void deveRetornarSucessoQuandoBuscarUmaPautaPorId() throws Exception {
        Long id = 1L;
        PautaResponseDTO pauta = PautaUtil.criarPautaResponsDTO();
        PautaRequestDTO pautaRequestDTO = PautaUtil.criarPautaRequestDTO();

        when(pautaService.buscarPautaPorId(id)).thenReturn(pauta);

        var result = mockMvc.perform(get("/v1/pauta/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();


        String json = result.getResponse().getContentAsString();
        PautaResponseDTO pautaResponseDTO = objectMapper.readValue(json, PautaResponseDTO.class);

        assertEquals(id, pautaResponseDTO.getId());
    }

    @Test
    void deveRetornarSucessoQuandoBuscarUmaPautaPorIdSessao() throws Exception {
        Long id = 1L;
        PautaResponseDTO pauta = PautaUtil.criarPautaResponsDTO();
        PautaRequestDTO pautaRequestDTO = PautaUtil.criarPautaRequestDTO();

        when(pautaService.buscarPautaPorSessao(id)).thenReturn(pauta);

        var result = mockMvc.perform(get("/v1/pauta/sessao/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();


        String json = result.getResponse().getContentAsString();
        PautaResponseDTO pautaResponseDTO = objectMapper.readValue(json, PautaResponseDTO.class);

        assertEquals(id, pautaResponseDTO.getId());
    }

    @Test
    void deveRetornarSucessoQuandoBuscarResultado() throws Exception {
        Long id = 1L;
        PautaResultadoDTO pauta = PautaUtil.criarPautaResultadoDTO();

        when(pautaService.resultado(id)).thenReturn(pauta);

        var result = mockMvc.perform(get("/v1/pauta/{id}/resultado", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();


        String json = result.getResponse().getContentAsString();
        PautaResultadoDTO pautaResultadoDTO = objectMapper.readValue(json, PautaResultadoDTO.class);

        assertEquals(id, pautaResultadoDTO.getId());
        assertNotNull(pautaResultadoDTO.getQuantidadeVotosSim());
        assertNotNull(pautaResultadoDTO.getQuantidadevotosNao());
    }
}
