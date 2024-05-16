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
            if (isValid(row+i, col+i)){
                ChessPosition next = new ChessPosition(row+i, col+i);
                if(board.getPiece(next) == null){
                    moves.add(new ChessMove(myPosition, next,null));
                }
                else if(board.getPiece(next).getTeamColor() != current){
                    moves.add(new ChessMove(myPosition, next,null));
                    break;
                }
                else{
                    break;
                }
            }
            else{
                break;
            }
        }
        for(int i = 1; i < 9; i++){
            if (isValid(row+i, col-i)){
                ChessPosition next = new ChessPosition(row+i, col-i);
                if(board.getPiece(next) == null){
                    moves.add(new ChessMove(myPosition, next,null));
                }
                else if(board.getPiece(next).getTeamColor() != current){
                    moves.add(new ChessMove(myPosition, next,null));
                    break;
                }
                else{
                    break;
                }
            }
            else{
                break;
            }
        }
        for(int i = 1; i < 9; i++){
            if (isValid(row-i, col+i)){
                ChessPosition next = new ChessPosition(row-i, col+i);
                if(board.getPiece(next) == null){
                    moves.add(new ChessMove(myPosition, next,null));
                }
                else if(board.getPiece(next).getTeamColor() != current){
                    moves.add(new ChessMove(myPosition, next,null));
                    break;
                }
                else{
                    break;
                }
            }
            else{
                break;
            }
        }
        for(int i = 1; i < 9; i++){
            if (isValid(row-i, col-i)){
                ChessPosition next = new ChessPosition(row-i, col-i);
                if(board.getPiece(next) == null){
                    moves.add(new ChessMove(myPosition, next,null));
                }
                else if(board.getPiece(next).getTeamColor() != current){
                    moves.add(new ChessMove(myPosition, next,null));
                    break;
                }
                else{
                    break;
                }
            }
            else{
                break;
            }
        }
        return moves;
    }

    @Override
    public Boolean isValid(int row, int col) {
        return row < 9 && row > 0 && col < 9 && col > 0;
    }
}
