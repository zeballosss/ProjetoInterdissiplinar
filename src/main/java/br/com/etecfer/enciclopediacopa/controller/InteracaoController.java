package br.com.etecfer.enciclopediacopa.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.etecfer.enciclopediacopa.model.Usuario;
import br.com.etecfer.enciclopediacopa.service.InteracaoService;

@Controller
public class InteracaoController {

    private final InteracaoService interacaoService;

    public InteracaoController(InteracaoService interacaoService) {
        this.interacaoService = interacaoService;
    }

    @PostMapping("/interacoes/jogadores/{id}/favoritar")
    public String alternarFavorito(@PathVariable Long id,
                                   @AuthenticationPrincipal Usuario usuario,
                                   RedirectAttributes atributos) {
        boolean adicionado = interacaoService.alternarFavorito(usuario.getId(), id);
        atributos.addFlashAttribute("mensagem", adicionado
                ? "Jogador adicionado aos favoritos."
                : "Jogador removido dos favoritos.");
        return "redirect:/jogadores/" + id;
    }

    @PostMapping("/interacoes/jogadores/{id}/avaliar")
    public String avaliar(@PathVariable Long id,
                          @AuthenticationPrincipal Usuario usuario,
                          @RequestParam Integer nota,
                          @RequestParam(required = false) String comentario,
                          RedirectAttributes atributos) {
        try {
            interacaoService.avaliar(usuario.getId(), id, nota, comentario);
            atributos.addFlashAttribute("mensagem", "Sua avaliacao foi salva.");
        } catch (IllegalArgumentException erro) {
            atributos.addFlashAttribute("erro", erro.getMessage());
        }
        return "redirect:/jogadores/" + id;
    }
}
