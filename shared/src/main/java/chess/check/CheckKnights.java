package chess.check;

import chess.*;

public class CheckKnights implements Check {
    public Boolean checkDirection(ChessGame.TeamColor color, int row, int col, ChessBoard board){
        int[][] possibilities = { {-2, 1}, {-2, -1}, {1,-2}, {-1, -2}, {1,2}, {-1, 2},
                {2, 1}, {2, -1}};
        for (int[] possible: possibilities) {
            int r = row + possible[0];
            int c = col + possible[1];
            if(isValid(r, c)){
                ChessPiece current = board.getPiece(new ChessPosition(r, c));
                if (current != null && (current.getPieceType() == ChessPiece.PieceType.KNIGHT)) {
                    if(current.getTeamColor() != color){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public Boolean isValid(int row, int col){
        return row > 0 && row < 9 && col > 0 && col < 9;
    }
}
