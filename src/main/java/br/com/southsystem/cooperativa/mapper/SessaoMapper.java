package br.com.southsystem.cooperativa.mapper;


import br.com.southsystem.cooperativa.dto.response.SessaoResponseDTO;
import br.com.southsystem.cooperativa.entity.Sessao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface SessaoMapper {

    @Mapping(source = "pauta.id", target = "pautaId")
    SessaoResponseDTO sessaoToSessaoResponseDTO(Sessao sessao);

}
