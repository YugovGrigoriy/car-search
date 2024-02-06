package ru.edu.repo;

import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edu.entity.ReportEntity;

import java.util.List;

@Repository
@NonNullApi
public interface ReportRepository extends JpaRepository<ReportEntity,Long> {

    List<ReportEntity>findAll();

}
