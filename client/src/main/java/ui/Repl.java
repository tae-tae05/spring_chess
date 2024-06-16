package ui;

import chess.ChessGame;
import model.GameData;
import model.UserData;
import request.JoinGameRequest;
import request.LoginRequest;
import results.CreateGameResults;
import results.ListGameResults;
import results.LogoutResults;
import results.RegisterResults;

import java.util.Objects;
import java.util.Scanner;

public class Repl {
    private PrintingChessBoard printBoard = new PrintingChessBoard();
    private PrintListResults printingLists = new PrintListResults();
    private PrintingHelp help = new PrintingHelp();
    private RegisterResults loginResult;
    private LoginRequest loginRequest;
    private CreateGameResults createGame;
    private ListGameResults listGame;
    private UserData registerRequest;
    private ServerFacade serverFacade;

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

    public Repl(){
        loginResult = null;
        loginRequest = null;
        createGame = null;
        listGame = null;
        registerRequest = null;
        serverFacade = null;
        loginStatus = false;
        keepRunning = true;

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
                            joinRequest = setTeamColor(inputs[2],joinRequest);
                            runJoin(joinRequest, loginResult, inputs[1]);
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
                    printBoard.printBoard(currentGame.game());
                }
            }
        } catch (ResponseException e) {
            System.out.println("failed to join game -> " + e.getMessage());
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
            listGame = serverFacade.listGames(loginResult);
            for (GameData game : listGame.games()) {
                if (Objects.equals(String.valueOf(game.gameID()), gameID)) {
                    printBoard.printBoard(game.game());
                    foundGame = true;
                }
            }
            if (!foundGame) {
                System.out.println("game does not exist");
            }
        } catch (ResponseException e) {
            System.out.println("unable to observe game -> " + e.getMessage());
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
}
