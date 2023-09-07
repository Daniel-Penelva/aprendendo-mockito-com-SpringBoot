package com.daniel.aprendendomockito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.daniel.aprendendomockito.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    Usuario findByNome(String nome);
    
}
