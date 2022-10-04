package br.com.challenge.analise.transacoes.financeiras.repository;


import br.com.challenge.analise.transacoes.financeiras.model.InfoImportacao;
import br.com.challenge.analise.transacoes.financeiras.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface InfoImportacaoRepository extends JpaRepository<InfoImportacao, Long> {

    Optional<InfoImportacao> findByDataTransacao(LocalDate dataTransacao);
}
