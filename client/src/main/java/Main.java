import chess.ChessGame;
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
        String url = "http://localhost:8080";
        ServerFacade serverFacade = new ServerFacade(url);
        boolean keepRunning = true;
        boolean loginStatus = false;
        System.out.println("Welcome to Chess. Type help to get started.");
        System.out.print("[LOGGED OUT] >>> ");
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
                        System.out.println("System will now close.");
                    }
                    case "help" -> {
                        help.printBeforeLogin();
                        System.out.print("[LOGGED OUT] >>> ");
                    }
                    case "register" -> {
                        UserData registerRequest;
                        if (inputs.length == 4) { // tokens are correct length
                            registerRequest = new UserData(inputs[1], inputs[2], inputs[3]);
                            try {
                                loginResult = serverFacade.register(registerRequest);
                                loginStatus = true;
                                System.out.println("You have successfully registered! Welcome.");
                                System.out.print("[LOGGED IN] >>> ");
                            } catch (ResponseException e) {
                                System.out.println(e.getMessage());
                                System.out.print("[LOGGED OUT] >>>");
                            }
                        }
                        else{
                            System.out.println("you have not provided enough information. type 'help' for specific commands.");
                            System.out.print("[LOGGED OUT] >>> ");
                        }
                    }
                    case "login" -> {
                        if(inputs.length == 3){
                            loginRequest = new LoginRequest(inputs[1], inputs[2]);
                            try {
                                loginResult = serverFacade.login(loginRequest);
                                System.out.println("Success! You are now logged in.");
                                System.out.print("[LOGGED IN] >>> ");
                                loginStatus = true;
                            }
                            catch (ResponseException e) {
                                System.out.println("failed to login -> " + e.getMessage());
                                System.out.print("[LOGGED OUT] >>> ");
                            }
                        }
                        else{
                            System.out.println("you have not provided enough information. type 'help' for specific commands.");
                            System.out.print("[LOGGED OUT] >>> ");
                        }
                    }
                    default -> {
                        System.out.println(nextStep + " is not a valid command, please type 'help' for a list of commands");
                        System.out.print("[LOGGED OUT] >>> ");
                    }

                }
            }
            else{
                switch (nextStep) {
                    case "logout" -> {
                        if(inputs.length == 1){
                            try {
                                LogoutResults logout = serverFacade.logout(loginResult);
                                loginStatus = false;
                                System.out.println("You have successfully logged out.");
                                System.out.print("[LOGGED OUT] >>> ");
                            }
                            catch (ResponseException e) {
                                System.out.println(e.getMessage());
                                System.out.print("[LOGGED IN] >>> ");
                            }
                        }
                        else {
                            System.out.println("input is incorrect. type 'help' for specific commands.");
                            System.out.print("[LOGGED IN] >>> ");
                        }
                    }
                    case "help" -> {
                        help.printAfterLogin();
                        System.out.print("[LOGGED IN] >>> ");
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
                            System.out.println("input was not correct. Enter help for specific commands");
                        }
                        System.out.print("[LOGGED IN] >>> ");
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
                                listGame = serverFacade.listGames(loginResult);
                                for(GameData currentGame: listGame.games()) {
                                    if (Objects.equals(String.valueOf(currentGame.gameID()), inputs[1])) {
                                        printBoard.printBoard(currentGame.game());
                                    }
                                }
                            } catch (ResponseException e) {
                                System.out.println("failed to join game -> " + e.getMessage());
                            }
                        }
                        else {
                            System.out.println("input was not correct. Enter help for specific commands");
                        }
                        System.out.print("[LOGGED IN] >>> ");
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
                            System.out.println("input was not correct. Enter help for specific commands");
                        }
                        System.out.print("[LOGGED IN] >>> ");

                    }
                    case "observe" -> {
                        if(inputs.length == 2) {
                            try {
                                boolean foundGame = false;
                                listGame = serverFacade.listGames(loginResult);
                                for(GameData game: listGame.games()){
                                    if(Objects.equals(String.valueOf(game.gameID()), inputs[1])){
                                        printBoard.printBoard(game.game());
                                        foundGame = true;
                                    }

                                }
                                if(!foundGame){
                                    System.out.println("game does not exist");
                                }
                            } catch (ResponseException e) {
                                System.out.println("unable to observe game -> " + e.getMessage());
                            }
                            System.out.print("[LOGGED IN] >>> ");
                        }
                    }
                    case "quit" -> {
                        try {
                            LogoutResults logout = serverFacade.logout(loginResult);
                            System.out.println("Thanks for playing!");
                        } catch (ResponseException e) {
                            System.out.println("failed to quit -> " + e.getMessage());
                            System.out.print("[LOGGED IN] >>> ");
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