package ru.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.edu.entity.ReportEntity;
import ru.edu.repo.ReportRepository;

import java.util.List;

@Service
public class ReportService {
    private ReportRepository repository;

    public void save(ReportEntity report){
        repository.save(report);
    }
    public List<ReportEntity> findAll(){
        return repository.findAll();
    }

    @Autowired
    public void setReportRepository(ReportRepository repository) {
        this.repository = repository;
    }


}
