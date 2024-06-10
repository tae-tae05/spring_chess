package client;

import chess.ChessGame;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.*;
import request.JoinGameRequest;
import request.LoginRequest;
import results.*;
import server.Server;
import ui.ResponseException;
import ui.ServerFacade;


public class ServerFacadeTests {

    private static ServerFacade serverFacade;
    private static Server server;
    private static UserData user = new UserData("jj", "arayofj", "jj@email.com");

    private static RegisterResults results;

    @BeforeAll
    public static void init() throws ResponseException {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        serverFacade = new ServerFacade("http://localhost:" + port);
    }

    @BeforeEach
    void clearAll() throws ResponseException {
        ClearResults cleared = serverFacade.clearAll(results);
        results = serverFacade.register(user);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @Test
    public void registerPass() throws ResponseException {
        UserData user = new UserData("jin", "jinny", "jin@email.com");
        RegisterResults results = serverFacade.register(user);

        Assertions.assertNotNull(results);
        Assertions.assertEquals("jin", results.username());
    }

    @Test
    public void registerFail() throws ResponseException {
        UserData user = new UserData("jin", "jinny", null);
        Assertions.assertThrows(ResponseException.class, () -> serverFacade.register(user));
    }

    @Test
    public void loginPass() throws ResponseException {
        LoginRequest loginRequest = new LoginRequest("jj", "arayofj");
        RegisterResults results = serverFacade.login(loginRequest);

        Assertions.assertNotNull(results.authToken());
        Assertions.assertEquals("jj", results.username());
    }

    @Test
    public void loginFail() throws ResponseException {
        LoginRequest loginRequest = new LoginRequest("jj", "arayofg");
        Assertions.assertThrows(ResponseException.class, () -> serverFacade.login(loginRequest));
    }

    @Test
    public void logoutPass() throws ResponseException {
        LoginRequest loginRequest = new LoginRequest("jj", "arayofj");
        RegisterResults results = serverFacade.login(loginRequest);
        LogoutResults logout = serverFacade.logout(results);

        Assertions.assertNull(logout.message());
    }

    @Test
    public void logoutFail() throws ResponseException {
        RegisterResults fakeResults = new RegisterResults("jin", "ef621", null);

        Assertions.assertThrows(ResponseException.class, () -> serverFacade.logout(fakeResults));
    }

    @Test
    public void createPass() throws ResponseException {
        GameData game = new GameData(null, null, null, "GameOne", new ChessGame());
        CreateGameResults create = serverFacade.createGame(game, results);

        Assertions.assertNull(create.getMessage());
        Assertions.assertNotNull(create.getGameID());
    }

    @Test
    public void createFail() throws ResponseException {
        GameData game = new GameData(null, null, null, "GameOne", new ChessGame());
        RegisterResults fakeResults = new RegisterResults("jin", "aef23", null);

        Assertions.assertThrows(ResponseException.class, () -> serverFacade.logout(fakeResults));
    }

    @Test
    public void joinPass() throws ResponseException {
        GameData game = new GameData(null, null, null, "GameOne", new ChessGame());
        CreateGameResults create = serverFacade.createGame(game, results);
        JoinGameRequest joinRequest = new JoinGameRequest(create.getGameID(), ChessGame.TeamColor.WHITE);
        LogoutResults joined = serverFacade.joinGame(joinRequest, results);

        Assertions.assertNull(joined.message());
    }

    @Test
    public void joinFail() throws ResponseException {
        GameData game = new GameData(null, null, null, "GameOne", new ChessGame());
        CreateGameResults create = serverFacade.createGame(game, results);
        JoinGameRequest joinRequest = new JoinGameRequest(541, ChessGame.TeamColor.WHITE);

        Assertions.assertThrows(ResponseException.class, () -> serverFacade.joinGame(joinRequest, results));
    }

    @Test
    public void listPass() throws ResponseException {
        GameData game = new GameData(null, null, null, "GameOne", new ChessGame());
        serverFacade.createGame(game, results);

        ListGameResults listResults = serverFacade.listGames(results);

        Assertions.assertNull(listResults.message());
        Assertions.assertEquals(1, listResults.games().size());
    }

    @Test
    public void listFail() throws ResponseException {
        GameData game = new GameData(null, null, null, "GameOne", new ChessGame());
        RegisterResults fakeResults = new RegisterResults("jin", "aef23", null);
        serverFacade.createGame(game, results);

        Assertions.assertThrows(ResponseException.class, () -> serverFacade.listGames(fakeResults));
    }

    @Test
    public void clearPass() throws ResponseException {
        ClearResults cleared = serverFacade.clearAll(results);

        Assertions.assertNull(cleared.message());
    }
    @Test
    public void clearFail() throws ResponseException {
        RegisterResults fakeResults = new RegisterResults("jin", "aef23", null);
        ClearResults cleared = serverFacade.clearAll(fakeResults);

        Assertions.assertNull(cleared.message());
    }
}
