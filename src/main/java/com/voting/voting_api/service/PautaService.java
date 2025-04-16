package com.voting.voting_api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.voting.voting_api.dto.PautaDTO;
import com.voting.voting_api.model.Pauta;
import com.voting.voting_api.repository.PautaRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PautaService {
    
    private final PautaRepository pautaRepository = null;
    
    @Transactional(readOnly = true)
    public List<PautaDTO> findAll() {
        return pautaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public PautaDTO findById(Long id) {
        Pauta pauta = getPautaEntityById(id);
        return toDTO(pauta);
    }
    
    @Transactional
    public PautaDTO create(PautaDTO pautaDTO) {
        if (pautaDTO == null) {
            throw new IllegalArgumentException("PautaDTO não pode ser nulo");
        }
        
        Pauta pauta = new Pauta();
        pauta.setTitle(pautaDTO.getTitle());
        pauta.setDescription(pautaDTO.getDescription());
        
        return toDTO(pautaRepository.save(pauta));
    }
    
    @Transactional
    public PautaDTO update(Long id, PautaDTO pautaDTO) {
        if (pautaDTO == null) {
            throw new IllegalArgumentException("PautaDTO não pode ser nulo");
        }
        
        Pauta pauta = getPautaEntityById(id);
        pauta.setTitle(pautaDTO.getTitle());
        pauta.setDescription(pautaDTO.getDescription());
        
        return toDTO(pautaRepository.save(pauta));
    }
    
    @Transactional
    public void delete(Long id) {
        Pauta pauta = getPautaEntityById(id);
        pautaRepository.delete(pauta);
    }
    
    private PautaDTO toDTO(Pauta pauta) {
        if (pauta == null) {
            return null;
        }
        
        return new PautaDTO(
                pauta.getId(),
                pauta.getTitle(),
                pauta.getDescription()
        );
    }
    
    public Pauta getPautaEntityById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        
        return pautaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pauta não encontrada com o ID: " + id));
    }
}