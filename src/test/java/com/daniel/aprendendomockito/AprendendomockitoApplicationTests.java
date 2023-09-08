package com.daniel.aprendendomockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.daniel.aprendendomockito.model.Usuario;
import com.daniel.aprendendomockito.repository.UsuarioRepository;
import com.daniel.aprendendomockito.service.UsuarioService;

@SpringBootTest
class AprendendomockitoApplicationTests {

	@Mock
	private UsuarioRepository usuarioRepository;

	@InjectMocks
	private UsuarioService usuarioService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	//@Test
	public void testCriarUsuario() {

		// Criando um novo usuário
		Usuario novoUsuario = new Usuario();
		novoUsuario.setNome("Ana");

		// Definindo o comportamento do Mock do repositório
		when(usuarioRepository.save(any(Usuario.class))).thenReturn(novoUsuario);

		// Chamando o método do serviço para criar o usuário
		Usuario resultado = usuarioService.criarUsuario(novoUsuario);

		// Verificando se o usuário é o mesmo usuário do exemplo
		assertEquals(novoUsuario, resultado);
	}

	//@Test
	public void testBuscarTodosUsuarios(){

		// Criando uma lista de usuários como exemplo
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(new Usuario(1L, "João"));
		usuarios.add(new Usuario(2L, "Daniel"));
		usuarios.add(new Usuario(3L, "Maria"));
		usuarios.add(new Usuario(4L, "Igor"));

		// Definindo o comportamento do Mock do repositório
		when(usuarioRepository.findAll()).thenReturn(usuarios);

		// Chamando o método do serviço para buscar todos os usuários
		List<Usuario> resultados = usuarioService.listarUsuarios();

		//Verificando se o resultado é a mesma lista de usuários de exemplo
		assertEquals(usuarios, resultados);
	}

	@Test
	public void testBuscarUsuarioPorId(){
		// Criando um novo usuário
		Usuario usuario = new Usuario();
		usuario.setId(1L);
		usuario.setNome("Ana");

		// Definindo o comportamento do mock do repositório
		when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

		// Chamando o método do serviço para buscar o usuário por ID
		Usuario resultado = usuarioService.buscarUsuarioPorId(1L);

		 // Verificando se o resultado é o mesmo que o usuário de exemplo
		 assertEquals(usuario, resultado);
	}

}
