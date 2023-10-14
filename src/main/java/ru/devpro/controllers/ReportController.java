package ru.devpro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.devpro.model.Report;
import ru.devpro.model.User;
import ru.devpro.service.ReportService;
import ru.devpro.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {
    @Autowired
    private ReportService reportService;
    private UserService userService;

    @GetMapping("/user")
    public List<Report> getReportsForUser(@RequestParam Long userId) {
        User user = userService.findUserById(userId);
        return reportService.getReportsForUser(user);
    }
}
