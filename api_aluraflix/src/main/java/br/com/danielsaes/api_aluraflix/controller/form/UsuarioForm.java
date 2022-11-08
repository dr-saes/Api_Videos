package br.com.danielsaes.api_aluraflix.controller.form;

import br.com.danielsaes.api_aluraflix.modelo.Categoria;
import br.com.danielsaes.api_aluraflix.modelo.Perfil;
import br.com.danielsaes.api_aluraflix.modelo.Usuario;
import br.com.danielsaes.api_aluraflix.modelo.Video;
import br.com.danielsaes.api_aluraflix.repository.UsuarioRepository;
import br.com.danielsaes.api_aluraflix.repository.VideoRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

@Getter
@Setter
public class UsuarioForm {

    @NotBlank
    @NotNull
    private String nome;
    @NotBlank
    @NotNull
    private String email;
    @NotBlank
    @NotNull
    private String senha;


    public Usuario toUsuario() {

        Usuario usuario = new Usuario();
        Perfil perfilUsuario = new Perfil(Long.valueOf(1), "ROLE_USUARIO");
        List<Perfil> perfis = new ArrayList<>();
        perfis.add(perfilUsuario);


        usuario.setNome(this.nome);
        usuario.setEmail(this.email);
        usuario.setSenha(new BCryptPasswordEncoder().encode(this.senha));
        usuario.setPerfis(perfis);

        return usuario;
    }

    public Optional<Usuario> atualizar(Long id, UsuarioRepository usuarioRepository){
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        usuario.get().setNome(this.nome);
        usuario.get().setEmail(this.email);
        usuario.get().setSenha(new BCryptPasswordEncoder().encode(this.senha));

        return usuario;
    }



}


