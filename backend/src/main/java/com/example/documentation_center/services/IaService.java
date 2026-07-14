package com.example.documentation_center.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IaService {

    private static final Logger log = LoggerFactory.getLogger(IaService.class);

    @Value("${ia.provider:ollama}")
    private String provider;

    // Ollama
    @Value("${ia.ollama.url:http://localhost:11434/api/generate}")
    private String ollamaUrl;

    @Value("${ia.ollama.model:mistral}")
    private String ollamaModel;

    // Claude API
    @Value("${ia.claude.url:https://api.anthropic.com/v1/messages}")
    private String claudeUrl;

    @Value("${ia.claude.api-key:}")
    private String claudeApiKey;

    @Value("${ia.claude.model:claude-haiku-4-5-20251001}")
    private String claudeModel;

    // Gemini API
    @Value("${ia.gemini.api-key:}")
    private String geminiApiKey;

    @Value("${ia.gemini.model:gemini-2.0-flash}")
    private String geminiModel;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String gerarResumo(String titulo, String conteudo) {
        String prompt = String.format(
            "Você é um assistente técnico. Gere um resumo objetivo em até 3 frases curtas em português para a documentação abaixo. " +
            "Retorne apenas o resumo, sem introduções ou explicações extras.\n\n" +
            "Título: %s\n\nConteúdo: %s",
            titulo, conteudo
        );
        return chamarIA(prompt);
    }

    public String sugerirTagsECategoria(String titulo, String conteudo) {
        String prompt = String.format(
            "Você é um assistente técnico. Analise a documentação abaixo e retorne APENAS um JSON válido no formato: " +
            "{\"tags\": [\"tag1\", \"tag2\", \"tag3\"], \"categoria\": \"NomeCategoria\"}. " +
            "Sugira até 5 tags relevantes e 1 categoria. Responda somente o JSON, sem markdown ou texto extra.\n\n" +
            "Título: %s\n\nConteúdo: %s",
            titulo, conteudo
        );
        return chamarIA(prompt);
    }

    private String chamarIA(String prompt) {
        if ("claude".equalsIgnoreCase(provider)) {
            return chamarClaude(prompt);
        }
        if ("gemini".equalsIgnoreCase(provider)) {
            return chamarGemini(prompt);
        }
        return chamarOllama(prompt);
    }

    private String chamarOllama(String prompt) {
        try {
            Map<String, Object> body = new HashMap<>();
            body.put("model", ollamaModel);
            body.put("prompt", prompt);
            body.put("stream", false);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(ollamaUrl, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                JsonNode json = objectMapper.readTree(response.getBody());
                return json.get("response").asText().trim();
            }
        } catch (Exception e) {
            log.error("[IA/Ollama] Erro: {}", e.getMessage(), e);
        }
        return "";
    }

    private String chamarGemini(String prompt) {
        try {
            String url = String.format(
                "https://generativelanguage.googleapis.com/v1beta/models/%s:generateContent?key=%s",
                geminiModel, geminiApiKey
            );

            Map<String, Object> part = new HashMap<>();
            part.put("text", prompt);

            Map<String, Object> content = new HashMap<>();
            content.put("parts", List.of(part));

            Map<String, Object> body = new HashMap<>();
            body.put("contents", List.of(content));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                JsonNode json = objectMapper.readTree(response.getBody());
                return json.get("candidates").get(0)
                           .get("content").get("parts").get(0)
                           .get("text").asText().trim();
            }
        } catch (Exception e) {
            log.error("[IA/Gemini] Erro: {}", e.getMessage(), e);
        }
        return "";
    }

    private String chamarClaude(String prompt) {
        try {
            Map<String, Object> message = new HashMap<>();
            message.put("role", "user");
            message.put("content", prompt);

            Map<String, Object> body = new HashMap<>();
            body.put("model", claudeModel);
            body.put("max_tokens", 1024);
            body.put("messages", List.of(message));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-api-key", claudeApiKey);
            headers.set("anthropic-version", "2023-06-01");
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(claudeUrl, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                JsonNode json = objectMapper.readTree(response.getBody());
                return json.get("content").get(0).get("text").asText().trim();
            }
        } catch (Exception e) {
            log.error("[IA/Claude] Erro: {}", e.getMessage(), e);
        }
        return "";
    }
}
