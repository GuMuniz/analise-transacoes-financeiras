package br.com.challenge.analise.transacoes.financeiras.dto;

import br.com.challenge.analise.transacoes.financeiras.model.InfoImportacao;
import br.com.challenge.analise.transacoes.financeiras.model.Usuario;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class InfoImportacaoDto {
    private LocalDate dataTransacao;
    private LocalDateTime dataImportacao;

    private Usuario usuario;

    public InfoImportacaoDto() {
    }

    public InfoImportacaoDto(InfoImportacao infoImportacao) {
        this.dataTransacao = infoImportacao.getDataTransacao();
        this.dataImportacao = infoImportacao.getDataImportacao();
        this.usuario = infoImportacao.getUsuario();
    }
}

