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
        return board.findMoves(row, col, a, b, board, thisPosition, current);
    }
    @Override
    public Boolean isValid(int row, int col) {
        return row < 9 && row > 0 && col < 9 && col > 0;
    }
}
