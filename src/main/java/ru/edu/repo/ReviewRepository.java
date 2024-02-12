package ru.edu.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edu.entity.ReviewEntity;
@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity,Long> {
}
