package br.com.challenge.analise.transacoes.financeiras.dto;

import br.com.challenge.analise.transacoes.financeiras.model.Usuario;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class NovoUsuarioDto {

    @NotBlank(message = "O campo nome é obrigatório")
    private String nome;

    @NotBlank(message = "O campo email é obrigatório")
    @Email(message = "O email informado é invalido")
    private String email;

    public Usuario toUsuario(){
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha();

        return usuario;
    }
}
