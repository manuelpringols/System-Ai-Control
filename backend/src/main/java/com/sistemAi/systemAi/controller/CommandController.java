package com.sistemAi.systemAi.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemAi.systemAi.service.OpenAIService;

@RestController
@RequestMapping("/api")
public class CommandController {

    
    private final OpenAIService openAIService = new OpenAIService();
    
       
    
        @PostMapping("/ask")
        public ResponseEntity<String> askQuestion(@RequestBody Map<String, String> requestBody) {
            String question = requestBody.get("question");
            if (question == null || question.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("La domanda è obbligatoria.");
            }
    
            // Invia la domanda a OpenAI e ottieni la risposta
            String response = openAIService.getResponseFromOpenAI(question);
            return ResponseEntity.ok(response);
        }
    }
    