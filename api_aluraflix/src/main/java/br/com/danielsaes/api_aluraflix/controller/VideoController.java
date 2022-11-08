package br.com.danielsaes.api_aluraflix.controller;

import br.com.danielsaes.api_aluraflix.controller.form.VideoForm;
import br.com.danielsaes.api_aluraflix.service.VideoService;
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
//@RequestMapping("/videos")
public class VideoController {

    private final VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/videos")
    public ResponseEntity<?> listarVideos(@RequestParam(required = false) String titulo, @PageableDefault(sort = "id",
            direction = Sort.Direction.ASC, page = 0, size = 100) Pageable paginacao) {

        if (titulo == null) {
            return this.videoService.listarVideos(null, paginacao);
        } else {
            return this.videoService.listarVideos(titulo, paginacao);
        }
    }

    @GetMapping("/videos/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable Long id, Pageable paginacao) {

        return this.videoService.listarPorId(id, paginacao);
    }

    @PostMapping ("/videos")
    @Transactional
    public ResponseEntity<?> cadastrarVideo(@RequestBody @Valid VideoForm videoForm, UriComponentsBuilder
            uriBuilder) throws Exception {

        return this.videoService.cadastrarVideo(videoForm, uriBuilder);
    }

    @PutMapping ("/videos/{id}")
    @Transactional
    public ResponseEntity<?> atualizarPorId(@PathVariable Long id, @RequestBody @Valid VideoForm videoForm) {

        return this.videoService.atualizarPorId(id, videoForm);
    }

    @DeleteMapping("/videos/{id}")
    @Transactional
    public ResponseEntity<?> deletarPorId(@PathVariable Long id) {

        return this.videoService.deletarPorId(id);
    }

    @GetMapping("/categorias/:{id}/videos")
    public ResponseEntity<?> listarVideosPorCategoriaId(@PathVariable Long id, @PageableDefault(sort = "id",
            direction = Sort.Direction.ASC, page = 0, size = 100)Pageable paginacao){

        return this.videoService.listarVideosPorCategoriaId(id, paginacao);
    }



}