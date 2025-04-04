package com.voting.voting_api.service;

import com.voting.voting_api.dto.VoteDTO;
import com.voting.voting_api.model.Session;
import com.voting.voting_api.model.Vote;
import com.voting.voting_api.cliente.ValidacaoCliente;
import com.voting.voting_api.cliente.ValidacaoCliente.ValidacaoCPFResultado;
import com.voting.voting_api.cliente.ValidacaoCliente.PermissaoVoto;
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
    private final ValidacaoCliente validacaoCliente;
    
    @Transactional
    public VoteDTO registerVote(VoteDTO voteDTO) {
        ValidacaoCPFResultado validacaoResult = validacaoCliente.validaCPF(voteDTO.getCpf());
        if (validationResult == null) {
            throw new CpfInvalidoException("CPF inválido");
        }
        
        if (validationResult.getStatus() == VotePermission.UNABLE_TO_VOTE) {
            throw new CpfSemVotoException("CPF não habilitado para votar");
        }

        Session session = sessionService.getSessionEntityById(voteDTO.getSessionId());
        
        if (session.getClosed()) {
            throw new IllegalStateException("A sessão de votação está encerrada");
        }
        
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(session.getEndTime())) {
            throw new IllegalStateException("O prazo para votação nesta sessão já expirou");
        }
        
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
                savedVote.getOption(),
                voteDTO.getCpf()
        );
    }
}

