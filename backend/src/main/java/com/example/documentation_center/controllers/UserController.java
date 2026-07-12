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

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "UserEndpoint")
@RestController
@RequestMapping("/api/user/v1")
public class UserController {

    @Autowired
    private UserServices service;

    //@Autowired
    //private PagedResourcesAssembler<UserDTO> assembler;

    @Operation(summary = "Find all users" )
    @GetMapping(produces = { "application/json", "application/xml", "application/x-yaml" })
    public ResponseEntity<CollectionModel<UserDTO>> findAll(
            @RequestParam(value="page", defaultValue = "0") int page,
            @RequestParam(value="limit", defaultValue = "12") int limit,
            @RequestParam(value="direction", defaultValue = "asc") String direction) {

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "id"));

        Page<UserDTO> users =  service.findAll(pageable);
        users
                .stream()
                .forEach(p -> p.add(
                                linkTo(methodOn(UserController.class).findById(p.getKey())).withSelfRel()
                        )
                );

        return ResponseEntity.ok(CollectionModel.of(users));
    }


    @Operation(summary = "Find users by name" )
    @GetMapping(value = "/findUserByUsername/{username}", produces = { "application/json", "application/xml", "application/x-yaml" })
    public ResponseEntity<CollectionModel<UserDTO>> findPersonByName(
            @PathVariable("username") String username,
            @RequestParam(value="page", defaultValue = "0") int page,
            @RequestParam(value="limit", defaultValue = "12") int limit,
            @RequestParam(value="direction", defaultValue = "asc") String direction) {

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "id"));

        Page<UserDTO> users =  service.findUserByNome(username, pageable);
        users
                .stream()
                .forEach(p -> p.add(
                                linkTo(methodOn(UserController.class).findById(p.getKey())).withSelfRel()
                        )
                );

        return ResponseEntity.ok(CollectionModel.of(users));
    }

    @Operation(summary = "Find a specific user by your ID" )
    @GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
    public UserDTO findById(@PathVariable("id") Long id) {
        UserDTO userDTO = service.findById(id);
        userDTO.add(linkTo(methodOn(UserController.class).findById(id)).withSelfRel());
        return userDTO;
    }

    @Operation(summary = "Create a new user")
    @PostMapping(produces = { "application/json", "application/xml", "application/x-yaml" },
            consumes = { "application/json", "application/xml", "application/x-yaml" })
    public UserDTO create(@RequestBody UserDTO user) {
        UserDTO userDTO = service.create(user);
        userDTO.add(linkTo(methodOn(UserController.class).findById(userDTO.getKey())).withSelfRel());
        return userDTO;
    }

    @Operation(summary = "Update a specific person")
    @PutMapping(produces = { "application/json", "application/xml", "application/x-yaml" },
            consumes = { "application/json", "application/xml", "application/x-yaml" })
    public UserDTO update(@RequestBody UserDTO user) {
        UserDTO userDTO = service.update(user);
        userDTO.add(linkTo(methodOn(UserController.class).findById(userDTO.getKey())).withSelfRel());
        return userDTO;
    }


    @Operation(summary = "Disable a user by your ID" )
    @PatchMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
    public UserDTO disablePerson(@PathVariable("id") Long id) {
        UserDTO userDTO = service.disableUser(id);
        userDTO.add(linkTo(methodOn(UserController.class).findById(id)).withSelfRel());
        return userDTO;
    }

    @Operation(summary = "Delete a specific user by your ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
