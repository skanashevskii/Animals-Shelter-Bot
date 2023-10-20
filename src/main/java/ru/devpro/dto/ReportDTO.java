package ru.devpro.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import lombok.Data;
import org.springframework.stereotype.Component;
import ru.devpro.enums.AccessLevel;
import ru.devpro.model.User;

import java.time.LocalDate;

@Data
public class ReportDTO {

    private Long id;
    private LocalDate reportDate;  // Дата отчета
    private String filePath;  // Путь к файлу отчета
    private AccessLevel accessLevel;  // Поле для управления доступом
    //@JsonIgnore
    private User user;  // Ссылка на владельца отчета


}
