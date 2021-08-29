package br.com.southsystem.cooperativa.controller.v1;

import br.com.southsystem.cooperativa.dto.request.SessaoRequestDTO;
import br.com.southsystem.cooperativa.dto.response.SessaoResponseDTO;
import br.com.southsystem.cooperativa.service.ISessaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/sessao")
public class SessaoController {

    private final ISessaoService sessaoService;

    public SessaoController(ISessaoService sessaoService) {
        this.sessaoService = sessaoService;
    }

    @PostMapping
    public ResponseEntity<SessaoResponseDTO> criarSessao(@Valid @RequestBody SessaoRequestDTO sessaoRequestDTO) {
        SessaoResponseDTO sessao = sessaoService.criarSessao(sessaoRequestDTO);

        return new ResponseEntity<>(sessao, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessaoResponseDTO> buscarSessao(@PathVariable Long id) {
        SessaoResponseDTO sessao = this.sessaoService.buscarPorId(id);
        return new ResponseEntity<>(sessao, HttpStatus.OK);
    }
}
