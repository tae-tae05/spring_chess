package server;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.sql.MySQLDataAccess;
import handlers.*;
import server.websocket.WebsocketHandler;
import service.GameService;
import spark.*;

import java.sql.SQLException;


public class Server {
    DataAccess data;

    private final GameService gameService;
    private WebsocketHandler ws;
    public Server(){
        try{
            data = new MySQLDataAccess();
        }
        catch(DataAccessException | SQLException e){
            throw new RuntimeException(e.getMessage());
        }
        try {
            configureDatabase();
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
        gameService = new GameService(data);
        ws = new WebsocketHandler(gameService, data);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);
        Spark.staticFiles.location("web");

        Spark.webSocket("/ws", ws);
        Spark.get("/echo/:msg", (req, res) -> "HTTP response: " + req.params(":msg"));

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", new RegisterHandler(data));
        Spark.delete("/db", new ClearHandlers(data));
        Spark.post("/session", new LoginHandler(data));
        Spark.delete("/session", new LogoutHandler(data));
        Spark.get("/game", new ListGamesHandler(data));
        Spark.post("/game", new CreateGameHandler(data));
        Spark.put("/game", new JoinGameHandler(data));



        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
    public static void main(String[] args) {
        var server = new Server();
        server.run(8080);
    }

    public static void configureDatabase() throws DataAccessException, SQLException {

        DatabaseManager.createDatabase();
        try(var connection = DatabaseManager.getConnection()) {
            var userTable = """
                        CREATE TABLE IF NOT EXISTS user (
                        username VARCHAR(64) NOT NULL,
                        password VARCHAR(64) NOT NULL,
                        email VARCHAR(64) NOT NULL,
                        PRIMARY KEY (username)
                    )""";

            try (var createTable = connection.prepareStatement(userTable)) {
                createTable.executeUpdate();
            }

            var gameTable = """
                    CREATE TABLE IF NOT EXISTS `game` (
                    `gameID` INT,
                    `whiteUsername` VARCHAR(64),
                    `blackUsername` VARCHAR(64),
                    `gameName` VARCHAR(64) NOT NULL,
                    `game` LONGTEXT NOT NULL,
                    PRIMARY KEY (gameID)
                    )""";


            try (var createTable = connection.prepareStatement(gameTable)) {
                createTable.executeUpdate();
            }

            var authTable = """
                    CREATE TABLE IF NOT EXISTS auth (
                    authToken VARCHAR(64) NOT NULL,
                    username VARCHAR(64) NOT NULL,
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