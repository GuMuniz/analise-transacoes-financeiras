package br.com.challenge.analise.transacoes.financeiras.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ImportacoesRealizadasDto {
    private LocalDateTime dataImportacao;
    private LocalDateTime dataTransacao;

    public ImportacoesRealizadasDto() {
    }

    public ImportacoesRealizadasDto(LocalDateTime dataImportacao, LocalDateTime dataTransacao) {
        this.dataImportacao = dataImportacao;
        this.dataTransacao = dataTransacao;
    }

}
