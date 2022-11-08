package br.com.danielsaes.api_aluraflix.repository;

import br.com.danielsaes.api_aluraflix.modelo.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video,Long> {

    Page<Video> findAll(Pageable paginacao);
    Page<Video> findByTitulo(String titulo, Pageable paginacao);

    @Query("SELECT titulo, url FROM Video  WHERE titulo = :titulo and url = :url")
    List<Video> findByTituloAndUrl(@Param("titulo") String titulo, @Param("url") String url);

    Page<Video> findByCategoriaId(Long id, Pageable paginacao);
}