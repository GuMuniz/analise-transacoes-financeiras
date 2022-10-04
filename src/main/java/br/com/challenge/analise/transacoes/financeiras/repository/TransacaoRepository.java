package br.com.challenge.analise.transacoes.financeiras.repository;

import br.com.challenge.analise.transacoes.financeiras.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    @Query("SELECT t FROM Transacao t WHERE MONTH(t.infoImportacao.dataTransacao) = :mes " +
            " AND YEAR(t.infoImportacao.dataTransacao) = :ano AND t.valorTransacao >= 100000")
    List<Transacao> buscarTransacaoSuspeitas(@Param("mes") int mes, @Param("ano") int ano);


}

