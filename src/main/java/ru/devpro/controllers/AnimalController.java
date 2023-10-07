package ru.devpro.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.devpro.model.Cat;
import ru.devpro.model.Dog;
import ru.devpro.repositories.CatsRepository;
import ru.devpro.repositories.DogsRepository;
import ru.devpro.service.CatService;
import ru.devpro.service.DogService;


import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("animals")
@Tag(name = "Животные", description = "Методы работы с животными")
public class AnimalController {
    private final CatService catService;
    private final DogService dogService;
    private final CatsRepository catsRepository;
    private final DogsRepository dogsRepository;

    public AnimalController(CatService catService,
                            DogService dogService,
                            CatsRepository catsRepository,
                            DogsRepository dogsRepository) {
        this.catService = catService;
        this.dogService = dogService;
        this.catsRepository = catsRepository;
        this.dogsRepository = dogsRepository;
    }

    @GetMapping("/cats")
    @Operation(summary = "Найти кошек")
    public String getCats() {
        return catService.getCats();
    }
    @GetMapping("/dogs")
    @Operation(summary = "Найти собак")
    public String getDogs() {
        return dogService.getDogs();
    }

    @GetMapping("{id}")
    @Operation(summary = "Информация о кошке")
    public ResponseEntity<Cat> getCatInfo(@Parameter(description = "ID животного")
                                                  @PathVariable long id) {
        Cat cat = catService.findCatById(id);
        if (cat == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cat);
    }
    @GetMapping("{id}")
    @Operation(summary = "Информация о собаке")
    public ResponseEntity<Dog> getDogInfo(@Parameter(description = "ID животного")
                                                  @PathVariable long id) {
        Dog dog = dogService.findDogById(id);
        if (dog == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dog);
    }


    @GetMapping("/{catsId}/cat")
    @Operation(summary = "Инфо о кошках")
    public Collection<Cat> getCatsByGroupId(@PathVariable long groupId) {
        return catService.getCatsByGroupId(groupId);

    }
    @GetMapping("/{dogsId}/dog")
    @Operation(summary = "Инфо о собаках")
    public Collection<Dog> getDogsByGroupId(@PathVariable long groupId) {
        return dogService.getDogById(groupId);

    }


    @PostMapping
    @Operation(summary = "Создание кошки")
    public Cat createCat(@RequestBody Cat cat) {
        return catService.createCat(cat);
    }

    @PutMapping
    @Operation(summary = "Редактирование группы кошки")
    public ResponseEntity<Cat> editGroup(@RequestBody Cat cat) {
        Cat foundCat = catService.editCat(cat);
        if (foundCat == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundCat);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление кошки")
    public ResponseEntity<Cat> deleteAnimal(@PathVariable long id) {
        catService.deleteAnimal(id);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/byBreed")
    @Operation(summary = "Сортировка по породе")
    public ResponseEntity<Collection<Cat>> findCats(@RequestParam(required = false) String breed
                                                             ) {
        if (breed != null && !breed.isBlank()) {
            return ResponseEntity.ok(catService.findAllbyColorIgnoreCase(breed));
        }

        return ResponseEntity.ok(Collections.emptyList());
    }
}
