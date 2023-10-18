package ru.devpro.service;

import ru.devpro.dto.AnimalDTO;
import ru.devpro.dto.ReportDTO;
import ru.devpro.model.Animal;
import ru.devpro.model.Report;

import java.util.Collection;

public interface ReportService {
    ReportDTO createReport(ReportDTO reportDTO); // Метод для создания отчета

    Report editReport(Report report);

    void deleteReport(Long repostId);

    Report findReportById(Long reportId);

    Collection<Report> findAll();
}
