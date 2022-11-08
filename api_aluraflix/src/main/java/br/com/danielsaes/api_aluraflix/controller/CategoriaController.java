package br.com.danielsaes.api_aluraflix.controller;

import br.com.danielsaes.api_aluraflix.controller.form.CategoriaForm;
import br.com.danielsaes.api_aluraflix.controller.form.VideoForm;
import br.com.danielsaes.api_aluraflix.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private CategoriaService categoriaService;

    @Autowired
    public CategoriaController(CategoriaService categoriaService) {

        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<?> listarCategorias(@RequestParam(required = false) String titulo, @PageableDefault(sort = "id",
            direction = Sort.Direction.ASC, page = 0, size = 100) Pageable paginacao) {

        if (titulo == null) {
            return this.categoriaService.listarCategorias(null, paginacao);
        } else {
            return this.categoriaService.listarCategorias(titulo, paginacao);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable Long id, Pageable paginacao) {
        return this.categoriaService.listarPorId(id, paginacao);
    }


    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrarCategoria(@RequestBody @Valid CategoriaForm categoriaForm, UriComponentsBuilder
            uriBuilder) throws Exception {

        return this.categoriaService.cadastrarCategoria(categoriaForm, uriBuilder);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> atualizarPorId(@PathVariable Long id, @RequestBody @Valid CategoriaForm categoriaForm) {

        return this.categoriaService.atualizarPorId(id, categoriaForm);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deletarPorId(@PathVariable Long id) {

        return this.categoriaService.deletarPorId(id);
    }


}
