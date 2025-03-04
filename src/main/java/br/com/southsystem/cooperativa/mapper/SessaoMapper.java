package br.com.southsystem.cooperativa.mapper;


import br.com.southsystem.cooperativa.dto.request.SessaoRequestDTO;
import br.com.southsystem.cooperativa.dto.response.SessaoResponseDTO;
import br.com.southsystem.cooperativa.entity.Sessao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface SessaoMapper {

    @Mapping(source = "pauta.id", target = "idPauta")
    @Mapping(source = "pauta.descricao", target = "descricaoPauta")
    SessaoResponseDTO sessaoToSessaoResponseDTO(Sessao sessao);

    @Mapping(source = "idPauta", target = "pauta.id")
    Sessao sessaoRequestDTOToSessao(SessaoRequestDTO sessaoRequestDTO);

}
