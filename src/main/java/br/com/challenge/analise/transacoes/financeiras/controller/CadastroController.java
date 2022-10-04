package br.com.challenge.analise.transacoes.financeiras.controller;

import br.com.challenge.analise.transacoes.financeiras.dto.NovoUsuarioDto;
import br.com.challenge.analise.transacoes.financeiras.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/cadastro")
public class CadastroController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String formCadastro(NovoUsuarioDto novoUsuario){return "cadastro";}

    @PostMapping
    public ModelAndView cadastrarUsuario(@Valid NovoUsuarioDto novoUsuario, BindingResult result){
        ModelAndView model = null;
        if(result.hasErrors()){
            model = new ModelAndView("cadastro");
            return model;
        }

        if(usuarioService.existeUsuario(novoUsuario.getEmail())) {
            model = new ModelAndView("redirect:/cadastro");
            model.addObject("error", "Usuário já cadastrado.");
            return model;
        }

        try {
            usuarioService.cadastrarUsuario(novoUsuario.toUsuario());
            model = new ModelAndView("redirect:/login");
            return model;
        }catch(MailException e){
            model = new ModelAndView("redirect:/cadastro");
            model.addObject("error", "Não foi possivel enviar o email.");
            return model;
        }
    }
}
