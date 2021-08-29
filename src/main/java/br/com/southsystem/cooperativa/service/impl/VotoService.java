package br.com.southsystem.cooperativa.service.impl;

import br.com.southsystem.cooperativa.dto.request.VotoRequestDTO;
import br.com.southsystem.cooperativa.dto.response.SessaoResponseDTO;
import br.com.southsystem.cooperativa.dto.response.VotoResponseDTO;
import br.com.southsystem.cooperativa.entity.Voto;
import br.com.southsystem.cooperativa.exceptions.BadRequestException;
import br.com.southsystem.cooperativa.mapper.VotoMapper;
import br.com.southsystem.cooperativa.repository.VotoRepository;
import br.com.southsystem.cooperativa.service.ISessaoService;
import br.com.southsystem.cooperativa.service.IVotoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class VotoService implements IVotoService {

    private final VotoRepository votoRepository;
    private final VotoMapper votoMapper;
    private final ISessaoService sessaoService;

    public VotoService(VotoRepository votoRepository, VotoMapper votoMapper, ISessaoService sessaoService) {
        this.votoRepository = votoRepository;
        this.votoMapper = votoMapper;
        this.sessaoService = sessaoService;
    }

    @Override
    public VotoResponseDTO votar(VotoRequestDTO votoRequestDTO) {

        validarCpf(votoRequestDTO.getCpf());
        validarSessao(votoRequestDTO.getIdSessao());

        if (jaVotou(votoRequestDTO.getCpf())) {
            throw new BadRequestException("O CPF informado já efetuou um voto nesta sessão");
        }

        Voto voto = votoMapper.votoRequestDTOToVoto(votoRequestDTO);
        voto.setDataVoto(LocalDateTime.now());

        Voto novoVoto = votoRepository.save(voto);

        return votoMapper.votoToVotoResponseDTO(novoVoto);
    }

    private boolean jaVotou(String cpf) {
        Optional<Voto> voto = this.votoRepository.findByCpf(cpf);
        return voto.isPresent();
    }

    private String validarCpf(String cpf) {
        String cpfSemMascara = cpf.replaceAll("[^\\d]", "");
        if (cpfSemMascara.length() != 11) {
            throw new BadRequestException("O CPF informado é inválido");
        }
        return cpfSemMascara;
    }

    private void validarSessao(Long id) {
        SessaoResponseDTO sessao = sessaoService.buscarPorId(id);

        LocalDateTime now = LocalDateTime.now();
        if (sessao.getDataFechamento().isBefore(now)) {
            throw new BadRequestException("Está sessão já foi encerreda");
        }
    }


}
