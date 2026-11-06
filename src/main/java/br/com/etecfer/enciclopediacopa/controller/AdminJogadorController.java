package br.com.etecfer.enciclopediacopa.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.etecfer.enciclopediacopa.model.Jogador;
import br.com.etecfer.enciclopediacopa.service.ImagemJogadorService;
import br.com.etecfer.enciclopediacopa.service.JogadorService;
import jakarta.validation.Valid;

@Controller
public class AdminJogadorController {

    private final JogadorService jogadorService;
    private final ImagemJogadorService imagemJogadorService;

    public AdminJogadorController(JogadorService jogadorService,
                                  ImagemJogadorService imagemJogadorService) {
        this.jogadorService = jogadorService;
        this.imagemJogadorService = imagemJogadorService;
    }

    @GetMapping("/admin/jogadores")
    public String listar(Model model) {
        model.addAttribute("jogadores", jogadorService.buscar(null));
        return "admin/jogadores/lista";
    }

    @GetMapping("/admin/jogadores/novo")
    public String novo(Model model) {
        model.addAttribute("jogador", new Jogador());
        model.addAttribute("titulo", "Cadastrar jogador");
        return "admin/jogadores/formulario";
    }

    @GetMapping("/admin/jogadores/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("jogador", jogadorService.buscarPorId(id));
        model.addAttribute("titulo", "Editar jogador");
        return "admin/jogadores/formulario";
    }

    @PostMapping("/admin/jogadores/salvar")
    public String salvar(@Valid @ModelAttribute("jogador") Jogador jogador,
                         BindingResult resultado,
                         @RequestParam(name = "arquivoImagem", required = false) MultipartFile arquivoImagem,
                         @RequestParam(name = "novaImagemUrl", required = false) String novaImagemUrl,
                         @RequestParam(name = "removerImagem", defaultValue = "false") boolean removerImagem,
                         Model model,
                         RedirectAttributes atributos) {

        String titulo = jogador.getId() == null ? "Cadastrar jogador" : "Editar jogador";
        if (resultado.hasErrors()) {
            model.addAttribute("titulo", titulo);
            model.addAttribute("imagemUrlDigitada", novaImagemUrl);
            return "admin/jogadores/formulario";
        }

        Jogador jogadorOriginal = jogador.getId() == null ? null : jogadorService.buscarPorId(jogador.getId());
        String imagemAnterior = jogadorOriginal == null ? null : jogadorOriginal.getImagemUrl();

        try {
            if (arquivoImagem != null && !arquivoImagem.isEmpty()) {
                // Quando o administrador envia um arquivo, ele tem prioridade sobre a URL.
                jogador.setImagemUrl(imagemJogadorService.salvar(arquivoImagem));
            } else if (removerImagem) {
                jogador.setImagemUrl(null);
            } else if (novaImagemUrl != null && !novaImagemUrl.isBlank()) {
                String url = novaImagemUrl.trim();
                if (!url.startsWith("https://") && !url.startsWith("http://")) {
                    model.addAttribute("titulo", titulo);
                    model.addAttribute("erroImagem", "A URL da imagem deve começar com http:// ou https://.");
                    model.addAttribute("imagemUrlDigitada", novaImagemUrl);
                    return "admin/jogadores/formulario";
                }
                jogador.setImagemUrl(url);
            } else if (jogadorOriginal != null) {
                // Se nenhuma foto nova foi escolhida, mantém a foto atual durante a edição.
                jogador.setImagemUrl(imagemAnterior);
            }

            Jogador jogadorSalvo = jogadorService.salvar(jogador);
            if (imagemAnterior != null && !imagemAnterior.equals(jogadorSalvo.getImagemUrl())) {
                excluirImagemAntigaSemInterromper(imagemAnterior);
            }

            atributos.addFlashAttribute("mensagem", "Jogador salvo com sucesso.");
            return "redirect:/admin/jogadores";
        } catch (IllegalArgumentException erro) {
            model.addAttribute("titulo", titulo);
            model.addAttribute("erroImagem", erro.getMessage());
            model.addAttribute("imagemUrlDigitada", novaImagemUrl);
            return "admin/jogadores/formulario";
        } catch (IOException erro) {
            model.addAttribute("titulo", titulo);
            model.addAttribute("erroImagem", "Não foi possível salvar a foto. Tente outro arquivo.");
            model.addAttribute("imagemUrlDigitada", novaImagemUrl);
            return "admin/jogadores/formulario";
        }
    }

    @PostMapping("/admin/jogadores/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes atributos) {
        Jogador jogador = jogadorService.buscarPorId(id);
        jogadorService.excluir(id);
        excluirImagemAntigaSemInterromper(jogador.getImagemUrl());
        atributos.addFlashAttribute("mensagem", "Jogador excluído.");
        return "redirect:/admin/jogadores";
    }

    private void excluirImagemAntigaSemInterromper(String imagemUrl) {
        try {
            imagemJogadorService.excluirSeForImagemEnviada(imagemUrl);
        } catch (IOException erro) {
            System.err.println("Não foi possível remover uma foto antiga: " + erro.getMessage());
        }
    }
}
