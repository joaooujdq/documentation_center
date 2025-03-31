package com.example.documentation_center.controllers;

import com.example.documentation_center.dtos.UserDTO;
import com.example.documentation_center.models.User;
import com.example.documentation_center.services.UserServices;
import com.example.documentation_center.services.exceptions.BusinessException;
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
@RequestMapping("/v1/ts/users")
@Tag(name = "Endpoint de User")
public class UserController {
    @Autowired
    private UserServices service;
    @GetMapping
    @Operation(summary = "Busca todos as users")
    public ResponseEntity<CollectionModel<UserDTO>> buscarTodos(
            @RequestParam(value="page", defaultValue = "0") int page,
            @RequestParam(value="limit", defaultValue = "16") int limit,
            @RequestParam(value="direction", defaultValue = "desc") String direction,
            @RequestParam(value="ordenation", defaultValue = "codigo") String ordenation) {
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, ordenation));
        Page<UserDTO> pages = service.findAll(pageable);
        pages
                .stream()
                .forEach(p -> p.add(
                                linkTo(methodOn(UserController.class).buscarUm(p.getCodigo())).withSelfRel()
                        )
                );
        return ResponseEntity.ok(CollectionModel.of(pages));
    }
    @GetMapping("/nomes")
    @Operation(summary = "Busca pelo nome")
    public ResponseEntity<UserDTO> buscarPeloNome(String nomes, String senhas) {
        UserDTO objDTO1 = service.findByNome(nomes);
        UserDTO objDTO2 = service.findBySenha(senhas);
        if(objDTO2.getSenha() == objDTO1.getSenha()){
            System.out.println("perfil encontrado");
        }else{
            System.out.println("perfil não encontrado");
            throw new BusinessException("Perfil nao encontrado!");
        }

        return ResponseEntity.ok(objDTO1);
    }

    /*@GetMapping("/razao")
    @Operation(summary = "Busca pela razao")
    public ResponseEntity<CollectionModel<UserDTO>> buscarPelaRazao(
            @RequestParam(value="page", defaultValue = "0") int page,
            @RequestParam(value="limit", defaultValue = "12") int limit,
            @RequestParam(value="direction", defaultValue = "desc") String direction,
            @RequestParam(value="ordenation", defaultValue = "codigo") String ordenation,
            String razao) {
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, ordenation));
        Page<UserDTO> pages = service.findByRazaoContains(razao, pageable);
        pages
                .stream()
                .forEach(p -> p.add(
                                linkTo(methodOn(UserController.class).buscarUm(p.getCodigo())).withSelfRel()
                        )
                );
        return ResponseEntity.ok(CollectionModel.of(pages));
    }
    @GetMapping("/endereco")
    @Operation(summary = "Busca pelo endereco")
    public ResponseEntity<CollectionModel<UserDTO>> buscarPeloEndereco(
            @RequestParam(value="page", defaultValue = "0") int page,
            @RequestParam(value="limit", defaultValue = "12") int limit,
            @RequestParam(value="direction", defaultValue = "desc") String direction,
            @RequestParam(value="ordenation", defaultValue = "codigo") String ordenation,
            String endereco) {
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, ordenation));
        Page<UserDTO> pages = service.findByEnderecoContains(endereco, pageable);
        pages
                .stream()
                .forEach(p -> p.add(
                                linkTo(methodOn(UserController.class).buscarUm(p.getCodigo())).withSelfRel()
                        )
                );
        return ResponseEntity.ok(CollectionModel.of(pages));
    }*/

    @GetMapping("/{id}")
    @Operation(summary = "Busca por Id")
    public ResponseEntity<UserDTO> buscarUm(@PathVariable Integer id) {
        UserDTO objDTO = service.findById(id);
        objDTO.add(linkTo(methodOn(UserController.class).buscarUm(id)).withSelfRel());
        return ResponseEntity.ok(objDTO);
    }
/*    @GetMapping("/nome/{nome}")
    @Operation(summary = "Busca pelo Nome")
    public ResponseEntity<UserDTO> buscarUser(@PathVariable String user) {
        UserDTO objDTO = service.findByNome(user);
        objDTO.add(linkTo(methodOn(UserController.class).buscarUser(user)).withSelfRel());
        return ResponseEntity.ok(objDTO);
    }*/
    @PutMapping
    @Operation(summary = "Atualiza uma User")
    public ResponseEntity<UserDTO> atualizar(@RequestBody UserDTO objBody) {
        UserDTO objDTO = service.save(objBody);
        objDTO.add(linkTo(methodOn(UserController.class).buscarUm(objDTO.getCodigo())).withSelfRel());
        return ResponseEntity.ok(objDTO);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Insere uma user")
    public ResponseEntity<UserDTO> incluir(@RequestBody  UserDTO obj){
        UserDTO objDTO = service.save(obj);
        System.out.println("teste1");
        objDTO.add(linkTo(methodOn(UserController.class).buscarUm(objDTO.getCodigo())).withSelfRel());
        System.out.println("teste2");
        return ResponseEntity.ok(objDTO);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma user pelo id")
    public ResponseEntity<Void> excluir(@PathVariable Integer id){
        if(!service.existById(id)){
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
