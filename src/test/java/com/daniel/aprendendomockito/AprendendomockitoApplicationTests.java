package com.daniel.aprendendomockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

	@Test
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

}
