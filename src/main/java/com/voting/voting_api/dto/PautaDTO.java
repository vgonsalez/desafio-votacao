package com.voting.voting_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PautaDTO {
    private Long id;
    
    @NotBlank(message = "O título da pauta é obrigatório")
    private String title;
    
    @NotBlank(message = "A descrição da pauta é obrigatória")
    private String description;

	public PautaDTO(Long id2, String title2, String description2) {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
    
}
