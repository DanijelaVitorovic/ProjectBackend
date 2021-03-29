package com.dex.coreserver.service;

import com.dex.coreserver.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user,String username);
    List<User> findAll(String username);
    List<User> findAllDeleted(String username);
    List<User>  deactivateUser(Long id,String username);
    List<User>  activateUser(Long id,String username);
    List<User>  deleteUser(Long id,String username);
    User findUserById(Long id, String username);
}
