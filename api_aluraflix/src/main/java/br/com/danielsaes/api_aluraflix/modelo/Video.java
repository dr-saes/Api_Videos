package br.com.danielsaes.api_aluraflix.modelo;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String descricao;

    private String url;

    @OneToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;


}
