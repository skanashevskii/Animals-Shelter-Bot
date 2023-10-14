package ru.devpro.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.devpro.model.ShelterLocation;
import ru.devpro.model.User;
import ru.devpro.repositories.ShelterLocationRepository;

@Service
public class ShelterLocationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShelterLocationService.class);

    private final ShelterLocationRepository shelterLocationRepository;

    public ShelterLocationService(ShelterLocationRepository shelterLocationRepository) {
        this.shelterLocationRepository = shelterLocationRepository;
    }
    public ShelterLocation createShelterLocation(ShelterLocation shelterLocation) {
        LOGGER.info("Was invoked method for create animal: {}", shelterLocation);
        return shelterLocationRepository.save(shelterLocation);
    }
}
