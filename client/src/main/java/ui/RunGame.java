package ui;

import chess.*;
import model.*;
import websocket.WebsocketClient;

import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Scanner;

public class RunGame {
    ServerFacade server;
    String url;
    ChessGame game;
    int gameID;
    WebsocketClient webS;
    String auth;
    Scanner scanner = new Scanner(System.in);
    ChessGame.TeamColor myColor;
    boolean observer = false;
    PrintingChessBoard boardPrinter = new PrintingChessBoard();

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

    public RunGame(ServerFacade server, String url, String auth, int gameID, ChessGame.TeamColor color, WebsocketClient webS, ChessGame game){
        this.server = server;
        this.url = url;
        this.auth = auth;
        this.gameID = gameID;
        this.myColor = color;
        this.webS = webS;
        if(myColor == null){
            observer = true;
        }
        this.game = game;
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
        boolean run = true;
        while(run){
            System.out.println("Type help for all options");
            String input = scanner.nextLine();
            var inputs = input.split(" ");
            switch(inputs[0]) {
                case "quit" ->{
                    try{
                        webS.leave(auth, gameID);
                        run = false;
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
                case "redraw" -> {
                    redraw();
                }
                default -> {
                    System.out.println(inputs[0] + " is not a valid command, please type 'help' for a list of commands");
                }
            }
        }
    }

    public void move() throws ServerException {
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

        ChessPosition startPosition = new ChessPosition(row, col);
        ChessPiece piece = board.getPiece(startPosition);

        while(game.validMoves(startPosition).isEmpty() || piece == null){
            System.out.println("You can't move that piece. Try again, what piece would you like to move?");
            row = getRow();
            col = getCol();
            startPosition = new ChessPosition(row, col);
            piece = board.getPiece(startPosition);
        }

        System.out.println("Where would you like to move it to?");
        int newRow = getRow();
        int newCol = getCol();
        ChessPosition end = new ChessPosition(newRow, newCol);

        Collection<ChessPosition> validMoves = endMoves(game.validMoves(startPosition));
        while(!validMoves.contains(end)){
            System.out.println("That's not a valid move. Where would you like to move that piece to?");
            newRow = getRow();
            newCol = getCol();
            end = new ChessPosition(row, col);
        }
        ChessPiece.PieceType promote = null;
        if(piece.getPieceType() == ChessPiece.PieceType.PAWN && (row == 8 || row == 1)){
            promote = getPromotion();
        }
        ChessMove move = new ChessMove(startPosition, end, promote);
        if(piece.getTeamColor() == ChessGame.TeamColor.WHITE){
            game.setTeamTurn(ChessGame.TeamColor.BLACK);
        }
        else{
            game.setTeamTurn(ChessGame.TeamColor.WHITE);
        }
        webS.makeMove(auth, gameID, move);
    }

    public Collection<ChessPosition> endMoves(Collection<ChessMove> moves){
        Collection<ChessPosition> possibilities = new ArrayList<>();
        for(ChessMove move: moves){
            possibilities.add(move.getEndPosition());
        }
        return possibilities;
    }

    public ChessPiece.PieceType getPromotion(){
        System.out.println("What would you like to promote your pawn to?\nq -> queen\nr -> rook\nk -> knight\nb -> bishop");
        String piece = scanner.nextLine();
        switch (piece) {
            case "q" -> {
                return ChessPiece.PieceType.QUEEN;
            }
            case "r" -> {
                return ChessPiece.PieceType.ROOK;
            }
            case "k" -> {
                return ChessPiece.PieceType.KNIGHT;
            }
            case "b" -> {
                return ChessPiece.PieceType.BISHOP;
            }
            default -> {
                System.out.println("Not valid. Try again!");
                return getPromotion();
            }
        }
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
        webS.resign(auth, gameID, myColor);
    }

    public void leave(){
        try{
            webS.leave(auth, gameID);
        } catch (ServerException e) {
            System.out.println("failed to leave game");
        }
    }
    public void redraw(){
        printBoard();
    }

    public void printBoard(){
        if(myColor == ChessGame.TeamColor.BLACK){
            printBlack();
        }
        else{
            printWhite();
        }
    }

    public void printWhite(){
        boardPrinter.printWhite(game);
    }

    public void printBlack(){
        boardPrinter.printBlack(game);
    }

    public void printHelp(){
        String help = """
                redraw - see the board
                leave - exit the game
                move - make your move
                help - see all commands
                quit - exit""";
        System.out.println(help);
    }

    public void setAuth(String auth){
        this.auth = auth;
    }
    public void setColor(ChessGame.TeamColor color){
        this.myColor = color;
    }
    public void setGameID(int gameID){
        this.gameID = gameID;
    }

    public void setGame(ChessGame game){
        this.game = game;
    }
}
