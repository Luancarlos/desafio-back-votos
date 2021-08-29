package br.com.southsystem.cooperativa.controller.v1;


import br.com.southsystem.cooperativa.dto.request.VotoRequestDTO;
import br.com.southsystem.cooperativa.dto.response.VotoResponseDTO;
import br.com.southsystem.cooperativa.service.impl.VotoService;
import br.com.southsystem.cooperativa.util.VotoUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = VotoController.class)
public class VotoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VotoService votoService;

    @Test
    void deveRetornarSucessoQuandoVotar() throws Exception {

        VotoResponseDTO votoResponseDTO = VotoUtil.criarVotoResponseDTO();
        VotoRequestDTO votoRequestDTO = VotoUtil.criarVotoRequestDTO();

        when(votoService.votar(any(VotoRequestDTO.class))).thenReturn(votoResponseDTO);

        mockMvc.perform(post("/v1/voto")
                .content(objectMapper.writeValueAsString(votoRequestDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}
