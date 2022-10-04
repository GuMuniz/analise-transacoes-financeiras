package br.com.challenge.analise.transacoes.financeiras.controller;

import br.com.challenge.analise.transacoes.financeiras.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/analise")
public class AnaliseController {

    @Autowired
    private TransacaoService transacaoService;

    @GetMapping
    public String analiseView(){return "analise";}

    @PostMapping
    public ModelAndView indentificarTransacoesMes(String data){
        ModelAndView model = new ModelAndView("analise");
        model.addObject("listTransacaoSuspeita", transacaoService.transacoesSuspeita(data));
        return model;
    }
}
