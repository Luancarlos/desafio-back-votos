package br.com.southsystem.cooperativa.service.impl;

import br.com.southsystem.cooperativa.dto.request.VotoRequestDTO;
import br.com.southsystem.cooperativa.dto.response.SessaoResponseDTO;
import br.com.southsystem.cooperativa.dto.response.VotoResponseDTO;
import br.com.southsystem.cooperativa.entity.Voto;
import br.com.southsystem.cooperativa.exceptions.BadRequestException;
import br.com.southsystem.cooperativa.mapper.VotoMapper;
import br.com.southsystem.cooperativa.repository.VotoRepository;
import br.com.southsystem.cooperativa.util.SessaoUtil;
import br.com.southsystem.cooperativa.util.VotoUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VotoServiceTest {

    @Mock
    private VotoRepository votoRepository;

    @Mock
    private VotoMapper votoMapper;

    @Mock
    private SessaoService sessaoService;

    @InjectMocks
    private VotoService votoService;

    @Test
    void deveSalvarUmVoto() {
        Voto voto = VotoUtil.criarVoto();
        VotoResponseDTO votoResponseDTO = VotoUtil.criarVotoResponseDTO();
        VotoRequestDTO votoRequestDTO = VotoUtil.criarVotoRequestDTO();
        SessaoResponseDTO sessaoResponseDTO = SessaoUtil.criarSessaoRenponse();
        sessaoResponseDTO.setDataFechamento(LocalDateTime.now().plusHours(1));

        when(votoRepository.save(any(Voto.class))).thenReturn(voto);
        when(sessaoService.buscarPorId(1L)).thenReturn(sessaoResponseDTO);
        when(votoMapper.votoToVotoResponseDTO(any(Voto.class))).thenReturn(votoResponseDTO);
        when(votoMapper.votoRequestDTOToVoto(any(VotoRequestDTO.class))).thenReturn(voto);

        VotoResponseDTO novoVoto = votoService.votar(votoRequestDTO);

        assertNotNull(novoVoto.getId());
        assertNotNull(novoVoto.getCpf());
        assertNotNull(novoVoto.getVoto());
        assertEquals(novoVoto.getVoto(), "Sim");
        assertEquals(novoVoto.getId(), 1L);
    }

    @Test
    void deveLancarExcecaoQuandoCpfInvalido() {
        VotoRequestDTO votoRequestDTO = VotoUtil.criarVotoRequestDTO();
        votoRequestDTO.setCpf("1111111");
        SessaoResponseDTO sessaoResponseDTO = SessaoUtil.criarSessaoRenponse();
        sessaoResponseDTO.setDataFechamento(LocalDateTime.now().plusHours(1));

        var erro = assertThrows(BadRequestException.class, () -> {
            votoService.votar(votoRequestDTO);
        });

        assertEquals(erro.getMessage(), "O CPF informado é inválido");
    }

    @Test
    void deveLancarExcecaoQuandoSessaoEncerrada() {

        VotoRequestDTO votoRequestDTO = VotoUtil.criarVotoRequestDTO();
        SessaoResponseDTO sessaoResponseDTO = SessaoUtil.criarSessaoRenponse();

        when(sessaoService.buscarPorId(1L)).thenReturn(sessaoResponseDTO);

        var erro = assertThrows(BadRequestException.class, () -> {
            votoService.votar(votoRequestDTO);
        });

        assertEquals(erro.getMessage(), "Está sessão já foi encerreda");
    }


    @Test
    void deveLancarExcecaoQuandoCpfJaVotouNaSessao() {
        Voto voto = VotoUtil.criarVoto();
        VotoRequestDTO votoRequestDTO = VotoUtil.criarVotoRequestDTO();
        SessaoResponseDTO sessaoResponseDTO = SessaoUtil.criarSessaoRenponse();
        sessaoResponseDTO.setDataFechamento(LocalDateTime.now().plusHours(1));

        when(sessaoService.buscarPorId(1L)).thenReturn(sessaoResponseDTO);
        when(votoRepository.findByCpf(anyString())).thenReturn(Optional.of(voto));

        var erro = assertThrows(BadRequestException.class, () -> {
            votoService.votar(votoRequestDTO);
        });

        assertEquals(erro.getMessage(), "O CPF informado já efetuou um voto nesta sessão");
    }



}
