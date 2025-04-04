package com.voting.voting_api.controller;

import com.voting.voting_api.dto.VoteDTO;
import com.voting.voting_api.service.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/votes")
@RequiredArgsConstructor
public class VoteController {
    
    private final VoteService voteService;
    
    @PostMapping
    public ResponseEntity<VoteDTO> registerVote(@Valid @RequestBody VoteDTO voteDTO) {
        VoteDTO registeredVote = voteService.registerVote(voteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredVote);
    }
}
