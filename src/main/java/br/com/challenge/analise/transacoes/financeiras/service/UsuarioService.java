package br.com.challenge.analise.transacoes.financeiras.service;

import br.com.challenge.analise.transacoes.financeiras.dto.UsuarioDto;
import br.com.challenge.analise.transacoes.financeiras.model.Usuario;
import br.com.challenge.analise.transacoes.financeiras.repository.PerfilRepository;
import br.com.challenge.analise.transacoes.financeiras.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private JavaMailSender mailSender;

    public void cadastrarUsuario(Usuario usuario){
        enviarSenhaUsuario(usuario);
        usuario.encoderSenha();
        usuario.getPerfis().add(perfilRepository.findById(1l).get());
        usuarioRepository.save(usuario);

    }
    public void enviarSenhaUsuario(Usuario usuario) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(usuario.getEmail());
        mailMessage.setTo(usuario.getEmail());
        mailMessage.setSubject("Sua senha para acessar a aplicacao (Não Responda)");
        mailMessage.setText("""
            
            Abaixo sua senha para poder se logar na aplicacao:
            
            """ + usuario.getPassword());

        mailSender.send(mailMessage);

    }

    public boolean existeUsuario(String email){
        return usuarioRepository.findByEmail(email).isPresent();
    }

    public List<UsuarioDto> listar(){
        return usuarioRepository.findAll().stream().map(UsuarioDto::new).collect(Collectors.toList());
    }

}
