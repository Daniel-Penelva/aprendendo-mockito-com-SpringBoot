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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    // Criar Usuario - http://localhost:8080/usuarios
    @PostMapping
    public Usuario criarUsuario(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Listar Usuário - http://localhost:8080/usuarios
    @GetMapping
    public List<Usuario> listaUsuarios() {
        return usuarioRepository.findAll();
    }

    // Buscar usuário por id - http://localhost:8080/usuarios/{id}
    @GetMapping
    public Usuario buscarUsuarioPorId(@PathVariable Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    // Buscar usuário por nome - http://localhost:8080/usuarios/{nome}
    @GetMapping("/{nome}")
    public ResponseEntity<?> buscarUsuarioPorNome(@PathVariable String nome) {
        List<Usuario> usuarios = usuarioRepository.findByNome(nome);

        if (!usuarios.isEmpty()) {
            return ResponseEntity.ok(usuarios);
        }
        return ResponseEntity.notFound().build();
    }

    // Atualizar usuario - http://localhost:8080/usuarios/{id}
    @PutMapping("/{id}")
    public Usuario atualizarUsuario(@PathVariable Long id, @RequestBody Usuario novoUsuario){
        Usuario usuario = usuarioRepository.findById(id).orElse(null);

        if(usuario != null){
            usuario.setNome(novoUsuario.getNome());
            return usuarioRepository.save(usuario);
        }
        return null;
    }

    // Deletar um usuario - http://localhost:8080/usuarios/{id}
    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable Long id){
        usuarioRepository.deleteById(id);
    } 
}
