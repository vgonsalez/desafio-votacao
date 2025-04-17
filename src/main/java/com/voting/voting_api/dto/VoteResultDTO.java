package com.voting.voting_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteResultDTO {
    private Long pautaId;
    private String pautaTitle;
    private Long sessionId;
    private Integer totalVotes;
    private Integer simVotes;
    private Integer naoVotes;
    private String result;
	public VoteResultDTO(Long pautaId, String pautaTitle, Long sessionId, Integer totalVotes, Integer simVotes,
			Integer naoVotes, String result) {
		super();
		this.pautaId = pautaId;
		this.pautaTitle = pautaTitle;
		this.sessionId = sessionId;
		this.totalVotes = totalVotes;
		this.simVotes = simVotes;
		this.naoVotes = naoVotes;
		this.result = result;
	}
    
    
}
