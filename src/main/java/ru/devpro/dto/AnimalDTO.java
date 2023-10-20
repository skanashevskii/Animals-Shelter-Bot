package ru.devpro.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.stereotype.Component;
import ru.devpro.enums.AnimalType;
import ru.devpro.model.User;

import java.time.LocalDateTime;

@Data
public class AnimalDTO {
    private Long id;

    private String name;

    private AnimalType type_animal;

    private String breed;

    private String text;

    private LocalDateTime dateTime;
    @JsonIgnore
    private User user;


}
