package br.com.southsystem.cooperativa.service;

import br.com.southsystem.cooperativa.dto.request.VotoRequestDTO;
import br.com.southsystem.cooperativa.dto.response.VotoResponseDTO;

public interface VotoService {
    VotoResponseDTO votar(VotoRequestDTO votoRequestDTO);
}
