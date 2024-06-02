package service;

import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import results.ClearResults;
import spark.Response;

public class ClearService {
    private MemoryUserDAO USERS_DB = new MemoryUserDAO();
    private MemoryAuthDAO AUTH_DB = new MemoryAuthDAO();
    private MemoryGameDAO GAMES_DB = new MemoryGameDAO();
    public ClearResults clearAll(Response response){
        ClearResults clearResult = new ClearResults(null);
        try {
            USERS_DB.deleteUsers();
            AUTH_DB.clear();
            GAMES_DB.deleteGames();
            response.status(200);
        }
        catch(Exception e){
            clearResult.setMessage("Error: " + e.getMessage());
            response.status(500);
        }
        return clearResult;
    }

}
