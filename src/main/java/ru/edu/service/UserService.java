package ru.edu.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.edu.controller.car.controller.ApiCarController;
import ru.edu.entity.UserEntity;
import ru.edu.repo.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The UserService class provides service methods for managing user entities and authentication.
 */
@Service
public class UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(ApiCarController.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor for UserService that autowires the UserRepository and PasswordEncoder.
     *
     * @param userRepository The UserRepository to be used by the service.
     * @param passwordEncoder The PasswordEncoder to be used for password encryption.
     */
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    /**
     * Load user by username for authentication.
     *
     * @param username The username of the user to load.
     * @return UserDetails object representing the user.
     * @throws UsernameNotFoundException if the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            authorities
        );
    }
    /**
     * Saves a new user to bd .
     *
     * @param user The user entity to save.
     * @return true if the user is successfully saved, false otherwise.
     */

    public boolean saveUser(UserEntity user) {
        UserEntity newUser = userRepository.findByUsername(user.getUsername());
        if (newUser != null) {
            return false;
        }
        try {
            user.setUsername(user.getUsername());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        } catch (Exception e) {
            logger.error("Failed to save user: " + e);
            return false;
        }
        return true;
    }
    /**
     * Updates an existing user in bd.
     *
     * @param user The user entity to update.
     */
    public void updateUser(UserEntity user) {
        try {
            userRepository.save(user);
        } catch (Exception e) {
            logger.error("Failed to update user: " + e);
        }
    }
    /**
     * Finds a user by username.
     *
     * @param username The username of the user to find.
     * @return The UserEntity object if found, null otherwise.
     */
    public UserEntity findByUsername(String username) {

        return userRepository.findByUsername(username);
    }
    /**
     * Finds a user by id.
     *
     * @param id The id of the user to find.
     * @return The UserEntity object if found, null otherwise.
     */
    public UserEntity findById(long id) {

        return userRepository.findById(id);
    }
    /**
     * Finds all users with role 'USER'.
     *
     * @return A list of UserEntity objects with role 'USER'.
     */
    public List<UserEntity> findAllUserByRoleUser() {
        return userRepository.findByRole("USER");
    }
    /**
     * Finds all users with role 'BLOCKED'.
     *
     * @return A list of UserEntity objects with role 'BLOCKED'.
     */
    public List<UserEntity> findAllUserByRoleBlocked() {
        return userRepository.findByRole("BLOCKED");
    }
    /**
     * Changes the role of a user.
     *
     * @param newRole The new role to assign to the user.
     * @param id The id of the user whose role is to be changed.
     */
    public void changeRole(String newRole, long id) {
        UserEntity user = findById(id);
        if (user == null || user.getRole().contains("ADMIN")) {
            throw new UsernameNotFoundException("Username: " + id);
        }

        user.setRole(newRole);
        updateUser(user);
    }
    /**
     * Generates a unique login if the provided login is already in use.
     *
     * @param login The login to check for uniqueness.
     * @return A unique login if the provided one is already in use.
     */
    public String generateLogin(String login) {

        UserEntity newUser = userRepository.findByUsername(login);
        String newLogin = login;
        while (newUser != null) {
            newLogin = login + (int) Math.floor(Math.random() * 101);
            newUser = userRepository.findByUsername(newLogin);
        }
        return "login is already in use, try it: " + newLogin;
    }


}

