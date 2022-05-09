package com.Project.Backend.service;

import com.Project.Backend.entity.User;
import com.Project.Backend.exception.BaseException;
import com.Project.Backend.exception.UserException;
import com.Project.Backend.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Log4j2
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.userRepository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    //แกะ password
    public boolean matchPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    //สร้างฟังชั่นหาด้วย email
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    //สร้างฟังชั่นหาด้วย token
    public Optional<User> findByToken(String token) {
        return userRepository.findByToken(token);
    }

    //สร้างฟังชั่นหาด้วย id
    public Optional<User> findById(String id) {
        log.info("Load User From DB: " + id);
        return userRepository.findById(id);
    }

    //สร้าง
    public User create(String email, String password, String fistName, String lastName, String token, Date tokenExpireDate) throws BaseException {

        // validate
        if (Objects.isNull(email)) {
            //throw error email null
            throw UserException.createEmailNull();
        }
        if (Objects.isNull(password)) {
            //throw error password null
            throw UserException.createPasswordNull();
        }
        if (Objects.isNull(fistName)) {
            //throw error name null
            throw UserException.createFirstNameNull();
        }
        if (Objects.isNull(lastName)) {
            //throw error name null
            throw UserException.createLastNameNull();
        }

        //verify
        if (userRepository.existsByEmail(email)) {
            throw UserException.emailDuplicate();
        }

        //save
        User entity = new User();
        entity.setEmail(email);
        entity.setPassword(passwordEncoder.encode(password));
        entity.setFirstName(fistName);
        entity.setLastName(lastName);
        entity.setToken(token);
        entity.setTokenExpire(tokenExpireDate);

        return userRepository.save(entity);

    }

    //Update (ฟังชั่นรวม)
    public User update(User user) {
        return userRepository.save(user);
    }

    //updateUser
    public User updateUser(String id, String firstName, String lastName) throws UserException {
        Optional<User> opt = userRepository.findById(id);

        if (opt.isEmpty()) {
            // throw error not found
            throw UserException.notFound();
        }
        User user = opt.get();
        user.setFirstName(firstName);
        user.setLastName(lastName);

        return userRepository.save(user);
    }

    //listUser
    public List<User> listUsers() {
        List<User> users = (List<User>) userRepository.findAll();

        return users;
    }

    //GetUserId
    public User getUser(String id) throws BaseException {
        Optional<User> opt = userRepository.findById(id);
        if (opt.isEmpty()) {
            // throw error not found
            throw UserException.notFound();
        }
        User user = opt.get();

        return user;
    }

    //Delete
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }


}
