package com.daniel.aprendendomockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

	//@Test
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

	//@Test
	public void testBuscarUsuarioPorNome(){

		// Criando uma lista de usuários de exemplos de nome DAniel
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(new Usuario(1L, "Daniel"));
		usuarios.add(new Usuario(2L, "Daniel"));
		usuarios.add(new Usuario(3L, "Daniel"));

		 // Definindo o comportamento do mock do repositório
		 when(usuarioRepository.findByNome("Daniel")).thenReturn(usuarios);

		 // Chamando o método do serviço para buscar usuários por nome
		 List<Usuario> resultado = usuarioService.buscarUsuariosPorNome("Daniel");

		 // Verificando se o resultado é a mesma lista de usuários de exemplo
		 assertEquals(usuarios, resultado);
	}

	@Test
	public void testAlterarUsuarioporId(){

		// Criando um usuário de exemplo com um nome inicial
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Daniel");

		// Criando um novo objeto Usuario com o nome atualizado
		Usuario novoUsuario = new Usuario();
		novoUsuario.setNome("Daniel Up");

		// Definindo o comportamento do mock do repositório (utilizando o findByID e save)
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

		// Atualizando o nome do usuário
        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(1L, novoUsuario);

        // Verificando se o nome foi atualizado corretamente
        assertEquals("Daniel Up", usuarioAtualizado.getNome());
	}

	@Test
	public void testDeletarUsuarioPorId(){
		
		// Simular que um usuário com o ID 1 existe no repositório
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(new Usuario()));

        // Chamando o método do serviço para deletar o usuário por ID
        usuarioService.deletarUsuario(1L);

        // Verificando se o método deleteById do repositório foi chamado com o ID correto
        verify(usuarioRepository, times(1)).deleteById(1L);
	}
}
