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
        // Преобразование ShelterDTO в сущность Shelter с использованием маппера
        Shelter shelter = shelterMapper.toEntity(shelterDTO);
        // Сохранение сущности Shelter в репозитории
        Shelter savedShelter = shelterRepository.save(shelter);
        // Преобразование сохраненной сущности Shelter обратно в DTO

        return shelterMapper.toDTO(savedShelter);
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

    /*@Override
    public ShelterDTO editShelter(ShelterDTO shelterDTO) {
        return null;
    }*/

    @Override
    public ShelterDTO findShelterById(Long shelterId) {
        return null;
    }

    @Override
    public Collection<ShelterDTO> findAll() {
        return null;
    }

    @Override
    public ShelterDTO editShelter(ShelterDTO shelterDTO) {
        LOGGER.info("Was invoked method for edit shelter : {}", shelterDTO);

        // Преобразовать ShelterDTO в Shelter с использованием маппера
        Shelter shelterEntity = shelterMapper.toEntity(shelterDTO);

        // Сохранить обновленную сущность Shelter в репозитории
        Shelter savedEntity = shelterRepository.save(shelterEntity);

        // Попытаться найти обновленную сущность Shelter
        return shelterRepository.findById(savedEntity.getId())
                .map(dbEntity -> {
                    // Обновить поля Shelter
                    dbEntity.setName(savedEntity.getName());
                    dbEntity.setSafety(savedEntity.getSafety());

                    // Сохранить обновленную сущность Shelter
                    shelterRepository.save(dbEntity);

                    // Преобразовать обновленную сущность Shelter обратно в ShelterDTO
                    ShelterDTO updatedShelterDTO = shelterMapper.toDTO(dbEntity);

                    return updatedShelterDTO;
                })
                .orElse(null);
    }
}


   /* @Override
    public ShelterDTO findShelterById(Long shelterId) {
        return shelterRepository.findById(shelterId).orElse(null);*/


   /* @Override
    public Collection<ShelterDTO> findAll() {
        LOGGER.info("Was invoked method for finding all shelters");
        return shelterRepository.findAll();
    }*/

