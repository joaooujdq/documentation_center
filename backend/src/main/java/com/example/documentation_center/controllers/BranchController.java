package com.example.documentation_center.controllers;

import com.example.documentation_center.dtos.BranchDTO;
import com.example.documentation_center.models.Branch;
import com.example.documentation_center.services.BranchServices;
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
@RequestMapping("/v1/ts/branchs")
@Tag(name = "Endpoint de Branch")
public class BranchController {
    @Autowired
    private BranchServices service;
    @GetMapping
    @Operation(summary = "Busca todos as branchs")
    public ResponseEntity<CollectionModel<BranchDTO>> buscarTodos(
            @RequestParam(value="page", defaultValue = "0") int page,
            @RequestParam(value="limit", defaultValue = "12") int limit,
            @RequestParam(value="direction", defaultValue = "desc") String direction,
            @RequestParam(value="ordenation", defaultValue = "codigo") String ordenation) {
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, ordenation));
        Page<BranchDTO> pages = service.findAll(pageable);
        pages
                .stream()
                .forEach(p -> p.add(
                                linkTo(methodOn(BranchController.class).buscarUm(p.getCodigo())).withSelfRel()
                        )
                );
        return ResponseEntity.ok(CollectionModel.of(pages));
    }
    @GetMapping("/nomes")
    @Operation(summary = "Busca pelo nome")
    public ResponseEntity<CollectionModel<BranchDTO>> buscarPeloNome(
            @RequestParam(value="page", defaultValue = "0") int page,
            @RequestParam(value="limit", defaultValue = "12") int limit,
            @RequestParam(value="direction", defaultValue = "desc") String direction,
            @RequestParam(value="ordenation", defaultValue = "codigo") String ordenation,
            String nomes) {
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, ordenation));
        Page<BranchDTO> pages = service.findByNomeContains(nomes, pageable);
        pages
                .stream()
                .forEach(p -> p.add(
                                linkTo(methodOn(BranchController.class).buscarUm(p.getCodigo())).withSelfRel()
                        )
                );
        return ResponseEntity.ok(CollectionModel.of(pages));
    }
    /*@GetMapping("/razao")
    @Operation(summary = "Busca pela razao")
    public ResponseEntity<CollectionModel<BranchDTO>> buscarPelaRazao(
            @RequestParam(value="page", defaultValue = "0") int page,
            @RequestParam(value="limit", defaultValue = "12") int limit,
            @RequestParam(value="direction", defaultValue = "desc") String direction,
            @RequestParam(value="ordenation", defaultValue = "codigo") String ordenation,
            String razao) {
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, ordenation));
        Page<BranchDTO> pages = service.findByRazaoContains(razao, pageable);
        pages
                .stream()
                .forEach(p -> p.add(
                                linkTo(methodOn(BranchController.class).buscarUm(p.getCodigo())).withSelfRel()
                        )
                );
        return ResponseEntity.ok(CollectionModel.of(pages));
    }
    @GetMapping("/endereco")
    @Operation(summary = "Busca pelo endereco")
    public ResponseEntity<CollectionModel<BranchDTO>> buscarPeloEndereco(
            @RequestParam(value="page", defaultValue = "0") int page,
            @RequestParam(value="limit", defaultValue = "12") int limit,
            @RequestParam(value="direction", defaultValue = "desc") String direction,
            @RequestParam(value="ordenation", defaultValue = "codigo") String ordenation,
            String endereco) {
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, ordenation));
        Page<BranchDTO> pages = service.findByEnderecoContains(endereco, pageable);
        pages
                .stream()
                .forEach(p -> p.add(
                                linkTo(methodOn(BranchController.class).buscarUm(p.getCodigo())).withSelfRel()
                        )
                );
        return ResponseEntity.ok(CollectionModel.of(pages));
    }*/

    @GetMapping("/{id}")
    @Operation(summary = "Busca por Id")
    public ResponseEntity<BranchDTO> buscarUm(@PathVariable Integer id) {
        BranchDTO objDTO = service.findById(id);
        objDTO.add(linkTo(methodOn(BranchController.class).buscarUm(id)).withSelfRel());
        return ResponseEntity.ok(objDTO);
    }
/*    @GetMapping("/nome/{nome}")
    @Operation(summary = "Busca pelo Nome")
    public ResponseEntity<BranchDTO> buscarBranch(@PathVariable String branch) {
        BranchDTO objDTO = service.findByNome(branch);
        objDTO.add(linkTo(methodOn(BranchController.class).buscarBranch(branch)).withSelfRel());
        return ResponseEntity.ok(objDTO);
    }*/
    @PutMapping
    @Operation(summary = "Atualiza uma Branch")
    public ResponseEntity<BranchDTO> atualizar(@RequestBody BranchDTO objBody) {
        BranchDTO objDTO = service.save(objBody);
        objDTO.add(linkTo(methodOn(BranchController.class).buscarUm(objDTO.getCodigo())).withSelfRel());
        return ResponseEntity.ok(objDTO);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Insere uma branch")
    public ResponseEntity<BranchDTO> incluir(@Valid @RequestBody  BranchDTO obj){
        BranchDTO objDTO = service.save(obj);
        objDTO.add(linkTo(methodOn(BranchController.class).buscarUm(objDTO.getCodigo())).withSelfRel());
        return ResponseEntity.ok(objDTO);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma branch pelo id")
    public ResponseEntity<Void> excluir(@PathVariable Integer id){
        if(!service.existById(id)){
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
