package ui;

import chess.ChessGame;
import chess.InvalidMoveException;
import model.GameData;
import model.UserData;
import request.JoinGameRequest;
import request.LoginRequest;
import results.CreateGameResults;
import results.ListGameResults;
import results.LogoutResults;
import results.RegisterResults;
import websocket.NotifHandler;
import websocket.WebsocketClient;
import websocket.messages.*;

import java.rmi.ServerException;
import java.util.Objects;
import java.util.Scanner;

public class Repl implements NotifHandler {
    private PrintingChessBoard printBoard = new PrintingChessBoard();
    private PrintListResults printingLists = new PrintListResults();
    private PrintingHelp help = new PrintingHelp();
    private RegisterResults loginResult;
    private LoginRequest loginRequest;
    private CreateGameResults createGame;
    private ListGameResults listGame;
    private UserData registerRequest;
    private ServerFacade serverFacade;
    private RunGame runGame;

    private String url = "http://localhost:8080";

    private final WebsocketClient webS;

    private int counter = 1;
    private boolean loginStatus = false;

    private boolean keepRunning = true;

    private void setStatus(boolean login, boolean run){
        loginStatus = login;
        keepRunning = run;
    }
    private void printLoginStatus(){
        if(loginStatus){
            System.out.print("[LOGGED IN] >>> ");
        }
        else{
            System.out.print("[LOGGED OUT] >>> ");
        }
    }

    public Repl(String url) throws ServerException {
        this.loginResult = null;
        this.loginRequest = null;
        this.createGame = null;
        this.listGame = null;
        this.registerRequest = null;
        this.serverFacade = null;
        this.loginStatus = false;
//        this.keepRunning = true;
        this.url = url;
        webS = new WebsocketClient(url, this);
        this.runGame = new RunGame(serverFacade, url, null, 0, null,this, webS);
    }
    public void run(String url) {
        serverFacade = new ServerFacade(url);
        System.out.println("Welcome to Chess. Type help to get started.");
        Scanner scanner = new Scanner(System.in);
        while (keepRunning) {
            printLoginStatus();
            String input = scanner.nextLine();
            var inputs = input.split(" ");
            if (!loginStatus) {
                switch (inputs[0]) {
                    case "quit" -> {
                        keepRunning = false;
                        System.out.println("System will now close.");
                    }
                    case "help" -> {
                        help.printBeforeLogin();
                    }
                    case "register" -> {
                        if (inputs.length == 4) { // tokens are correct length
                            registerRequest = new UserData(inputs[1], inputs[2], inputs[3]);
                            runRegister(registerRequest);
                        } else {
                            System.out.println("you have not provided enough information. type 'help' for specific commands.");
                        }
                    }
                    case "login" -> {
                        if (inputs.length == 3) {
                            loginRequest = new LoginRequest(inputs[1], inputs[2]);
                            runLogin(loginRequest);
                        } else {
                            System.out.println("you have not provided enough information. type 'help' for specific commands.");
                        }
                    }
                    default -> {
                        System.out.println(inputs[0] + " is not a valid command, please type 'help' for a list of commands");
                    }
                }
            } else {
                switch (inputs[0]) {
                    case "logout" -> {
                        if (inputs.length == 1) {
                            runLogout(loginResult);
                        } else {
                            System.out.println("input is incorrect. type 'help' for specific commands.");
                        }
                    }
                    case "help" -> {
                        help.printAfterLogin();
                    }
                    case "create" -> {
                        if (inputs.length == 2) {
                            runCreate(inputs[1], loginResult);
                        } else {
                            System.out.println("input was not correct. Enter help for specific commands");
                        }
                    }
                    case "join" -> {
                        JoinGameRequest joinRequest = new JoinGameRequest();
                        if (inputs.length == 3) {
                            joinRequest.setGameID(Integer.valueOf(inputs[1]));
                            JoinGameRequest newJoinRequest = setTeamColor(inputs[2],joinRequest);
                            runJoin(newJoinRequest, loginResult, inputs[1]);
                        } else {
                            System.out.println("input was not correct. Enter help for specific commands");
                        }
                    }
                    case "list" -> {
                        if (inputs.length == 1) {
                            try {
                                listGame = serverFacade.listGames(loginResult);
                                printingLists.printGames(listGame);
                            } catch (ResponseException e) {
                                System.out.println("unable to list games -> " + e.getMessage());
                            }
                        } else {
                            System.out.println("input was not correct. Enter help for specific commands");
                        }

                    }
                    case "observe" -> {
                        if (inputs.length == 2) {
                            observing(loginResult, inputs[1]);
                        }
                    }
                    case "quit" -> {
                        loginQuit();
                    }
                    default -> {
                        System.out.println(inputs[0] + " is not a valid command, please type 'help' for a list of commands");
                    }
                }
            }
        }
        System.exit(0);
    }

    public String getURL(){
        return url;
    }

    private void runRegister(UserData request){
        try{
            loginResult = serverFacade.register(request);
            loginStatus = true;
            System.out.println("You have successfully registered and are logged in! Welcome.");
        }
        catch (ResponseException e) {
            System.out.println(e.getMessage());
        }
    }

    private void runLogin(LoginRequest request){
        try {
            loginResult = serverFacade.login(request);
            System.out.println("Success! You are now logged in.");
            loginStatus = true;
        } catch (ResponseException e) {
            System.out.println("failed to login -> " + e.getMessage());
        }
    }

    private void runLogout(RegisterResults result){
        try {
            LogoutResults logout = serverFacade.logout(result);
            loginStatus = false;
            System.out.println("You have successfully logged out.");
        } catch (ResponseException e) {
            System.out.println(e.getMessage());
        }
    }

    private void runCreate(String gameName, RegisterResults result){
        try {
            listGame = serverFacade.listGames(loginResult);
            counter = listGame.games().size() + 1;
            GameData game = new GameData(counter, null, null, gameName, new ChessGame());
            this.createGame = serverFacade.createGame(game, result);
            System.out.println("Game has been successfully created!");
            counter++;
        } catch (ResponseException e) {
            System.out.println("Unable to create game -> " + e.getMessage());
        }
    }

    private void runJoin(JoinGameRequest join, RegisterResults request, String gameID){
        try {
            serverFacade.joinGame(join, loginResult);
            System.out.println("You have joined successfully!");
            listGame = serverFacade.listGames(loginResult);
            for (GameData currentGame : listGame.games()) {
                if (Objects.equals(String.valueOf(currentGame.gameID()), gameID)) {
                    webS.connect(request.authToken(), join.getTeamColor(), join.getGameID());
                    runGame.setAuth(request.authToken());
                    runGame.setGameID(join.getGameID());
                    runGame.setColor(join.getTeamColor());

                    runGame.runGame();
                }
            }
        } catch (ResponseException e) {
            System.out.println("failed to join game -> " + e.getMessage());
        } catch (ServerException | InvalidMoveException e) {
            throw new RuntimeException(e);
        }
    }

    private JoinGameRequest setTeamColor(String team, JoinGameRequest join){
        if (team.equals("WHITE")) {
            join.setTeamColor(ChessGame.TeamColor.WHITE);
        } else if (team.equals("BLACK")) {
            join.setTeamColor(ChessGame.TeamColor.BLACK);
        }
        return join;
    }

    private void observing(RegisterResults request, String gameID){
        try {
            boolean foundGame = false;
            GameData game = null;
            listGame = serverFacade.listGames(loginResult);
            for (GameData tempGame : listGame.games()) {
                if (Objects.equals(String.valueOf(tempGame.gameID()), gameID)) {
                    game = tempGame;
                    foundGame = true;
                }
            }
            if (!foundGame) {
                System.out.println("game does not exist");
                return;
            }
            webS.connect(request.authToken(), null, game.gameID());
            runGame.setAuth(request.authToken());
            runGame.setGameID(game.gameID());
            runGame.setAsObserver();
            runGame.setGame(game.game());
            runGame.runGame();
        } catch (ResponseException e) {
            System.out.println("unable to observe game -> " + e.getMessage());
        } catch (ServerException | InvalidMoveException e) {
            throw new RuntimeException(e);
        }
    }

    private void loginQuit(){
        try {
            LogoutResults logout = serverFacade.logout(loginResult);
            System.out.println("Thanks for playing!");
        } catch (ResponseException e) {
            System.out.println("failed to quit -> " + e.getMessage());
        }
        setStatus(false, false);
    }

    @Override
    public void updateGame(LoadGameM message){
        //update game here
        ChessGame game = message.getGame();
        //redraw board here?
        PrintingChessBoard print = new PrintingChessBoard();
        runGame.redraw(message.getGame());
    }


    @Override
    public void printMessage(ServerMessage message) {
        if(message.getServerMessageType() == ServerMessage.ServerMessageType.ERROR){
            ErrorM error = (ErrorM) message;
            System.out.println(EscapeSequences.SET_TEXT_ITALIC + EscapeSequences.SET_TEXT_COLOR_RED + error.getErrorMessage() + EscapeSequences.RESET_TEXT_COLOR + EscapeSequences.RESET_TEXT_BOLD_FAINT);
        }
        if(message.getServerMessageType() == ServerMessage.ServerMessageType.NOTIFICATION){
            NotificationM notif = (NotificationM) message;
            System.out.println(EscapeSequences.SET_TEXT_BOLD + EscapeSequences.SET_TEXT_COLOR_BLUE + notif.getMessage() + EscapeSequences.RESET_TEXT_COLOR + EscapeSequences.RESET_TEXT_BOLD_FAINT);
        }

    }
}
