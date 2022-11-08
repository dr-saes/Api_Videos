package br.com.danielsaes.api_aluraflix.repository;

import br.com.danielsaes.api_aluraflix.modelo.Usuario;
import br.com.danielsaes.api_aluraflix.modelo.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByNome(String nome);

    Page<Usuario> findAll(Pageable paginacao);
    Page<Usuario> findByNome(String Nome, Pageable paginacao);

    Page<Usuario> findById(Long id, Pageable paginacao);

    @Query("SELECT nome, email FROM Usuario  WHERE nome = :nome and email = :email")
    List<Video> findByNomeAndEmail(@Param("nome") String nome, @Param("email") String email);

}
