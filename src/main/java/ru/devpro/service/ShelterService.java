package ru.devpro.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.devpro.dto.ShelterDTO;


import ru.devpro.dto.ShelterMapper;
import ru.devpro.model.Shelter;
import ru.devpro.repositories.ShelterRepository;

@Service

public class ShelterService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShelterService.class);

    private final ShelterRepository shelterRepository;
    private final ShelterMapper shelterMapper;

    public ShelterService(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
        this.shelterMapper = ShelterMapper.INSTANCE;
    }
    public ShelterDTO createShelter(ShelterDTO shelterDTO) {
        LOGGER.info("Was invoked method for create shelter: {}", shelterDTO);
        Shelter shelterEntity = shelterMapper.shelterDTOToShelterEntity(shelterDTO);
        Shelter savedEntity = shelterRepository.save(shelterEntity);
        return shelterMapper.shelterEntityToShelterDTO(savedEntity);
    }

    public ShelterDTO editShelter(ShelterDTO shelterDTO) {
        LOGGER.info("Was invoked method for edit animal : {}", shelterDTO);
        return shelterRepository.findById(shelterDTO.getId())
                .map(dbEntity -> {
                    dbEntity.setName(shelterDTO.getName());
                    Shelter updateEntity = shelterRepository.save(dbEntity);

                    return shelterMapper.shelterEntityToShelterDTO(updateEntity);
                })
                .orElse(null);
    }
    public ShelterDTO findShelterById(long id) {
        LOGGER.info("Was invoked method for seach student by id: {}", id);
        return shelterRepository.findById(id)
                .map(shelterMapper::shelterEntityToShelterDTO)
                .orElse(null);
    }
    public void deleteShelter(long id) {
        LOGGER.info("Was invoked method for delete student by id: {}", id);
        shelterRepository.findById(id)
                .map(entity -> {
                    shelterRepository.delete(entity);
                    return true;
                });
    }
}
