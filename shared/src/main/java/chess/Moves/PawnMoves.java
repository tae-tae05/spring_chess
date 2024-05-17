package chess.Moves;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMoves implements MovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor current) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        Collection<ChessMove> moves = new ArrayList<>();
        if(current == ChessGame.TeamColor.WHITE){
            //check diagonals
            for(int i = col - 1; i < col + 2; i+= 2)
            {
                if(isValid(row + 1, i)){
                    ChessPosition next = new ChessPosition(row+1, i);
                    if(row + 1 == 8){
                        if (board.getPiece(next) != null && board.getPiece(next).getTeamColor() != current) {
                            moves.add(new ChessMove(myPosition, next, ChessPiece.PieceType.ROOK));
                            moves.add(new ChessMove(myPosition, next, ChessPiece.PieceType.QUEEN));
                            moves.add(new ChessMove(myPosition, next, ChessPiece.PieceType.KNIGHT));
                            moves.add(new ChessMove(myPosition, next, ChessPiece.PieceType.BISHOP));
                        }
                    }
                    //check if there are enemy diagonals
                    else {
                        if (board.getPiece(next) != null && board.getPiece(next).getTeamColor() != current) {
                            moves.add(new ChessMove(myPosition, next, null));
                        }
                    }
                }
            }
            //check straight point
            if(isValid(row +1, col) && board.getPiece(new ChessPosition(row+1, col)) == null){
                if(row == 7){
                    moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col), ChessPiece.PieceType.ROOK));
                    moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col), ChessPiece.PieceType.QUEEN));
                    moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col), ChessPiece.PieceType.KNIGHT));
                    moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col), ChessPiece.PieceType.BISHOP));
                }
                else {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col), null));
                }
            }
            //adds the extra move if it's at the beginning
            if(row == 2 && board.getPiece(new ChessPosition(row + 2, col)) == null && board.getPiece(new ChessPosition(row + 1, col)) == null)
            {
                moves.add(new ChessMove(myPosition, new ChessPosition(row + 2, col), null));
            }

        }
        else{
            //check diagonals
            for(int i = col - 1; i < col + 2; i+= 2)
            {
                if(isValid(row - 1, i)){
                    ChessPosition next = new ChessPosition(row-1, i);
                    //check if there are enemy diagonals
                    if(row - 1 == 1){
                        if (board.getPiece(next) != null && board.getPiece(next).getTeamColor() != current) {
                            moves.add(new ChessMove(myPosition, next, ChessPiece.PieceType.ROOK));
                            moves.add(new ChessMove(myPosition, next, ChessPiece.PieceType.QUEEN));
                            moves.add(new ChessMove(myPosition, next, ChessPiece.PieceType.KNIGHT));
                            moves.add(new ChessMove(myPosition, next, ChessPiece.PieceType.BISHOP));
                        }
                    }
                    else {
                        if (board.getPiece(next) != null && board.getPiece(next).getTeamColor() != current) {
                            moves.add(new ChessMove(myPosition, next, null));
                        }
                    }
                }
            }
            //check straight point
            if(isValid(row - 1, col) && board.getPiece(new ChessPosition(row - 1, col)) == null){
                if(row == 2){
                    moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col), ChessPiece.PieceType.ROOK));
                    moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col), ChessPiece.PieceType.QUEEN));
                    moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col), ChessPiece.PieceType.KNIGHT));
                    moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col), ChessPiece.PieceType.BISHOP));
                }
                else {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col), null));
                }
            }
            //adds the extra move if it's at the beginning
            if(row == 7 && board.getPiece(new ChessPosition(row - 2, col)) == null && board.getPiece(new ChessPosition(row - 1, col)) == null)
            {
                moves.add(new ChessMove(myPosition, new ChessPosition(row - 2, col), null));
            }

        }
        return moves;
    }

    @Override
    public Boolean isValid(int row, int col) {
        return row < 9 && row > 0 && col < 9 && col > 0;
    }
}
