package model;

public class UserData {
    private final String USERNAME;
    private final String PASSWORD;
    private final String EMAIL;

    public UserData(String username, String password, String email){
        this.USERNAME = username;
        this.PASSWORD = password;
        this.EMAIL = email;
    }

    public String returnUsername(){
        return USERNAME;
    }

    public String returnPassword(){
        return PASSWORD;
    }

    public String returnEmail(){
        return EMAIL;
    }

//    public void changeUsername(String newUsername){
//        this.USERNAME = newUsername;
//    }
//    public void changePassword(String newPassword){
//        this.PASSWORD = newPassword;
//    }
//
//    public void changeEmail(String newEmail){
//        this.EMAIL = newEmail;
//    }
}
