package com.daniel.aprendendomockito.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daniel.aprendendomockito.model.Usuario;
import com.daniel.aprendendomockito.repository.UsuarioRepository;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/usuarios")
@AllArgsConstructor
public class UsuarioController {

    private UsuarioRepository usuarioRepository;

    // Criar Usuario - http://localhost:8080/usuarios/
    @PostMapping(value = "/", produces = "application/json")
    public Usuario criarUsuario(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Listar Usuário - http://localhost:8080/usuarios
    @GetMapping
    public List<Usuario> listaUsuarios() {
        return usuarioRepository.findAll();
    }

    // Buscar usuário por id - http://localhost:8080/usuarios/{id}
    @GetMapping(value = "/{id}", produces = "application/json")
    public Usuario buscarUsuarioPorId(@PathVariable Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    // Buscar usuário por nome -
    // http://localhost:8080/usuarios/usuarioPorNome/{nome}
    @GetMapping(value = "usuarioPorNome/{nome}", produces = "application/json")
    public ResponseEntity<?> buscarUsuarioPorNome(@PathVariable String nome) {
        List<Usuario> usuarios = usuarioRepository.findByNome(nome);

        if (!usuarios.isEmpty()) {
            return ResponseEntity.ok(usuarios);
        }
        return ResponseEntity.notFound().build();
    }

    // Atualizar usuario - http://localhost:8080/usuarios/{idAtualizar}
    @PutMapping(value = "/{idAtualizar}", produces = "application/json")
    public Usuario atualizarUsuario(@PathVariable(value = "idAtualizar") Long id, @RequestBody Usuario novoUsuario) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);

        if (usuario != null) {
            usuario.setNome(novoUsuario.getNome());
            return usuarioRepository.save(usuario);
        }
        return null;
    }

    // Deletar um usuario - http://localhost:8080/usuarios/{idDeletar}
    @DeleteMapping(value = "/{idDeletar}", produces = "application/json")
    public void deletarUsuario(@PathVariable(value = "idDeletar") Long id) {
        usuarioRepository.deleteById(id);
    }
}
