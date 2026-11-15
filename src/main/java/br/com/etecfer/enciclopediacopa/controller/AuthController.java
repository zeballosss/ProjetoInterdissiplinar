package br.com.etecfer.enciclopediacopa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.etecfer.enciclopediacopa.service.UsuarioService;

@Controller
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/cadastro")
    public String cadastro() {
        return "auth/cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrar(@RequestParam String nome,
                            @RequestParam String email,
                            @RequestParam String senha,
                            RedirectAttributes atributos) {
        try {
            usuarioService.cadastrar(nome, email, senha);
            atributos.addFlashAttribute("sucesso", "Conta criada. Agora voce pode entrar.");
            return "redirect:/login";
        } catch (IllegalArgumentException erro) {
            atributos.addFlashAttribute("erroCadastro", erro.getMessage());
            return "redirect:/cadastro";
        }
    }
}
