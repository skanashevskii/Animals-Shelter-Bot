package ru.devpro.model;

import jakarta.persistence.*;
import ru.devpro.enums.AccessLevel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_date")
    private LocalDate reportDate;  // Дата отчета

    @Column(name = "file_path")
    private String filePath;  // Путь к файлу отчета
    @Enumerated(EnumType.STRING)
    @Column(name = "access_level")
    private AccessLevel accessLevel;  // Поле для управления доступом
    @Column(name="report_time", nullable = false)
    private LocalDateTime dateTime;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  // Ссылка на владельца отчета

    public Report() {
    }

    public Report(Long id, LocalDate reportDate, String filePath, AccessLevel accessLevel, User user) {
        this.id = id;
        this.reportDate = reportDate;
        this.filePath = filePath;
        this.accessLevel = accessLevel;
        this.user = user;
    }
    // Другие поля и геттеры/сеттеры


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return Objects.equals(id, report.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", reportDate=" + reportDate +
                ", filePath='" + filePath + '\'' +
                ", accessLevel=" + accessLevel +
                ", user=" + user +
                '}';
    }
}