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
    public void printWhiteBoard(ChessGame game){
        ChessBoard board = game.getBoard();
        for(int r = 1; r < 9; r++){
            for(int c = 1; c < 9; c++){
                ChessPiece piece = game.getBoard().getPiece(new ChessPosition(r, c));
                if (piece == null) System.out.print(EscapeSequences.EMPTY);
                else {
                    switch (piece.getPieceType()) {
                        case KING -> System.out.print(EscapeSequences.WHITE_KING);
                        case QUEEN -> System.out.print(EscapeSequences.WHITE_QUEEN);
                        case BISHOP -> System.out.print(EscapeSequences.WHITE_BISHOP);
                        case KNIGHT -> System.out.print(EscapeSequences.WHITE_KNIGHT);
                        case ROOK -> System.out.print(EscapeSequences.WHITE_ROOK);
                        case PAWN -> System.out.print(EscapeSequences.WHITE_PAWN);
                    }
                }
            }
        }
    }

    public void printBlackBoard(ChessGame game){
        ChessBoard board = game.getBoard();
    }

    public void printOutlineText(ChessGame.TeamColor teamColor){

    }
}

