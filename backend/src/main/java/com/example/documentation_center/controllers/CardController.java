package com.example.documentation_center.controllers;

import com.example.documentation_center.dtos.CardDTO;
import com.example.documentation_center.models.Card;
import com.example.documentation_center.services.CardServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/v1/ts/cards")
@Tag(name = "Endpoint de Card")
public class CardController {
    @Autowired
    private CardServices service;
    @GetMapping
    @Operation(summary = "Busca todos as cards")
    public ResponseEntity<CollectionModel<CardDTO>> buscarTodos(
            @RequestParam(value="page", defaultValue = "0") int page,
            @RequestParam(value="limit", defaultValue = "12") int limit,
            @RequestParam(value="direction", defaultValue = "desc") String direction,
            @RequestParam(value="ordenation", defaultValue = "codigo") String ordenation) {
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, ordenation));
        Page<CardDTO> pages = service.findAll(pageable);
        pages
                .stream()
                .forEach(p -> p.add(
                                linkTo(methodOn(CardController.class).buscarUm(p.getCodigo())).withSelfRel()
                        )
                );
        return ResponseEntity.ok(CollectionModel.of(pages));
    }
    @GetMapping("/nomes")
    @Operation(summary = "Busca pelo nome")
    public ResponseEntity<CollectionModel<CardDTO>> buscarPeloNome(
            @RequestParam(value="page", defaultValue = "0") int page,
            @RequestParam(value="limit", defaultValue = "12") int limit,
            @RequestParam(value="direction", defaultValue = "desc") String direction,
            @RequestParam(value="ordenation", defaultValue = "codigo") String ordenation,
            String nomes) {
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, ordenation));
        Page<CardDTO> pages = service.findByNomeContains(nomes, pageable);
        pages
                .stream()
                .forEach(p -> p.add(
                                linkTo(methodOn(CardController.class).buscarUm(p.getCodigo())).withSelfRel()
                        )
                );
        return ResponseEntity.ok(CollectionModel.of(pages));
    }
    /*@GetMapping("/razao")
    @Operation(summary = "Busca pela razao")
    public ResponseEntity<CollectionModel<CardDTO>> buscarPelaRazao(
            @RequestParam(value="page", defaultValue = "0") int page,
            @RequestParam(value="limit", defaultValue = "12") int limit,
            @RequestParam(value="direction", defaultValue = "desc") String direction,
            @RequestParam(value="ordenation", defaultValue = "codigo") String ordenation,
            String razao) {
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, ordenation));
        Page<CardDTO> pages = service.findByRazaoContains(razao, pageable);
        pages
                .stream()
                .forEach(p -> p.add(
                                linkTo(methodOn(CardController.class).buscarUm(p.getCodigo())).withSelfRel()
                        )
                );
        return ResponseEntity.ok(CollectionModel.of(pages));
    }
    @GetMapping("/endereco")
    @Operation(summary = "Busca pelo endereco")
    public ResponseEntity<CollectionModel<CardDTO>> buscarPeloEndereco(
            @RequestParam(value="page", defaultValue = "0") int page,
            @RequestParam(value="limit", defaultValue = "12") int limit,
            @RequestParam(value="direction", defaultValue = "desc") String direction,
            @RequestParam(value="ordenation", defaultValue = "codigo") String ordenation,
            String endereco) {
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, ordenation));
        Page<CardDTO> pages = service.findByEnderecoContains(endereco, pageable);
        pages
                .stream()
                .forEach(p -> p.add(
                                linkTo(methodOn(CardController.class).buscarUm(p.getCodigo())).withSelfRel()
                        )
                );
        return ResponseEntity.ok(CollectionModel.of(pages));
    }*/

    @GetMapping("/{id}")
    @Operation(summary = "Busca por Id")
    public ResponseEntity<CardDTO> buscarUm(@PathVariable Integer id) {
        CardDTO objDTO = service.findById(id);
        objDTO.add(linkTo(methodOn(CardController.class).buscarUm(id)).withSelfRel());
        return ResponseEntity.ok(objDTO);
    }
/*    @GetMapping("/nome/{nome}")
    @Operation(summary = "Busca pelo Nome")
    public ResponseEntity<CardDTO> buscarCard(@PathVariable String card) {
        CardDTO objDTO = service.findByNome(card);
        objDTO.add(linkTo(methodOn(CardController.class).buscarCard(card)).withSelfRel());
        return ResponseEntity.ok(objDTO);
    }*/
    @PutMapping
    @Operation(summary = "Atualiza uma Card")
    public ResponseEntity<CardDTO> atualizar(@RequestBody CardDTO objBody) {
        CardDTO objDTO = service.save(objBody);
        objDTO.add(linkTo(methodOn(CardController.class).buscarUm(objDTO.getCodigo())).withSelfRel());
        return ResponseEntity.ok(objDTO);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Insere uma card")
    public ResponseEntity<CardDTO> incluir(@Valid @RequestBody  CardDTO obj){
        CardDTO objDTO = service.save(obj);
        objDTO.add(linkTo(methodOn(CardController.class).buscarUm(objDTO.getCodigo())).withSelfRel());
        return ResponseEntity.ok(objDTO);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma card pelo id")
    public ResponseEntity<Void> excluir(@PathVariable Integer id){
        if(!service.existById(id)){
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
