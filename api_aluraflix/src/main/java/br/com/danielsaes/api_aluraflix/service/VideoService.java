package br.com.danielsaes.api_aluraflix.service;

import br.com.danielsaes.api_aluraflix.controller.VideoController;
import br.com.danielsaes.api_aluraflix.controller.dto.VideoDto;
import br.com.danielsaes.api_aluraflix.controller.form.VideoForm;
import br.com.danielsaes.api_aluraflix.modelo.Video;
import br.com.danielsaes.api_aluraflix.repository.VideoRepository;
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
public class VideoService {

    private VideoRepository videoRepository;

    @Autowired
    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public ResponseEntity<?> listarVideos(String titulo, Pageable paginacao) {

        if (titulo == null) {
            Page<Video> listaVideos = videoRepository.findAll(paginacao);
            if (listaVideos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lista de videos inexistente");
            } else {
                Page<VideoDto> listaVideosDto = VideoDto.converterLista(listaVideos);
                for (VideoDto videoDto : listaVideosDto) {
                    long id = videoDto.getId();
                    videoDto
                            .add(linkTo(methodOn(VideoController.class).listarPorId(id, paginacao))
                                    .withRel("Buscar video por Id")
                                    .withHref("http://localhost:8080/videos/" + videoDto.getId())
                                    .withType("GET"));
                    videoDto
                            .add(linkTo(methodOn(VideoController.class).listarPorId(id, paginacao))
                                    .withRel("Deletar video por Id")
                                    .withHref("http://localhost:8080/videos/" + videoDto.getId())
                                    .withType("DELETE"));
                    videoDto
                            .add(linkTo(methodOn(VideoController.class).listarPorId(id, paginacao))
                                    .withRel("Atualizar video por Id")
                                    .withHref("http://localhost:8080/videos/" + videoDto.getId())
                                    .withType("PUT"));

                    videoDto
                            .add(linkTo(methodOn(VideoController.class).listarPorId(id, paginacao))
                                    .withRel("Exibição de videos com o titulo: " + videoDto.getTitulo())
                                    .withHref("http://localhost:8080/videos?titulo=" + videoDto.getTitulo())
                                    .withType("GET"));

                    videoDto
                            .add(linkTo(methodOn(VideoController.class).listarPorId(id, paginacao))
                                    .withRel("Exibição de videos por categoriaId: " + videoDto.getCategoriaId())
                                    .withHref("http://localhost:8080/categorias/:" + videoDto.getCategoriaId() + "/videos")
                                    .withType("GET"));

                    videoDto
                            .add(linkTo(methodOn(VideoController.class).listarPorId(id, paginacao))
                                    .withRel("Exibição lista de categorias:")
                                    .withHref("http://localhost:8080/categorias")
                                    .withType("GET"));

                }
                return new ResponseEntity<Page<VideoDto>>(listaVideosDto, HttpStatus.OK);

            }
        } else {
            Page<Video> listaVideoTitulo = videoRepository.findByTitulo(titulo, paginacao);

            if (listaVideoTitulo.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lista de videos inexistente");
            } else {

                Page<VideoDto> listaVideoDtoTitulo = VideoDto.converterLista(listaVideoTitulo);
                for (VideoDto videoDto : listaVideoDtoTitulo) {
                    long id = videoDto.getId();
                    videoDto
                            .add(linkTo(methodOn(VideoController.class).listarVideos(videoDto.getTitulo(), paginacao))
                                    .withRel(" Lista de Videos")
                                    .withHref("http://localhost:8080/videos")
                                    .withType("GET"));
                    videoDto
                            .add(linkTo(methodOn(VideoController.class).listarPorId(id, paginacao))
                                    .withRel("Exibição de videos por categoriaId: " + videoDto.getCategoriaId())
                                    .withHref("http://localhost:8080/videos/categorias/:" + videoDto.getCategoriaId() + "/videos")
                                    .withType("GET"));

                    videoDto
                            .add(linkTo(methodOn(VideoController.class).listarPorId(id, paginacao))
                                    .withRel("Exibição lista de categorias:")
                                    .withHref("http://localhost:8080/categorias")
                                    .withType("GET"));

                    videoDto
                            .add(linkTo(methodOn(VideoController.class).listarVideos(videoDto.getTitulo(), paginacao))
                                    .withRel("Buscar por Id")
                                    .withHref("http://localhost:8080/videos/" + videoDto.getId())
                                    .withType("GET"));
                    ;
                    videoDto
                            .add(linkTo(methodOn(VideoController.class).listarVideos(videoDto.getTitulo(), paginacao))
                                    .withRel("Deletar por Id")
                                    .withHref("http://localhost:8080/videos/" + videoDto.getId())
                                    .withType("DELETE"));
                    ;
                    videoDto
                            .add(linkTo(methodOn(VideoController.class).listarVideos(videoDto.getTitulo(), paginacao))
                                    .withRel("Atualizar por Id")
                                    .withHref("http://localhost:8080/videos/" + videoDto.getId())
                                    .withType("PUT"));
                    ;


                }
                return new ResponseEntity<Page<VideoDto>>(listaVideoDtoTitulo, HttpStatus.OK);
            }
        }
    }


    public ResponseEntity<?> listarPorId(Long id, Pageable paginacao) {

        Optional<Video> video = videoRepository.findById(id);
        if (!video.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id inexistente");
        } else {
            VideoDto videoDto = VideoDto.converterVideo(video);
            videoDto
                    .add(linkTo(methodOn(VideoController.class).listarVideos(videoDto.getTitulo(), paginacao))
                            .withRel("Lista de videos")
                            .withHref("http://localhost:8080/videos")
                            .withType("GET"));

            videoDto
                    .add(linkTo(methodOn(VideoController.class).listarPorId(id, paginacao))
                            .withRel("Exibição lista de categorias:")
                            .withHref("http://localhost:8080/categorias")
                            .withType("GET"));

            videoDto
                    .add(linkTo(methodOn(VideoController.class).listarPorId(id, paginacao))
                            .withRel("Exibição de videos por categoriaId: " + videoDto.getCategoriaId())
                            .withHref("http://localhost:8080/categorias/:" + videoDto.getCategoriaId() + "/videos")
                            .withType("GET"));

            videoDto
                    .add(linkTo(methodOn(VideoController.class).listarPorId(id, paginacao))
                            .withRel("Exibição de videos com o titulo: " + videoDto.getTitulo())
                            .withHref("http://localhost:8080/videos?titulo=" + videoDto.getTitulo())
                            .withType("GET"));

            videoDto
                    .add(linkTo(methodOn(VideoController.class).listarVideos(videoDto.getTitulo(), paginacao))
                            .withRel("Deletar por id")
                            .withHref("http://localhost:8080/videos" + videoDto.getId())
                            .withType("DELETE"));

            videoDto
                    .add(linkTo(methodOn(VideoController.class).listarVideos(videoDto.getTitulo(), paginacao))
                            .withRel("Atualizar por id")
                            .withHref("http://localhost:8080/videos" + videoDto.getId())
                            .withType("PUT"));

            return new ResponseEntity<VideoDto>(videoDto, HttpStatus.OK);
        }
    }

    public ResponseEntity<?> cadastrarVideo(VideoForm videoForm, UriComponentsBuilder uriBuilder) {
        Video video = new Video();
        video = videoForm.toVideo();

        try {
            if (!videoRepository
                    .findByTituloAndUrl(video.getTitulo(), video.getUrl())
                    .isEmpty()) {
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Video já existente");
        }

        videoRepository.save(video);
        URI uri = uriBuilder.path("/videos/{id}").buildAndExpand(video.getId()).toUri();
        return ResponseEntity.created(uri).body(new VideoDto(video));
    }

    public ResponseEntity<Object> atualizarPorId(@PathVariable Long id, @RequestBody @Valid VideoForm form) {
        Optional<Video> optional = videoRepository.findById(id);
        if (optional.isPresent()) {
            Optional<Video> video = form.atualizar(id, videoRepository);
            return ResponseEntity.ok(new VideoDto(video));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id inexistente");
    }

    public ResponseEntity<Object> deletarPorId(Long id) {
        Optional<Video> video = videoRepository.findById(id);
        if (video.isPresent()) {
            videoRepository.deleteById(id);
            return ResponseEntity.ok().body("Video deletado");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id inexistente");
    }

    public ResponseEntity<?> listarVideosPorCategoriaId(Long id, Pageable paginacao) {

        Page<Video> listaVideosPorCategoriaId = videoRepository.findByCategoriaId(id, paginacao);

        if (listaVideosPorCategoriaId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lista de videos inexistente");
        } else {

            Page<VideoDto> listaVideosPorCategoriaIdDto = VideoDto.converterLista(listaVideosPorCategoriaId);

            for (VideoDto videoDto : listaVideosPorCategoriaIdDto) {
                long videoId = videoDto.getId();
                videoDto
                        .add(linkTo(methodOn(VideoController.class).listarPorId(videoId, paginacao))
                                .withRel("Buscar video por Id")
                                .withHref("http://localhost:8080/videos/" + videoDto.getId())
                                .withType("GET"));
                videoDto
                        .add(linkTo(methodOn(VideoController.class).listarPorId(videoId, paginacao))
                                .withRel("Deletar video por Id")
                                .withHref("http://localhost:8080/videos/" + videoDto.getId())
                                .withType("DELETE"));
                videoDto
                        .add(linkTo(methodOn(VideoController.class).listarPorId(videoId, paginacao))
                                .withRel("Atualizar video por Id")
                                .withHref("http://localhost:8080/videos/" + videoDto.getId())
                                .withType("PUT"));

                videoDto
                        .add(linkTo(methodOn(VideoController.class).listarPorId(videoId, paginacao))
                                .withRel("Exibição de videos por titulo: " + videoDto.getTitulo())
                                .withHref("http://localhost:8080/videos?titulo=" + videoDto.getTitulo())
                                .withType("GET"));
                videoDto
                        .add(linkTo(methodOn(VideoController.class).listarVideos(null, paginacao))
                                .withRel("Lista de videos")
                                .withHref("http://localhost:8080/videos")
                                .withType("GET"));

                videoDto
                        .add(linkTo(methodOn(VideoController.class).listarPorId(id, paginacao))
                                .withRel("Exibição lista de categorias:")
                                .withHref("http://localhost:8080/categorias")
                                .withType("GET"));
            }

            return new ResponseEntity<Page<VideoDto>>(listaVideosPorCategoriaIdDto, HttpStatus.OK);
        }
    }

}
