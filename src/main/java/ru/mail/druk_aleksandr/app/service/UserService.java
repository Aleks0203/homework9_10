package ru.mail.druk_aleksandr.app.service;

import java.util.List;

import ru.mail.druk_aleksandr.app.service.model.UserDTO;

public interface UserService {
    List<UserDTO> findAll();

    void addUser(UserDTO userDTO);

    void deleteUserById(int userId);
}
