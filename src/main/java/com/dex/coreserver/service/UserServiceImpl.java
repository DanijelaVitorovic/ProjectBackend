package com.dex.coreserver.service;

import com.dex.coreserver.exceptions.UserEmailException;
import com.dex.coreserver.model.Employee;
import com.dex.coreserver.model.User;
import com.dex.coreserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    EmployeeService employeeService;

    @Override
    public User createUser(User user, String username) {

        try {
            User userDb= userRepository.findByUsername(username);
            user.setActive("DA");
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setUsername(user.getUsername());
            user.setConfirmPassword("");
            user.setDeleted(false);

            return userRepository.save(user);
        }
        catch (Exception ex){
            ex.printStackTrace();
            throw new UserEmailException("Email '"+user.getUsername()+"' već postoji!");
        }
    }

    @Override
    public List<User> findAll(String username) {
        User user=userRepository.findByUsername(username);
        return userRepository.findByIsDeletedOrderByIdAsc(false);
    }

    @Override
    public List<User> findAllDeleted(String username) {
        User user=userRepository.findByUsername(username);
        return userRepository.findByIsDeletedOrderByIdAsc(true);
    }

    @Override
    public List<User> deactivateUser(Long id, String username) {
        User userForDeactivation = findUserById(id,username);
        userForDeactivation.setActive("NE");
        userRepository.save(userForDeactivation);
        return findAll(username);
    }

    @Override
    public List<User> activateUser(Long id, String username) {
        User userForDeactivation = findUserById(id,username);
        userForDeactivation.setActive("DA");
        userRepository.save(userForDeactivation);
        return findAll(username);
    }

    @Override
    public List<User> deleteUser(Long id, String username) {
        User userForDelete = findUserById(id,username);
        userForDelete.setActive("NE");
        userForDelete.setDeleted(true);
        userRepository.save(userForDelete);
        return findAll(username);
    }

    @Override
    public User findUserById(Long id, String username) {
        User user=userRepository.findByUsername(username);
        Optional<User> userOptional = userRepository.findById(id);
        if(!userOptional.isPresent()){
            throw new RuntimeException("Korisnik nije pronadjen!");
        }
        return userOptional.get();
    }

    @Override
    public User findUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    public Employee findEmployeeByUserUsername(String username){
        User foundUser= findUserByUsername(username);
        Employee foundEmployee= employeeService.findEmployeeByUser(foundUser);
        return foundEmployee;
    }
}


