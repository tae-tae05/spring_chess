package ui;

import chess.ChessBoard;
import chess.*;

import java.awt.*;

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

    public void printWhiteBoard(ChessGame game){
        ChessBoard board = game.getBoard();
        
        for(int r = 1; r < 9; r++){
            for(int c = 1; c < 9; c++){
//                ChessPiece piece = game.getBoard().getPiece(new ChessPosition(r, c));
//                if (piece == null) System.out.print(EscapeSequences.EMPTY);
//                else {
//                    switch (piece.getPieceType()) {
//                        case KING -> System.out.print(EscapeSequences.WHITE_KING);
//                        case QUEEN -> System.out.print(EscapeSequences.WHITE_QUEEN);
//                        case BISHOP -> System.out.print(EscapeSequences.WHITE_BISHOP);
//                        case KNIGHT -> System.out.print(EscapeSequences.WHITE_KNIGHT);
//                        case ROOK -> System.out.print(EscapeSequences.WHITE_ROOK);
//                        case PAWN -> System.out.print(EscapeSequences.WHITE_PAWN);
//                    }
//                }

                if((r+c)%2 == 0){
                    System.out.print(ANSI_BLACK + "██" + ANSI_RESET);
                }
                else{
                    System.out.print(ANSI_WHITE + "██" + ANSI_RESET);
                }
            }
            System.out.println("\n");
        }
    }

    public void printBlackBoard(ChessGame game){
        ChessBoard board = game.getBoard();
    }

    public void printOutlineText(ChessGame.TeamColor teamColor){

    }
}

