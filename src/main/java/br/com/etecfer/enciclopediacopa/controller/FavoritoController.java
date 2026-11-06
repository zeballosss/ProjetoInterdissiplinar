package br.com.etecfer.enciclopediacopa.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.etecfer.enciclopediacopa.model.Usuario;
import br.com.etecfer.enciclopediacopa.repository.FavoritoRepository;

@Controller
public class FavoritoController {

    private final FavoritoRepository favoritoRepository;

    public FavoritoController(FavoritoRepository favoritoRepository) {
        this.favoritoRepository = favoritoRepository;
    }

    @GetMapping("/favoritos")
    public String listar(@AuthenticationPrincipal Usuario usuario, Model model) {
        model.addAttribute("favoritos", favoritoRepository.findByUsuarioIdOrderByIdDesc(usuario.getId()));
        return "favoritos/lista";
    }
}
