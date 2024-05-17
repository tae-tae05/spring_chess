package chess.Moves;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class KingMoves implements MovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor current) {
        int row = myPosition.getRow() - 2;
        int col = myPosition.getColumn() - 2;
        Collection<ChessMove> moves = new ArrayList<>();
        for(int i = 1; i < 4; i++){
            if(isValid(row + 1, col+i)){
                ChessPosition next = new ChessPosition(row+1, col+i);
                if(board.getPiece(next) == null){
                    moves.add(new ChessMove(myPosition, next, null));
                }
                else if(board.getPiece(next).getTeamColor() != current){
                    moves.add(new ChessMove(myPosition, next, null));
                }
            }
            if(isValid(row+3, col+1)){
                ChessPosition next = new ChessPosition(row+3, col+i);
                if(board.getPiece(next) == null){
                    moves.add(new ChessMove(myPosition, next, null));
                }
                else if(board.getPiece(next).getTeamColor() != current){
                    moves.add(new ChessMove(myPosition, next, null));
                }
            }
        }
        if(isValid(row+2, col+1)){
            ChessPosition next = new ChessPosition(row+2, col+1);
            if(board.getPiece(next) == null || board.getPiece(next).getTeamColor() != current){
                moves.add(new ChessMove(myPosition, next, null));
            }
        }
        if(isValid(row+2, col+3)){
            ChessPosition next = new ChessPosition(row+2, col+3);
            if(board.getPiece(next) == null || board.getPiece(next).getTeamColor() != current){
                moves.add(new ChessMove(myPosition, next, null));
            }
        }
        return moves;
    }

    @Override
    public Boolean isValid(int row, int col) {
        return row < 9 && row > 0 && col < 9 && col > 0;
    }
}
