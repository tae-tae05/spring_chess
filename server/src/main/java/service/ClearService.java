package service;

import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import results.ClearResults;
import spark.Response;

public class ClearService {
    private MemoryUserDAO USERS_DAO = new MemoryUserDAO();
    private MemoryAuthDAO AUTH_DAO = new MemoryAuthDAO();
    private MemoryGameDAO GAMES_DAO = new MemoryGameDAO();



    public ClearResults clearAll(Response response){
        ClearResults clearResult = new ClearResults(null);
        try {
            USERS_DAO.deleteUsers();
            AUTH_DAO.clear();
            GAMES_DAO.deleteGames();
            response.status(200);
        }
        catch(Exception e){
            clearResult.setMessage("Error: " + e.getMessage());
            response.status(500);
        }
        return clearResult;
    }

}
