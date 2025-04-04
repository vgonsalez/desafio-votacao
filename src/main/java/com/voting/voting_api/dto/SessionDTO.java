package com.voting.voting_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionDTO {
    private Long id;
    private Long pautaId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer durationInMinutes;  // Usado apenas na criação
    private Boolean closed;
}
