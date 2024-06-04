package service;

import chess.ChessGame;
import dataAccess.*;

import model.AuthData;
import model.GameData;
import model.UserData;
import org.eclipse.jetty.http.MetaData;
import org.junit.jupiter.api.*;
import passoff.model.TestAuthResult;
import passoff.model.TestCreateRequest;
import passoff.model.TestUser;
import passoff.server.TestServerFacade;
import request.JoinGameRequest;
import request.LoginRequest;
import results.*;
import server.Server;
import service.UserService;
import spark.Response;
import spark.ResponseTransformerRouteImpl;
import spark.utils.Assert;

import javax.xml.crypto.Data;

class ServiceUnitTests {

    MemoryGameDAO testGame = new MemoryGameDAO();
    MemoryAuthDAO testAuth = new MemoryAuthDAO();
    MemoryUserDAO testUser = new MemoryUserDAO();

    private static UserService userService = new UserService();

    private static RegisterService registerService = new RegisterService();

    private GameService gameService = new GameService();
    private ClearService clearService = new ClearService();

    // Initialize a spark.Response variable for testing
    private static final Response response = new Response() {
        private int status;

        @Override
        public void status(int statusCode) {
            this.status = statusCode;
        }

        @Override
        public int status() {
            return status;
        }
    };

    private static TestUser existingUser;
    private static TestUser newUser;

    private static String existingAuth;

    private static GameData gameOne = new GameData(5699, null, null, "beat unnie", new ChessGame());
    private static GameData gameTwo = new GameData(1654, null, null, "beat joon", new ChessGame());

    @BeforeAll
    public static void init() {

        existingUser = new TestUser("ExistingUser", "existingUserPassword", "eu@mail.com");

        newUser = new TestUser("NewUser", "newUserPassword", "nu@mail.com");
//
//        createRequest = new TestCreateRequest("testGame");
    }

    @BeforeEach
    public void setup() throws DataAccessException {
        ClearResults results = clearService.clearAll(response);
        RegisterService regService  = new RegisterService();
        UserData holdingUser = new UserData(existingUser.getUsername(), existingUser.getPassword(), existingUser.getEmail());
        //one user already logged in
        RegisterResults regResults = regService.register(holdingUser, response);
        existingAuth = regResults.authToken();
    }

    @Test
    @Order(2)
    @DisplayName("Normal User Login")
    public void successLogin() throws DataAccessException {
        LoginRequest request = new LoginRequest(existingUser.getUsername(), existingUser.getPassword());

        RegisterResults loginResult = userService.login(request, response);

        Assertions.assertEquals(existingUser.getUsername(), loginResult.username(),
                "Response did not give the same username as user");
        Assertions.assertNotNull(loginResult.authToken(), "Response did not return an authToken");
    }

    @Test
    @Order(3)
    @DisplayName("Login Failure")
    public void loginFailure() throws DataAccessException {
        LoginRequest request = new LoginRequest(newUser.getUsername(), newUser.getPassword());

        RegisterResults loginResult = userService.login(request, response);

        Assertions.assertEquals("Error: unauthorized", loginResult.message(), "Login did not fail.");
        Assertions.assertNull(loginResult.authToken());

    }

    @Test
    @Order(4)
    @DisplayName("Logout Success")
    public void logoutSuccess() throws DataAccessException{
        AuthData auth = userService.AUTH_DAO.getAuth(existingUser.getUsername());

        LogoutAndJoinResults logoutResult = userService.logout(auth, response);

        Assertions.assertEquals(null, logoutResult.message(), "logout was not successful");
    }

    @Test
    @Order(4)
    @DisplayName("Logout Failure")
    public void logoutFailure() throws DataAccessException {
        AuthData unauthorized = new AuthData("984356dsef", "existingUser");

        LogoutAndJoinResults logoutResult = userService.logout(unauthorized, response);

        Assertions.assertEquals("Error: unauthorized", logoutResult.message(), "logout succeeded");
    }

    @Test
    @Order(5)
    @DisplayName("Register Success")
    public void registerSuccess() throws DataAccessException {
        UserData testUser = new UserData(newUser.getUsername(), newUser.getPassword(), newUser.getEmail());

        RegisterResults registerResults = registerService.register(testUser, response);

        Assertions.assertEquals(newUser.getUsername(),registerResults.username(), "register did not succeed");
        Assertions.assertNotNull(registerResults.authToken(), "authToken was not created");
    }


    @Test
    @Order(6)
    @DisplayName("Register Failed")
    public void registerFailed() throws DataAccessException{
        UserData testUser = new UserData(existingUser.getUsername(), null, existingUser.getEmail());
        RegisterResults registerResults = registerService.register(testUser, response);

        Assertions.assertEquals("Error: bad request",registerResults.message(), "register succeeded");
        Assertions.assertNull(registerResults.authToken(), "authToken was created when there should be null");
    }

    @Test
    @Order(7)
    @DisplayName("Create Game Success")
    public void createGameSuccess() throws DataAccessException{
        AuthData auth = userService.AUTH_DAO.getAuth(existingUser.getUsername());
        CreateGameResults gameResults = gameService.createGame(gameOne, auth, response);

        Assertions.assertNull(gameResults.getMessage(), "did not create game succesfully");
    }

    @Test
    @Order(8)
    @DisplayName("Create Game Failure")
    public void failedCreateGame() throws DataAccessException{
        AuthData auth = new AuthData("+9873dadf", "Jin");
        CreateGameResults gameResults = gameService.createGame(gameOne, auth, response);

        Assertions.assertNotNull(gameResults.getMessage(), "game was created successfully");
    }

    @Test
    @Order(8)
    @DisplayName("List Games Success")
    public void listGames() throws DataAccessException{
        AuthData auth = userService.AUTH_DAO.getAuth(existingUser.getUsername());
        ListGameResults results = gameService.listGames(auth, response);

        Assertions.assertNull(results.message(), "games were not returned");
    }

    @Test
    @Order(9)
    @DisplayName("List Games Failure")
    public void listGamesFailed() throws DataAccessException{
        AuthData auth = new AuthData("+9873dadf", "Jin");
        ListGameResults results = gameService.listGames(auth, response);

        Assertions.assertNotNull(results.message(), "games were not returned");
    }

    @Test
    @Order(10)
    @DisplayName("Join Game Success")
    public void joinedGame() throws DataAccessException{
        AuthData auth = userService.AUTH_DAO.getAuth(existingUser.getUsername());
        GameData game = new GameData(null, null, null, "my game", new ChessGame());
        CreateGameResults temp = gameService.createGame(game, auth, response);
        JoinGameRequest joinRequest = new JoinGameRequest(temp.getGameID(), ChessGame.TeamColor.WHITE);
        LogoutAndJoinResults joinResults = gameService.joinGame(joinRequest, auth, response);

        Assertions.assertNull(joinResults.message(), "did not join game successfully");
    }


    @Test
    @Order(11)
    @DisplayName("Join Game Failure")
    public void failedJoiningGame() throws DataAccessException{

        AuthData auth = userService.AUTH_DAO.getAuth(existingUser.getUsername());
        GameData game = new GameData(null, "Jin", null, "my game", new ChessGame());
        CreateGameResults temp = gameService.createGame(game, auth, response);
        JoinGameRequest joinRequest = new JoinGameRequest(temp.getGameID(), ChessGame.TeamColor.WHITE);
        LogoutAndJoinResults joinResults = gameService.joinGame(joinRequest, auth, response);

        Assertions.assertNotNull(joinResults.message(), "joined successfully");
    }

    @Test
    @Order(12)
    @DisplayName("Clear")
    public void clearAll(){
        ClearResults results = clearService.clearAll(response);

        Assertions.assertNull(results.message(), "failed to clear database");
    }
}