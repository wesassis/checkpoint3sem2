package com.example.controller;

import com.example.model.Item;
import com.example.repository.ItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Testes de Integração - ItemController")
public class ItemControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        itemRepository.deleteAll();
    }

    // ===== TESTES POST (CREATE) =====

    @Test
    @DisplayName("POST /api/itens - Deve criar um novo item com sucesso")
    public void testCriarItem() throws Exception {
        Item item = new Item("Primeiro Item", "Descricao do primeiro item");

        MvcResult result = mockMvc.perform(post("/api/itens")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(item)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.nome", is("Primeiro Item")))
                .andExpect(jsonPath("$.descricao", is("Descricao do primeiro item")))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Item criadoItem = objectMapper.readValue(responseBody, Item.class);

        assertThat(criadoItem.getId()).isNotNull();
        assertThat(criadoItem.getNome()).isEqualTo("Primeiro Item");
        assertThat(criadoItem.getDescricao()).isEqualTo("Descricao do primeiro item");
    }

    @Test
    @DisplayName("POST /api/itens - Deve retornar erro ao enviar nome vazio")
    public void testCriarItemComNomeVazio() throws Exception {
        Item item = new Item("", "Descricao sem nome");

        mockMvc.perform(post("/api/itens")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(item)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    // ===== TESTES GET (READ) =====

    @Test
    @DisplayName("GET /api/itens - Deve listar todos os itens")
    public void testListarItens() throws Exception {
        Item item1 = new Item("Item 1", "Descricao 1");
        Item item2 = new Item("Item 2", "Descricao 2");

        itemRepository.save(item1);
        itemRepository.save(item2);

        mockMvc.perform(get("/api/itens")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", is("Item 1")))
                .andExpect(jsonPath("$[1].nome", is("Item 2")));
    }

    @Test
    @DisplayName("GET /api/itens - Deve retornar lista vazia quando não há itens")
    public void testListarItensVazio() throws Exception {
        mockMvc.perform(get("/api/itens")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("GET /api/itens/{id} - Deve buscar um item por ID")
    public void testBuscarItemPorId() throws Exception {
        Item itemSalvo = itemRepository.save(new Item("Item para Buscar", "Descricao para busca"));

        mockMvc.perform(get("/api/itens/{id}", itemSalvo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemSalvo.getId().intValue())))
                .andExpect(jsonPath("$.nome", is("Item para Buscar")))
                .andExpect(jsonPath("$.descricao", is("Descricao para busca")));
    }

    @Test
    @DisplayName("GET /api/itens/{id} - Deve retornar 404 para ID inexistente")
    public void testBuscarItemPorIdInexistente() throws Exception {
        mockMvc.perform(get("/api/itens/{id}", 999L)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // ===== TESTES PUT (UPDATE) =====

    @Test
    @DisplayName("PUT /api/itens/{id} - Deve atualizar um item existente")
    public void testAtualizarItem() throws Exception {
        Item itemSalvo = itemRepository.save(new Item("Nome Original", "Descricao Original"));

        Item itemAtualizado = new Item("Nome Atualizado", "Descricao Atualizada");

        mockMvc.perform(put("/api/itens/{id}", itemSalvo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(itemAtualizado)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemSalvo.getId().intValue())))
                .andExpect(jsonPath("$.nome", is("Nome Atualizado")))
                .andExpect(jsonPath("$.descricao", is("Descricao Atualizada")));

        Item verificacao = itemRepository.findById(itemSalvo.getId()).orElse(null);
        assertThat(verificacao).isNotNull();
        assertThat(verificacao.getNome()).isEqualTo("Nome Atualizado");
        assertThat(verificacao.getDescricao()).isEqualTo("Descricao Atualizada");
    }

    @Test
    @DisplayName("PUT /api/itens/{id} - Deve atualizar apenas o nome")
    public void testAtualizarApenasNome() throws Exception {
        Item itemSalvo = itemRepository.save(new Item("Nome Original", "Descricao Original"));

        Item itemAtualizado = new Item("Nome Novo", null);

        mockMvc.perform(put("/api/itens/{id}", itemSalvo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(itemAtualizado)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Nome Novo")))
                .andExpect(jsonPath("$.descricao", is("Descricao Original")));
    }

    @Test
    @DisplayName("PUT /api/itens/{id} - Deve retornar 404 para ID inexistente")
    public void testAtualizarItemInexistente() throws Exception {
        Item itemAtualizado = new Item("Nome", "Descricao");

        mockMvc.perform(put("/api/itens/{id}", 999L)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(itemAtualizado)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // ===== TESTES DELETE (DELETE) =====

    @Test
    @DisplayName("DELETE /api/itens/{id} - Deve deletar um item existente")
    public void testDeletarItem() throws Exception {
        Item itemSalvo = itemRepository.save(new Item("Item para Deletar", "Sera deletado"));

        mockMvc.perform(delete("/api/itens/{id}", itemSalvo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isNoContent());

        boolean existeAposDelete = itemRepository.existsById(itemSalvo.getId());
        assertThat(existeAposDelete).isFalse();
    }

    @Test
    @DisplayName("DELETE /api/itens/{id} - Deve retornar 404 para ID inexistente")
    public void testDeletarItemInexistente() throws Exception {
        mockMvc.perform(delete("/api/itens/{id}", 999L)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // ===== TESTES DE FLUXO COMPLETO CRUD =====

    @Test
    @DisplayName("Fluxo completo CRUD - Create, Read, Update, Delete")
    public void testFluxoCompletoCRUD() throws Exception {
        // CREATE
        Item novoItem = new Item("Item Completo", "Teste CRUD");
        MvcResult createResult = mockMvc.perform(post("/api/itens")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(novoItem)))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = createResult.getResponse().getContentAsString();
        Item itemCriado = objectMapper.readValue(responseBody, Item.class);
        Long itemId = itemCriado.getId();

        // READ - Buscar o item criado
        mockMvc.perform(get("/api/itens/{id}", itemId)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Item Completo")));

        // UPDATE - Atualizar o item
        Item itemAtualizado = new Item("Item Atualizado no Teste", "Descricao modificada");
        mockMvc.perform(put("/api/itens/{id}", itemId)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(itemAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Item Atualizado no Teste")));

        // DELETE - Deletar o item
        mockMvc.perform(delete("/api/itens/{id}", itemId)
                .characterEncoding("UTF-8"))
                .andExpect(status().isNoContent());

        // Verificar que foi deletado
        mockMvc.perform(get("/api/itens/{id}", itemId)
                .characterEncoding("UTF-8"))
                .andExpect(status().isNotFound());
    }
}
