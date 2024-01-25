package ru.edu.repo;


import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.stereotype.Repository;
import ru.edu.entity.UserSite;

@Repository
public interface UserRepository extends JpaRepository<UserSite, Long> {
    UserSite findByUsername(String username);


}
