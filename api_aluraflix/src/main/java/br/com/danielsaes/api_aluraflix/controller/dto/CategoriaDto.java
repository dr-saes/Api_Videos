package br.com.danielsaes.api_aluraflix.controller.dto;

import br.com.danielsaes.api_aluraflix.modelo.Categoria;
import br.com.danielsaes.api_aluraflix.modelo.Video;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.RepresentationModel;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDto extends RepresentationModel<CategoriaDto> {

    private Long id;
    private String titulo;
    private String cor;



    public CategoriaDto(Categoria categoria) {
        id = categoria.getId();
        titulo = categoria.getTitulo();
        cor = categoria.getCor();
    }

    public CategoriaDto(Optional<Categoria> categoria) {
        id = categoria.get().getId();
        titulo = categoria.get().getTitulo();
        cor = categoria.get().getCor();
    }

    public static Page<CategoriaDto> converterLista(Page<Categoria> listaCategorias) {
        return listaCategorias.map(CategoriaDto::new);
    }

    public static CategoriaDto converterCategoria(Optional<Categoria> categoria) {

        return new CategoriaDto(categoria);
    }

}
