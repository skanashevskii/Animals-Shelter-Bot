package ru.devpro.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.devpro.animal.shelter.repositories.AnimalRepository;
import ru.devpro.animal.shelter.service.AnimalService;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("animals")
@Tag(name = "Животные", description = "Методы работы с животными")
public class AnimalController {
    private final AnimalService animalService;
    private final AnimalRepository animalRepository;

    public AnimalController(AnimalService animalService, AnimalRepository animalRepository) {
        this.animalService = animalService;
        this.animalRepository = animalRepository;
    }

    @GetMapping("/cats")
    @Operation(summary = "Найти кошек")
    public String getCats() {
        return animalService.getCats();
    }
    @GetMapping("/dogs")
    @Operation(summary = "Найти собак")
    public String getDogs() {
        return animalService.getDogs();
    }

    @GetMapping("{id}")
    @Operation(summary = "Информация о животном")
    public ResponseEntity<Animal> getFacultyInfo(@Parameter(description = "ID животного")
                                                  @PathVariable long id) {
        Animal animal = animalService.findAnimalById(id);
        if (animal == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(animal);
    }

    @GetMapping("/{catsId}/cat")
    @Operation(summary = "Инфо о кошках")
    public Collection<Animal> getCatsByGroupId(@PathVariable long groupId) {
        return animalService.getCatsByGroupId(groupId);

    }


    @PostMapping
    @Operation(summary = "Создание группы кошки")
    public Faculty createFaculty(@RequestBody Animal animal) {
        return animalService.createGroup(group);
    }

    @PutMapping
    @Operation(summary = "Редактирование группы кошки")
    public ResponseEntity<Animal> editGroup(@RequestBody Animal animal) {
        Animal foundGroup = animalService.editGroup(animal);
        if (foundGroup == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundGroup);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление животного")
    public ResponseEntity<Animal> deleteAnimal(@PathVariable long id) {
        animalService.deleteAnimal(id);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/byColorOrName")
    @Operation(summary = "Сортировка по цвету")
    public ResponseEntity<Collection<Animal>> findAnimals(@RequestParam(required = false) String color
                                                             ) {
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(animalService.findAllbyColorIgnoreCase(color));
        }

        return ResponseEntity.ok(Collections.emptyList());
    }
}
