package br.com.danielsaes.api_aluraflix.repository;

import br.com.danielsaes.api_aluraflix.modelo.Categoria;
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
public interface CategoriaRepository extends JpaRepository<Categoria,Long> {

    Page<Categoria> findAll(Pageable paginacao);
    Page<Categoria> findByTitulo(String titulo, Pageable paginacao);

    @Query("SELECT titulo, cor FROM Categoria  WHERE titulo = :titulo and cor = :cor")
    List<Categoria> findByTituloAndCor(@Param("titulo") String titulo, @Param("cor") String cor);

}
