package ru.mail.druk_aleksandr.app.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import ru.mail.druk_aleksandr.app.repository.ConnectionRepository;
import ru.mail.druk_aleksandr.app.repository.UserInformationRepository;
import ru.mail.druk_aleksandr.app.repository.UserRepository;
import ru.mail.druk_aleksandr.app.repository.impl.ConnectionRepositoryImpl;
import ru.mail.druk_aleksandr.app.repository.impl.UserInformationRepositoryImpl;
import ru.mail.druk_aleksandr.app.repository.impl.UserRepositoryImpl;
import ru.mail.druk_aleksandr.app.repository.model.User;
import ru.mail.druk_aleksandr.app.repository.model.UserInformation;
import ru.mail.druk_aleksandr.app.service.UserService;
import ru.mail.druk_aleksandr.app.service.model.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static UserService instance;
    private ConnectionRepository connectionRepository = ConnectionRepositoryImpl.getInstance();
    private UserRepository userRepository = UserRepositoryImpl.getInstance();
    private UserInformationRepository userInformationRepository = UserInformationRepositoryImpl.getInstance();

    private UserServiceImpl() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public List<UserDTO> findAll() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<User> people = userRepository.findAll(connection);
                List<UserDTO> userDTOList = people.stream()
                        .map(this::convertObjectToDTO)
                        .collect(Collectors.toList());
                connection.commit();
                return userDTOList;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public void addUser(UserDTO userDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = convertDTOToObject(userDTO);
                userRepository.add(connection, user);
                user.getUserInformation().setUserId(user.getId());
                userInformationRepository.add(connection, user.getUserInformation());
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void deleteUserById(int userId) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                userInformationRepository.delete(connection, userId);
                userRepository.delete(connection, userId);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private UserDTO convertObjectToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setActive(user.getActive());
        userDTO.setAge(user.getAge());
        if (user.getUserInformation() != null) {
            userDTO.setAddress(user.getUserInformation().getAddress());
            userDTO.setTelephone(user.getUserInformation().getTelephone());
        }
        return userDTO;
    }

    private User convertDTOToObject(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setActive(userDTO.getActive());
        user.setAge(userDTO.getAge());
        UserInformation userInformation = new UserInformation();
        userInformation.setTelephone(userDTO.getTelephone());
        userInformation.setAddress(userDTO.getAddress());
        user.setUserInformation(userInformation);
        return user;
    }
}
