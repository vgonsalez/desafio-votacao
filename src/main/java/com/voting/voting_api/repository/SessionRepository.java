package com.voting.voting_api.repository;

import com.voting.voting_api.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findByPautaIdAndClosedFalse(Long pautaId);
    
    @Query("SELECT s FROM Session s WHERE s.closed = false AND s.endTime <= :now")
    List<Session> findSessionsToClose(LocalDateTime now);
}
