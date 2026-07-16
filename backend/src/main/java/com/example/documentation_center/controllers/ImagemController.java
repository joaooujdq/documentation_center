package com.example.documentation_center.controllers;

import com.example.documentation_center.models.Imagem;
import com.example.documentation_center.repositories.ImagemDAO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/v1/ts/imagens")
@Tag(name = "Endpoint de Imagens")
public class ImagemController {

    private final ImagemDAO imagemDAO;

    public ImagemController(ImagemDAO imagemDAO) {
        this.imagemDAO = imagemDAO;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Faz upload de uma imagem e retorna o ID gerado")
    public ResponseEntity<Map<String, Long>> upload(@RequestParam("arquivo") MultipartFile arquivo) throws IOException {
        Imagem imagem = new Imagem();
        imagem.setDados(arquivo.getBytes());
        imagem.setTipo(arquivo.getContentType() != null ? arquivo.getContentType() : "image/jpeg");
        imagem.setNome(arquivo.getOriginalFilename());
        Imagem saved = imagemDAO.save(imagem);
        return ResponseEntity.ok(Map.of("id", saved.getId()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Serve os bytes de uma imagem pelo ID com o Content-Type correto")
    public ResponseEntity<byte[]> download(@PathVariable Long id) {
        return imagemDAO.findById(id)
                .map(imagem -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.parseMediaType(imagem.getTipo()));
                    return ResponseEntity.ok().headers(headers).body(imagem.getDados());
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
