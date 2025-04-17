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
    
    @NotBlank(message = "O CPF do associado é obrigatório")
    private String cpf;

	public VoteDTO(String associateId2, Long id, VoteOption option2, String cpf2) {
		// TODO Auto-generated constructor stub
	}

	public String getAssociateId() {
		return associateId;
	}

	public void setAssociateId(String associateId) {
		this.associateId = associateId;
	}

	public Long getSessionId() {
		return sessionId;
	}

	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}

	public VoteOption getOption() {
		return option;
	}

	public void setOption(VoteOption option) {
		this.option = option;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
    
    
}
