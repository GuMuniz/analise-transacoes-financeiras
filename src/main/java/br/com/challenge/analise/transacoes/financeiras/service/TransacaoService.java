package br.com.challenge.analise.transacoes.financeiras.service;

import br.com.challenge.analise.transacoes.financeiras.dto.ImportacoesRealizadasDto;
import br.com.challenge.analise.transacoes.financeiras.model.Transacao;
import br.com.challenge.analise.transacoes.financeiras.repository.TransacaoRepository;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TransacaoService {
    @Autowired
    private TransacaoRepository transacaoRepository;


    public String salvarTransacoes(MultipartFile file)  {
        try{
            CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()));
            List<String[]> todasLinha = csvReader.readAll();
            csvReader.close();

            if(todasLinha.isEmpty()){
                return "O arquivo está vazio";
            }
            String dataTransacoes = (todasLinha.get(0).length == 8 && formataData(todasLinha.get(0)[7]) != null)
                    ?formataData(todasLinha.get(0)[7])
                    : null;
            if(dataTransacoes == null){
                return "Data não informada ou em formato invalido";
            }
            if( ! buscarPorDataTransacao(LocalDate.parse(dataTransacoes)).isEmpty()){
                return "Data das transações já salvas na base de dados";
            }
            LocalDateTime dataImportacao= LocalDateTime.now();
            for(String[] linha : todasLinha){
                List<String> lista = new ArrayList<>(Arrays.asList(linha));
                lista.removeIf(String::isBlank);
                if(lista.size() == 8 && lista.get(7).contains(dataTransacoes)){
                    Transacao transacao = converterLinhaTransacao(lista, dataImportacao);
                    if(transacao != null){
                        transacaoRepository.save(transacao);
                    }
                }
            }
        }
        catch(IOException e){
            return "Erro ao tentar ler o arquivo";
        }
        return null;
    }
    public List<Transacao> buscarPorDataTransacao(LocalDate data) {
        return transacaoRepository.buscarTodasPorDataTransacao(data);
    }
    public String formataData(String data) {
        try {
            LocalDateTime.parse(data);
            return data.substring(0, data.indexOf("T"));
        } catch (DateTimeParseException e) {
            return null;
        }
    }
    public Transacao converterLinhaTransacao(List<String> linha, LocalDateTime dataImportacao) {
        try {
            Transacao transacao = new Transacao();
            transacao.setBancoOrigem(linha.get(0));
            transacao.setAgenciaOrigem(linha.get(1));
            transacao.setContaOrigem(linha.get(2));
            transacao.setBancoDestino(linha.get(3));
            transacao.setAgenciaDestino(linha.get(4));
            transacao.setContaDestino(linha.get(5));
            transacao.setValorTransacao(new BigDecimal(linha.get(6)));
            transacao.setDataTransacao(LocalDateTime.parse(linha.get(7)));
            transacao.setDataImportacao(dataImportacao);
            return transacao;
        } catch (NumberFormatException | DateTimeParseException e) {
            return null;
        }

    }
    public List<ImportacoesRealizadasDto> getImportacoesRealizadasDto (){
        List<ImportacoesRealizadasDto> list = new ArrayList<>();
        List<LocalDateTime> listImportacao = transacaoRepository.findAllDataImportacao();
        List<LocalDateTime> listTransacao = transacaoRepository.findAllDataTransacao();
        for(int i =0; i< listTransacao.size(); i++){
            list.add(new ImportacoesRealizadasDto(listImportacao.get(i), listTransacao.get(i)));
        }
        return list;
    }
}
