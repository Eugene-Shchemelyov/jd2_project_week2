package com.gmail.eugene.shchemelyov.editor.repository.impl;

import com.gmail.eugene.shchemelyov.editor.repository.DocumentRepository;
import com.gmail.eugene.shchemelyov.editor.repository.connection.ConnectionHandler;
import com.gmail.eugene.shchemelyov.editor.repository.exception.CapacityException;
import com.gmail.eugene.shchemelyov.editor.repository.model.Document;
import com.mysql.jdbc.Statement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Repository
public class DocumentRepositoryImpl implements DocumentRepository {
    private static final Logger logger = LogManager.getLogger();
    private final ConnectionHandler connectionHandler;

    @Autowired
    public DocumentRepositoryImpl(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    @Override
    public Document save(Document document) {
        try (Connection connection = connectionHandler.getConnection()) {
            connection.setAutoCommit(false);
            String query = "INSERT INTO T_DOCUMENT(F_UNIQUE_NUMBER, F_DESCRIPTION, F_DELETED) " +
                    "VALUES (?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, document.getUniqueNumber().toString());
                ps.setString(2, document.getDescription());
                ps.setBoolean(3, document.isDeleted());
                int countSuccessQuery = ps.executeUpdate();
                try (ResultSet resultSet = ps.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        document.setId(resultSet.getLong(1));
                        connection.commit();
                        logger.info(String.format("%s %d", "Count documents created: ", countSuccessQuery));
                    }
                } catch (SQLException e) {
                    connection.rollback();
                    logger.error(String.format("%s\n %s", "Connection failed on result set", e.getMessage()), e);
                    throw new CapacityException(String.format("%s! %s", "Connection failed on result set", e.getMessage()), e);
                }
            } catch (SQLException e) {
                connection.rollback();
                logger.error(String.format("%s\n %s", "Connection failed on prepared statement", e.getMessage()), e);
                throw new CapacityException(String.format("%s! %s", "Connection failed on prepared statement", e.getMessage()), e);
            }
        } catch (SQLException e) {
            logger.error(String.format("%s\n %s", "Connection failed on get connection", e.getMessage()), e);
            throw new CapacityException(String.format("%s! %s", "Connection failed on get connection", e.getMessage()), e);
        }
        return document;
    }

    @Override
    public Document getDocumentById(Long id) {
        try (Connection connection = connectionHandler.getConnection()) {
            connection.setAutoCommit(false);
            String query = "SELECT * FROM T_DOCUMENT WHERE F_ID = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setLong(1, id);
                try (ResultSet resultSet = ps.executeQuery()) {
                    if (resultSet.next()) {
                        Document document = getDocument(resultSet);
                        logger.info("Document found");
                        connection.commit();
                        return document;
                    } else {
                        connection.commit();
                        logger.info("Document not found");
                        return new Document();
                    }
                } catch (SQLException e) {
                    connection.rollback();
                    logger.error(String.format("%s! %s", "Connection failed on result set", e.getMessage()), e);
                    throw new CapacityException(String.format("%s! %s", "Connection failed on result set", e.getMessage()), e);
                }
            } catch (SQLException e) {
                connection.rollback();
                logger.error(String.format("%s! %s", "Connection failed on prepared statement", e.getMessage()), e);
                throw new CapacityException(String.format("%s %s", "Connection failed on prepared statement!", e.getMessage()), e);
            }
        } catch (SQLException e) {
            logger.error(String.format("%s! %s", "Connection failed on get connection", e.getMessage()), e);
            throw new CapacityException(String.format("%s! %s", "Connection failed on get connection", e.getMessage()), e);
        }
    }

    private Document getDocument(ResultSet resultSet) throws SQLException {
        Document document = new Document();
        document.setId(resultSet.getLong("F_ID"));
        document.setUniqueNumber(UUID.fromString(resultSet.getString("F_UNIQUE_NUMBER")));
        document.setDescription(resultSet.getString("F_DESCRIPTION"));
        document.setDeleted(resultSet.getBoolean("F_DELETED"));
        return document;
    }

    @Override
    public void deleteDocument(Document document) {
        try (Connection connection = connectionHandler.getConnection()) {
            connection.setAutoCommit(false);
            String query = "UPDATE T_DOCUMENT SET F_DELETED = ? WHERE F_ID = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setBoolean(1, document.isDeleted());
                ps.setLong(2, document.getId());
                int countSuccessQuery = ps.executeUpdate();
                connection.commit();
                logger.info(String.format("%s %d", "Count documents deleted: ", countSuccessQuery));
            } catch (SQLException e) {
                connection.rollback();
                logger.error(String.format("%s! %s", "Connection failed on prepared statement", e.getMessage()), e);
                throw new CapacityException(String.format("%s! %s", "Connection failed on prepared statement", e.getMessage()), e);
            }
        } catch (SQLException e) {
            logger.error(String.format("%s! %s", "Connection failed on get connection", e.getMessage()), e);
            throw new CapacityException(String.format("%s! %s", "Connection failed on get connection", e.getMessage()), e);
        }
    }
}
