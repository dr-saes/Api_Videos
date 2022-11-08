package br.com.danielsaes.api_aluraflix.controller;

import br.com.danielsaes.api_aluraflix.controller.form.UsuarioForm;
import br.com.danielsaes.api_aluraflix.controller.form.VideoForm;
import br.com.danielsaes.api_aluraflix.service.UsuarioService;
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
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuariosService) {
       this.usuarioService = usuariosService;
    }

    @GetMapping
    public ResponseEntity<?> listarUsuarios(@RequestParam(required = false) String nome, @PageableDefault(sort = "nome",
            direction = Sort.Direction.ASC, page = 0, size = 100) Pageable paginacao) {

        if (nome == null) {
            return this.usuarioService.listarUsuarios(null, paginacao);
        } else {
            return this.usuarioService.listarUsuarios(nome, paginacao);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable Long id, Pageable paginacao) {
        return this.usuarioService.listarPorId(id, paginacao);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrarUsuario(@RequestBody @Valid UsuarioForm usuarioForm, UriComponentsBuilder
            uriBuilder) throws Exception {

        return this.usuarioService.cadastrarUsuario(usuarioForm, uriBuilder);
    }

    @PutMapping ("/{id}")
    @Transactional
    public ResponseEntity<?> atualizarPorId(@PathVariable Long id, @RequestBody @Valid UsuarioForm usuarioForm) {

        return this.usuarioService.atualizarPorId(id, usuarioForm);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deletarPorId(@PathVariable Long id) {

        return this.usuarioService.deletarPorId(id);
    }
}
