package com.example.controller;

import com.example.model.Item;
import com.example.repository.ItemRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/itens")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    /**
     * POST /api/itens - Criar um novo item
     */
    @PostMapping
    public ResponseEntity<Item> criarItem(@Valid @RequestBody Item item) {
        Item novoItem = itemRepository.save(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoItem);
    }

    /**
     * GET /api/itens - Listar todos os itens
     */
    @GetMapping
    public ResponseEntity<List<Item>> listarItens() {
        List<Item> itens = itemRepository.findAll();
        return ResponseEntity.ok(itens);
    }

    /**
     * GET /api/itens/{id} - Buscar item por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Item> buscarItemPorId(@PathVariable Long id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isPresent()) {
            return ResponseEntity.ok(item.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * PUT /api/itens/{id} - Atualizar um item
     */
    @PutMapping("/{id}")
    public ResponseEntity<Item> atualizarItem(@PathVariable Long id, @Valid @RequestBody Item itemAtualizado) {
        Optional<Item> itemExistente = itemRepository.findById(id);
        if (itemExistente.isPresent()) {
            Item item = itemExistente.get();
            if (itemAtualizado.getNome() != null && !itemAtualizado.getNome().isBlank()) {
                item.setNome(itemAtualizado.getNome());
            }
            if (itemAtualizado.getDescricao() != null) {
                item.setDescricao(itemAtualizado.getDescricao());
            }
            Item itemSalvo = itemRepository.save(item);
            return ResponseEntity.ok(itemSalvo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /api/itens/{id} - Deletar um item
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarItem(@PathVariable Long id) {
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
