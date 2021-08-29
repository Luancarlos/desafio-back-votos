package br.com.southsystem.cooperativa.controller.v1;

import br.com.southsystem.cooperativa.dto.request.SessaoRequestDTO;
import br.com.southsystem.cooperativa.dto.response.SessaoResponseDTO;
import br.com.southsystem.cooperativa.service.ISessaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/sessao")
@Api( tags = "Sessão")
public class SessaoController {

    private static final Logger logger = LoggerFactory.getLogger(SessaoController.class);
    private final ISessaoService sessaoService;

    public SessaoController(ISessaoService sessaoService) {
        this.sessaoService = sessaoService;
    }

    @PostMapping
    @ApiOperation(value = "Responsável por abrir uma sessão")
    public ResponseEntity<SessaoResponseDTO> criarSessao(@Valid @RequestBody SessaoRequestDTO sessaoRequestDTO) {
        logger.info("Abrindo a sessão: {}", sessaoRequestDTO);
        SessaoResponseDTO sessao = sessaoService.criarSessao(sessaoRequestDTO);
        return new ResponseEntity<>(sessao, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Responsável por buscar uma sessão por id")
    public ResponseEntity<SessaoResponseDTO> buscarSessao(@PathVariable Long id) {
        logger.info("Buscando uma sessão por id: {}", id);
        SessaoResponseDTO sessao = this.sessaoService.buscarPorId(id);
        return new ResponseEntity<>(sessao, HttpStatus.OK);
    }
}
