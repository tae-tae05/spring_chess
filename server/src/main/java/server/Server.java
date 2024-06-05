package server;

import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import handlers.*;
import model.UserData;
import spark.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import static dataaccess.DatabaseManager.createDatabase;


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

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    public static void main(String[] args) {
        var server = new Server();
        try{
            createDatabase();
        }
        catch(DataAccessException e){
            throw new RuntimeException(e.getMessage());
        }
        server.run(8080);
    }
}