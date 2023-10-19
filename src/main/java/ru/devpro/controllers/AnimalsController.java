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
import ru.devpro.dto.AnimalDTO;
import ru.devpro.enums.AnimalType;
import ru.devpro.model.Animal;
import ru.devpro.service.AnimalService;


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
    public ResponseEntity<AnimalDTO> createAnimal(
            @Parameter(description = "Принимает объект животное")
            @RequestBody AnimalDTO animalDTO,
            @Parameter(description = "Тип животного (CAT/DOG)") @RequestParam AnimalType type) {

        LOGGER.info("Received request to save animal: {}", animalDTO);

        // Сервис для создания животного
        AnimalDTO createdAnimal = animalService.createAnimal(animalDTO, type);

        // Проверка, что животное было успешно создано
        if (createdAnimal != null) {
            // Верните успешный результат с созданным животным
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAnimal);
        } else {
            // Если создание не удалось, верните код ошибки
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Корректировка объекта животное",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Animal.class )
                            )

                    }
            )
    })

    @PutMapping
    @Operation(summary = "Изменение информации о животном")
    public ResponseEntity<AnimalDTO> editAnimal(
            @RequestParam Long id,
            @RequestBody AnimalDTO animalDTO,
            @Parameter(description = "Тип животного (CAT/DOG)") @RequestParam AnimalType type) {
        // Передаем id, animalDTO и type в сервис для обработки
        AnimalDTO foundAnimal = animalService.editAnimal(id, animalDTO, type);
        if (foundAnimal == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(foundAnimal);
    }
    @GetMapping("/{animalId}")
    @Operation(summary = "Получение информации о животном по ID")
    public ResponseEntity<AnimalDTO> getAnimalById(
            @RequestParam Long animalId) {
        AnimalDTO animalDTO = animalService.findAnimalById(animalId);
        if (animalDTO != null) {
            return ResponseEntity.ok(animalDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @DeleteMapping("{id}")
    @Operation(summary = "Удаление животного")
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long id) {
        animalService.deleteAnimal(id);
        return ResponseEntity.ok().build();
    }

  /*  @GetMapping("/byBreed")
    @Operation(summary = "Сортировка по породе")
    public ResponseEntity<Collection<Animal>> findAnimalsByBreed(@RequestParam(required = false) String breed) {
        if (breed != null && !breed.isBlank()) {
            return ResponseEntity.ok(animalService.findAllbyBreedIgnoreCase(breed));
        }

        return ResponseEntity.ok(Collections.emptyList());
    }*/
}
