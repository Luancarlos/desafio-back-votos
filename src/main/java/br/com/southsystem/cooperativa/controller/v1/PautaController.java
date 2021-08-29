package br.com.southsystem.cooperativa.controller.v1;

import br.com.southsystem.cooperativa.dto.request.PautaRequestDTO;
import br.com.southsystem.cooperativa.dto.response.PautaResponseDTO;
import br.com.southsystem.cooperativa.dto.response.PautaResultadoDTO;
import br.com.southsystem.cooperativa.service.IPautaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/pauta")
public class PautaController {

    private final IPautaService pautaService;

    public PautaController(IPautaService pautaService) {
        this.pautaService = pautaService;
    }

    @PostMapping
    public ResponseEntity<PautaResponseDTO> salvar(@Valid @RequestBody PautaRequestDTO pautaDTO) {
        PautaResponseDTO pauta = this.pautaService.criarPauta(pautaDTO);
        return new ResponseEntity<>(pauta, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PautaResponseDTO> buscarPorId(@PathVariable Long id) {
        PautaResponseDTO pauta = this.pautaService.buscarPautaPorId(id);
        return new ResponseEntity<>(pauta, HttpStatus.OK);
    }

    @GetMapping("/sessao/{id}")
    public ResponseEntity<PautaResponseDTO> buscarPorSessaoId(@PathVariable Long id) {
        PautaResponseDTO pauta = this.pautaService.buscarPautaPorSessao(id);
        return new ResponseEntity<>(pauta, HttpStatus.OK);
    }

    @GetMapping("/{id}/resultado")
    public ResponseEntity<PautaResultadoDTO> buscarResultadoVotacaoPorPauta(@PathVariable Long id) {
        PautaResultadoDTO pauta = this.pautaService.resultado(id);
        return new ResponseEntity<>(pauta, HttpStatus.OK);
    }
}
