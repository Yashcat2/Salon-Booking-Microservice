package com.yash.service;

import com.yash.exception.UserException;
import com.yash.modal.User;

import java.util.List;

public interface IUserService {

    User createUser(User user);
    User getUserById(Long id) throws UserException;
    List<User> getAllUsers();
    void deleteUser(Long id) throws UserException;
    User updateUser(Long id, User user) throws UserException;
}
