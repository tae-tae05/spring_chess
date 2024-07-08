package chess.moves;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMoves implements MovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor current) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        Collection<ChessMove> addMoves = board.findMoves(row, col, 1, 1, board, myPosition, current);
        Collection<ChessMove> moves = new ArrayList<>(addMoves);
        Collection<ChessMove> addMoves1 = board.findMoves(row, col, 1, -1, board, myPosition, current);
        moves.addAll(addMoves1);
        Collection<ChessMove> addMoves2 = board.findMoves(row, col, -1, 1, board, myPosition, current);
        moves.addAll(addMoves2);
        Collection<ChessMove> addMoves3 = board.findMoves(row, col, -1, -1, board, myPosition, current);
        moves.addAll(addMoves3);
        return moves;
    }


    @Override
    public Boolean isValid(int row, int col) {
        return row < 9 && row > 0 && col < 9 && col > 0;
    }

}
