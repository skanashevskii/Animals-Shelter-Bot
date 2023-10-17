package ru.devpro.controllers;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.devpro.dto.ShelterLocationDTO;

import ru.devpro.service.ShelterLocationService;

@RestController
@RequestMapping("/shelterLocation")
@Tag(name = "Адреса", description = "Методы работы с адресами приюта")
public class ShelterLocationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShelterLocationController.class);
    private final ShelterLocationService shelterLocationService;

    public ShelterLocationController(ShelterLocationService shelterLocationService) {
        this.shelterLocationService = shelterLocationService;
    }


    // Создание адреса приюта
    @PostMapping
    public ShelterLocationDTO createShelterLocation(
            @Parameter(description = "Принимает объект адрес приюта")
            @RequestBody ShelterLocationDTO shelterLocationDTO) {
        LOGGER.info("Received request to save animal: {}", shelterLocationDTO);
        return shelterLocationService.createShelterLocation(shelterLocationDTO);
    }
}
