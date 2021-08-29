package br.com.southsystem.cooperativa.service.impl;

import br.com.southsystem.cooperativa.dto.request.VotoRequestDTO;
import br.com.southsystem.cooperativa.dto.response.VotoResponseDTO;
import br.com.southsystem.cooperativa.entity.Voto;
import br.com.southsystem.cooperativa.exceptions.BadRequestException;
import br.com.southsystem.cooperativa.mapper.VotoMapper;
import br.com.southsystem.cooperativa.repository.VotoRepository;
import br.com.southsystem.cooperativa.service.VotoService;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class IVotoService implements VotoService {

    private final VotoRepository votoRepository;
    private final VotoMapper votoMapper;

    public IVotoService(VotoRepository votoRepository, VotoMapper votoMapper) {
        this.votoRepository = votoRepository;
        this.votoMapper = votoMapper;
    }

    @Override
    public VotoResponseDTO votar(VotoRequestDTO votoRequestDTO) {
        if (jaVotou(votoRequestDTO.getCpf())) {
            throw new BadRequestException("O CPF informado já efetuou um voto nesta sessão");
        }

        Voto voto = votoMapper.votoRequestDTOToVoto(votoRequestDTO);

        Voto novoVoto = votoRepository.save(voto);

        return votoMapper.votoToVotoResponseDTO(novoVoto);
    }

    private boolean jaVotou(String cpf) {
        Optional<Voto> voto = this.votoRepository.findByCpf(cpf);
        return voto.isPresent();
    }

    private String validarCpf(String cpf) {
        return cpf;
    }


}
