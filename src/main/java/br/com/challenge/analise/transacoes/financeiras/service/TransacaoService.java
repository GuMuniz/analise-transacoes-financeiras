package br.com.challenge.analise.transacoes.financeiras.service;

import br.com.challenge.analise.transacoes.financeiras.dto.TransacaoDto;
import br.com.challenge.analise.transacoes.financeiras.model.InfoImportacao;
import br.com.challenge.analise.transacoes.financeiras.model.Transacao;
import br.com.challenge.analise.transacoes.financeiras.model.Usuario;
import br.com.challenge.analise.transacoes.financeiras.repository.TransacaoRepository;
import br.com.challenge.analise.transacoes.financeiras.repository.UsuarioRepository;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransacaoService {
    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private InfoImportacaoService importacaoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public String salvarTransacoes(MultipartFile file) {
        try {
            CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()));
            List<String[]> todasLinha = csvReader.readAll();
            csvReader.close();

            if (todasLinha.isEmpty()) {
                return "O arquivo está vazio";
            }
            String dataTransacoes = (todasLinha.get(0).length == 8 && formataData(todasLinha.get(0)[7]) != null)
                    ? formataData(todasLinha.get(0)[7])
                    : null;
            if (dataTransacoes == null) {
                return "Data não informada ou em formato invalido";
            }
            if (importacaoService.existeDataTransacao(LocalDate.parse(dataTransacoes))) {
                return "Data das transações já salvas na base de dados";
            }
            InfoImportacao info = salvarInfoImportacao(LocalDate.parse(dataTransacoes));
            for (String[] linha : todasLinha) {
                List<String> lista = new ArrayList<>(Arrays.asList(linha));
                lista.removeIf(String::isBlank);
                if (lista.size() == 8 && lista.get(7).contains(dataTransacoes)) {
                    Transacao transacao = converterLinhaTransacao(lista, info);
                    if (transacao != null) {
                        transacaoRepository.save(transacao);
                    }
                }
            }
            return null;
        } catch (IOException e) {
            return "Erro ao tentar ler o arquivo";
        }
    }

    public String formataData(String data) {
        try {
            LocalDateTime.parse(data);
            return data.substring(0, data.indexOf("T"));
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public InfoImportacao salvarInfoImportacao(LocalDate dataTransacoes) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        InfoImportacao info = new InfoImportacao(dataTransacoes, LocalDateTime.now(), usuario.get());
        importacaoService.salvarInfoImportacao(info);
        return info;
    }

    public Transacao converterLinhaTransacao(List<String> linha, InfoImportacao info) {
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
            transacao.setInfoImportacao(info);

            return transacao;
        } catch (NumberFormatException | DateTimeParseException e) {
            return null;
        }

    }

    public List<TransacaoDto> transacoesSuspeita(String data) {
        int mes = Integer.parseInt(data.substring(data.indexOf("-")+1));
        int ano = Integer.parseInt(data.substring(0, data.indexOf("-")));
        return transacaoRepository.buscarTransacaoSuspeitas(mes, ano).stream().map(TransacaoDto::new).collect(Collectors.toList());
    }
}