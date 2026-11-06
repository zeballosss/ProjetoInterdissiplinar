package br.com.etecfer.enciclopediacopa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.etecfer.enciclopediacopa.service.JogadorService;

@Controller
public class HomeController {

    private final JogadorService jogadorService;

    public HomeController(JogadorService jogadorService) {
        this.jogadorService = jogadorService;
    }

    @GetMapping("/")
    public String inicio(@RequestParam(required = false) String saiu, Model model) {
        var jogadores = jogadorService.buscar(null);
        model.addAttribute("jogadores", jogadores.stream().limit(8).toList());
        model.addAttribute("totalJogadores", jogadorService.contar());
        if (saiu != null) {
            model.addAttribute("mensagem", "Voce saiu da sua conta.");
        }
        return "index";
    }

    @GetMapping("/jogadores")
    public String listar(@RequestParam(required = false) String busca, Model model) {
        model.addAttribute("jogadores", jogadorService.buscar(busca));
        model.addAttribute("busca", busca == null ? "" : busca);
        model.addAttribute("totalJogadores", jogadorService.contar());
        return "jogadores/lista";
    }

    @GetMapping("/acesso-negado")
    public String acessoNegado() {
        return "acesso-negado";
    }
}
