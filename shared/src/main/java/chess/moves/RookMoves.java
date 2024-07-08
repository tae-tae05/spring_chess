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
        return board.findMoves(row, col, p, j, board, myPosition, current);
    }
    @Override
    public Boolean isValid(int row, int col) {
        return row < 9 && row > 0 && col < 9 && col > 0;
    }
}
