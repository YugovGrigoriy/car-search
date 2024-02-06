package ru.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.edu.entity.UserSite;
import ru.edu.repo.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


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
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            authorities
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
        int age=user.getAge();
        if(age>120||age<1){
            //todo //
        }
        try {
            userRepository.save(user);
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

    public UserSite findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public UserSite findById(long id){return userRepository.findById(id);}

    public List<UserSite> findAllUserByRoleUser() {
        return userRepository.findByRole("USER");
    }
    public List<UserSite> findAllUserByRoleBlocked() {
        return userRepository.findByRole("BLOCKED");
    }
    public void changeRole(String newRole,long id){
        UserSite user=findById(id);
        user.setRole(newRole);
        updateUser(user);
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

