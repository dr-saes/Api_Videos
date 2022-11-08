package br.com.danielsaes.api_aluraflix.controller.form;

import br.com.danielsaes.api_aluraflix.modelo.Categoria;
import br.com.danielsaes.api_aluraflix.modelo.Video;
import br.com.danielsaes.api_aluraflix.repository.CategoriaRepository;
import br.com.danielsaes.api_aluraflix.repository.VideoRepository;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Getter
@Setter
public class CategoriaForm {

    @NotBlank
    @NotNull
    private String titulo;
    @NotBlank
    @NotNull
    private String cor;



    public Categoria toCategoria() {
        Categoria categoria = new Categoria();
        categoria.setTitulo(this.titulo);
        categoria.setCor(this.cor);
        return categoria;
    }

    public Optional<Categoria> atualizar(Long id, CategoriaRepository categoriaRepository){
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        categoria.get().setTitulo(this.titulo);
        categoria.get().setCor(this.cor);
        return categoria;
    }



}


