package ru.devpro.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.devpro.model.Report;
import ru.devpro.model.User;
import ru.devpro.service.ReportService;
import ru.devpro.service.UserServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/reports")
@Tag(name = "Отчеты", description = "Методы работы с отчетами")
public class ReportController {
    @Autowired
    private ReportService reportService;
    private UserServiceImpl userServiceImpl;

    @GetMapping("/user")
    public List<Report> getReportsForUser(@RequestParam Long userId) {
        User user = userServiceImpl.findUserById(userId);
        return reportService.getReportsForUser(user);
    }
}
