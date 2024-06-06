package chess.moves;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMoves implements MovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor current) {
        int row = myPosition.getRow();;
        int col = myPosition.getColumn();
        Collection<ChessMove> addMoves = tempMoves(row, col, 1, 1, board, myPosition, current);
        Collection<ChessMove> moves = new ArrayList<>(addMoves);
        moves.addAll(tempMoves(row, col, 1, -1, board, myPosition, current));
        moves.addAll(tempMoves(row, col, -1, 1, board, myPosition, current));
        moves.addAll(tempMoves(row, col, -1, -1, board, myPosition, current));
        moves.addAll(tempMoves(row, col, 1, 0, board, myPosition, current));
        moves.addAll(tempMoves(row, col, -1, 0, board, myPosition, current));
        moves.addAll(tempMoves(row, col, 0, 1, board, myPosition, current));
        moves.addAll(tempMoves(row, col, 0, -1, board, myPosition, current));
        return moves;
    }

    public Collection<ChessMove> tempMoves(int row, int col, int p, int j, ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor current){
        Collection<ChessMove> moves = new ArrayList<>();
        for(int i = 1; i < 9; i++){
            int a = p * i;
            int b = j * i;
            if(isValid(row+a, col+b)){
                ChessPosition next = new ChessPosition(row+a, col+b);
                if(board.getPiece(next) == null){
                    ChessMove tempMove = new ChessMove(myPosition, next, null);
                    moves.add(tempMove);
                }
                else if(board.getPiece(next) != null) {
                    if (board.getPiece(next).getTeamColor() == current) {
                        break;
                    }
                    ChessMove tempMove = new ChessMove(myPosition, next, null);
                    moves.add(tempMove);
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
