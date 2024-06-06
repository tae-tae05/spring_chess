package service;

import dataaccess.DataAccess;
import dataaccess.memory.MemoryAuthDAO;
import dataaccess.memory.MemoryGameDAO;
import dataaccess.memory.MemoryUserDAO;
import results.ClearResults;
import spark.Response;

public class ClearService {
    private final DataAccess data;
    public ClearService(DataAccess data){
        this.data = data;
    }


    public ClearResults clearAll(Response response){
        ClearResults clearResult = new ClearResults(null);
        try {
            data.getUserDAO().deleteUsers();
            data.getAuthDAO().clear();
            data.getGameDAO().deleteGames();
            response.status(200);
        }
        catch(Exception e){
            clearResult.setMessage("Error: " + e.getMessage());
            response.status(500);
        }
        return clearResult;
    }

}
