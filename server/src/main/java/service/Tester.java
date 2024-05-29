package service;

import com.google.gson.Gson;
import model.UserData;

import java.util.List;
import java.util.Map;

public class Tester {
    public static void main(String[] args){
        UserData user = new UserData("taetae", "taetae05", "hjhan2002");

        var serializer = new Gson();
        var json = serializer.toJson(user);
        System.out.println(json);
    }
}
