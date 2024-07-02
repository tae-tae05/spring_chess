package ui;

import chess.*;
import model.*;
import websocket.WebsocketFacade;

import java.rmi.ServerException;
import java.util.Scanner;

public class RunGame {
    ChessGame game;
    int gameID;
    WebsocketFacade webS;
    AuthData auth;

    public void runGame(){
        Scanner scanner = new Scanner(System.in);
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
//                case "move" -> {
//
//                }
//                case "leave" -> {
//
//                }
//                case "resign" -> {
//
//                }
//                case "connect" -> {
//
//                }
                default -> {
                    System.out.println(inputs[0] + " is not a valid command, please type 'help' for a list of commands");
                }
            }
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
