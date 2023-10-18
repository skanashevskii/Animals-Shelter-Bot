package ru.devpro.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import org.springframework.stereotype.Component;
import ru.devpro.enums.AccessLevel;
import ru.devpro.model.User;

import java.time.LocalDate;

@Component
public class ReportDTO {

    private Long id;
    private LocalDate reportDate;  // Дата отчета
    private String filePath;  // Путь к файлу отчета
    private AccessLevel accessLevel;  // Поле для управления доступом
    @JsonIgnore
    private User user;  // Ссылка на владельца отчета

    public ReportDTO() {
    }

    public ReportDTO(Long id, LocalDate reportDate, String filePath,
                     AccessLevel accessLevel, User user) {
        this.id = id;
        this.reportDate = reportDate;
        this.filePath = filePath;
        this.accessLevel = accessLevel;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
