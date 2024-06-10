package ui;

import chess.ChessBoard;
import chess.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PrintingChessBoard {
    Color blackPiece = new Color(0, 0, 0);
    Color whitePiece = new Color(255, 255, 255);
    Color lightSquares = new Color(223, 223, 223);
    Color darkSquares = new Color(91, 91, 91);
    Color outlineText = new Color(0, 0, 0);
    Color whiteTeamText = new Color(242, 89, 89);
    Color blackTeamText = new Color(0,0, 153);

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private static Map<Integer, String> letters = new HashMap<>();

    public PrintingChessBoard(){
        letters.put(0, "A");
        letters.put(1, "B");
        letters.put(2, "C");
        letters.put(3, "D");
        letters.put(4, "E");
        letters.put(5, "F");
        letters.put(6, "G");
        letters.put(7, "H");

    }

    public void printBoard(ChessGame game){
        ChessBoard board = game.getBoard();
        ChessGame.TeamColor turnColor = ChessGame.TeamColor.WHITE;
        printOutlineText(ChessGame.TeamColor.WHITE);
        String backgroundColor = "";
        System.out.print("\n");
        int whiteRow = 1;
        int blackRow = 8;
        for (int r = 1; r < 9; r++) {
            System.out.print(" " + whiteRow + " ");
            for (int c = 1; c < 9; c++) {
                if ((r + c) % 2 == 0) {
                    backgroundColor = EscapeSequences.SET_BG_COLOR_LIGHT_GREY;
                } else {
                    backgroundColor = EscapeSequences.SET_BG_COLOR_DARK_GREY;
                }
                ChessPiece piece = game.getBoard().getPiece(new ChessPosition(r, c));
                if (piece == null) {
                    System.out.print(backgroundColor + EscapeSequences.EMPTY + EscapeSequences.RESET_BG_COLOR);
                }
                else{
                    printPiece(piece, turnColor, backgroundColor);
                }
            }
            System.out.print(" " + whiteRow + " ");
            whiteRow++;
            System.out.print("\n");
        }
        printOutlineText(ChessGame.TeamColor.WHITE);
        System.out.println();
        printOutlineText(ChessGame.TeamColor.BLACK);
        System.out.print("\n");
        for (int r = 8; r > 0; r--) {
            System.out.print(" " + blackRow + " ");
            for (int c = 8; c > 0; c--) {
                if ((r + c) % 2 == 0) {
                    backgroundColor = EscapeSequences.SET_BG_COLOR_LIGHT_GREY;
                } else {
                    backgroundColor = EscapeSequences.SET_BG_COLOR_DARK_GREY;
                }
                ChessPiece piece = game.getBoard().getPiece(new ChessPosition(r, c));
                if (piece == null) {
                    System.out.print(backgroundColor + EscapeSequences.EMPTY + EscapeSequences.RESET_BG_COLOR);
                }
                else{
                    printPiece(piece, turnColor, backgroundColor);
                }
            }
            System.out.print(" " + blackRow + " ");
            blackRow--;
            System.out.print("\n");
        }
        printOutlineText(ChessGame.TeamColor.BLACK);
        System.out.println();
    }

    public void printPiece(ChessPiece piece, ChessGame.TeamColor color, String backgroundColor){
        if (piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
            switch (piece.getPieceType()) {
                case KING ->
                        System.out.print(backgroundColor + EscapeSequences.BLACK_KING + EscapeSequences.RESET_BG_COLOR);
                case QUEEN ->
                        System.out.print(backgroundColor + EscapeSequences.BLACK_QUEEN + EscapeSequences.RESET_BG_COLOR);
                case BISHOP ->
                        System.out.print(backgroundColor + EscapeSequences.BLACK_BISHOP + EscapeSequences.RESET_BG_COLOR);
                case KNIGHT ->
                        System.out.print(backgroundColor + EscapeSequences.BLACK_KNIGHT + EscapeSequences.RESET_BG_COLOR);
                case ROOK ->
                        System.out.print(backgroundColor + EscapeSequences.BLACK_ROOK + EscapeSequences.RESET_BG_COLOR);
                case PAWN ->
                        System.out.print(backgroundColor + EscapeSequences.BLACK_PAWN + EscapeSequences.RESET_BG_COLOR);
            }
        } else {
            switch (piece.getPieceType()) {
                case KING ->
                        System.out.print(backgroundColor + EscapeSequences.WHITE_KING + EscapeSequences.RESET_BG_COLOR);
                case QUEEN ->
                        System.out.print(backgroundColor + EscapeSequences.WHITE_QUEEN + EscapeSequences.RESET_BG_COLOR);
                case BISHOP ->
                        System.out.print(backgroundColor + EscapeSequences.WHITE_BISHOP + EscapeSequences.RESET_BG_COLOR);
                case KNIGHT ->
                        System.out.print(backgroundColor + EscapeSequences.WHITE_KNIGHT + EscapeSequences.RESET_BG_COLOR);
                case ROOK ->
                        System.out.print(backgroundColor + EscapeSequences.WHITE_ROOK + EscapeSequences.RESET_BG_COLOR);
                case PAWN ->
                        System.out.print(backgroundColor + EscapeSequences.WHITE_PAWN + EscapeSequences.RESET_BG_COLOR);
            }
        }
    }

    public void printOutlineText(ChessGame.TeamColor teamColor){
        System.out.print("   ");
        if(teamColor == ChessGame.TeamColor.WHITE){
            for(int i = 0; i < 8; i++) {
                System.out.print(" " + letters.get(i) + " ");
            }
        }
        else{
            for(int i = 7; i > -1; i--) {
                System.out.print(" " + letters.get(i) + " ");
            }
        }
    }
}

