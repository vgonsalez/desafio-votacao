package com.voting.voting_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.voting.voting_api.dto.PautaDTO;
import com.voting.voting_api.service.PautaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/pautas")
@RequiredArgsConstructor
public class PautaController {
    
    private final PautaService pautaService = new PautaService();
    
    @GetMapping
    public ResponseEntity<List<PautaDTO>> getAllPautas() {
        return ResponseEntity.ok(pautaService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PautaDTO> getPautaById(@PathVariable Long id) {
        return ResponseEntity.ok(pautaService.findById(id));
    }
    
    @PostMapping
    public ResponseEntity<PautaDTO> createAgenda(@Valid @RequestBody PautaDTO pautaDTO) {
        PautaDTO createdAgenda = pautaService.create(pautaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAgenda);
    }
}
