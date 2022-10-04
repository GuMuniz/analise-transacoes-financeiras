package br.com.challenge.analise.transacoes.financeiras.repository;

import br.com.challenge.analise.transacoes.financeiras.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Optional<Usuario> findByEmail(String email);

}