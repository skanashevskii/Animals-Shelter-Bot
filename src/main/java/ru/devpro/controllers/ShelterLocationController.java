package ru.devpro.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.devpro.model.Shelter;
import ru.devpro.model.ShelterLocation;
import ru.devpro.repositories.ShelterLocationRepository;

@RestController
@RequestMapping("/shelterLocation")

public class ShelterLocationController {

    private final ShelterLocationRepository shelterLocationRepository;

    public ShelterLocationController(ShelterLocationRepository shelterLocationRepository) {
        this.shelterLocationRepository = shelterLocationRepository;
    }
    // Создание адреса приюта
    @PostMapping
    public ShelterLocation createShelterAddress(@RequestBody ShelterLocation shelterLocation) {
        return shelterLocationRepository.save(shelterLocation);
    }
}
