package ru.devpro.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ru.devpro.dto.ReportDTO;

import ru.devpro.service.ReportServiceImpl;




@RestController
@RequestMapping("/reports")
@Tag(name = "Отчеты", description = "Методы работы с отчетами")
public class ReportController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class);
    @Autowired
    private ReportServiceImpl reportService;


    @PostMapping
    @Operation(summary = "Создание отчета",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Создание объекта отчет"
            )
    )

    public ReportDTO createAnimal(@Parameter(description = "Принимает объект отчет")
                                  @RequestBody ReportDTO reportDTO) {
        LOGGER.info("Received request to save animal: {}", reportDTO);

        return reportService.createReport(reportDTO);
    }
}
