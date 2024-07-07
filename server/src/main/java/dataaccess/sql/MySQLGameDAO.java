package dataaccess.sql;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.GameDAO;
import model.GameData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class MySQLGameDAO implements GameDAO {

    static int i = 1;
    public MySQLGameDAO() throws DataAccessException, SQLException {
    }
    @Override
    public void updateGame(ChessGame newGame, int gameID) throws DataAccessException {
        var gson = new Gson();
        String game = gson.toJson(newGame);
        String update = "UPDATE game SET game = ? WHERE gameID = " + gameID;
        try(var connection = DatabaseManager.getConnection()){
            var pst = connection.prepareStatement(update);
            pst.setString(1, game);
            int result = pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to update game " + e.getMessage());
        }
    }

    @Override
    public void makeUsernameNull(ChessGame.TeamColor color, int gameID){
        String update = "";
        if(color == ChessGame.TeamColor.WHITE) {
            update = "UPDATE game set whiteUsername = ? WHERE gameID = " + gameID;
        }
        else{
            update = "UPDATE game set blackUsername = ? WHERE gameID = " + gameID;
        }
        try(var connection = DatabaseManager.getConnection()){
            var pst = connection.prepareStatement(update);
            pst.setString(1, null);
            int result = pst.executeUpdate();
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException("Unable to change username " + e.getMessage());
        }
    }

    @Override
    public Collection<GameData> listGames() {
        Collection<GameData> allGames= new ArrayList<>();
        try(var connection = DatabaseManager.getConnection()) {
            String findUsers = """
                    SELECT * FROM game""";
            try(var pst = connection.prepareStatement(findUsers)){
                var rs = pst.executeQuery();
                while(rs.next()){
                    var gson = new Gson();
                    ChessGame chess = gson.fromJson(rs.getString("game"), ChessGame.class);
                    String gameID = rs.getString("gameID");
                    GameData game = new GameData(Integer.valueOf(gameID), rs.getString("whiteUsername"), rs.getString("blackUsername"), rs.getString("gameName"), chess);
                    allGames.add(game);
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
        return allGames;
    }

    @Override
    public void addGame(GameData game) throws DataAccessException, SQLException {
        String sql = "INSERT INTO game (gameID, whiteUsername, blackUsername, gameName, game) VALUES (?, ?, ?, ?, ?)";
        try (var connection = DatabaseManager.getConnection()) {
            var pst = connection.prepareStatement(sql);
            var gson = new Gson();
            pst.setString(1, String.valueOf(game.gameID()));
            pst.setString(2, game.whiteUsername());
            pst.setString(3, game.blackUsername());
            pst.setString(4, game.gameName());
            pst.setString(5, gson.toJson(game.game()));
            int result = pst.executeUpdate();
        }
    }

    @Override
    public boolean verifyBlackPosition(int gameID) {
        String sql = "SELECT blackUsername FROM game WHERE gameID= '" + String.valueOf(gameID) + "'";
        try (var connection = DatabaseManager.getConnection()){
            var pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            rs.next();
            String check = rs.getString("blackUsername");
            return check == null;
        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean verifyWhitePosition(int gameID) {
        String sql = "SELECT whiteUsername FROM game WHERE gameID= '" + String.valueOf(gameID) + "'";
        try (var connection = DatabaseManager.getConnection()){
            var pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            rs.next();
            String check = rs.getString("whiteUsername");
            return check == null;
        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteGames() throws DataAccessException, SQLException {
        String sql = "TRUNCATE TABLE game";
        try (var connection = DatabaseManager.getConnection()) {
            var pst = connection.prepareStatement(sql);
            int result = pst.executeUpdate();
        }
    }

    @Override
    public void insertUsername(int gameID, String newUsername, ChessGame.TeamColor color) throws DataAccessException, SQLException {
        String sql = "";
        if(color == ChessGame.TeamColor.WHITE && verifyWhitePosition(gameID)){
            sql = "UPDATE game SET whiteUsername = ? WHERE gameID = " + String.valueOf(gameID);

        }
        else if(color == ChessGame.TeamColor.BLACK && verifyBlackPosition(gameID)){
            sql = "UPDATE game SET blackUsername = ? WHERE gameID = " + String.valueOf(gameID);
        }
        else{
            throw new DataAccessException("could not enter name");
        }
        try(var connection = DatabaseManager.getConnection()) {
            var pst = connection.prepareStatement(sql);
            pst.setString(1, newUsername);
            int result = pst.executeUpdate();
        }
    }

    @Override
    public boolean verifyGame(Integer gameID) {
        String sql = "SELECT gameID FROM game WHERE gameID= '" + gameID + "'";
        try (var connection = DatabaseManager.getConnection()){
            var pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
