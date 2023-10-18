package ru.devpro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.devpro.dto.ReportDTO;
import ru.devpro.enums.AccessLevel;
import ru.devpro.mapers.AnimalMapper;
import ru.devpro.mapers.ReportMapper;
import ru.devpro.model.Report;
import ru.devpro.model.User;
import ru.devpro.repositories.ReportRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;

    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
        this.reportMapper = ReportMapper.INSTANCE;
    }

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

    @Override
    public ReportDTO createReport(ReportDTO reportDTO) {
        return null;
    }

    @Override
    public Report editReport(Report report) {
        return null;
    }

    @Override
    public void deleteReport(Long repostId) {

    }

    @Override
    public Report findReportById(Long reportId) {
        return null;
    }

    @Override
    public Collection<Report> findAll() {
        return null;
    }
}
