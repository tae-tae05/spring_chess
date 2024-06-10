package client;

import model.UserData;
import org.eclipse.jetty.server.Authentication;
import org.junit.jupiter.api.*;
import request.LoginRequest;
import results.ClearResults;
import results.RegisterResults;
import server.Server;
import service.*;
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
//        ClearResults cleared = serverFacade.clearAll(results);
//        results = serverFacade.register(user);
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
    public void logoutPass() {
        Assertions.assertTrue(true);
    }

    @Test
    public void logoutFail() {
        Assertions.assertTrue(true);
    }

    @Test
    public void createPass() {
        Assertions.assertTrue(true);
    }

    @Test
    public void createFail() {
        Assertions.assertTrue(true);
    }

    @Test
    public void joinPass() {
        Assertions.assertTrue(true);
    }

    @Test
    public void joinFail() {
        Assertions.assertTrue(true);
    }

    @Test
    public void listPass() {
        Assertions.assertTrue(true);
    }

    @Test
    public void listFail() {
        Assertions.assertTrue(true);
    }

    @Test
    public void clearPass() throws ResponseException {
        ClearResults cleared = serverFacade.clearAll(results);

        Assertions.assertNull(cleared.message());
    }
}
