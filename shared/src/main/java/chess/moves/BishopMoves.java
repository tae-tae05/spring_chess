package chess.moves;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMoves implements MovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor current) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        Collection<ChessMove> addMoves = bishopMoves(row, col, 1, 1, board, myPosition, current);
        Collection<ChessMove> moves = new ArrayList<>(addMoves);
        Collection<ChessMove> addMoves1 = bishopMoves(row, col, 1, -1, board, myPosition, current);
        moves.addAll(addMoves1);
        Collection<ChessMove> addMoves2 = bishopMoves(row, col, -1, 1, board, myPosition, current);
        moves.addAll(addMoves2);
        Collection<ChessMove> addMoves3 = bishopMoves(row, col, -1, -1, board, myPosition, current);
        moves.addAll(addMoves3);
        return moves;
    }

    public Collection<ChessMove> bishopMoves(int row, int col, int p, int j, ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor current){
        Collection<ChessMove> moves = new ArrayList<>();
        for(int i = 1; i < 9; i++){
            int a = p * i;
            int b = j * i;
            if(isValid(row+a, col+b)){
                ChessPosition next = new ChessPosition(row+a, col+b);
                if(board.getPiece(next) == null){
                    ChessMove bishopMove = new ChessMove(myPosition, next, null);
                    moves.add(bishopMove);
                }
                else if(board.getPiece(next) != null) {
                    if (board.getPiece(next).getTeamColor() == current) {
                        break;
                    }
                    ChessMove bishopMove = new ChessMove(myPosition, next, null);
                    moves.add(bishopMove);
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
