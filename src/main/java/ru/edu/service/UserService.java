package ru.edu.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.edu.entity.UserEntity;
import ru.edu.repo.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    @Autowired
    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user =  userRepository.findByUsername(username);
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
            throw new RuntimeException(e);
        }
        return true;
    }

    public void updateUser(UserEntity user) {
        try {
            userRepository.save(user);
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

    public UserEntity findByUsername(String username) {

        return userRepository.findByUsername(username);
    }
    public UserEntity findById(long id){return userRepository.findById(id);}

    public List<UserEntity> findAllUserByRoleUser() {
        return userRepository.findByRole("USER");
    }
    public List<UserEntity> findAllUserByRoleBlocked() {
        return userRepository.findByRole("BLOCKED");
    }
    public void changeRole(String newRole,long id){
        UserEntity user=findById(id);
       if(user==null||user.getRole().contains("ADMIN")){
           throw new RuntimeException("user not found");
       }
        user.setRole(newRole);
        updateUser(user);
    }

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

