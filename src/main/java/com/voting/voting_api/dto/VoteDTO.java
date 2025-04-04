package com.voting.voting_api.dto;

import com.voting.voting_api.model.VoteOption;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteDTO {
    @NotBlank(message = "O ID do associado é obrigatório")
    private String associateId;
    
    @NotNull(message = "O ID da sessão é obrigatório")
    private Long sessionId;
    
    @NotNull(message = "A opção de voto é obrigatória")
    private VoteOption option;
}
