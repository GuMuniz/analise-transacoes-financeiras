package br.com.challenge.analise.transacoes.financeiras.service;

import br.com.challenge.analise.transacoes.financeiras.dto.InfoImportacaoDto;
import br.com.challenge.analise.transacoes.financeiras.model.InfoImportacao;
import br.com.challenge.analise.transacoes.financeiras.repository.InfoImportacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InfoImportacaoService {

    @Autowired
    private InfoImportacaoRepository importacaoRepository;

    public List<InfoImportacaoDto> buscarTodas(){
        return importacaoRepository.findAll().stream().map(InfoImportacaoDto::new).collect(Collectors.toList());
    }

    public boolean existeDataTransacao(LocalDate dataTransacao){
        return importacaoRepository.findByDataTransacao(dataTransacao).isPresent();
    }

    public void salvarInfoImportacao(InfoImportacao infoImportacao){
        importacaoRepository.save(infoImportacao);
    }
}
