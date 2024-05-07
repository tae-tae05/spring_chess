package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMoves implements MovesCalculator{
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor current) {
        Collection<ChessMove> moves = new ArrayList<>();
        return moves;
    }

    @Override
    public Boolean isValid(int row, int col) {
        return row < 9 && row > 0 && col < 9 && col > 0;
    }
}
