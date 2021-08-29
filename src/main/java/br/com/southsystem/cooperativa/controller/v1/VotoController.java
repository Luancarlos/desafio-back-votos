package br.com.southsystem.cooperativa.controller.v1;

import br.com.southsystem.cooperativa.dto.request.VotoRequestDTO;
import br.com.southsystem.cooperativa.dto.response.VotoResponseDTO;
import br.com.southsystem.cooperativa.service.IVotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/voto")
@Api( tags = "Voto")
public class VotoController {

    private static final Logger logger = LoggerFactory.getLogger(VotoController.class);
    private final IVotoService votoService;

    public VotoController(IVotoService votoService) {
        this.votoService = votoService;
    }

    @PostMapping
    @ApiOperation(value = "Responsável por adicionar voto")
    public ResponseEntity<VotoResponseDTO> votar(@Valid @RequestBody VotoRequestDTO votoRequestDTO) {
        logger.info("Adicinando um voto: {}", votoRequestDTO);
        VotoResponseDTO voto = votoService.votar(votoRequestDTO);
        return new ResponseEntity<>(voto, HttpStatus.CREATED);
    }
}
