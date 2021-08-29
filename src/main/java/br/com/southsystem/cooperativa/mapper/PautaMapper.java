package br.com.southsystem.cooperativa.mapper;

import br.com.southsystem.cooperativa.dto.request.PautaRequestDTO;
import br.com.southsystem.cooperativa.dto.response.PautaResponseDTO;
import br.com.southsystem.cooperativa.dto.response.PautaResultadoDTO;
import br.com.southsystem.cooperativa.entity.Pauta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface PautaMapper {

    PautaResponseDTO pautaToPautaResponse(Pauta pauta);

    Pauta pautaResponseDTOToPauta(PautaResponseDTO pautaResponseDTO);

    Pauta pautaRequestDTOToPauta(PautaRequestDTO pautaRequestDTO);

    @Mapping(source = "pauta.sessao.dataAbertura", target = "dataAberturaSessao")
    @Mapping(source = "pauta.sessao.dataFechamento", target = "dataFechamentoSessao")
    PautaResultadoDTO pautaToPautaResultado(Pauta pauta);

}
