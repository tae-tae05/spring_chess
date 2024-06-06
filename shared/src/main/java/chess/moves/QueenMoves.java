package chess.moves;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMoves implements MovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor current) {
        int row = myPosition.getRow();;
        int col = myPosition.getColumn();
        Collection<ChessMove> addMoves = queenMoves(row, col, 1, 1, board, myPosition, current);
        Collection<ChessMove> moves = new ArrayList<>(addMoves);
        moves.addAll(queenMoves(row, col, 1, -1, board, myPosition, current));
        moves.addAll(queenMoves(row, col, -1, 1, board, myPosition, current));
        moves.addAll(queenMoves(row, col, -1, -1, board, myPosition, current));
        moves.addAll(queenMoves(row, col, 1, 0, board, myPosition, current));
        moves.addAll(queenMoves(row, col, -1, 0, board, myPosition, current));
        moves.addAll(queenMoves(row, col, 0, 1, board, myPosition, current));
        moves.addAll(queenMoves(row, col, 0, -1, board, myPosition, current));
        return moves;
    }

    public Collection<ChessMove> queenMoves(int row, int col, int a, int b, ChessBoard board, ChessPosition thisPosition, ChessGame.TeamColor current){
        Collection<ChessMove> moves = new ArrayList<>();
        for(int i = 1; i < 9; i++){
            int q = a * i;
            int q1 = b * i;
            if(isValid(row+q, col+q1)){
                ChessPosition next = new ChessPosition(row+q, col+q1);
                if(board.getPiece(next) == null){
                    ChessMove queenMove = new ChessMove(thisPosition, next, null);
                    moves.add(queenMove);
                }
                else if(board.getPiece(next) != null) {
                    if (board.getPiece(next).getTeamColor() == current) {
                        break;
                    }
                    ChessMove queenMove = new ChessMove(thisPosition, next, null);
                    moves.add(queenMove);
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
