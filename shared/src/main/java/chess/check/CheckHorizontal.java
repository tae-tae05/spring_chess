package chess.check;

import chess.*;

public class CheckHorizontal implements Check {
    public Boolean checkDirection(ChessGame.TeamColor color, int row, int col, ChessBoard board) {
        for (int i = col - 1; i > 0; i--) {
            if (isValid(row, i)) {
                ChessPiece current = board.getPiece(new ChessPosition(row, i));
                if (current != null) {
                    if (current.getPieceType() == ChessPiece.PieceType.QUEEN || current.getPieceType() == ChessPiece.PieceType.ROOK) {
                        return current.getTeamColor() != color;
                    }
                }
            }
        }
        for (int j = col + 1; j < 9; j++) {
            if (isValid(row, j)) {
                ChessPiece piece = board.getPiece(new ChessPosition(row, j));
                if (piece != null) {
                    if (piece.getPieceType() == ChessPiece.PieceType.ROOK || piece.getPieceType() == ChessPiece.PieceType.QUEEN) {
                        return piece.getTeamColor() != color;
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
