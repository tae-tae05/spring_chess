package server;

import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import handlers.*;
import model.UserData;
import spark.*;

import java.sql.SQLException;
import java.util.ArrayList;


public class Server {
    private ArrayList<UserData> users = new ArrayList<>();
    private ArrayList<UserData> auths = new ArrayList<>();
    private ArrayList<UserData> games = new ArrayList<>();

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", new RegisterHandler());
        Spark.delete("/db", new ClearHandlers());
        Spark.post("/session", new LoginHandler());
        Spark.delete("/session", new LogoutHandler());
        Spark.get("/game", new ListGamesHandler());
        Spark.post("/game", new CreateGameHandler());
        Spark.put("/game", new JoinGameHandler());



        Spark.awaitInitialization();
        return Spark.port();
    }

    public static void configureDatabase() throws SQLException{
        try  {
            var connection = DatabaseManager.getConnection();
            var createDbStatement = connection.prepareStatement("CREATE DATABASE IF NOT EXISTS chess");
            createDbStatement.executeUpdate();

            connection.setCatalog("chess");

            var createUserTable = """
                CREATE TABLE IF NOT EXISTS users (
                name VARCHAR(64) NOT NULL,
                userData TEXT NOT NULL,
                PRIMARY KEY (username)
            )""";

            try (var createTableStatement = connection.prepareStatement(createUserTable)) {
                createTableStatement.executeUpdate();
            }

            var createGameTable = """
                    CREATE TABLE IF NOT EXISTS games (
                    gameID int NOT NULL AUTO_INCREMENT,
                    gameData TEXT NOT NULL,
                    PRIMARY KEY (gameID)
                    )""";

            try (var createTableStatement = connection.prepareStatement(createGameTable)) {
                createTableStatement.executeUpdate();
            }

            var createAuthTable = """
                    CREATE TABLE IF NOT EXISTS auths (
                    authToken int NOT NULL AUTO_INCREMENT,
                    username VARCHAR(225) NOT NULL,
                    PRIMARY KEY (authToken)
                    )""";

            try (var createTableStatement = connection.prepareStatement(createAuthTable)) {
                createTableStatement.executeUpdate();
            }
        }
        catch(SQLException | DataAccessException e){
            throw new SQLException(e.getMessage());
        }
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    public static void main(String[] args) {
        var server = new Server();
        try{
            configureDatabase();
        }
        catch(SQLException e){
            throw new RuntimeException(e.getMessage());
        }
        server.run(8080);
    }
}