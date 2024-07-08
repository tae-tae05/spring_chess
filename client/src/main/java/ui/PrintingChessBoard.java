package ui;

import chess.ChessBoard;
import chess.*;
import model.GameData;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PrintingChessBoard {
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

    public void printHighlight(ChessGame game, ChessPosition startPosition, Collection<ChessPosition> highlightMoves){
        System.out.println("highlight spot");
        ChessGame.TeamColor turn = game.getBoard().getPiece(startPosition).getTeamColor();
        printOutlineText(turn);
        System.out.print("\n");
        if(turn == ChessGame.TeamColor.WHITE) {
            int white = 8;
            for (int r = 8; r > 0; r--) {
                System.out.print(" " + white + " ");
                for (int c = 1; c < 9; c++) {
                    ChessPosition start = new ChessPosition(r, c);
                    printHelper(r, c, game, turn, highlightMoves.contains(start), startPosition);
                }
                System.out.print(" " + white + " ");
                white--;
                System.out.print("\n");
            }
        }
        else{
            int black = 1;
            for (int r = 1; r < 9; r++) {
                System.out.print(" " + black + " ");
                for (int c = 8; c > 0; c--) {
                    ChessPosition start = new ChessPosition(r, c);
                    printHelper(r, c, game, turn, highlightMoves.contains(start), startPosition);
                }
                System.out.print(" " + black + " ");
                black++;
                System.out.print("\n");
            }
        }
        printOutlineText(turn);
        System.out.println();
    }

    public void printBoard(ChessGame game){
        ChessBoard board = game.getBoard();
        printWhite(game);
        printBlack(game);
    }

    public void printWhite(ChessGame game){
        ChessGame.TeamColor turnColor = ChessGame.TeamColor.WHITE;
        printOutlineText(ChessGame.TeamColor.WHITE);
        System.out.print("\n");
        int whiteRow = 8;

        for (int r = 8; r > 0; r--) {
            System.out.print(" " + whiteRow + " ");
            for (int c = 1; c < 9; c++) {
                printHelper(r, c, game, turnColor, false, null);
            }
            System.out.print(" " + whiteRow + " ");
            whiteRow--;
            System.out.print("\n");
        }
        printOutlineText(ChessGame.TeamColor.WHITE);
        System.out.println();
    }

    public void printBlack(ChessGame game){
        ChessGame.TeamColor turnColor = ChessGame.TeamColor.WHITE;
        System.out.print("\n");
        int blackRow = 1;
        printOutlineText(ChessGame.TeamColor.BLACK);
        System.out.print("\n");
        for (int r = 1; r < 9; r++) {
            System.out.print(" " + blackRow + " ");
            for (int c = 8; c > 0; c--) {
                printHelper(r, c, game, turnColor, false, null);
            }
            System.out.print(" " + blackRow + " ");
            blackRow++;
            System.out.print("\n");
        }
        printOutlineText(ChessGame.TeamColor.BLACK);
        System.out.println();
    }

    public void printHelper(int row, int col, ChessGame game, ChessGame.TeamColor turnColor, boolean highlight, ChessPosition startPosition){
        String backgroundColor = "";
        if(startPosition != null && startPosition.getRow() == row && startPosition.getColumn() == col){
            backgroundColor = EscapeSequences.SET_BG_COLOR_YELLOW;
        }
        else if ((row + col) % 2 != 0) {
            if(highlight) {
                backgroundColor = EscapeSequences.SET_BG_COLOR_GREEN;
            }
            else{
                backgroundColor = EscapeSequences.SET_BG_COLOR_LIGHT_GREY;
            }
        } else {
            if(highlight) {
                backgroundColor = EscapeSequences.SET_BG_COLOR_DARK_GREEN;
            }
            else{
                backgroundColor = EscapeSequences.SET_BG_COLOR_DARK_GREY;
            }
        }
        ChessPiece piece = game.getBoard().getPiece(new ChessPosition(row, col));
        if (piece == null) {
            System.out.print(backgroundColor + EscapeSequences.EMPTY + EscapeSequences.RESET_BG_COLOR);
        }
        else{
            printPiece(piece, turnColor, backgroundColor);
        }
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

