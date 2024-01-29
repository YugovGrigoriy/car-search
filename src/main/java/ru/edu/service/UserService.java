package ru.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.edu.entity.UserSite;
import ru.edu.repo.UserRepository;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserSite user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            Collections.emptyList()

        );
    }

    public boolean saveUser(UserSite user) {
        UserSite newUser = userRepository.findByUsername(user.getUsername());
        if (newUser != null) {
            return false;
        }
        try {
            user.setUsername(user.getUsername());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public void updateUser(UserSite user) {
        //todo проверку на корректность возраста и имени
        try {
            userRepository.save(user);
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

    public UserSite findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public String generateLogin(String login) {

        UserSite newUser = userRepository.findByUsername(login);
        String newLogin = login;
        while (newUser != null) {
            newLogin = login + (int) Math.floor(Math.random() * 101);
            newUser = userRepository.findByUsername(newLogin);

        }
        return "login is already in use, try it: " + newLogin;
    }


}

