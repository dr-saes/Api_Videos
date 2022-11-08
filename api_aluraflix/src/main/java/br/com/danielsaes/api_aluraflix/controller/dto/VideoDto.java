package br.com.danielsaes.api_aluraflix.controller.dto;

import br.com.danielsaes.api_aluraflix.modelo.Categoria;
import br.com.danielsaes.api_aluraflix.modelo.Video;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.RepresentationModel;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VideoDto extends RepresentationModel<VideoDto> {

    private Long id;

    private Long categoriaId;
    private String titulo;
    private String descricao;
    private String url;



    public VideoDto(Video video) {
        id = video.getId();
        categoriaId = video.getCategoria().getId();
        titulo = video.getTitulo();
        descricao = video.getDescricao();
        url = video.getUrl();

    }

    public VideoDto(Optional<Video> video) {
        id = video.get().getId();
        categoriaId = video.get().getCategoria().getId();
        titulo = video.get().getTitulo();
        descricao = video.get().getDescricao();
        url = video.get().getUrl();

    }

    public VideoDto(Categoria categoria) {
    }

    public static Page<VideoDto> converterLista(Page<Video> listaVideos) {

        return listaVideos.map(VideoDto::new);
    }

    public static VideoDto converterVideo(Optional<Video> video) {

        return new VideoDto(video);
    }



}
