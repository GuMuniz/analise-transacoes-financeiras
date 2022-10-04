package br.com.challenge.analise.transacoes.financeiras.dto;

import br.com.challenge.analise.transacoes.financeiras.model.Usuario;
import lombok.Data;

@Data
public class UsuarioDto {

    private Long id;
    private String nome;
    private String email;

    public UsuarioDto(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
    }

}
