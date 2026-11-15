package br.com.etecfer.enciclopediacopa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.etecfer.enciclopediacopa.service.ImagemJogadorService;

@Configuration
public class UploadWebConfig implements WebMvcConfigurer {

    private final ImagemJogadorService imagemJogadorService;

    public UploadWebConfig(ImagemJogadorService imagemJogadorService) {
        this.imagemJogadorService = imagemJogadorService;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String localizacao = imagemJogadorService.getDiretorioUploads().toUri().toString();
        if (!localizacao.endsWith("/")) {
            localizacao += "/";
        }

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(localizacao);
    }
}
