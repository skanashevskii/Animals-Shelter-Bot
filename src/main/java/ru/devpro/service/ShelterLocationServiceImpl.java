package ru.devpro.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ru.devpro.dto.ShelterLocationDTO;
import ru.devpro.mapers.ShelterLocationMapper;

import ru.devpro.mapers.ShelterMapper;
import ru.devpro.model.ShelterLocation;
import ru.devpro.repositories.ShelterLocationRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public ShelterLocationDTO createShelterLocation(ShelterLocationDTO shelterLocationDTO) {
        LOGGER.info("Received request to save shelter: {}",shelterLocationDTO);

        ShelterLocation shelterEntity = shelterLocationMapper.toEntity(shelterLocationDTO); // Преобразуйте DTO в сущность
        ShelterLocation savedEntity = shelterLocationRepository.save(shelterEntity);
        return shelterLocationMapper.toDTO(savedEntity); // Преобразуйте сущность обратно в DTO
    }
   /* @Override
    public ShelterLocationDTO createShelterLocation(String address, String city, String state, String zipcode) {
        LOGGER.info("Received request to save shelter with address: {}, city: {}, state: {}, zipcode: {}", address, city, state, zipcode);
        ShelterLocationDTO shelterLocationDTO = new ShelterLocationDTO();
        shelterLocationDTO.setAddress(address);
        shelterLocationDTO.setCity(city);
        shelterLocationDTO.setState(state);
        shelterLocationDTO.setZipcode(zipcode);

        LocalDateTime currentDateTime = LocalDateTime.now();
        shelterLocationDTO.setDateTime(currentDateTime);

        ShelterLocation shelterEntity = shelterLocationMapper.toEntity(shelterLocationDTO);
        ShelterLocation savedEntity = shelterLocationRepository.save(shelterEntity);

        return shelterLocationMapper.toDTO(savedEntity);
    }*/
    @Override
    public ShelterLocationDTO editShelterLocation(ShelterLocationDTO shelterLocationDTO) {
        LOGGER.info("Was invoked method for edit shelter location: {}", shelterLocationDTO);

        // Преобразовать ShelterLocationDTO в ShelterLocation с использованием маппера
        ShelterLocation shelterLocationEntity = shelterLocationMapper.toEntity(shelterLocationDTO);

        // Сохранить обновленную сущность ShelterLocation в репозитории
        ShelterLocation savedEntity = shelterLocationRepository.save(shelterLocationEntity);

        // Попытаться найти обновленную сущность ShelterLocation
        return shelterLocationRepository.findById(savedEntity.getId())
                .map(dbEntity -> {
                    // Обновить поля ShelterLocation
                    dbEntity.setAddress(shelterLocationEntity.getAddress());
                    dbEntity.setCity(shelterLocationEntity.getCity());
                    dbEntity.setState(shelterLocationEntity.getState());
                    dbEntity.setZipcode(shelterLocationEntity.getZipcode());
                    dbEntity.setDateTime(shelterLocationEntity.getDateTime());
                    dbEntity.setShelter(shelterLocationEntity.getShelter());

                    // Сохранить обновленную сущность ShelterLocation
                    shelterLocationRepository.save(dbEntity);

                    // Преобразовать обновленную сущность ShelterLocation обратно в ShelterLocationDTO
                    ShelterLocationDTO updatedShelterLocationDTO = shelterLocationMapper.toDTO(dbEntity);

                    return updatedShelterLocationDTO;
                })
                .orElse(null);
    }

    @Override
    public void deleteShelterLocation(Long id) {
        shelterLocationRepository.deleteById(id);
    }

    @Override
    public ShelterLocationDTO findShelterLocationById(Long shelterId) {
        Optional<ShelterLocation> shelterOptional = shelterLocationRepository.findById(shelterId);
        return shelterOptional.map(shelterLocationMapper::toDTO).orElse(null);
    }

    @Override
    public Collection<ShelterLocationDTO> findAll() {
        List<ShelterLocation> shelters = shelterLocationRepository.findAll();
        return shelters.stream().map(shelterLocationMapper::toDTO).collect(Collectors.toList());
    }





}
