package ru.devpro.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.devpro.enums.AnimalType;
import ru.devpro.model.Animal;
import ru.devpro.service.AnimalService;
import ru.devpro.service.UserService;


import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/animals")
@Tag(name = "Животные", description = "Методы работы с животными")
public class AnimalsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnimalsController.class);
    private final AnimalService animalService;

    public AnimalsController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @PostMapping
    @Operation(summary = "Создание животного",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Создание объекта животное"
            )
    )
    public ResponseEntity<Animal> createAnimal(
            @Parameter(description = "Принимает объект животное") @RequestBody Animal animal,
            @Parameter(description = "Тип животного (CAT/DOG)") @RequestParam AnimalType type) {
        LOGGER.info("Received request to save animal: {}", animal);
        Animal createdAnimal = animalService.createAnimal(animal,type);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAnimal);
    }

    @PutMapping
    @Operation(summary = "Изменение информации о животном")
    public ResponseEntity<Animal> editAnimal(
            @RequestBody Animal animal,
            @Parameter(description = "Тип животного (CAT/DOG)") @RequestParam AnimalType type) {
        animal.setType_animal(String.valueOf(type));
        Animal foundAnimal = animalService.editAnimal(animal);
        if (foundAnimal == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(foundAnimal);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление животного")
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long id) {
        animalService.deleteAnimal(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/byBreed")
    @Operation(summary = "Сортировка по породе")
    public ResponseEntity<Collection<Animal>> findAnimalsByBreed(@RequestParam(required = false) String breed) {
        if (breed != null && !breed.isBlank()) {
            return ResponseEntity.ok(animalService.findAllbyBreedIgnoreCase(breed));
        }

        return ResponseEntity.ok(Collections.emptyList());
    }
}
