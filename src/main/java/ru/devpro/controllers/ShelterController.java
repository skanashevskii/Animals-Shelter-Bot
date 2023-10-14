package ru.devpro.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.devpro.dto.ShelterDTO;

import ru.devpro.model.Shelter;

import ru.devpro.service.ShelterService;

@RestController
@RequestMapping("/shelters")
public class ShelterController {
    private final ShelterService shelterService;


    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;

    }

    // Создание нового приюта
    @PostMapping
    @Operation(summary = "Создание приюта")
    public ResponseEntity<?> createShelter(@RequestBody ShelterDTO shelterDTO) {
        ShelterDTO createdShelter = shelterService.createShelter(shelterDTO);
        return new ResponseEntity<>(createdShelter, HttpStatus.CREATED);
    }
    @PutMapping
    @Operation(summary = "Изменение инфо о приюте")
    public ResponseEntity<ShelterDTO> editShelter(@RequestBody ShelterDTO shelterDTO) {
        ShelterDTO foundShelter =shelterService.editShelter(shelterDTO);
        if (foundShelter == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundShelter);
    }
    // Получение информации о приюте по его идентификатору
    @GetMapping("/{id}")
    public ResponseEntity<ShelterDTO> getShelterById(@PathVariable Long id) {
        ShelterDTO shelterDTO = shelterService.findShelterById(id);
        if(shelterDTO == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(shelterDTO,HttpStatus.OK);
    }
    @ApiResponses({
            @ApiResponse(
                    responseCode = "ничего",
                    description = "Удаление приюта",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Shelter.class )
                            )

                    }
            )
    })

    // Удаление приюта по его идентификатору

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление приюта")
    public ResponseEntity<Void> deleteShelter(@PathVariable Long id) {
        shelterService.deleteShelter(id);
        return ResponseEntity.ok().build();
    }
}
