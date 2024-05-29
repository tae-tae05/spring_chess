package service;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.MemoryUserDAO;
import dataAccess.UserDAO;
import model.AuthData;
import model.UserData;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Tester {
    public static void main(String[] args) throws DataAccessException {
        MemoryUserDAO USERS_DB = new MemoryUserDAO();
        UserData user = new UserData("tae", "taetae", "hjhan");

        var serializer = new Gson();
        boolean inUsers = USERS_DB.getUser(user);
        if(!inUsers){
            USERS_DB.createUser(user);
        }
        AuthData one = new AuthData(UUID.randomUUID().toString(), user.username());
        var toJson = serializer.toJson(one);
        var two = serializer.fromJson(toJson, AuthData.class);
        System.out.println(two);
    }
}
