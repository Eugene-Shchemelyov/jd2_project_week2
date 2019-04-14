package com.gmail.eugene.shchemelyov.editor.repository.connection;

import com.gmail.eugene.shchemelyov.editor.repository.exception.CapacityException;
import com.gmail.eugene.shchemelyov.editor.repository.properties.CapacityProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

@Component
public class ConnectionHandler {
    private static final Logger logger = LogManager.getLogger(ConnectionHandler.class);
    private final CapacityProperties capacityProperties;

    public ConnectionHandler(CapacityProperties capacityProperties) {
        try {
            Class.forName(capacityProperties.getDriver());
        } catch (ClassNotFoundException e) {
            logger.error(String.format("%s! %s", "Connection failed", e.getMessage()), e);
        }
        this.capacityProperties = capacityProperties;
    }

    public Connection getConnection() {
        Properties properties = new Properties();
        properties.setProperty("user", capacityProperties.getUsername());
        properties.setProperty("password", capacityProperties.getPassword());
        try {
            return DriverManager.getConnection(capacityProperties.getUrl(), properties);
        } catch (SQLException e) {
            logger.error(String.format("%s! %s", "Connection failed on get connection", e.getMessage()), e);
            throw new CapacityException(String.format("%s! %s", "Connection failed on get connection", e.getMessage()), e);
        }
    }

    @PostConstruct
    public void createCapacityTables() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS T_DOCUMENT " +
                "(" +
                "F_ID BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                "F_UNIQUE_NUMBER VARCHAR(40) NOT NULL UNIQUE, " +
                "F_DESCRIPTION VARCHAR(100) NOT NULL," +
                "F_DELETED BOOLEAN NOT NULL" +
                ") ENGINE InnoDB;";
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(createTableQuery);
                connection.commit();
                logger.info("Capacity created or exists");
            } catch (SQLException e) {
                connection.rollback();
                logger.error(String.format("%s! %s", "Connection failed on statement", e.getMessage()), e);
                throw new CapacityException(String.format("%s! %s", "Connection failed on statement", e.getMessage()), e);
            }
        } catch (SQLException e) {
            logger.error(String.format("%s! %s", "Connection failed on get connection", e.getMessage()), e);
            throw new CapacityException(String.format("%s! %s", "Connection failed on get connection", e.getMessage()), e);
        }
    }
}
