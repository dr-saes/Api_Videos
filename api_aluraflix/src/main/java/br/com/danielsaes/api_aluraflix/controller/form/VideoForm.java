package br.com.danielsaes.api_aluraflix.controller.form;

import br.com.danielsaes.api_aluraflix.modelo.Categoria;
import br.com.danielsaes.api_aluraflix.modelo.Video;
import br.com.danielsaes.api_aluraflix.repository.VideoRepository;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Getter
@Setter
public class VideoForm {

    @NotBlank
    @NotNull
    private String titulo;
    @NotBlank
    @NotNull
    private String descricao;
    @NotBlank
    @NotNull
    private String url;

    private Categoria categoria;


    public Video toVideo() {
        Video video = new Video();
        video.setTitulo(this.titulo);
        video.setDescricao(this.descricao);
        video.setUrl(this.url);
        video.setCategoria(this.categoria);
        if(categoria.getId() == null ){
            categoria.setId(Long.valueOf(1));
        } if (categoria.getTitulo() == null) {
            categoria.setTitulo("Livre");
            if(categoria.getCor() == null){
                categoria.setCor("VERDE");
            }
        }
        return video;
    }

    public Optional<Video> atualizar(Long id, VideoRepository videoRepository){
        Optional<Video> video = videoRepository.findById(id);
        video.get().setTitulo(this.titulo);
        video.get().setDescricao(this.descricao);
        video.get().setUrl(this.url);
        video.get().setCategoria(this.categoria);
        if(categoria.getId() == null ){
            categoria.setId(Long.valueOf(1));
        } if (categoria.getTitulo() == null) {
            categoria.setTitulo("Livre");
            if(categoria.getCor() == null){
                categoria.setCor("VERDE");
            }
        }

        return video;
    }



}


