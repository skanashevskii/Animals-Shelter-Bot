package ru.devpro.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.extensions.Extension;
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
import ru.devpro.model.Cat;
import ru.devpro.model.Dog;
import ru.devpro.service.CatService;
import ru.devpro.service.DogService;


import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("animals")
@Tag(name = "Животные", description = "Методы работы с животными")
public class AnimalsController {
   private static final Logger LOGGER =LoggerFactory.getLogger(AnimalsController.class);
    private final CatService catService;
    private final DogService dogService;

    public AnimalsController(CatService catService,
                             DogService dogService
                            ) {
        this.catService = catService;
        this.dogService = dogService;
    }
    //КОНТРОЛЛЕРЫ КОШКИ

    @PostMapping
    @Operation(summary = "Создание кошки",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(

                    description = "Создание объекта кошка"))

    public Cat createCat(@Parameter(description = "Принимает объект кошка")@RequestBody Cat cat) {
        LOGGER.info("Received request to save student: {}", cat);
        return catService.createCat(cat);
    }
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Корректировка объекта кошка",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Cat.class )
                            )

                    }
            )
    })
    @PutMapping
    @Operation(summary = "Изменение инфо о кошке")
    public ResponseEntity<Cat> editCat(@RequestBody Cat cat) {
        Cat foundCat = catService.editCat(cat);
        if (foundCat == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundCat);
    }
    @ApiResponses({
            @ApiResponse(
                    responseCode = "ничего",
                    description = "Удаление кошки",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Cat.class )
                            )

                    }
            )
    })
    @DeleteMapping("{id}")
    @Operation(summary = "Удаление кошки")
    public ResponseEntity<Void> deleteCat(@PathVariable Long catId) {
        catService.deleteCat(catId);
        return ResponseEntity.ok().build();
    }
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Поиск всех кошек",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Cat[].class )
                            )

                    }
            )
    })
    @GetMapping("/cats")
    @Operation(summary = "Найти кошек")
    public Collection<Cat> getCats() {
        return catService.getAllCats();
    }
    //============================
    //КОНТРОЛЛЕРЫ СОБАКИ

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
    public Collection<Cat> getCatsById(@PathVariable long catId) {
        return catService.getCatsByGroupId(catId);

    }
    @GetMapping("/{dogsId}/dog")
    @Operation(summary = "Инфо о собаках")
    public Collection<Dog> getDogsById(@PathVariable long dogId) {
        return dogService.getDogById(dogId);

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
        catService.deleteCat(id);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/byBreed")
    @Operation(summary = "Сортировка по породе")
    public ResponseEntity<Collection<Cat>> findCats(@RequestParam(required = false) String breed
                                                             ) {
        if (breed != null && !breed.isBlank()) {
            return ResponseEntity.ok(catService.findAllbyBreedIgnoreCase(breed));
        }

        return ResponseEntity.ok(Collections.emptyList());
    }
}
