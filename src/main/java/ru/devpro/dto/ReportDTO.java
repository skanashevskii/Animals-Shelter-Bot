package ru.devpro.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import lombok.Data;
import org.springframework.stereotype.Component;
import ru.devpro.enums.AccessLevel;
import ru.devpro.model.Animal;
import ru.devpro.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ReportDTO {

    private Long id;
    private LocalDateTime reportDate;  // Дата отчета
    private String filePath;  // Путь к файлу отчета
    private AccessLevel accessLevel;  // Поле для управления доступом
    private LocalDateTime dateTime;
    //@JsonIgnore
    private UserDTO userDTO;  // Ссылка на владельца отчета
    //@JsonIgnore
    private AnimalDTO animalDTO;


}
