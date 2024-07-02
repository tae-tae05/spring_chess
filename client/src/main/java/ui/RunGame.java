package ui;

import chess.*;
import model.*;
import websocket.WebsocketFacade;

import java.rmi.ServerException;
import java.util.Map;
import java.util.Scanner;

public class RunGame {
    ServerFacade server;
    String url;
    ChessGame game;
    int gameID;
    WebsocketFacade webS;
    AuthData auth;
    Scanner scanner = new Scanner(System.in);
    ChessGame.TeamColor myColor;
    boolean observer = false;

    private final Map<Character, Integer> numbers = Map.of(
            'a', 1,
            'b', 2,
            'c', 3,
            'd', 4,
            'e', 5,
            'f', 6,
            'g', 7,
            'h', 8
    );

    public RunGame(ServerFacade server, String url, AuthData auth, int gameID, ChessGame.TeamColor color, WebsocketFacade webS){
        this.server = server;
        this.url = url;
        this.auth = auth;
        this.gameID = gameID;
        this.myColor = color;
        this.webS = webS;
        if(myColor == null){
            observer = true;
        }
    }

    private int getRow(){
        System.out.print("Row = ");
        String r = scanner.nextLine();
        int row = Integer.parseInt(r);
        if(row > 0 && row < 9){
            return row;
        }
        else{
            System.out.println("Enter a correct number");
            return getRow();
        }
    }

    private int getCol(){
        System.out.print("Col = ");
        String c = scanner.nextLine();
        Integer col = null;
        if(numbers.containsKey(c.charAt(0))) {
            col = numbers.get(c.charAt(0));
            if(col > 0 && col < 9){
                return col;
            }
            else{
                System.out.println("Enter a correct letter within range");
                return getCol();
            }
        }
        else{
            System.out.println("Enter a correct letter.");
            return getCol();
        }
    }
    public void runGame() throws ServerException {
        while(true){
            System.out.println("Type help for all options");
            String input = scanner.nextLine();
            var inputs = input.split(" ");
            switch(inputs[0]) {
                case "quit" ->{
                    try{
                        webS.leave(auth.authToken(), gameID);
                    } catch (ServerException e) {
                        System.out.println("failed to quit game, connection issues?");
                    }
                }
                case "help" -> {
                    printHelp();
                }
                case "move" -> {
                    move();
                }
                case "leave" -> {
                    leave();
                }
                case "resign" -> {
                    resign();
                }
                default -> {
                    System.out.println(inputs[0] + " is not a valid command, please type 'help' for a list of commands");
                }
            }
        }
    }

    public void move(){
        if(observer){
            return;
        }
        if(game.getGameOverStatus()){
            System.out.println("game over. you can't make more moves");
            return;
        }
        if(game.getTeamTurn() != myColor){
            System.out.println("It's not your turn");
            return;
        }
        ChessBoard board = game.getBoard();
        System.out.println("What piece would you like to move?");
        int row = getRow();
        int col = getCol();

    }


    public void resign() throws ServerException {
        System.out.println("Are you sure you want to resign? Type y for yes, n for no");
        String answer = scanner.nextLine();
        if(!answer.equals("y")){
            return;
        }
        if(myColor == ChessGame.TeamColor.WHITE){
            game.setWinner(ChessGame.TeamColor.BLACK);
        }
        else if(myColor == ChessGame.TeamColor.BLACK){
            game.setWinner(ChessGame.TeamColor.WHITE);
        }
        game.setGameOver();
        webS.resign(auth.authToken(), gameID, myColor);
    }

    public void leave(){
        try{
            webS.leave(auth.authToken(), gameID);
        } catch (ServerException e) {
            System.out.println("failed to leave game");
        }
    }

    public void printHelp(){
        String help = """
                redraw - see the board
                leave - exit the game
                move - make your move
                help - see all commands""";
        System.out.println(help);
    }
}
