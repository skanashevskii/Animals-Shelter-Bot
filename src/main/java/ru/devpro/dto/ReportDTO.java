package ru.devpro.dto;

import jakarta.persistence.*;
import ru.devpro.enums.AccessLevel;
import ru.devpro.model.User;

import java.time.LocalDate;

public class ReportDTO {

    private Long id;
    private LocalDate reportDate;  // Дата отчета
    private String filePath;  // Путь к файлу отчета
    private AccessLevel accessLevel;  // Поле для управления доступом
    private User user;  // Ссылка на владельца отчета
}
