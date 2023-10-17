package ru.devpro.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import ru.devpro.dto.ShelterDTO;
import ru.devpro.mapers.ShelterMapper;
import ru.devpro.model.Shelter;
import ru.devpro.repositories.ShelterRepository;

import java.util.Collection;

@Service

public class ShelterServiceImpl implements ShelterService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShelterServiceImpl.class);

    private final ShelterRepository shelterRepository;
    private final ShelterMapper shelterMapper;

    public ShelterServiceImpl(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
        this.shelterMapper = ShelterMapper.INSTANCE;
    }

    @Override
    public ShelterDTO createShelter(ShelterDTO shelterDTO) {
        LOGGER.info("Received request to save shelter: {}", shelterDTO);
        Shelter shelterEntity = shelterMapper.toEntity(shelterDTO); // Преобразуйте DTO в сущность
        Shelter savedEntity = shelterRepository.save(shelterEntity);
        return shelterMapper.toDTO(savedEntity); // Преобразуйте сущность обратно в DTO
    }

    @Override
    public void deleteShelter(Long id) {
        LOGGER.info("Was invoked method for delete student by id: {}", id);
        shelterRepository.findById(id)
                .map(entity -> {
                    shelterRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Shelter editShelter(Shelter shelter) {
        LOGGER.info("Was invoked method for edit animal : {}", shelter);
        return shelterRepository.findById(shelter.getId())
                .map(dbEntity -> {
                    dbEntity.setName(shelter.getName());
                    shelterRepository.save(dbEntity);
                    return dbEntity;
                })
                .orElse(null);
    }
    @Override
    public Shelter findShelterById(Long userId) {
        return shelterRepository.findById(userId).orElse(null);
    }

    @Override
    public Collection<Shelter> findAll() {
        LOGGER.info("Was invoked method for finding all shelters");
        return shelterRepository.findAll();
    }
}
