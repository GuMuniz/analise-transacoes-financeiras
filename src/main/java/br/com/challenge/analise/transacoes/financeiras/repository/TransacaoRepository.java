package br.com.challenge.analise.transacoes.financeiras.repository;

import br.com.challenge.analise.transacoes.financeiras.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    @Query("SELECT t FROM Transacao t WHERE DAY(t.dataTransacao) = DAY(:data) AND MONTH(t.dataTransacao) = MONTH(:data)  AND YEAR(t.dataTransacao) = YEAR(:data)")
    List<Transacao> buscarTodasPorDataTransacao(@Param("data") LocalDate data);
    @Query("SELECT t.dataImportacao FROM Transacao t GROUP BY Cast(t.dataTransacao as LocalDate)")
    List<LocalDateTime> findAllDataImportacao();
    @Query("SELECT t.dataTransacao FROM Transacao t GROUP BY Cast(t.dataTransacao as LocalDate)")
    List<LocalDateTime> findAllDataTransacao();

}

