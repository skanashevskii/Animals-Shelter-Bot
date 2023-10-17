package ru.devpro.service;


import ru.devpro.dto.UserDTO;
import ru.devpro.model.User;

import java.util.Collection;

public interface UserService {

   UserDTO createUser(UserDTO userDTO); // Метод для создания пользователя

    User editUser(User user);

    void deleteUser(Long userId);

    User findUserById(Long userId);

    Collection<User> findAll();
}
