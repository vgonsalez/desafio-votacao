package com.voting.voting_api.service;

import com.voting.voting_api.dto.PautaDTO;
import com.voting.voting_api.model.Pauta;
import com.voting.voting_api.repository.PautaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PautaService {
    
    private final PautaRepository pautaRepository;
    
    public List<PautaDTO> findAll() {
        return pautaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public PautaDTO findById(Long id) {
        Pauta pauta = pautaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pauta não encontrada com o ID: " + id));
        return toDTO(pauta);
    }
    
    @Transactional
    public PautaDTO create(PautaDTO pautaDTO) {
        Pauta pauta = new Pauta();
        pauta.setTitle(pautaDTO.getTitle());
        pauta.setDescription(pautaDTO.getDescription());
        
        return toDTO(pautaRepository.save(pauta));
    }
    
    private PautaDTO toDTO(Pauta pauta) {
        return new PautaDTO(
                pauta.getId(),
                pauta.getTitle(),
                pauta.getDescription()
        );
    }
    
    Pauta getPautaEntityById(Long id) {
        return pautaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pauta não encontrada com o ID: " + id));
    }
}
