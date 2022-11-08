package br.com.danielsaes.api_aluraflix.service;

import br.com.danielsaes.api_aluraflix.controller.UsuarioController;
import br.com.danielsaes.api_aluraflix.controller.dto.UsuarioDto;
import br.com.danielsaes.api_aluraflix.controller.form.UsuarioForm;
import br.com.danielsaes.api_aluraflix.modelo.Usuario;
import br.com.danielsaes.api_aluraflix.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public ResponseEntity<?> listarUsuarios(String nome, Pageable paginacao) {

        if (nome == null) {

            Page<Usuario> listaUsuario = usuarioRepository.findAll(paginacao);
            if (listaUsuario.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lista de usuarios inexistente");
            } else {
                Page<UsuarioDto> listaUsuarioDto = UsuarioDto.converterLista(listaUsuario);
                for (UsuarioDto usuarioDto : listaUsuarioDto) {
                    long id = usuarioDto.getId();
                    usuarioDto
                            .add(linkTo(methodOn(UsuarioController.class).listarUsuarios(null, paginacao))
                                    .withRel("Buscar usuario por Id")
                                    .withHref("http://localhost:8080/usuarios/" + usuarioDto.getId())
                                    .withType("GET"));
                    usuarioDto
                            .add(linkTo(methodOn(UsuarioController.class).listarPorId(id, paginacao))
                                    .withRel("Listar usuario por nome")
                                    .withHref("http://localhost:8080/usuarios?nome=" + usuarioDto.getNome())
                                    .withType("GET"));
                    usuarioDto
                            .add(linkTo(methodOn(UsuarioController.class).listarPorId(id, paginacao))
                                    .withRel("Atualizar usuario por Id")
                                    .withHref("http://localhost:8080/usuarios/" + usuarioDto.getId())
                                    .withType("PUT"));
                    usuarioDto
                            .add(linkTo(methodOn(UsuarioController.class).listarPorId(id, paginacao))
                                    .withRel("Deletar usuario por Id")
                                    .withHref("http://localhost:8080/usuarios/" + usuarioDto.getId())
                                    .withType("DELETE"));
                }
                return new ResponseEntity<Page<UsuarioDto>>(listaUsuarioDto, HttpStatus.OK);
            }
        } else {
            Page<Usuario> listaUsuarioNome = usuarioRepository.findByNome(nome, paginacao);

            Page<UsuarioDto> listaUsuarioNomeDto;
            if (listaUsuarioNome.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lista de usuarios inexistente");

            } else {

                listaUsuarioNomeDto = UsuarioDto.converterLista(listaUsuarioNome);
                for (UsuarioDto usuarioDto : listaUsuarioNomeDto) {
                    long id = usuarioDto.getId();
                    usuarioDto
                            .add(linkTo(methodOn(UsuarioController.class).listarUsuarios(usuarioDto.getNome(), paginacao))
                                    .withRel(" Lista de usuarios")
                                    .withHref("http://localhost:8080/usuarios")
                                    .withType("GET"));
                    usuarioDto
                            .add(linkTo(methodOn(UsuarioController.class).listarUsuarios(null, paginacao))
                                    .withRel("Buscar usuario por Id")
                                    .withHref("http://localhost:8080/usuarios/" + usuarioDto.getId())
                                    .withType("GET"));
                    usuarioDto
                            .add(linkTo(methodOn(UsuarioController.class).listarPorId(id, paginacao))
                                    .withRel("Atualizar usuario por Id")
                                    .withHref("http://localhost:8080/usuarios/" + usuarioDto.getId())
                                    .withType("PUT"));
                    usuarioDto
                            .add(linkTo(methodOn(UsuarioController.class).listarPorId(id, paginacao))
                                    .withRel("Deletar usuario por Id")
                                    .withHref("http://localhost:8080/usuarios/" + usuarioDto.getId())
                                    .withType("DELETE"));
                }
            }
            return new ResponseEntity<Page<UsuarioDto>>(listaUsuarioNomeDto, HttpStatus.OK);
        }
    }
    public ResponseEntity<?> listarPorId(Long id, Pageable paginacao) {

        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (!usuario.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id inexistente");
        } else {
            UsuarioDto usuarioDto = UsuarioDto.converterVideo(usuario);
            usuarioDto
                    .add(linkTo(methodOn(UsuarioController.class).listarUsuarios(usuarioDto.getNome(), paginacao))
                            .withRel("Lista de usuarios")
                            .withHref("http://localhost:8080/usuarios")
                            .withType("GET"));
            usuarioDto
                    .add(linkTo(methodOn(UsuarioController.class).listarPorId(id, paginacao))
                            .withRel("Listar usuario por nome")
                            .withHref("http://localhost:8080/usuarios?nome=" + usuarioDto.getNome())
                            .withType("GET"));
            usuarioDto
                    .add(linkTo(methodOn(UsuarioController.class).listarPorId(id, paginacao))
                            .withRel("Atualizar usuario por Id")
                            .withHref("http://localhost:8080/usuarios/" + usuarioDto.getId())
                            .withType("PUT"));
            usuarioDto
                    .add(linkTo(methodOn(UsuarioController.class).listarPorId(id, paginacao))
                            .withRel("Deletar usuario por Id")
                            .withHref("http://localhost:8080/usuarios/" + usuarioDto.getId())
                            .withType("DELETE"));


            return new ResponseEntity<UsuarioDto>(usuarioDto, HttpStatus.OK);
        }
    }

    public ResponseEntity<?> cadastrarUsuario(UsuarioForm usuarioForm, UriComponentsBuilder uriBuilder) {
        Usuario usuario = new Usuario();
        usuario = usuarioForm.toUsuario();

        try {
            if (!usuarioRepository
                    .findByNomeAndEmail(usuario.getNome(), usuario.getEmail())
                    .isEmpty()) {
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuario já existente");
        }

        usuarioRepository.save(usuario);
        URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new UsuarioDto(usuario));
    }

    public ResponseEntity<Object> atualizarPorId(@PathVariable Long id, @RequestBody @Valid UsuarioForm usuarioForm) {
        Optional<Usuario> optional = usuarioRepository.findById(id);
        if (optional.isPresent()) {
            Optional<Usuario> usuario = usuarioForm.atualizar(id, usuarioRepository);
            return ResponseEntity.ok(new UsuarioDto(usuario));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id inexistente");
    }

    public ResponseEntity<Object> deletarPorId(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.ok().body("Usuario deletado");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id inexistente");
    }


}



