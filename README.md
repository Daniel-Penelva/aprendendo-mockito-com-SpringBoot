# Documentação Aprendendo JUint5 e Mockito com Spring Boot

## Classe Entidade: Usuario

A classe `Usuario` é uma representação de um usuário em um sistema. Ela possui dois atributos principais:

- `id`: Este é um campo do tipo `Long` que atua como uma chave primária na tabela do banco de dados. O valor do `id` é gerado automaticamente pelo banco de dados e é usado para identificar exclusivamente cada usuário.

- `nome`: O campo `nome` é uma `String` que armazena o nome do usuário.

```java
package com.daniel.aprendendomockito.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    
}
```

### Anotações Utilizadas

A classe `Usuario` faz uso de várias anotações para configurar seu comportamento em relação ao mapeamento do banco de dados:

- `@Entity`: Esta anotação é usada para marcar a classe `Usuario` como uma entidade JPA (Java Persistence API). Isso indica que a classe deve ser mapeada para uma tabela no banco de dados.

- `@Table(name = "usuarios")`: Define o nome da tabela no banco de dados para a entidade `Usuario`. Neste caso, a tabela é nomeada como "usuarios".

- `@Id`: Esta anotação identifica o campo `id` como a chave primária da entidade. Isso significa que o valor do `id` é único para cada registro na tabela do banco de dados.

- `@GeneratedValue(strategy = GenerationType.IDENTITY)`: Define a estratégia de geração de valores para o campo `id`. Neste caso, a estratégia `GenerationType.IDENTITY` indica que o valor do `id` será gerado pelo banco de dados automaticamente.

- `@Data`: A anotação `@Data` é parte do projeto Lombok e é usada para gerar automaticamente métodos como `getter`, `setter`, `equals`, `hashCode` e outros métodos de conveniência para os campos da classe.

## Interface Repository: UsuarioRepository

A interface `UsuarioRepository` desempenha um papel fundamental em aplicativos Spring Boot que utilizam o Spring Data JPA para acessar dados em um banco de dados. Esta interface estende `JpaRepository`, fornecendo métodos e recursos para operações comuns de banco de dados, como criar, ler, atualizar e excluir registros da entidade `Usuario`.

```java
package com.daniel.aprendendomockito.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.daniel.aprendendomockito.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    @Query("SELECT u FROM Usuario u WHERE u.nome LIKE %:nome%")
    List<Usuario> findByNome(@Param("nome") String nome);
}
```

### Descrição da Interface

A interface `UsuarioRepository` fornece métodos de acesso aos dados relacionados à entidade `Usuario`. Alguns dos métodos mais importantes incluem:

- `findAll()`: Retorna uma lista de todos os registros da tabela `usuarios`.

- `findById(Long id)`: Retorna um registro da tabela `usuarios` com base no `id` fornecido.

- `save(Usuario usuario)`: Salva um novo registro da entidade `Usuario` no banco de dados.

- `deleteById(Long id)`: Exclui um registro da tabela `usuarios` com base no `id` fornecido.

Além dos métodos padrão fornecidos pelo `JpaRepository`, a interface `UsuarioRepository` também define um método personalizado `findByNome(String nome)`. Esse método utiliza a anotação `@Query` para definir uma consulta JPA personalizada que pesquisa registros da tabela `usuarios` com base no nome fornecido como parâmetro.

## Classe Service: UsuarioService

A classe `UsuarioService` é uma parte essencial de um aplicativo Spring Boot que lida com operações relacionadas a entidades `Usuario`. Ela atua como uma camada de serviço intermediária entre os controladores (que recebem solicitações HTTP) e o repositório (que realiza operações de banco de dados). Com a ajuda das anotações do Spring e do projeto Lombok, ela simplifica o desenvolvimento de lógica de negócios e a injeção de dependência.

```java
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
```

### Descrição da Classe

A classe `UsuarioService` oferece uma variedade de métodos para realizar operações relacionadas a usuários, incluindo:

- `criarUsuario(Usuario usuario)`: Cria um novo registro de usuário no banco de dados.

- `listarUsuarios()`: Retorna uma lista de todos os usuários cadastrados.

- `buscarUsuarioPorId(Long id)`: Retorna um usuário com base no ID fornecido.

- `buscarUsuariosPorNome(String nome)`: Retorna uma lista de usuários com nomes que contenham a sequência fornecida.

- `atualizarUsuario(Long id, Usuario novoUsuario)`: Atualiza os dados de um usuário existente com base no ID fornecido.

- `deletarUsuario(Long id)`: Exclui um usuário com base no ID fornecido.

Esses métodos são projetados para fornecer funcionalidades CRUD (Criar, Ler, Atualizar e Excluir) para a entidade `Usuario`. Eles encapsulam a lógica de negócios associada às operações de usuário.

### Anotações Utilizadas

A classe `UsuarioService` faz uso de algumas anotações do Spring Framework para sua configuração:

- `@Service`: Esta anotação marca a classe como um componente de serviço do Spring. Ela permite que a classe seja automaticamente detectada e gerenciada pelo mecanismo de injeção de dependência do Spring.

- `@AllArgsConstructor`: Esta anotação do projeto Lombok gera automaticamente um construtor que aceita todos os campos da classe como argumentos. Isso é útil para a injeção de dependência no construtor.

### Uso da Classe

A classe `UsuarioService` é geralmente usada pelos controladores para implementar a lógica de negócios relacionada aos usuários. Ela delega as operações de acesso a dados para o `UsuarioRepository`, que é injetado como uma dependência.

## Classe Controller: UsuarioController

A classe `UsuarioController` é uma parte fundamental de um aplicativo Spring Boot que lida com operações relacionadas a usuários. Ela atua como a camada de controle que recebe solicitações HTTP e coordena a execução de operações no sistema. Esta documentação tem como objetivo fornecer uma visão geral detalhada dos endpoints e funcionalidades oferecidas por esta classe.

```java
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
import com.daniel.aprendendomockito.service.UsuarioService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/usuarios")
@AllArgsConstructor
public class UsuarioController {

    private UsuarioService usuarioService;

    // Criar Usuario - http://localhost:8080/usuarios/
    @PostMapping(value = "/", produces = "application/json")
    public Usuario criarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.criarUsuario(usuario);
    }

    // Listar Usuário - http://localhost:8080/usuarios
    @GetMapping
    public List<Usuario> listaUsuarios() {
        return usuarioService.listarUsuarios();
    }

    // Buscar usuário por id - http://localhost:8080/usuarios/{id}
    @GetMapping(value = "/{id}", produces = "application/json")
    public Usuario buscarUsuarioPorId(@PathVariable Long id) {
        return usuarioService.buscarUsuarioPorId(id);
    }

    // Buscar usuário por nome -
    // http://localhost:8080/usuarios/usuarioPorNome/{nome}
    @GetMapping(value = "usuarioPorNome/{nome}", produces = "application/json")
    public ResponseEntity<?> buscarUsuarioPorNome(@PathVariable String nome) {
        List<Usuario> usuarios = usuarioService.buscarUsuariosPorNome(nome);

        if (!usuarios.isEmpty()) {
            return ResponseEntity.ok(usuarios);
        }
        return ResponseEntity.notFound().build();
    }

    // Atualizar usuario - http://localhost:8080/usuarios/{idAtualizar}
    @PutMapping(value = "/{idAtualizar}", produces = "application/json")
    public Usuario atualizarUsuario(@PathVariable(value = "idAtualizar") Long id, @RequestBody Usuario novoUsuario) {
        return usuarioService.atualizarUsuario(id, novoUsuario);
    }

    // Deletar um usuario - http://localhost:8080/usuarios/{idDeletar}
    @DeleteMapping(value = "/{idDeletar}", produces = "application/json")
    public void deletarUsuario(@PathVariable(value = "idDeletar") Long id) {
        usuarioService.deletarUsuario(id);
    }
}
```

### Descrição da Classe

A classe `UsuarioController` oferece uma série de endpoints para realizar operações relacionadas a usuários. Ela é marcada com a anotação `@RestController`, o que a torna uma classe controladora de API REST. As principais funcionalidades e endpoints são:

#### 1. Criar Usuário

- **Método HTTP:** POST
- **Endpoint:** `/usuarios/`
- **Descrição:** Este endpoint permite criar um novo usuário. Recebe um objeto JSON no corpo da solicitação representando os detalhes do novo usuário. A resposta é o usuário criado com um ID atribuído pelo sistema.

#### 2. Listar Usuários

- **Método HTTP:** GET
- **Endpoint:** `/usuarios`
- **Descrição:** Este endpoint retorna uma lista de todos os usuários cadastrados no sistema.

#### 3. Buscar Usuário por ID

- **Método HTTP:** GET
- **Endpoint:** `/usuarios/{id}`
- **Descrição:** Este endpoint permite buscar um usuário pelo seu ID. O ID é passado como parte da URL da solicitação.

#### 4. Buscar Usuário por Nome

- **Método HTTP:** GET
- **Endpoint:** `/usuarios/usuarioPorNome/{nome}`
- **Descrição:** Este endpoint permite buscar usuários cujo nome contenha a sequência fornecida. O nome é passado como parte da URL da solicitação.
- **Neste trecho de código:**

```java
if (!usuarios.isEmpty()) {
    return ResponseEntity.ok(usuarios);
}
return ResponseEntity.notFound().build();
```
O objetivo é lidar com a resposta HTTP que será enviada de volta ao cliente que faz a solicitação de busca de usuários por nome. Vou explicar linha por linha:

   - if (!usuarios.isEmpty()) : Esta linha verifica se a lista de usuários (usuarios) retornada pela consulta ao banco de dados não está vazia. Isso é feito usando o método isEmpty() da lista, que retorna true se a lista estiver vazia e false se houver pelo menos um usuário na lista.

   - return ResponseEntity.ok(usuarios);: Se a lista de usuários não estiver vazia, isso significa que foram encontrados usuários com o nome especificado na consulta. Nesse caso, a resposta HTTP será construída com o status 200 OK e o corpo da resposta conterá a lista de usuários encontrados (usuarios). Em resumo, isso significa que a operação foi bem-sucedida, e a lista de usuários está sendo retornada como parte do corpo da resposta.

   - return ResponseEntity.notFound().build();: Se a lista de usuários estiver vazia, isso significa que nenhum usuário foi encontrado com o nome especificado na consulta. Nesse caso, a resposta HTTP será construída com o status 404 Not Found. Isso indica que o recurso solicitado (usuários com o nome especificado) não foi encontrado. O método build() é usado para construir uma resposta vazia, já que não há dados para serem incluídos no corpo da resposta, uma vez que nenhum usuário foi encontrado.

#### 5. Atualizar Usuário

- **Método HTTP:** PUT
- **Endpoint:** `/usuarios/{idAtualizar}`
- **Descrição:** Este endpoint permite atualizar os detalhes de um usuário existente. O ID do usuário a ser atualizado é passado como parte da URL da solicitação, e os novos detalhes são fornecidos no corpo da solicitação em formato JSON.

#### 6. Deletar Usuário

- **Método HTTP:** DELETE
- **Endpoint:** `/usuarios/{idDeletar}`
- **Descrição:** Este endpoint permite excluir um usuário com base no seu ID. O ID é passado como parte da URL da solicitação.

### Uso da Classe

A classe `UsuarioController` é usada para criar uma API RESTful que oferece acesso às funcionalidades relacionadas a usuários. Ela se comunica com a classe de serviço `UsuarioService` para executar operações de negócios. 

## Classe Teste: AprendendomockitoApplicationTests

Esta classe de teste faz parte de um projeto que utiliza o framework Spring Boot e a biblioteca Mockito para realizar testes de unidade em uma aplicação Java. Ela é responsável por testar os componentes relacionados à entidade "Usuário".

```java
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

	@Test
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

	@Test
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
```

### Anotações

- `@Mock`: Essa anotação é usada para criar mocks de objetos. No contexto deste teste, são criados mocks do `UsuarioRepository`.

- `@InjectMocks`: Essa anotação é usada para injetar os mocks criados com `@Mock` em uma instância da classe `UsuarioService`. Isso permite que os métodos da classe `UsuarioService` sejam testados com o uso dos mocks.

- `@BeforeEach`: Essa anotação marca um método que deve ser executado antes de cada método de teste. No método `setUp()`, é utilizado para inicializar os mocks com o uso de `MockitoAnnotations.openMocks(this)`.

- `@Test`: Essa anotação marca um método como um método de teste. Os métodos anotados com `@Test` são executados pelo mecanismo de teste do JUnit.

### Métodos de Teste

A classe de teste possui vários métodos de teste, cada um focado em testar um aspecto específico da funcionalidade relacionada a usuários. Aqui estão alguns dos principais métodos de teste:

#### `testCriarUsuario()`

- Este método testa a criação de um novo usuário com a classe `UsuarioService`.
- Um mock do `UsuarioRepository` é configurado para simular o comportamento de salvar um usuário.
- O método `criarUsuario()` do `UsuarioService` é chamado com um novo usuário e o resultado é comparado com o usuário esperado.

#### `testBuscarTodosUsuarios()`

- Este método testa a busca de todos os usuários com a classe `UsuarioService`.
- Um mock do `UsuarioRepository` é configurado para simular o comportamento de retornar uma lista de usuários.
- O método `listarUsuarios()` do `UsuarioService` é chamado e o resultado é comparado com a lista de usuários esperada.

#### `testBuscarUsuarioPorId()`

- Este método testa a busca de um usuário por ID com a classe `UsuarioService`.
- Um mock do `UsuarioRepository` é configurado para simular o comportamento de retornar um usuário por ID.
- O método `buscarUsuarioPorId()` do `UsuarioService` é chamado e o resultado é comparado com o usuário esperado.

#### `testBuscarUsuarioPorNome()`

- Este método testa a busca de usuários por nome com a classe `UsuarioService`.
- Um mock do `UsuarioRepository` é configurado para simular o comportamento de retornar uma lista de usuários com o mesmo nome.
- O método `buscarUsuariosPorNome()` do `UsuarioService` é chamado e o resultado é comparado com a lista de usuários esperada.

#### `testAlterarUsuarioporId()`

- Este método testa a atualização do nome de um usuário com a classe `UsuarioService`.
- Um mock do `UsuarioRepository` é configurado para simular o comportamento de buscar um usuário por ID e salvar o usuário atualizado.
- O método `atualizarUsuario()` do `UsuarioService` é chamado e o nome do usuário atualizado é verificado.

#### `testDeletarUsuarioPorId()`

- Este método testa a exclusão de um usuário por ID com a classe `UsuarioService`.
- Um mock do `UsuarioRepository` é configurado para simular o comportamento de encontrar um usuário por ID.
- O método `deletarUsuario()` do `UsuarioService` é chamado e a verificação é feita para garantir que o método `deleteById()` do repositório seja chamado com o ID correto.

### Considerações Finais

Esta classe de teste ilustra como o Mockito pode ser usado em conjunto com o JUnit para testar componentes de uma aplicação Spring Boot. Cada método de teste se concentra em uma funcionalidade específica relacionada a usuários e usa mocks para isolar o código sendo testado.

## Autor
### **Documentação feita por:** `Daniel Penelva de Andrade`




