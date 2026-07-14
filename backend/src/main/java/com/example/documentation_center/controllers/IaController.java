package com.example.documentation_center.controllers;

import com.example.documentation_center.services.IaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/ts/ia")
@Tag(name = "Endpoint de IA")
public class IaController {

    private final IaService iaService;

    public IaController(IaService iaService) {
        this.iaService = iaService;
    }

    @PostMapping("/resumo")
    @Operation(summary = "Gera resumo automático (TL;DR) para uma documentação")
    public ResponseEntity<Map<String, String>> gerarResumo(@RequestBody Map<String, String> body) {
        String titulo = body.getOrDefault("titulo", "");
        String conteudo = body.getOrDefault("conteudo", "");
        if (titulo.isBlank() && conteudo.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        String resumo = iaService.gerarResumo(titulo, conteudo);
        return ResponseEntity.ok(Map.of("resumo", resumo));
    }

    @PostMapping("/sugestoes")
    @Operation(summary = "Sugere tags e categoria para uma documentação via IA")
    public ResponseEntity<String> sugerirTagsECategoria(@RequestBody Map<String, String> body) {
        String titulo = body.getOrDefault("titulo", "");
        String conteudo = body.getOrDefault("conteudo", "");
        if (titulo.isBlank() && conteudo.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        String resultado = iaService.sugerirTagsECategoria(titulo, conteudo);
        return ResponseEntity.ok(resultado);
    }
}
