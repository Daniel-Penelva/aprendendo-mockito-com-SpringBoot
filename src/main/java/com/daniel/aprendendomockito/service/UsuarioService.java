package com.daniel.aprendendomockito.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.daniel.aprendendomockito.model.Usuario;
import com.daniel.aprendendomockito.repository.UsuarioRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioService {

   private UsuarioRepository usuarioRepository;

   // Criar um usuário 
   public Usuario criarUsuario(Usuario usuario){
    return usuarioRepository.save(usuario);
   }

   // Listar todos os usuários
   public List<Usuario> listarUsuarios(){
    return usuarioRepository.findAll();
   }

   // Listar usuário por id
   public Usuario buscarUsuarioPorId(Long id){
    Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
    return usuarioOptional.orElse(null);
   }

   // Buscar usuário por nome
   public List<Usuario> buscarUsuariosPorNome(String nome){
    return usuarioRepository.findByNome(nome);
   }

   // Atualizar usuário
   public Usuario atualizarUsuario(Long id, Usuario novoUsuario){
    Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

    if(usuarioOptional.isPresent()){
        Usuario usuario = usuarioOptional.get();
        usuario.setNome(novoUsuario.getNome());
        return usuarioRepository.save(usuario);
    }
    return null;
   }

   // Deletar usuário
   public void deletarUsuario(Long id){
    usuarioRepository.deleteById(id);
   }
}
