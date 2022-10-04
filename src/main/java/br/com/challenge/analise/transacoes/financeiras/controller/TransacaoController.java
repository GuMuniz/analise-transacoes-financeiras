package br.com.challenge.analise.transacoes.financeiras.controller;

import br.com.challenge.analise.transacoes.financeiras.service.InfoImportacaoService;
import br.com.challenge.analise.transacoes.financeiras.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private InfoImportacaoService importacaoService;
    @GetMapping
    public ModelAndView transacoes(){
        ModelAndView model = new ModelAndView("transacoes");
        model.addObject("infoImportacao", importacaoService.buscarTodas());
        return model;
    }

    @PostMapping("/salvar")
    public ModelAndView salvarTransacoes(@RequestParam("file") MultipartFile file) {
        ModelAndView model = new ModelAndView("redirect:/transacoes");
        if(file.getContentType().equals("text/csv")){
            String mensagem = transacaoService.salvarTransacoes(file);
            if(mensagem == null){
                model.addObject("mensagem", "Transações salvas com sucesso !!");
                return model;
            }
            model.addObject("error", mensagem);
            return model;
        }
        model.addObject("error", "formato do arquivo inválido");
        return model;
    }

}