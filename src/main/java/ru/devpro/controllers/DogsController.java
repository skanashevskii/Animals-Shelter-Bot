package ru.devpro.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.devpro.model.Dog;
import ru.devpro.service.DogService;

import java.util.Collection;

@RestController
@RequestMapping("dogs")
@Tag(name = "Собачий приют", description = "Методы работы с собачьим приютом")
public class DogsController {
    private final DogService dogService;

    public DogsController(DogService dogService) {
        this.dogService = dogService;
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Собака успешно добавлена"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный запрос"
            )
    })
    @PostMapping
    @Operation(summary = "Создание собаки",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Необходимо заполнить поля name и breed"))
    public ResponseEntity<Dog> createDog(@RequestBody Dog dog) {
        Dog savedDog = dogService.createDog(dog);
        if (savedDog == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(savedDog);
    }

    @GetMapping("{id}")
    @Operation(summary = "Информация о собаке по id")
    public ResponseEntity<Dog> getDogById(@Parameter(description = "id собаки")
                                          @PathVariable long id) {
        Dog dog = dogService.findDogById(id);
        if (dog == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dog);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Информация обновлена"),
            @ApiResponse(responseCode = "400", description = "Неверный запрос"),
            @ApiResponse(responseCode = "404", description = "Собака с данным id не найдена")
    })
    @PutMapping
    @Operation(summary = "Изменение информации о собаке",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Введите id изменяемой собаки и новые данные"))
    public ResponseEntity<Dog> editDog(@RequestBody Dog dog) {
        Dog foundDog = dogService.editDog(dog);
        if (foundDog == null) {
            return ResponseEntity.badRequest().build();
        } else if (foundDog.equals(new Dog())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundDog);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Собака удалена"),
            @ApiResponse(responseCode = "404", description = "Собака с данным id не найдена")
    })
    @DeleteMapping("{id}")
    @Operation(summary = "Удаление собаки по id")
    public ResponseEntity<Void> deleteDog(@Parameter(description = "id удаляемой собаки")
                                          @PathVariable Long id) {
        if (dogService.deleteDog(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Собаки, отсортированные по id")
    })
    @GetMapping
    @Operation(summary = "Показать всех собак")
    public Collection<Dog> getDogs() {
        return dogService.findDogsOrderedById();
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Собаки, сгруппированные по породам")
    })
    @GetMapping("breed/all")
    @Operation(summary = "Показать всех собак по породам")
    public Collection<Dog> getDogsByBreeds() {
        return dogService.findDogsGroupedByBreeds();
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Собаки введенной породы найдены"),
            @ApiResponse(responseCode = "404", description = "Собаки введенной породы не найдены")
    })
    @GetMapping("breed")
    @Operation(summary = "Поиск по породе")
    public ResponseEntity<Collection<Dog>> findDogsOrderedByBreeds(
            @Parameter(description = "Введите точное название породы (в любом регистре)")
            @RequestParam String breed) {
        Collection<Dog> dogs = dogService.findAllByBreedIgnoreCase(breed);
        if (dogs == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dogs);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Собаки с введенной кличкой найдены"),
            @ApiResponse(responseCode = "404", description = "Собаки с введенной кличкой не найдены")
    })
    @GetMapping("name")
    @Operation(summary = "Поиск по кличке")
    public ResponseEntity<Collection<Dog>> findDogsByName(
            @Parameter(description = "Введите кличку (в любом регистре)")
            @RequestParam String name) {
        Collection<Dog> dogs = dogService.findByNameIgnoreCase(name);
        if (dogs == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dogs);
    }
}