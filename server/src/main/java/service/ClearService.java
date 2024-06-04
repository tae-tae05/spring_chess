package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import results.ClearResults;
import spark.Response;

public class ClearService {
    private MemoryUserDAO userDAO = new MemoryUserDAO();
    private MemoryAuthDAO authDAO = new MemoryAuthDAO();
    private MemoryGameDAO gamesDAO = new MemoryGameDAO();



    public ClearResults clearAll(Response response){
        ClearResults clearResult = new ClearResults(null);
        try {
            userDAO.deleteUsers();
            authDAO.clear();
            gamesDAO.deleteGames();
            response.status(200);
        }
        catch(Exception e){
            clearResult.setMessage("Error: " + e.getMessage());
            response.status(500);
        }
        return clearResult;
    }

}
