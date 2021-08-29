package br.com.southsystem.cooperativa.controller.v1;

import br.com.southsystem.cooperativa.dto.request.PautaRequestDTO;
import br.com.southsystem.cooperativa.dto.response.PautaResponseDTO;
import br.com.southsystem.cooperativa.dto.response.PautaResultadoDTO;
import br.com.southsystem.cooperativa.service.IPautaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/pauta")
@Api( tags = "Pauta")
public class PautaController {

    private final IPautaService pautaService;

    public PautaController(IPautaService pautaService) {
        this.pautaService = pautaService;
    }

    @PostMapping
    @ApiOperation(value = "Responsável por criar uma pauta")
    public ResponseEntity<PautaResponseDTO> salvar(@Valid @RequestBody PautaRequestDTO pautaDTO) {
        PautaResponseDTO pauta = this.pautaService.criarPauta(pautaDTO);
        return new ResponseEntity<>(pauta, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Responsável por buscar uma pauta por id")
    public ResponseEntity<PautaResponseDTO> buscarPorId(@PathVariable Long id) {
        PautaResponseDTO pauta = this.pautaService.buscarPautaPorId(id);
        return new ResponseEntity<>(pauta, HttpStatus.OK);
    }

    @GetMapping("/sessao/{id}")
    @ApiOperation(value = "Responsável por buscar uma pauta por id da sessão")
    public ResponseEntity<PautaResponseDTO> buscarPorSessaoId(@PathVariable Long id) {
        PautaResponseDTO pauta = this.pautaService.buscarPautaPorSessao(id);
        return new ResponseEntity<>(pauta, HttpStatus.OK);
    }

    @GetMapping("/{id}/resultado")
    @ApiOperation(value = "Responsável por mostrar o resultado da votação")
    public ResponseEntity<PautaResultadoDTO> buscarResultadoVotacaoPorPauta(@PathVariable Long id) {
        PautaResultadoDTO pauta = this.pautaService.resultado(id);
        return new ResponseEntity<>(pauta, HttpStatus.OK);
    }
}
