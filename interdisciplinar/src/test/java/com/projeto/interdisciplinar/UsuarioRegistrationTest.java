package com.projeto.interdisciplinar;

import com.projeto.interdisciplinar.model.Usuario;
import com.projeto.interdisciplinar.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UsuarioRegistrationTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void deveSalvarUsuarioComSenhaCriptografada() {
        Usuario usuario = new Usuario();
        usuario.setNome("Maria");
        usuario.setEmail("maria@email.com");
        usuario.setSenha("123456");

        Usuario salvo = usuarioRepository.save(usuario);

        assertThat(salvo.getIdUsuario()).isNotNull();
        assertThat(salvo.getEmail()).isEqualTo("maria@email.com");
        assertThat(passwordEncoder.matches("123456", salvo.getSenha())).isTrue();
    }
}
