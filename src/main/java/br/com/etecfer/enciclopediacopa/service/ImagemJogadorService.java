package br.com.etecfer.enciclopediacopa.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagemJogadorService {

    private static final long TAMANHO_MAXIMO = 5L * 1024L * 1024L;
    private final Path diretorioUploads;

    public ImagemJogadorService(@Value("${app.upload.dir:uploads}") String diretorioUploads) {
        this.diretorioUploads = Paths.get(diretorioUploads).toAbsolutePath().normalize();
    }

    public String salvar(MultipartFile arquivo) throws IOException {
        if (arquivo == null || arquivo.isEmpty()) {
            return null;
        }

        if (arquivo.getSize() > TAMANHO_MAXIMO) {
            throw new IllegalArgumentException("A foto deve ter no máximo 5 MB.");
        }

        String extensao = descobrirExtensao(arquivo.getContentType());
        if (extensao == null) {
            throw new IllegalArgumentException("Formato inválido. Envie uma imagem JPG, PNG ou WEBP.");
        }

        Path pastaJogadores = diretorioUploads.resolve("jogadores").normalize();
        if (!pastaJogadores.startsWith(diretorioUploads)) {
            throw new IllegalArgumentException("Diretório de imagens inválido.");
        }

        validarConteudoDaImagem(arquivo, extensao);
        Files.createDirectories(pastaJogadores);
        String nomeArquivo = UUID.randomUUID() + extensao;
        Path destino = pastaJogadores.resolve(nomeArquivo);
        try (InputStream entrada = arquivo.getInputStream()) {
            Files.copy(entrada, destino, StandardCopyOption.REPLACE_EXISTING);
        }

        return "/uploads/jogadores/" + nomeArquivo;
    }

    public void excluirSeForImagemEnviada(String imagemUrl) throws IOException {
        String prefixo = "/uploads/jogadores/";
        if (imagemUrl == null || !imagemUrl.startsWith(prefixo)) {
            return;
        }

        String nomeArquivo = imagemUrl.substring(prefixo.length());
        if (nomeArquivo.isBlank() || nomeArquivo.contains("/") || nomeArquivo.contains("\\")) {
            return;
        }

        Path pastaJogadores = diretorioUploads.resolve("jogadores").normalize();
        Path arquivo = pastaJogadores.resolve(nomeArquivo).normalize();
        if (arquivo.startsWith(pastaJogadores)) {
            Files.deleteIfExists(arquivo);
        }
    }

    public Path getDiretorioUploads() {
        return diretorioUploads;
    }

    private void validarConteudoDaImagem(MultipartFile arquivo, String extensao) throws IOException {
        try (InputStream entrada = arquivo.getInputStream()) {
            byte[] cabecalho = entrada.readNBytes(12);
            boolean valido;

            if (".jpg".equals(extensao)) {
                valido = cabecalho.length >= 3
                        && (cabecalho[0] & 0xFF) == 0xFF
                        && (cabecalho[1] & 0xFF) == 0xD8
                        && (cabecalho[2] & 0xFF) == 0xFF;
            } else if (".png".equals(extensao)) {
                byte[] assinatura = {(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A};
                valido = cabecalho.length >= assinatura.length;
                for (int i = 0; valido && i < assinatura.length; i++) {
                    valido = cabecalho[i] == assinatura[i];
                }
            } else {
                valido = cabecalho.length >= 12
                        && cabecalho[0] == 'R' && cabecalho[1] == 'I'
                        && cabecalho[2] == 'F' && cabecalho[3] == 'F'
                        && cabecalho[8] == 'W' && cabecalho[9] == 'E'
                        && cabecalho[10] == 'B' && cabecalho[11] == 'P';
            }

            if (!valido) {
                throw new IllegalArgumentException("O arquivo selecionado não parece ser uma imagem válida.");
            }
        }
    }

    private String descobrirExtensao(String contentType) {
        if (contentType == null) {
            return null;
        }

        return switch (contentType.toLowerCase(Locale.ROOT)) {
            case "image/jpeg", "image/jpg" -> ".jpg";
            case "image/png" -> ".png";
            case "image/webp" -> ".webp";
            default -> null;
        };
    }
}
