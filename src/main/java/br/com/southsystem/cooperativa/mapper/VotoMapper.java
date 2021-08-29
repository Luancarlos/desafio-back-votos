package br.com.southsystem.cooperativa.mapper;

import br.com.southsystem.cooperativa.dto.request.VotoRequestDTO;
import br.com.southsystem.cooperativa.dto.response.VotoResponseDTO;
import br.com.southsystem.cooperativa.entity.Voto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface VotoMapper {

    @Mapping(source = "idSessao", target = "sessao.id")
    Voto votoRequestDTOToVoto(VotoRequestDTO votoRequestDTO);

    @Mapping(source = "sessao.id", target = "idSessao")
    VotoResponseDTO votoToVotoResponseDTO(Voto voto);
}
