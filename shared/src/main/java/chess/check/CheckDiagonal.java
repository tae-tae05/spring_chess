package chess.check;

import chess.*;
public class CheckDiagonal implements Check{
    public Boolean checkDirection(ChessGame.TeamColor color, int row, int col, ChessBoard board){
        boolean direction1 = checkDiagonal(1, 1, color, row, col, board);
        boolean direction2 = checkDiagonal(1, -1, color, row, col, board);
        boolean direction3 = checkDiagonal(-1, 1, color, row, col, board);
        boolean direction4 = checkDiagonal(-1, -1, color, row, col, board);
        return direction1 || direction2 || direction3 || direction4;
    }

    public boolean checkDiagonal(int a, int b, ChessGame.TeamColor current, int r, int c, ChessBoard board){
        for(int i = 1; i < 9; i++) {
            int d = a * i;
            int g = b * i;
            if (isValid(r+d, c+g)) {
                ChessPiece spot = board.getPiece(new ChessPosition(r+d, c+g));
                if (spot != null) {
                    if (spot.getPieceType() == ChessPiece.PieceType.BISHOP || spot.getPieceType() == ChessPiece.PieceType.QUEEN) {
                        return spot.getTeamColor() != current;
                    } else if (spot.getTeamColor() == current) {
                        break;
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
