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
}
