package br.com.etecfer.enciclopediacopa.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.etecfer.enciclopediacopa.model.Jogador;
import br.com.etecfer.enciclopediacopa.model.Perfil;
import br.com.etecfer.enciclopediacopa.model.Usuario;
import br.com.etecfer.enciclopediacopa.repository.JogadorRepository;
import br.com.etecfer.enciclopediacopa.repository.UsuarioRepository;

@Component
public class DadosIniciaisService implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final JogadorRepository jogadorRepository;
    private final PasswordEncoder passwordEncoder;
    private final Path marcadorFotosPadrao;

    @Value("${app.admin.email}")
    private String emailAdmin;

    @Value("${app.admin.password}")
    private String senhaAdmin;

    public DadosIniciaisService(UsuarioRepository usuarioRepository,
                                JogadorRepository jogadorRepository,
                                PasswordEncoder passwordEncoder,
                                @Value("${app.upload.dir:uploads}") String diretorioUploads) {
        this.usuarioRepository = usuarioRepository;
        this.jogadorRepository = jogadorRepository;
        this.passwordEncoder = passwordEncoder;
        this.marcadorFotosPadrao = Paths.get(diretorioUploads).toAbsolutePath()
                .normalize().resolve(".fotos-padrao-aplicadas");
    }

    @Override
    public void run(String... args) {
        criarAdministradorInicial();
        cadastrarJogadoresDeExemplo();
        aplicarFotosPadraoAosJogadoresDeExemplo();
    }

    private void criarAdministradorInicial() {
        if (usuarioRepository.existsByEmailIgnoreCase(emailAdmin)) {
            return;
        }

        Usuario administrador = new Usuario(
                "Administrador",
                emailAdmin.toLowerCase(),
                passwordEncoder.encode(senhaAdmin),
                Perfil.ADMIN
        );
        usuarioRepository.save(administrador);
    }

    private void cadastrarJogadoresDeExemplo() {
        if (jogadorRepository.count() > 0) {
            return;
        }

        List<Jogador> jogadores = List.of(
                new Jogador("Lionel Messi", "Argentina", "Atacante", 10, "Nao informado",
                        "Jogador argentino conhecido pela visao de jogo, controle de bola e capacidade de criar oportunidades."),
                new Jogador("Cristiano Ronaldo", "Portugal", "Atacante", 7, "Nao informado",
                        "Atacante portugues reconhecido pela finalizacao, impulsao e longa carreira no futebol internacional."),
                new Jogador("Kylian Mbappe", "Franca", "Atacante", 10, "Nao informado",
                        "Atacante frances conhecido pela velocidade, movimentacao e capacidade de finalizacao."),
                new Jogador("Neymar Jr.", "Brasil", "Atacante", 10, "Nao informado",
                        "Jogador brasileiro conhecido pela criatividade, dribles e participacao na construcao das jogadas."),
                new Jogador("Vinicius Junior", "Brasil", "Atacante", 7, "Nao informado",
                        "Atacante brasileiro que se destaca pela velocidade, habilidade no um contra um e movimentacao."),
                new Jogador("Luka Modric", "Croacia", "Meio-campista", 10, "Nao informado",
                        "Meio-campista croata conhecido pelo passe, controle do ritmo da partida e leitura de jogo."),
                new Jogador("Harry Kane", "Inglaterra", "Atacante", 9, "Nao informado",
                        "Atacante ingles conhecido pela finalizacao e pela capacidade de participar da criacao de jogadas."),
                new Jogador("Kevin De Bruyne", "Belgica", "Meio-campista", 7, "Nao informado",
                        "Meio-campista belga reconhecido pela qualidade dos passes, cruzamentos e chutes de media distancia."),
                new Jogador("Son Heung-min", "Coreia do Sul", "Atacante", 7, "Nao informado",
                        "Atacante sul-coreano conhecido pela velocidade, finalizacao com os dois pes e movimentacao."),
                new Jogador("Mohamed Salah", "Egito", "Atacante", 11, "Nao informado",
                        "Atacante egipcio conhecido pela velocidade, conducoes em direcao ao gol e finalizacao." )
        );

        jogadorRepository.saveAll(jogadores);
    }
    private void aplicarFotosPadraoAosJogadoresDeExemplo() {
        Map<String, String> fotos = Map.ofEntries(
                Map.entry("lionel messi", "https://upload.wikimedia.org/wikipedia/commons/thumb/7/74/Lionel_Messi_close-up.jpg/500px-Lionel_Messi_close-up.jpg"),
                Map.entry("cristiano ronaldo", "https://upload.wikimedia.org/wikipedia/commons/6/64/Cristiano_Ronaldo_2018_%28cropped%29.jpg"),
                Map.entry("kylian mbappe", "https://upload.wikimedia.org/wikipedia/commons/thumb/4/45/Kylian_Mbapp%C3%A9.jpg/500px-Kylian_Mbapp%C3%A9.jpg"),
                Map.entry("neymar jr.", "https://upload.wikimedia.org/wikipedia/commons/a/aa/Neymar_ney_%28cropped%29.jpg"),
                Map.entry("vinicius junior", "https://upload.wikimedia.org/wikipedia/commons/f/f7/Vinicius_Junior_%282025%29.jpg"),
                Map.entry("luka modric", "https://upload.wikimedia.org/wikipedia/commons/1/12/Luka_Modric_Croatia_v_Portugal_2_July_2026-025.jpg"),
                Map.entry("harry kane", "https://upload.wikimedia.org/wikipedia/commons/9/9c/Harry_Kane.jpg"),
                Map.entry("kevin de bruyne", "https://upload.wikimedia.org/wikipedia/commons/3/36/Kevin_De_Bruyne.jpg"),
                Map.entry("son heung-min", "https://upload.wikimedia.org/wikipedia/commons/f/f4/Son_Heung-min_2016.jpg"),
                Map.entry("mohamed salah", "https://upload.wikimedia.org/wikipedia/commons/0/03/Mohamed_Salah.JPG")
        );

        boolean primeiraAplicacao = !Files.exists(marcadorFotosPadrao);
        boolean houveAlteracao = false;
        List<Jogador> jogadores = jogadorRepository.findAll();

        for (Jogador jogador : jogadores) {
            String nome = jogador.getNome() == null ? "" : jogador.getNome().trim().toLowerCase(Locale.ROOT);
            String fotoPadrao = fotos.get(nome);
            if (fotoPadrao == null) {
                continue;
            }

            String fotoAtual = jogador.getImagemUrl();
            // Corrige os enderecos antigos que usavam o dominio incorreto thumb.wikimedia.org.
            if (fotoAtual != null && fotoAtual.startsWith("https://thumb.wikimedia.org/wikipedia/commons/thumb/")) {
                jogador.setImagemUrl(fotoAtual.replace(
                        "https://thumb.wikimedia.org/wikipedia/commons/thumb/",
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/"));
                houveAlteracao = true;
            } else if (primeiraAplicacao && (fotoAtual == null || fotoAtual.isBlank())) {
                jogador.setImagemUrl(fotoPadrao);
                houveAlteracao = true;
            }
        }

        if (houveAlteracao) {
            jogadorRepository.saveAll(jogadores);
        }

        // O marcador evita restaurar uma foto padrao quando o administrador a remove manualmente.
        if (primeiraAplicacao) {
            try {
                Files.createDirectories(marcadorFotosPadrao.getParent());
                Files.writeString(marcadorFotosPadrao, "Fotos padrao aplicadas aos jogadores de exemplo.");
            } catch (IOException erro) {
                System.err.println("Nao foi possivel criar o marcador das fotos padrao: " + erro.getMessage());
            }
        }
    }

}
