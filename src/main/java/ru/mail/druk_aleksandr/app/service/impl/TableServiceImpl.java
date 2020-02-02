package ru.mail.druk_aleksandr.app.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;

import ru.mail.druk_aleksandr.app.repository.ConnectionRepository;
import ru.mail.druk_aleksandr.app.repository.TableRepository;
import ru.mail.druk_aleksandr.app.repository.impl.ConnectionRepositoryImpl;
import ru.mail.druk_aleksandr.app.repository.impl.TableRepositoryImpl;
import ru.mail.druk_aleksandr.app.service.TableService;
import ru.mail.druk_aleksandr.app.service.constant.CreateActionEnum;
import ru.mail.druk_aleksandr.app.service.constant.DropActionEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TableServiceImpl implements TableService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static TableService instance;
    private TableRepository tableRepository;
    private ConnectionRepository connectionRepository;

    private TableServiceImpl(ConnectionRepository connectionRepository, TableRepository tableRepository) {
        this.connectionRepository = connectionRepository;
        this.tableRepository = tableRepository;
    }

    public static TableService getInstance() {
        if (instance == null) {
            instance = new TableServiceImpl(
                    ConnectionRepositoryImpl.getInstance(),
                    TableRepositoryImpl.getInstance()
            );
        }
        return instance;
    }

    @Override
    public void deleteAllTables() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (DropActionEnum action : DropActionEnum.values()) {
                    tableRepository.executeQuery(connection, action.getQuery());
                }
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
    public void createAllTables() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (CreateActionEnum action : CreateActionEnum.values()) {
                    tableRepository.executeQuery(connection, action.getQuery());
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
