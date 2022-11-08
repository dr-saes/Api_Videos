package br.com.danielsaes.api_aluraflix.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class VideoServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void DeveriaDevolver200EListarTodosOsVideosComParametroNulo() throws Exception {

        URI uri = new URI("/videos");
        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void DeveriaDevolver404QdoAListaNaoExiste() throws Exception {

        URI uri = new URI("/videos");
        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void DeveriaDevolver200EDeverialistarTodosOsVideosPorTituloComParametroTitulo() throws Exception {

        URI uri = new URI("/videos?titulo=Livre");
        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void DeveriaDevolver404QdoAListaNaoExisteComTituloComoParametro() throws Exception {

        URI uri = new URI("/videos?titulo=livre");
        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void DeveriaDevolver202EListarVideoPorId() throws Exception {

        URI uri = new URI("/videos/1");
        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void DeveriaDevolver404ComIdInexistente() throws Exception {

        URI uri = new URI("/videos/1111111");
        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void DeveriaDevolver201ECriarVideo() throws Exception {

        URI uri = new URI("/videos");
        String json = "{\"titulo\" : \"Testes\" , \"descricao\" : \"DescricaoTeste\" , \"url\" : \"teste@teste.com\" , " +
                "\"categoria\" : { \"id\" : \"1\" , \"titulo\" : \"Livre\" , \"cor\" : \"VERDE\"}}";

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void DeveriaDevolver400ComJsonInvalido() throws Exception {

        URI uri = new URI("/videos");
        String json = "{}";

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void DeveriaDevolver409ENaoCriarVideoComVideoExistente() throws Exception {

        URI uri = new URI("/videos");
        String json = "{\"titulo\" : \"Testes\" , \"descricao\" : \"DescricaoTeste\" , \"url\" : \"teste@teste.com\" , " +
                "\"categoria\" : { \"id\" : \"1\" , \"titulo\" : \"Livre\" , \"cor\" : \"VERDE\"}}";

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    void DeveriaDevolver201EAtualizarVideo() throws Exception {

        URI uri = new URI("/videos/18");
        String json = "{\"titulo\" : \"TestesAtualizado\" , \"descricao\" : \"DescricaoTesteAtualizada\" , \"url\" : \"testeatualizado@teste.com\" , " +
                "\"categoria\" : { \"id\" : \"1\" , \"titulo\" : \"Livre\" , \"cor\" : \"VERDE\"}}";

        mockMvc.perform(MockMvcRequestBuilders.put(uri)
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void DeveriaDevolver404ENaoAtualiazarComIdInexistente() throws Exception {

        URI uri = new URI("/videos/188888888");
        String json = "{\"titulo\" : \"TestesAtualizado\" , \"descricao\" : \"DescricaoTesteAtualizada\" , \"url\" : \"testeatualizado@teste.com\" , " +
                "\"categoria\" : { \"id\" : \"1\" , \"titulo\" : \"Livre\" , \"cor\" : \"VERDE\"}}";

        mockMvc.perform(MockMvcRequestBuilders.put(uri)
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void DeveriaDevolver200EApagarVideoComIdExistente() throws Exception {

        URI uri = new URI("/videos/19");

        mockMvc .perform(MockMvcRequestBuilders.delete(uri))
				.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void DeveriaDevolver404ENaoApagarVideoComIdInexistente() throws Exception {

        URI uri = new URI("/videos/18999999");

        mockMvc .perform(MockMvcRequestBuilders.delete(uri))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void DeveriaDevolver200EListarVideosPorIdDaCategoria() throws Exception {

        URI uri = new URI("/categorias/:1/videos/");
        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void DeveriaDevolver404ENaoListarVideosPorIdDaCategoriaInexistente() throws Exception {

        URI uri = new URI("/categorias/:1111111/videos/");
        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}