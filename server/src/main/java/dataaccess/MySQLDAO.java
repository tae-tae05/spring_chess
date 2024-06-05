package dataaccess;

import java.sql.SQLException;

public class MySQLDAO {
    private boolean created = false;

    public MySQLDAO() throws DataAccessException, SQLException {
        configureDatabase();
    }

    protected void configureDatabase() throws DataAccessException, SQLException {
        if (!created) {
            DatabaseManager.createDatabase();
            created = true;
        }
        try {
            var connection = DatabaseManager.getConnection();

            var userTable = """
                        CREATE TABLE IF NOT EXISTS users (
                        username VARCHAR(64) NOT NULL,
                        password VARCHAR(64) NOT NULL,
                        email VARCHAR(64) NOT NULL,
                        PRIMARY KEY (username)
                    )""";

            try (var createTable = connection.prepareStatement(userTable)) {
                createTable.executeUpdate();
            }

            var gameTable = """
                    CREATE TABLE IF NOT EXISTS games (
                    gameID int NOT NULL AUTO_INCREMENT,
                    gameData TEXT NOT NULL,
                    PRIMARY KEY (gameID)
                    )""";

            try (var createTable = connection.prepareStatement(gameTable)) {
                createTable.executeUpdate();
            }

            var authTable = """
                    CREATE TABLE IF NOT EXISTS auths (
                    authToken int NOT NULL AUTO_INCREMENT,
                    username VARCHAR(225) NOT NULL,
                    PRIMARY KEY (authToken)
                    )""";

            try (var createTable = connection.prepareStatement(authTable)) {
                createTable.executeUpdate();
            }
        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
