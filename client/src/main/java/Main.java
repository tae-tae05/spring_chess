import chess.ChessGame;
import com.mysql.cj.log.Log;
import model.*;
import request.*;
import results.*;
import server.Server;
import ui.*;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ResponseException {
        PrintingChessBoard printBoard = new PrintingChessBoard();
        PrintListResults printingLists = new PrintListResults();
        PrintingHelp help = new PrintingHelp();
        RegisterResults loginResult = null;
        LoginRequest loginRequest;
        CreateGameResults createGame = new CreateGameResults();
        ListGameResults listGame = new ListGameResults(null, null);
        Server server = new Server();
        var port = server.run(0);
        ServerFacade serverFacade = new ServerFacade("http://localhost:" + port);
        boolean keepRunning = true;
        boolean loginStatus = false;
        System.out.println("Welcome to Chess. Type help to get started.");
        Scanner scanner = new Scanner(System.in);
        while (keepRunning) {
            //getting input and splitting it
            String input = scanner.nextLine();
            var inputs = input.split(" ");
            var nextStep = inputs[0];
            //set up needed info
            if(!loginStatus){
                switch (nextStep) {
                    case "quit" -> {
                        keepRunning = false;
                    }
                    case "help" -> {
                        help.printBeforeLogin();
                    }
                    case "register" -> {
                        UserData registerRequest = new UserData(null, null, null);
                        if (inputs.length == 4) { // tokens are correct length
                            registerRequest = new UserData(inputs[1], inputs[2], inputs[3]);
                            try {
                                loginResult = serverFacade.register(registerRequest);
                                serverFacade.register(registerRequest);
                                loginStatus = true;
                                System.out.println("You have successfully registered! Welcome.");

                            } catch (ResponseException e) {
                                System.out.println("could not get results -> " + e.getMessage());
                            }
                        }
                        else{
                            System.out.println("you have not provided enough information. type 'help' for specific commands.");
                        }
                    }
                    case "login" -> {
                        if(inputs.length == 3){
                            loginRequest = new LoginRequest(inputs[1], inputs[2]);
                            try {
                                loginResult = serverFacade.login(loginRequest);
                                System.out.println("Success! You are now logged in.");
                                loginStatus = true;
                            }
                            catch (ResponseException e) {
                                System.out.println("failed to login -> " + e.getMessage());
                            }
                        }
                        else{
                            System.out.println("you have not provided enough information. type 'help' for specific commands.");
                        }
                    }
                    default -> {
                        System.out.println(nextStep + " is not a valid command, please type 'help' for a list of commands");
                    }

                }
            }
            else{
                switch (nextStep) {
                    case "logout" -> {
                        if(inputs.length == 1){
                            try {
                                LogoutAndJoinResults logout = serverFacade.logout(loginResult);
                                loginStatus = false;
                                System.out.println("You have successfully logged out.");
                            }
                            catch (ResponseException e) {
                                System.out.println("logout error ->" + e.getMessage());
                            }
                        }
                        else {
                            System.out.println("input is incorrect. type 'help' for specific commands.");
                        }
                    }
                    case "help" -> {
                        help.printAfterLogin();
                    }
                    case "create" -> {
                        GameData game = new GameData(null, null, null, null, new ChessGame());
                        if(inputs.length == 2){
                            game = game.setGameName(inputs[1]);
                            try{
                                createGame = serverFacade.createGame(game, loginResult);
                            } catch (ResponseException e) {
                                System.out.println("Unable to create game -> " + e.getMessage());
                            }
                            System.out.println("Game has succesfully been created. The game id is " + createGame.getGameID());
                        }
                        else {
                            System.out.println("input was not current. Enter help for specific commands");
                        }
                    }
                    case "join" -> {
                        JoinGameRequest joinRequest = new JoinGameRequest();
                        if(inputs.length == 3){
                            joinRequest.setGameID(Integer.valueOf(inputs[1]));
                            if(inputs[2].equals("WHITE")){
                                joinRequest.setTeamColor(ChessGame.TeamColor.WHITE);
                            }
                            else if (inputs[2].equals("BLACK")){
                                joinRequest.setTeamColor(ChessGame.TeamColor.BLACK);
                            }
                            try{
                                serverFacade.joinGame(joinRequest, loginResult);
                                System.out.println("You have joined successfully!");
                            } catch (ResponseException e) {
                                System.out.println("failed to join game -> " + e.getMessage());
                            }
                        }
                        else {
                            System.out.println("input was not current. Enter help for specific commands");
                        }
                    }
                    case "list" -> {
                        if(inputs.length == 1){
                            try {
                                listGame = serverFacade.listGames(loginResult);
                                printingLists.printGames(listGame);
                            }
                            catch (ResponseException e){
                                System.out.println("unable to list games -> " + e.getMessage());
                            }
                        }
                        else {
                            System.out.println("input was not current. Enter help for specific commands");
                        }

                    }
                    case "observe" -> {
                        if(inputs.length == 2) {
                            try {
                                boolean foundGame = false;
                                listGame = serverFacade.listGames(loginResult);
                                for(GameData game: listGame.games()){
                                    if(Objects.equals(String.valueOf(game.gameID()), inputs[1])){
                                        System.out.println("found a matching board");
                                        printBoard.printWhiteBoard(game.game());
                                        foundGame = true;
                                    }

                                }
                                if(!foundGame){
                                    System.out.println("game does not exist");
                                }
                            } catch (ResponseException e) {
                                System.out.println("unable to observe game -> " + e.getMessage());
                            }
                        }
                    }
                    case "quit" -> {
                        try {
                            LogoutAndJoinResults logout = serverFacade.logout(loginResult);
                        } catch (ResponseException e) {
                            System.out.println("failed to quit -> " + e.getMessage());
                            System.out.print(">>> ");
                        }
                        loginStatus = false;
                        keepRunning = false;
                    }
                    default -> {
                        System.out.println(nextStep + " is not a valid command, please type 'help' for a list of commands");
                    }
                }
            }
        }
        System.exit(0);
    }
}