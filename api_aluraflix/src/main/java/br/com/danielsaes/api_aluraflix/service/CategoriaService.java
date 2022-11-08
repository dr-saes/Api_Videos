package br.com.danielsaes.api_aluraflix.service;

import br.com.danielsaes.api_aluraflix.controller.CategoriaController;
import br.com.danielsaes.api_aluraflix.controller.dto.CategoriaDto;
import br.com.danielsaes.api_aluraflix.controller.form.CategoriaForm;
import br.com.danielsaes.api_aluraflix.modelo.Categoria;
import br.com.danielsaes.api_aluraflix.repository.CategoriaRepository;
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
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class CategoriaService {

    private CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository) {

        this.categoriaRepository = categoriaRepository;
    }

    public ResponseEntity<?> listarCategorias(String titulo, Pageable paginacao) {

        if (titulo == null) {
            Page<Categoria> listaCategorias = categoriaRepository.findAll(paginacao);
            if (listaCategorias.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lista de categorias inexistente");
            } else {
                Page<CategoriaDto> listaCategoriaDto = CategoriaDto.converterLista(listaCategorias);
                for (CategoriaDto categoriaDto : listaCategoriaDto) {
                    long id = categoriaDto.getId();
                    categoriaDto
                            .add(linkTo(methodOn(CategoriaController.class).listarPorId(id, paginacao))
                                    .withRel("Buscar categoria por Id")
                                    .withHref("http://localhost:8080/categorias/" + categoriaDto.getId())
                                    .withType("GET"));
                    categoriaDto
                            .add(linkTo(methodOn(CategoriaController.class).listarPorId(id, paginacao))
                                    .withRel("Deletar categoria por Id")
                                    .withHref("http://localhost:8080/categorias/" + categoriaDto.getId())
                                    .withType("DELETE"));
                    categoriaDto
                            .add(linkTo(methodOn(CategoriaController.class).listarPorId(id, paginacao))
                                    .withRel("Atualizar categoria por Id")
                                    .withHref("http://localhost:8080/categorias/" + categoriaDto.getId())
                                    .withType("PUT"));

                    categoriaDto
                            .add(linkTo(methodOn(CategoriaController.class).listarPorId(id, paginacao))
                                    .withRel("Exibição de categoria por titulo: " + categoriaDto.getTitulo())
                                    .withHref("http://localhost:8080/categorias?titulo=" + categoriaDto.getTitulo())
                                    .withType("GET"));
                    categoriaDto
                            .add(linkTo(methodOn(CategoriaController.class).listarPorId(id, paginacao))
                                    .withRel("Exibição de videos por Id da categoria: " + categoriaDto.getTitulo() + " - id = " + categoriaDto.getId())
                                    .withHref("http://localhost:8080/categorias/:" + categoriaDto.getId() + "/videos")
                                    .withType("GET"));

                }
                return new ResponseEntity<Page<CategoriaDto>>(listaCategoriaDto, HttpStatus.OK);
            }
        } else {
            Page<Categoria> listaCategoriaTitulo = categoriaRepository.findByTitulo(titulo, paginacao);
            Page<CategoriaDto> listaCategoriaDtoTitulo = CategoriaDto.converterLista(listaCategoriaTitulo);
            for (CategoriaDto categoriaDto : listaCategoriaDtoTitulo) {
                categoriaDto
                        .add(linkTo(methodOn(CategoriaController.class).listarCategorias(categoriaDto.getTitulo(), paginacao))
                                .withRel(" Lista de Categorias")
                                .withHref("http://localhost:8080/categorias")
                                .withType("GET"));
                categoriaDto
                        .add(linkTo(methodOn(CategoriaController.class).listarCategorias(categoriaDto.getTitulo(), paginacao))
                                .withRel("Buscar de categoria por Id")
                                .withHref("http://localhost:8080/categorias/" + categoriaDto.getId())
                                .withType("GET"));
                ;
                categoriaDto
                        .add(linkTo(methodOn(CategoriaController.class).listarCategorias(categoriaDto.getTitulo(), paginacao))
                                .withRel("Deletar categoria por Id")
                                .withHref("http://localhost:8080/categorias/" + categoriaDto.getId())
                                .withType("DELETE"));
                ;
                categoriaDto
                        .add(linkTo(methodOn(CategoriaController.class).listarCategorias(categoriaDto.getTitulo(), paginacao))
                                .withRel("Atualizar categoria por Id")
                                .withHref("http://localhost:8080/categorias/" + categoriaDto.getId())
                                .withType("PUT"));
                ;
            }
            return new ResponseEntity<Page<CategoriaDto>>(listaCategoriaDtoTitulo, HttpStatus.OK);
        }
    }

    public ResponseEntity<?> listarPorId(Long id, Pageable paginacao) {

        Optional<Categoria> categoria = categoriaRepository.findById(id);
        if (!categoria.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id inexistente");
        } else {
            CategoriaDto categoriaDto = CategoriaDto.converterCategoria(categoria);
            categoriaDto
                    .add(linkTo(methodOn(CategoriaController.class).listarCategorias(categoriaDto.getTitulo(), paginacao))
                            .withRel("Lista de categorias")
                            .withHref("http://localhost:8080/categorias")
                            .withType("GET"));
            categoriaDto
                    .add(linkTo(methodOn(CategoriaController.class).listarPorId(id, paginacao))
                            .withRel("Exibição de categoria por titulo: " + categoriaDto.getTitulo())
                            .withHref("http://localhost:8080/categorias?titulo=" + categoriaDto.getTitulo())
                            .withType("GET"));

            categoriaDto
                    .add(linkTo(methodOn(CategoriaController.class).listarCategorias(categoriaDto.getTitulo(), paginacao))
                            .withRel("Deletar categoria por id")
                            .withHref("http://localhost:8080/categorias" + categoriaDto.getId())
                            .withType("DELETE"));

            categoriaDto
                    .add(linkTo(methodOn(CategoriaController.class).listarCategorias(categoriaDto.getTitulo(), paginacao))
                            .withRel("Atualizar categoria por id")
                            .withHref("http://localhost:8080/categorias" + categoriaDto.getId())
                            .withType("PUT"));

            return new ResponseEntity<CategoriaDto>(categoriaDto, HttpStatus.OK);
        }
    }

    public ResponseEntity<?> cadastrarCategoria(CategoriaForm categoriaForm, UriComponentsBuilder uriBuilder) {
        Categoria categoria = new Categoria();
        categoria = categoriaForm.toCategoria();

        try {
            if (!categoriaRepository
                    .findByTituloAndCor(categoria.getTitulo(), categoria.getCor())
                    .isEmpty()) {
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Categoria já existente");
        }

        categoriaRepository.save(categoria);
        URI uri = uriBuilder.path("/categorias/{id}").buildAndExpand(categoria.getId()).toUri();
        return ResponseEntity.created(uri).body(new CategoriaDto(categoria));
    }

    public ResponseEntity<Object> atualizarPorId(@PathVariable Long id, @RequestBody @Valid CategoriaForm categoriaForm) {
        Optional<Categoria> optional = categoriaRepository.findById(id);
        if (optional.isPresent()) {
            Optional<Categoria> categoria = categoriaForm.atualizar(id, categoriaRepository);
            return ResponseEntity.ok(new CategoriaDto(categoria));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id inexistente");
    }

    public ResponseEntity<Object> deletarPorId(Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        if (categoria.isPresent()) {
            categoriaRepository.deleteById(id);
            return ResponseEntity.ok().body("Categoria deletada");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id inexistente");
    }


}

