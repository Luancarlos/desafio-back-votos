package br.com.southsystem.cooperativa.service.impl;

import br.com.southsystem.cooperativa.dto.request.SessaoRequestDTO;
import br.com.southsystem.cooperativa.dto.response.SessaoResponseDTO;
import br.com.southsystem.cooperativa.entity.Pauta;
import br.com.southsystem.cooperativa.entity.Sessao;
import br.com.southsystem.cooperativa.exceptions.BadRequestException;
import br.com.southsystem.cooperativa.exceptions.ResourceNotFoundException;
import br.com.southsystem.cooperativa.mapper.PautaMapper;
import br.com.southsystem.cooperativa.mapper.SessaoMapper;
import br.com.southsystem.cooperativa.repository.SessaoRepository;
import br.com.southsystem.cooperativa.service.IPautaService;
import br.com.southsystem.cooperativa.service.ISessaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SessaoService implements ISessaoService {
    private static final Logger logger = LoggerFactory.getLogger(SessaoService.class);
    private final SessaoRepository sessaoRepository;
    private final SessaoMapper sessaoMapper;
    private final IPautaService pautaService;
    private final PautaMapper pautaMapper;
    private final JmsTemplate jmsTemplate;

    public SessaoService(SessaoRepository sessaoRepository, SessaoMapper sessaoMapper, IPautaService pautaService, PautaMapper pautaMapper, JmsTemplate jmsTemplate) {
        this.sessaoRepository = sessaoRepository;
        this.sessaoMapper = sessaoMapper;
        this.pautaService = pautaService;
        this.pautaMapper = pautaMapper;
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public SessaoResponseDTO criarSessao(SessaoRequestDTO sessaoRequestDTO) {
        validarDataFechamento(sessaoRequestDTO.getDataFechamento());

        Sessao sessao = sessaoMapper.sessaoRequestDTOToSessao(sessaoRequestDTO);
        sessao.setPauta(getPauta(sessaoRequestDTO.getIdPauta()));
        sessao.setDataAbertura(LocalDateTime.now());
        addDataFechamento(sessao);
        Sessao novaSessao = null;

        try {
            novaSessao = sessaoRepository.save(sessao);
        } catch (Exception e) {
            throw new BadRequestException("A pauta já está associada a uma sessão");
        }

        return sessaoMapper.sessaoToSessaoResponseDTO(novaSessao);
    }

    @Override
    public SessaoResponseDTO buscarPorId(Long id) {
        Sessao sessao = sessaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi possível encontrar a Sessão"));
        return sessaoMapper.sessaoToSessaoResponseDTO(sessao);
    }

    private void addDataFechamento(Sessao sessao) {
        if (sessao.getDataFechamento() == null) {
            sessao.setDataFechamento(LocalDateTime.now().plusMinutes(1));
        }
    }

    private void validarDataFechamento(LocalDateTime localDateTime) {
        LocalDateTime now = LocalDateTime.now();
        if (localDateTime != null && now.isAfter(localDateTime)) {
            throw new BadRequestException("A data de fechamento é menor que a data atual");
        }
    }

    private Pauta getPauta(Long id) {
        return pautaMapper.pautaResponseDTOToPauta(this.pautaService.buscarPautaPorId(id));
    }

    @Scheduled(fixedRate = 60000, zone = "America/Recife")
    @Transactional
    public void enviarMensagemQuandoFecharSessao() {
        logger.info("Buscando sessões fechadas");
        List<Sessao> sessoes = sessaoRepository.findAllByFechadoAndDataFechamentoIsLessThanEqual(false, LocalDateTime.now());

        sessoes.forEach(item -> {
            try {
                item.setFechado(true);
                sessaoRepository.save(item);
                SessaoResponseDTO sessaoResponseDTO = sessaoMapper.sessaoToSessaoResponseDTO(item);

                ObjectMapper mapper = new ObjectMapper();
                mapper.findAndRegisterModules();
                String json = mapper.writeValueAsString(sessaoResponseDTO);

                jmsTemplate.convertAndSend("resultado-votacao-queue", json);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        });

    }


}
