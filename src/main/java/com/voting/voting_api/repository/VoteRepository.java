package com.voting.voting_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.voting.voting_api.model.Vote;
import com.voting.voting_api.model.VoteOption;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    boolean existsByAssociateIdAndSessionId(String associateId, Long sessionId);
    
    @Query("SELECT COUNT(v) FROM Vote v WHERE v.session.id = :sessionId AND v.option = :option")
    Integer countBySessionIdAndOption(Long sessionId, VoteOption option);
    
    @Query("SELECT COUNT(v) FROM Vote v WHERE v.session.id = :sessionId")
    Integer countBySessionId(Long sessionId);
}
