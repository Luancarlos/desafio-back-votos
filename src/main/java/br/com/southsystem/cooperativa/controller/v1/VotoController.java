package br.com.southsystem.cooperativa.controller.v1;

import br.com.southsystem.cooperativa.dto.request.VotoRequestDTO;
import br.com.southsystem.cooperativa.dto.response.VotoResponseDTO;
import br.com.southsystem.cooperativa.service.VotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/voto")
public class VotoController {

    private final VotoService votoService;

    public VotoController(VotoService votoService) {
        this.votoService = votoService;
    }

    @PostMapping
    public ResponseEntity<VotoResponseDTO> votar(@Valid @RequestBody VotoRequestDTO votoRequestDTO) {
        VotoResponseDTO voto = votoService.votar(votoRequestDTO);
        return new ResponseEntity<>(voto, HttpStatus.CREATED);
    }
}
