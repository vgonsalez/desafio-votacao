package com.voting.voting_api.service;

import com.voting.voting_api.dto.VoteDTO;
import com.voting.voting_api.model.Session;
import com.voting.voting_api.model.Vote;
import com.voting.voting_api.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VoteService {
    
    private final VoteRepository voteRepository;
    private final SessionService sessionService;
    
    @Transactional
    public VoteDTO registerVote(VoteDTO voteDTO) {
        // Verificar se a sessão existe e está aberta
        Session session = sessionService.getSessionEntityById(voteDTO.getSessionId());
        
        // Verificar se a sessão está encerrada
        if (session.getClosed()) {
            throw new IllegalStateException("A sessão de votação está encerrada");
        }
        
        // Verificar se a sessão ainda está no prazo
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(session.getEndTime())) {
            throw new IllegalStateException("O prazo para votação nesta sessão já expirou");
        }
        
        // Verificar se o associado já votou nesta sessão
        if (voteRepository.existsByAssociateIdAndSessionId(voteDTO.getAssociateId(), voteDTO.getSessionId())) {
            throw new IllegalStateException("Este associado já votou nesta sessão");
        }
        
        Vote vote = new Vote();
        vote.setAssociateId(voteDTO.getAssociateId());
        vote.setSession(session);
        vote.setOption(voteDTO.getOption());
        
        Vote savedVote = voteRepository.save(vote);
        
        return new VoteDTO(
                savedVote.getAssociateId(),
                savedVote.getSession().getId(),
                savedVote.getOption()
        );
    }
}

