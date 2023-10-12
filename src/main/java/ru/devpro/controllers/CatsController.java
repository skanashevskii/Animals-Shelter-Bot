package ru.devpro.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.devpro.model.Cat;
import ru.devpro.service.CatService;

import java.util.Collection;

@RestController
@RequestMapping("cats")
@Tag(name = "Кошачий приют", description = "Методы работы с кошачьим приютом")
public class CatsController {
    private final CatService catService;

    public CatsController(CatService catService) {
        this.catService = catService;
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Кошка успешно добавлена"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный запрос"
            )
    })
    @PostMapping
    @Operation(summary = "Создание кошки",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Необходимо заполнить поля name и breed"))
    public ResponseEntity<Cat> createCat(@RequestBody Cat cat) {
        Cat savedCat = catService.createCat(cat);
        if (savedCat == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(savedCat);
    }

    @GetMapping("{id}")
    @Operation(summary = "Информация о кошке по id")
    public ResponseEntity<Cat> getCatById(@Parameter(description = "id кошки")
                                          @PathVariable long id) {
        Cat cat = catService.findCatById(id);
        if (cat == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cat);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Информация обновлена"),
            @ApiResponse(responseCode = "400", description = "Неверный запрос"),
            @ApiResponse(responseCode = "404", description = "Кошка с данным id не найдена")
    })
    @PutMapping
    @Operation(summary = "Изменение информации о кошке",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Введите id изменяемой кошки и новые данные"))
    public ResponseEntity<Cat> editCat(@RequestBody Cat cat) {
        Cat foundCat = catService.editCat(cat);
        if (foundCat == null) {
            return ResponseEntity.badRequest().build();
        } else if (foundCat.equals(new Cat())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundCat);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Кошка удалена"),
            @ApiResponse(responseCode = "404", description = "Кошка с данным id не найдена")
    })
    @DeleteMapping("{id}")
    @Operation(summary = "Удаление кошки по id")
    public ResponseEntity<Void> deleteCat(@Parameter(description = "id удаляемой кошки")
                                          @PathVariable Long id) {
        if (catService.deleteCat(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Кошки, отсортированные по id")
    })
    @GetMapping
    @Operation(summary = "Показать всех кошек")
    public Collection<Cat> getCats() {
        return catService.findCatsOrderedById();
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Кошки, сгруппированные по породам")
    })
    @GetMapping("breed/all")
    @Operation(summary = "Показать всех кошек по породам")
    public Collection<Cat> getCatsByBreeds() {
        return catService.findCatsGroupedByBreeds();
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Кошки введенной породы найдены"),
            @ApiResponse(responseCode = "404", description = "Кошки введенной породы не найдены")
    })
    @GetMapping("breed")
    @Operation(summary = "Поиск по породе")
    public ResponseEntity<Collection<Cat>> findCatsOrderedByBreeds(
            @Parameter(description = "Введите точное название породы (в любом регистре)")
            @RequestParam String breed) {
        Collection<Cat> cats = catService.findAllByBreedIgnoreCase(breed);
        if (cats == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cats);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Кошки с введенной кличкой найдены"),
            @ApiResponse(responseCode = "404", description = "Кошки с введенной кличкой не найдены")
    })
    @GetMapping("name")
    @Operation(summary = "Поиск по кличке")
    public ResponseEntity<Collection<Cat>> findCatsByName(
            @Parameter(description = "Введите кличку (в любом регистре)")
            @RequestParam String name) {
        Collection<Cat> cats = catService.findByNameIgnoreCase(name);
        if (cats == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cats);
    }
}