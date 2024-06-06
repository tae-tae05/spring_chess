package dataaccess;
import model.AuthData;
import model.GameData;
import model.UserData;
public interface DataAccess {
    AuthDAO getAuthDAO();
    GameDAO getGameDAO();
    UserDAO getUserDAO();
}
