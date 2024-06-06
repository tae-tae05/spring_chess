package chess.moves;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMoves implements MovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor current) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        Collection<ChessMove> moves = new ArrayList<>();

        for(int i = 1; i < 9; i++){
            ChessMove tempMove = helper(row+i, col+i, board, myPosition);
            ChessMove stopper = stop(row+i, col+i, board, myPosition, current);
            if(tempMove != null){
                moves.add(tempMove);
            }
           else if(stopper != null){
               moves.add(stopper);
               break;
            }
           else{
               break;
            }
        }
        for(int i = 1; i < 9; i++){
            ChessMove tempMove = helper(row+i, col-i, board, myPosition);
            ChessMove stopper = stop(row+i, col-i, board, myPosition, current);
            if(tempMove != null){
                moves.add(tempMove);
            }
            else if(stopper != null){
                moves.add(stopper);
                break;
            }
            else{
                break;
            }
        }
        for(int i = 1; i < 9; i++) {
            ChessMove tempMove = helper(row - i, col + i, board, myPosition);
            ChessMove stopper = stop(row - i, col + i, board, myPosition, current);
            if (tempMove != null) {
                moves.add(tempMove);
            } else if (stopper != null) {
                moves.add(stopper);
                break;
            } else {
                break;
            }
        }
        for(int i = 1; i < 9; i++){
            ChessMove tempMove = helper(row-i, col-i, board, myPosition);
            ChessMove stopper = stop(row-i, col-i, board, myPosition, current);
            if(tempMove != null){ moves.add(tempMove);
            }
            else if(stopper != null){
                moves.add(stopper);
                break;
            }
            else{ break;
            }
        }
        return moves;
    }

    public ChessMove helper(int r, int c, ChessBoard board, ChessPosition myPosition) {
        if(isValid(r, c)){
            ChessPosition next = new ChessPosition(r, c);
            if(board.getPiece(next) == null){
                return new ChessMove(myPosition, next, null);
            }
        }
        return null;
    }

    public ChessMove stop(int r, int c, ChessBoard board, ChessPosition position, ChessGame.TeamColor current){
        if(isValid(r, c)){
            ChessPosition test = new ChessPosition(r, c);
            if(board.getPiece(test) != null && board.getPiece(test).getTeamColor() != current) {
                return new ChessMove(position, test, null);
            }
        }
        return null;
    }

    @Override
    public Boolean isValid(int row, int col) {
        return row < 9 && row > 0 && col < 9 && col > 0;
    }

}
