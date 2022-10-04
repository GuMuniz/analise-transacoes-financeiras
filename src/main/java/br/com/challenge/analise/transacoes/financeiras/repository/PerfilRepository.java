package br.com.challenge.analise.transacoes.financeiras.repository;

import br.com.challenge.analise.transacoes.financeiras.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PerfilRepository  extends JpaRepository<Perfil, Long> {

}
