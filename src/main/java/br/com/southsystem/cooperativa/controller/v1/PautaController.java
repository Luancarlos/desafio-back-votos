package br.com.southsystem.cooperativa.controller.v1;

import br.com.southsystem.cooperativa.dto.request.PautaRequestDTO;
import br.com.southsystem.cooperativa.entity.Pauta;
import br.com.southsystem.cooperativa.service.PautaService;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/pauta")
public class PautaController {

    private final PautaService pautaService;

    public PautaController(PautaService pautaService) {
        this.pautaService = pautaService;
    }

    @PostMapping
    public ResponseEntity<Pauta> salvar(@Valid @RequestBody PautaRequestDTO pautaDTO) {
        Pauta pauta = this.pautaService.criarPauta(pautaDTO);
        return new ResponseEntity<>(pauta, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pauta> buscarPorId(@RequestParam("id") Long id) {
        Pauta pauta = this.pautaService.buscarPautaPorId(id);
        return new ResponseEntity<>(pauta, HttpStatus.OK);
    }

    @GetMapping("/sessao/{id}")
    public ResponseEntity<Pauta> buscarPorSessaoId(@RequestParam("id") Long id) {
        Pauta pauta = this.pautaService.buscarPautaPorSessao(id);
        return new ResponseEntity<>(pauta, HttpStatus.OK);
    }
}
