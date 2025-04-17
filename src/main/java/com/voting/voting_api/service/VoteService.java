package com.voting.voting_api.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.voting.voting_api.cliente.ValidacaoCliente;
import com.voting.voting_api.cliente.ValidacaoCliente.PermissaoVoto;
import com.voting.voting_api.cliente.ValidacaoCliente.ValidacaoCPFResultado;
import com.voting.voting_api.dto.VoteDTO;
import com.voting.voting_api.exception.CpfInvalidoException;
import com.voting.voting_api.exception.CpfSemVotoException;
import com.voting.voting_api.model.Session;
import com.voting.voting_api.model.Vote;
import com.voting.voting_api.repository.VoteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoteService {
    
    private final VoteRepository voteRepository = null;
    private final SessionService sessionService = new SessionService();
    private final ValidacaoCliente validacaoCliente = new ValidacaoCliente();
    
    @Transactional
    public VoteDTO registerVote(VoteDTO voteDTO) {
        ValidacaoCPFResultado validacaoResult = validacaoCliente.validaCPF(voteDTO.getCpf());
        if (validacaoResult == null) {
            throw new CpfInvalidoException("CPF inválido");
        }
        
        if (validacaoResult.getStatus() == PermissaoVoto.VOTO_INVALIDO) {
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

