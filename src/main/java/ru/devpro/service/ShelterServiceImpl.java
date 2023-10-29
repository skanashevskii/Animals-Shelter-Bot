package ru.devpro.service;

import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.devpro.dto.ShelterDTO;
import ru.devpro.mapers.ShelterMapper;
import ru.devpro.model.Shelter;
import ru.devpro.repositories.ShelterRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    @Override
    public void deleteShelter(Long id) {
        LOGGER.info("Received request to delete shelter by ID: {}", id);
        shelterRepository.findById(id).ifPresent(entity -> {
            shelterRepository.delete(entity);
            LOGGER.info("Shelter with ID {} deleted.", id);
        });
    }

    @Override
    public ShelterDTO findShelterById(Long shelterId) {
        LOGGER.info("Received request to find shelter by ID: {}", shelterId);
        return shelterRepository.findById(shelterId)
                .map(shelterMapper::toDTO)
                .orElse(null);
    }

    @Override
    public Collection<ShelterDTO> findAll() {
        LOGGER.info("Received request to find all shelters");
        List<ShelterDTO> shelterDTOs = new ArrayList<>();
        shelterRepository.findAll().forEach(shelter -> shelterDTOs.add(shelterMapper.toDTO(shelter)));
        return shelterDTOs;
    }














}




