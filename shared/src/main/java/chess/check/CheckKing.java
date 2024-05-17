package chess.check;

import chess.*;
public class CheckKing implements Check{
    public Boolean checkDirection(ChessGame.TeamColor color, int row, int col, ChessBoard board){
        for(int r = row-1; r < row+2; r++){
            for(int c = col-1; c < col+2; c++){
                if(isValid(r, c)) {
                    ChessPiece current = board.getPiece(new ChessPosition(r, c));
                    if (current != null && (current.getPieceType() == ChessPiece.PieceType.KING && current.getTeamColor() != color)) {
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
