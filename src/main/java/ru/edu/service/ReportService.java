package ru.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.edu.entity.ReportEntity;
import ru.edu.repo.ReportRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {
    private ReportRepository repository;

    public ReportService() {
    }

    @Autowired
    public ReportService(ReportRepository repository) {
        this.repository = repository;
    }

    public void save(ReportEntity report) {
        repository.save(report);
    }

    public List<ReportEntity> findAll() {
        return repository.findAll();
    }

    public List<String> getAllReports() {
        List<String> res = new ArrayList<>();
        List<ReportEntity> reports = findAll();
        for (int i = 0; i < reports.size(); i++) {
            ReportEntity report = reports.get(i);
            String r = String.format("Report #%d: id=%d, report date: %s, name = %s, email:%s, message:%s",
                i + 1,
                report.getId(),
                report.getLocalDateTime(),
                report.getName(),
                report.getEmail(),
                report.getMessage());
            res.add(r);

        }
        return res;
    }


}
