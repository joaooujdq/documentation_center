package com.example.documentation_center.controllers;

import com.example.documentation_center.dtos.FolderDTO;
import com.example.documentation_center.models.Folder;
import com.example.documentation_center.services.FolderServices;
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
@RequestMapping("/v1/ts/folders")
@Tag(name = "Endpoint de Folder")
public class FolderController {
    @Autowired
    private FolderServices service;
    @GetMapping
    @Operation(summary = "Busca todos as folders")
    public ResponseEntity<CollectionModel<FolderDTO>> buscarTodos(
            @RequestParam(value="page", defaultValue = "0") int page,
            @RequestParam(value="limit", defaultValue = "12") int limit,
            @RequestParam(value="direction", defaultValue = "desc") String direction,
            @RequestParam(value="ordenation", defaultValue = "codigo") String ordenation) {
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, ordenation));
        Page<FolderDTO> pages = service.findAll(pageable);
        pages
                .stream()
                .forEach(p -> p.add(
                                linkTo(methodOn(FolderController.class).buscarUm(p.getCodigo())).withSelfRel()
                        )
                );
        return ResponseEntity.ok(CollectionModel.of(pages));
    }
    @GetMapping("/nomes")
    @Operation(summary = "Busca pelo nome")
    public ResponseEntity<CollectionModel<FolderDTO>> buscarPeloNome(
            @RequestParam(value="page", defaultValue = "0") int page,
            @RequestParam(value="limit", defaultValue = "12") int limit,
            @RequestParam(value="direction", defaultValue = "desc") String direction,
            @RequestParam(value="ordenation", defaultValue = "codigo") String ordenation,
            String nomes) {
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, ordenation));
        Page<FolderDTO> pages = service.findByNomeContains(nomes, pageable);
        pages
                .stream()
                .forEach(p -> p.add(
                                linkTo(methodOn(FolderController.class).buscarUm(p.getCodigo())).withSelfRel()
                        )
                );
        return ResponseEntity.ok(CollectionModel.of(pages));
    }
    /*@GetMapping("/razao")
    @Operation(summary = "Busca pela razao")
    public ResponseEntity<CollectionModel<FolderDTO>> buscarPelaRazao(
            @RequestParam(value="page", defaultValue = "0") int page,
            @RequestParam(value="limit", defaultValue = "12") int limit,
            @RequestParam(value="direction", defaultValue = "desc") String direction,
            @RequestParam(value="ordenation", defaultValue = "codigo") String ordenation,
            String razao) {
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, ordenation));
        Page<FolderDTO> pages = service.findByRazaoContains(razao, pageable);
        pages
                .stream()
                .forEach(p -> p.add(
                                linkTo(methodOn(FolderController.class).buscarUm(p.getCodigo())).withSelfRel()
                        )
                );
        return ResponseEntity.ok(CollectionModel.of(pages));
    }
    @GetMapping("/endereco")
    @Operation(summary = "Busca pelo endereco")
    public ResponseEntity<CollectionModel<FolderDTO>> buscarPeloEndereco(
            @RequestParam(value="page", defaultValue = "0") int page,
            @RequestParam(value="limit", defaultValue = "12") int limit,
            @RequestParam(value="direction", defaultValue = "desc") String direction,
            @RequestParam(value="ordenation", defaultValue = "codigo") String ordenation,
            String endereco) {
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, ordenation));
        Page<FolderDTO> pages = service.findByEnderecoContains(endereco, pageable);
        pages
                .stream()
                .forEach(p -> p.add(
                                linkTo(methodOn(FolderController.class).buscarUm(p.getCodigo())).withSelfRel()
                        )
                );
        return ResponseEntity.ok(CollectionModel.of(pages));
    }*/

    @GetMapping("/{id}")
    @Operation(summary = "Busca por Id")
    public ResponseEntity<FolderDTO> buscarUm(@PathVariable Integer id) {
        FolderDTO objDTO = service.findById(id);
        objDTO.add(linkTo(methodOn(FolderController.class).buscarUm(id)).withSelfRel());
        return ResponseEntity.ok(objDTO);
    }
/*    @GetMapping("/nome/{nome}")
    @Operation(summary = "Busca pelo Nome")
    public ResponseEntity<FolderDTO> buscarFolder(@PathVariable String folder) {
        FolderDTO objDTO = service.findByNome(folder);
        objDTO.add(linkTo(methodOn(FolderController.class).buscarFolder(folder)).withSelfRel());
        return ResponseEntity.ok(objDTO);
    }*/
    @PutMapping
    @Operation(summary = "Atualiza uma Folder")
    public ResponseEntity<FolderDTO> atualizar(@RequestBody FolderDTO objBody) {
        FolderDTO objDTO = service.save(objBody);
        objDTO.add(linkTo(methodOn(FolderController.class).buscarUm(objDTO.getCodigo())).withSelfRel());
        return ResponseEntity.ok(objDTO);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Insere uma folder")
    public ResponseEntity<FolderDTO> incluir(@Valid @RequestBody  FolderDTO obj){
        FolderDTO objDTO = service.save(obj);
        objDTO.add(linkTo(methodOn(FolderController.class).buscarUm(objDTO.getCodigo())).withSelfRel());
        return ResponseEntity.ok(objDTO);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma folder pelo id")
    public ResponseEntity<Void> excluir(@PathVariable Integer id){
        if(!service.existById(id)){
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
