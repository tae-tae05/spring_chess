package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMoves implements MovesCalculator{
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor current) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        Collection<ChessMove> moves = new ArrayList<>();
        int[][] possibilities = {{-2,1}, {-2,-1}, {2,-1}, {2,1}, {1, -2}, {1, 2}, {-1,2}, {-1, -2}};
        for(int[] possible: possibilities){
            int r = row + possible[0];
            int c = col + possible[1];
            if(isValid(r, c)){
                ChessPosition next = new ChessPosition(r, c);
                if(board.getPiece(next) == null || board.getPiece(next).getTeamColor() != current){
                    moves.add(new ChessMove(myPosition, next, null));
                }
            }

        }
        return moves;
    }

    @Override
    public Boolean isValid(int row, int col) {
        return row < 9 && row > 0 && col < 9 && col > 0;
    }
}
