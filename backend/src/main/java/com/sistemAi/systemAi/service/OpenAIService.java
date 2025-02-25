package com.sistemAi.systemAi.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenAIService {

    private final String API_KEY = ""; // Inserisci qui la tua API key
    private final String API_URL = "https://api.openai.com/v1/chat/completions";

    public String getResponseFromOpenAI(String question) {
        RestTemplate restTemplate = new RestTemplate();

        // Corpo della richiesta
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4");
        requestBody.put("messages", List.of(
            Map.of("role", "system", "content", "Sei un assistente che traduce domande in comandi di sistema."),
            Map.of("role", "user", "content", question)
        ));
        requestBody.put("max_tokens", 150);
        requestBody.put("temperature", 0.7);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + API_KEY);
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            // Invia la richiesta
            ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, requestEntity, String.class);
            return parseResponse(response.getBody());
        } catch (HttpClientErrorException e) {
            return "Errore nella richiesta a OpenAI: " + e.getMessage();
        }
    }

    // Parsing della risposta di OpenAI
    private String parseResponse(String responseBody) {
        try {
            JSONObject jsonResponse = new JSONObject(responseBody);
            return jsonResponse.getJSONArray("choices")
            .getJSONObject(0)
            .getJSONObject("message")
            .getString("content");
        } catch (JSONException e) {
            return "Errore nell'analisi della risposta di OpenAI";
        }
    }
}
