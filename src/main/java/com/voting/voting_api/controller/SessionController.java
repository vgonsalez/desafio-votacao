package com.voting.voting_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.voting.voting_api.dto.SessionDTO;
import com.voting.voting_api.dto.VoteResultDTO;
import com.voting.voting_api.service.SessionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/sessions")
@RequiredArgsConstructor
public class SessionController {
    
    private final SessionService sessionService = new SessionService();
    
    @PostMapping
    public ResponseEntity<SessionDTO> openSession(@Valid @RequestBody SessionDTO sessionDTO) {
        SessionDTO openedSession = sessionService.openSession(sessionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(openedSession);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SessionDTO> getSessionById(@PathVariable Long id) {
        return ResponseEntity.ok(sessionService.findById(id));
    }
    
    @GetMapping("/{id}/result")
    public ResponseEntity<VoteResultDTO> getVoteResult(@PathVariable Long id) {
        return ResponseEntity.ok(sessionService.getVoteResult(id));
    }
    
    @PatchMapping("/{id}/close")
    public ResponseEntity<SessionDTO> closeSession(@PathVariable Long id) {
        return ResponseEntity.ok(sessionService.closeSession(id));
    }
}
