package chess.check;

import chess.*;

public class CheckHorizontal implements Check {
    public Boolean checkDirection(ChessGame.TeamColor color, int row, int col, ChessBoard board){
        for(int i = col - 1; i > 0; i--){
            if(isValid(row, i)) {
                ChessPiece current = board.getPiece(new ChessPosition(row, i));
                if (current != null) {
                    if (current.getPieceType() == ChessPiece.PieceType.QUEEN || current.getPieceType() == ChessPiece.PieceType.ROOK) {
                        return current.getTeamColor() != color;
                    }
                }
            }
        }
        for(int i = col + 1; i < 9; i++){
            if(isValid(row, i)) {
                ChessPiece current = board.getPiece(new ChessPosition(row, i));
                if (current != null) {
                    if (current.getPieceType() == ChessPiece.PieceType.QUEEN || current.getPieceType() == ChessPiece.PieceType.ROOK) {
                        return current.getTeamColor() != color;
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
