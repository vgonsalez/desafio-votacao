package com.voting.voting_api.service;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.voting.voting_api.dto.SessionDTO;
import com.voting.voting_api.dto.VoteResultDTO;
import com.voting.voting_api.model.Pauta;
import com.voting.voting_api.model.Session;
import com.voting.voting_api.model.VoteOption;
import com.voting.voting_api.repository.SessionRepository;
import com.voting.voting_api.repository.VoteRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SessionService {
    
    private final SessionRepository sessionRepository = null;
    private final VoteRepository voteRepository = null;
    private final PautaService pautaService = new PautaService();
    
    @Transactional
    public SessionDTO openSession(SessionDTO sessionDTO) {
        // Verificar se já existe uma sessão aberta para esta pauta
        sessionRepository.findByPautaIdAndClosedFalse(sessionDTO.getPautaId())
                .ifPresent(session -> {
                    throw new IllegalStateException("Já existe uma sessão de votação aberta para esta pauta");
                });
        
        Pauta pauta = pautaService.getPautaEntityById(sessionDTO.getPautaId());
        
        Session session = new Session();
        session.setPauta(pauta);
        session.setStartTime(LocalDateTime.now());
        
        // Se a duração for especificada, usá-la, caso contrário usar o padrão de 1 minuto
        if (sessionDTO.getDurationInMinutes() != null && sessionDTO.getDurationInMinutes() > 0) {
            session.setEndTime(session.getStartTime().plusMinutes(sessionDTO.getDurationInMinutes()));
        } else {
            session.setEndTime(session.getStartTime().plusMinutes(1));
        }
        
        Session savedSession = sessionRepository.save(session);
        return toDTO(savedSession);
    }
    
    public SessionDTO findById(Long id) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sessão não encontrada com o ID: " + id));
        return toDTO(session);
    }
    
    @Transactional
    public SessionDTO closeSession(Long id) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sessão não encontrada com o ID: " + id));
        
        if (session.getClosed()) {
            throw new IllegalStateException("A sessão já está fechada");
        }
        
        session.setClosed(true);
        return toDTO(sessionRepository.save(session));
    }
    
    public VoteResultDTO getVoteResult(Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("Sessão não encontrada com o ID: " + sessionId));
        
        // Contabilizar votos
        Integer simVotes = voteRepository.countBySessionIdAndOption(sessionId, VoteOption.SIM);
        Integer naoVotes = voteRepository.countBySessionIdAndOption(sessionId, VoteOption.NAO);
        Integer totalVotes = voteRepository.countBySessionId(sessionId);
        
        String result;
        if (simVotes > naoVotes) {
            result = "Aprovada";
        } else if (naoVotes > simVotes) {
            result = "Rejeitada";
        } else {
            result = "Empate";
        }
        
        return new VoteResultDTO(
                session.getPauta().getId(),
                session.getPauta().getTitle(),
                sessionId,
                totalVotes,
                simVotes,
                naoVotes,
                result
        );
    }
    
    @Scheduled(fixedRate = 10000) // Verifica a cada 10 segundos
    @Transactional
    public void closeExpiredSessions() {
        LocalDateTime now = LocalDateTime.now();
        sessionRepository.findSessionsToClose(now).forEach(session -> {
            session.setClosed(true);
            sessionRepository.save(session);
        });
    }
    
    Session getSessionEntityById(Long id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sessão não encontrada com o ID: " + id));
    }
    
    private SessionDTO toDTO(Session session) {
        return new SessionDTO(
                session.getId(),
                session.getPauta().getId(),
                session.getStartTime(),
                session.getEndTime(),
                null,
                session.getClosed()
        );
    }
}