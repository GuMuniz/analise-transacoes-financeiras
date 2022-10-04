package br.com.challenge.analise.transacoes.financeiras.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="usuarios")
public class Usuario implements UserDetails {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String nome;

    @ManyToMany
    @JoinTable(name="tb_Usuarios_perfis", joinColumns = @JoinColumn(name = "id_usuarios"),
            inverseJoinColumns = @JoinColumn(name = "id_perfis"))
    private List<Perfil> perfis = new ArrayList<>();



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha() {
        this.senha = geradorSenhaAleatoria();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Perfil> getPerfis() {
        return perfis;
    }

    public void setPerfis(List<Perfil> perfis) {
        this.perfis = perfis;
    }

    public void encoderSenha(){
        this.senha = new BCryptPasswordEncoder().encode(senha);
    }

    public String geradorSenhaAleatoria(){
        String letras = "abcdefghijklmnopqrstuvwxyz123456789ABCDEFGHIJKLMNOPRSTUVWXYZ@-&_?";
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder senha = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            senha.append(letras.charAt(secureRandom.nextInt(letras.length())));
        }
        return senha.toString();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return perfis;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}