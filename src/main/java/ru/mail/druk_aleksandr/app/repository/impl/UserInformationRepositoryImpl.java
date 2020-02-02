package ru.mail.druk_aleksandr.app.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import ru.mail.druk_aleksandr.app.repository.UserInformationRepository;
import ru.mail.druk_aleksandr.app.repository.model.UserInformation;

public class UserInformationRepositoryImpl extends GeneralRepositoryImpl<UserInformation> implements UserInformationRepository {
    private static UserInformationRepository instance;

    public static UserInformationRepository getInstance() {
        if (instance == null) {
            instance = new UserInformationRepositoryImpl();
        }
        return instance;
    }

    @Override
    public UserInformation add(Connection connection, UserInformation userInformation) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO user_information(user_id, telephone, address) VALUES (?,?,?)",
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, userInformation.getUserId());
            statement.setString(2, userInformation.getTelephone());
            statement.setString(3, userInformation.getAddress());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating userInformation failed, no rows affected.");
            }
            return userInformation;
        }
    }

    @Override
    public void delete(Connection connection, int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM user_information WHERE user_id=?")) {
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting userInformation failed, no rows affected.");
            }
        }
    }

    @Override
    public List<UserInformation> findAll(Connection connection) {
        throw new UnsupportedOperationException();
    }
}
