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

    public Collection<ChessMove> bishopMoves(int row, int col, int t, int y, ChessBoard board, ChessPosition position, ChessGame.TeamColor current){
        return board.findMoves(row, col, t, y, board, position, current);
    }

    @Override
    public Boolean isValid(int row, int col) {
        return row < 9 && row > 0 && col < 9 && col > 0;
    }

}
