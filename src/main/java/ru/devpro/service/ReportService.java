package ru.devpro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.devpro.enums.AccessLevel;
import ru.devpro.model.Report;
import ru.devpro.model.User;
import ru.devpro.repositories.ReportRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;

    public List<Report> getReportsForUser(User user) {
        AccessLevel userAccessLevel = user.getAccessLevel(); // Предположим, что есть метод для получения AccessLevel у пользователя

        if (AccessLevel.OWNER.equals(userAccessLevel)) {
            // Владельцу доступны отчеты с AccessLevel OWNER и BOTH
            return reportRepository.findByAccessLevelIn(Arrays.asList(AccessLevel.OWNER,
                    AccessLevel.BOTH));
        } else if (AccessLevel.VOLUNTEER.equals(userAccessLevel)) {
            // Волонтеру доступны отчеты с AccessLevel VOLUNTEER и BOTH
            return reportRepository.findByAccessLevelIn(Arrays.asList(AccessLevel.VOLUNTEER,
                    AccessLevel.BOTH));
        }

        return Collections.emptyList();
    }
}
