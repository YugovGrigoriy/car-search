package ru.edu.repo;


import org.springframework.data.jpa.repository.JpaRepository;



import org.springframework.stereotype.Repository;
import ru.edu.entity.UserSite;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserSite, Long> {
    UserSite findByUsername(String username);
    UserSite findById(long id);

    List<UserSite> findByRole(String role);


}
