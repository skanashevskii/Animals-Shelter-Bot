package ru.devpro.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import ru.devpro.enums.AccessLevel;
import ru.devpro.model.Animal;
import ru.devpro.model.Report;
import ru.devpro.model.Shelter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class UserDTO {

    private Long id;
    private Long chatId;
    private String name;
    private String family;
    private String role;
    private String telephone;
    private String email;
    private LocalDateTime dateTime;
    private AccessLevel accessLevel;
    @JsonIgnore
    private Shelter shelter;
    @JsonIgnore
    private Set<Animal> animals;
    @JsonIgnore
    private List<Report> reports;


}
