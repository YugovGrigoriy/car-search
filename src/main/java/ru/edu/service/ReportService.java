package ru.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.edu.entity.ReportEntity;
import ru.edu.repo.ReportRepository;

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
    /**
     * Saves a report entity to bd.
     *
     * @param report The report entity to save.
     */
    public void save(ReportEntity report) {
        repository.save(report);
    }
    /**
     * Retrieves a list of all reports.
     *
     * @return A list of all ReportEntity objects.
     */
    public List<ReportEntity> findAll() {
        return repository.findAll();
    }




}
