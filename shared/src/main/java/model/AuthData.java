package model;

//public class AuthData {
//    private final String AUTH_TOKEN;
//    private final String USERNAME;
//
//    public AuthData(String authToken, String username){
//        this.AUTH_TOKEN = authToken;
//        this.USERNAME = username;
//    }
//
//    public String returnAuthToken(){
//        return AUTH_TOKEN;
//    }
//    public String returnUsername(){
//        return USERNAME;
//    }
//}
record AuthData(String authToken, String Username) {}