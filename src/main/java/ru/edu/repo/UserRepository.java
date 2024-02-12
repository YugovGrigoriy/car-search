package ru.edu.repo;


import org.springframework.data.jpa.repository.JpaRepository;



import org.springframework.stereotype.Repository;
import ru.edu.entity.UserEntity;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
    UserEntity findById(long id);

    List<UserEntity> findByRole(String role);


}
