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

import ru.devpro.dto.ShelterDTO;
import ru.devpro.dto.UserDTO;
import ru.devpro.mapers.UserMapper;
import ru.devpro.model.Shelter;
import ru.devpro.model.User;
import ru.devpro.service.UserService;

import java.util.Collection;


@RestController
@RequestMapping("users")
@Tag(name = "Пользователи", description = "Методы работы с пользователями")
public class UsersController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);
    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    @Operation(summary = "Создание пользователя",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(

                    description = "Создание объекта пользователь"))


    public ResponseEntity<UserDTO> createUser(@Parameter(description = "Принимает объект пользователь")
                                           @RequestBody UserDTO userDTO) {
        LOGGER.info("Received request to save animal: {}", userDTO);
        UserDTO createUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createUser,HttpStatus.CREATED);
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Корректировка объекта пользователь",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class )
                            )

                    }
            )
    })
    @PutMapping
    @Operation(summary = "Изменение инфо о пользователе")
    public ResponseEntity<UserDTO> editUser(@RequestBody UserDTO userDTO) {
        UserDTO foundUser = userService.editUser(userDTO);
        if (foundUser == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundUser);
    }
   /* @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное удаление пользователя",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class )
                            )

                    }
            ),
            @ApiResponse(responseCode = "404",
                    description = "Пользователь не найден")
    })*/
    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление пользователя")
    @ApiResponse(responseCode = "200", description = "Успешное удаление пользователя")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    public ResponseEntity<Void> deleteUser(@RequestParam long userId) {
       userService.deleteUserById(userId);
        return ResponseEntity.ok().build();
    }


    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Поиск всех пользователей",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User[].class )
                            )

                    }
            )
    })

        @GetMapping
        @Operation(summary = "Поиск всех пользователей")
        public ResponseEntity<Collection<UserDTO>> findAllUsers() {

                return ResponseEntity.ok(userService.findAll());

        }

}

