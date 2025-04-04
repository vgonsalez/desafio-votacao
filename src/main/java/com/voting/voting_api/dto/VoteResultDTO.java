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
}
