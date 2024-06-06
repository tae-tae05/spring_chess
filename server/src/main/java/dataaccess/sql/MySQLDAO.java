package dataaccess.sql;

import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;

import java.sql.SQLException;

public class MySQLDAO {
    private boolean created = false;

//    public MySQLDAO() throws DataAccessException, SQLException {
//        configureDatabase();
//    }

//    protected void configureDatabase() throws DataAccessException, SQLException {
//        if (!created) {
//            DatabaseManager.createDatabase();
//            created = true;
//        }
//        try(var connection = DatabaseManager.getConnection()) {
//            String userTable = """
//                        CREATE TABLE IF NOT EXISTS user (
//                        `username` VARCHAR(64) NOT NULL,
//                        `password` VARCHAR(64) NOT NULL,
//                        `email` VARCHAR(64) NOT NULL,
//                        PRIMARY KEY (username)
//                    )""";
//
//            try (var createTable = connection.prepareStatement(userTable)) {
//                createTable.executeUpdate();
//            }
//
////            var gameTable = """
////                    CREATE TABLE IF NOT EXISTS games (
////                    gameID int NOT NULL,
////                    gameData TEXT NOT NULL,
////                    PRIMARY KEY (gameID)
////                    )""";
////
////            try (var createTable = connection.prepareStatement(gameTable)) {
////                createTable.executeUpdate();
////            }
////
////            var authTable = """
////                    CREATE TABLE IF NOT EXISTS auths (
////                    authToken VARCHAR(64) NOT NULL,
////                    username VARCHAR(64) NOT NULL,
////                    PRIMARY KEY (authToken)
////                    )""";
////
////            try (var createTable = connection.prepareStatement(authTable)) {
////                createTable.executeUpdate();
////            }
//        } catch (DataAccessException | SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
}
