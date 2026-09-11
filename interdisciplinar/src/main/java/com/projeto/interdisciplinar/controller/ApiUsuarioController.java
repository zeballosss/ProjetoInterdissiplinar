package com.projeto.interdisciplinar.controller;

import com.projeto.interdisciplinar.model.Usuario;
import com.projeto.interdisciplinar.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiUsuarioController {

    private final UsuarioService usuarioService;

    public ApiUsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/usuarios")
    public ResponseEntity<?> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        var usuarioEncontrado = usuarioService.buscarPorEmail(usuario.getEmail());

        if (usuarioEncontrado.isPresent() && usuarioService.validarSenha(usuario.getSenha(), usuarioEncontrado.get().getSenha())) {
            Map<String, Object> resposta = new HashMap<>();
            resposta.put("mensagem", "Login realizado com sucesso");
            resposta.put("usuario", usuarioEncontrado.get());
            return ResponseEntity.ok(resposta);
        }

        Map<String, String> erro = new HashMap<>();
        erro.put("mensagem", "Email ou senha inválidos");
        return ResponseEntity.status(401).body(erro);
    }
}
