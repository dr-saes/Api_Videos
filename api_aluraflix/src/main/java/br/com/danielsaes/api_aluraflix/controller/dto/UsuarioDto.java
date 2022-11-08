package br.com.danielsaes.api_aluraflix.controller.dto;

import br.com.danielsaes.api_aluraflix.modelo.Categoria;
import br.com.danielsaes.api_aluraflix.modelo.Usuario;
import br.com.danielsaes.api_aluraflix.modelo.Video;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.RepresentationModel;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto extends RepresentationModel<UsuarioDto> {

    private Long id;

    private String email;
    private String nome;



    public UsuarioDto(Usuario usuario) {
        id = usuario.getId();
        nome = usuario.getNome();
        email = usuario.getEmail();
    }

    public UsuarioDto(Optional<Usuario> usuario) {
        id = usuario.get().getId();
        nome = usuario.get().getNome();
        email = usuario.get().getEmail();

    }


    public static Page<UsuarioDto> converterLista(Page<Usuario> listaVideos) {

        return listaVideos.map(UsuarioDto::new);
    }

    public static UsuarioDto converterVideo(Optional<Usuario> usuario) {

        return new UsuarioDto(usuario);
    }



}
