package chess.moves;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class RookMoves implements MovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor current) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        Collection<ChessMove> moves = new ArrayList<>();
        moves.addAll(rookMoves(row, col, 1, 0, board, myPosition, current));
        moves.addAll(rookMoves(row, col, -1, 0, board, myPosition, current));
        moves.addAll(rookMoves(row, col, 0, 1, board, myPosition, current));
        moves.addAll(rookMoves(row, col, 0, -1, board, myPosition, current));
        return moves;
    }

    public Collection<ChessMove> rookMoves(int row, int col, int p, int j, ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor current){
        Collection<ChessMove> moves = new ArrayList<>();
        for(int i = 1; i < 9; i++){
            int b = p * i;
            int c = j * i;
            if(isValid(row+b, col+c)){
                ChessPosition next = new ChessPosition(row+b, col+c);
                if(board.getPiece(next) == null){
                    ChessMove rookMove = new ChessMove(myPosition, next, null);
                    moves.add(rookMove);
                }
                else if(board.getPiece(next) != null) {
                    if (board.getPiece(next).getTeamColor() == current) {
                        break;
                    }
                    ChessMove rookMove = new ChessMove(myPosition, next, null);
                    moves.add(rookMove);
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
