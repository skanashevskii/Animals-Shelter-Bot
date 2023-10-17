package ru.devpro.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ru.devpro.dto.ShelterLocationDTO;
import ru.devpro.mapers.ShelterLocationMapper;

import ru.devpro.mapers.ShelterMapper;
import ru.devpro.model.ShelterLocation;
import ru.devpro.repositories.ShelterLocationRepository;

import java.util.Collection;

@Service
public class ShelterLocationServiceImpl implements ShelterLocationService{
    private static final Logger LOGGER = LoggerFactory.getLogger(ShelterLocationServiceImpl.class);

    private final ShelterLocationRepository shelterLocationRepository;
    private final ShelterLocationMapper shelterLocationMapper;

    public ShelterLocationServiceImpl(ShelterLocationRepository shelterLocationRepository) {
        this.shelterLocationRepository = shelterLocationRepository;
        this.shelterLocationMapper = ShelterLocationMapper.INSTANCE;
    }


    @Override
    public void deleteShelterLocation(Long id) {

    }

    @Override
    public ShelterLocation editShelterLocation(ShelterLocation shelterLocation) {
        return null;
    }

    @Override
    public ShelterLocation findShelterById(Long userId) {
        return null;
    }

    @Override
    public Collection<ShelterLocation> findAll() {
        return null;
    }

    @Override
    public ShelterLocationDTO createShelterLocation(ShelterLocationDTO shelterLocationDTO) {
        LOGGER.info("Received request to save shelter: {}",shelterLocationDTO);
        ShelterLocation shelterEntity = shelterLocationMapper.toEntity(shelterLocationDTO); // Преобразуйте DTO в сущность
        ShelterLocation savedEntity = shelterLocationRepository.save(shelterEntity);
        return shelterLocationMapper.toDTO(savedEntity); // Преобразуйте сущность обратно в DTO
    }

}
