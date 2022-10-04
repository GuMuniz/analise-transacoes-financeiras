package br.com.challenge.analise.transacoes.financeiras.dto;

import br.com.challenge.analise.transacoes.financeiras.model.Transacao;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransacaoDto {

    private String bancoOrigem;
    private String agenciaOrigem;
    private String contaOrigem;
    private String bancoDestino;
    private String agenciaDestino;
    private String contaDestino;
    private BigDecimal valorTransacao;

    public TransacaoDto(Transacao transacao){
        this.bancoOrigem = transacao.getBancoOrigem();
        this.agenciaOrigem = transacao.getAgenciaOrigem();
        this.contaOrigem = transacao.getContaOrigem();
        this.bancoDestino = transacao.getBancoDestino();
        this.agenciaDestino = transacao.getAgenciaDestino();
        this.contaDestino = transacao.getContaDestino();
        this.valorTransacao = transacao.getValorTransacao();
    }
}
