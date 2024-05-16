package chess.check;

import chess.*;

public class CheckDiagonal implements Check {
    public Boolean checkDirection(ChessGame.TeamColor color, int row, int col, ChessBoard board){
        for(int i = 1; i < 9; i++){
            if(isValid(row + i, col + i)){
                ChessPiece current = board.getPiece(new ChessPosition(row + i, col + i));
                if(current != null){
                    if(current.getPieceType() == ChessPiece.PieceType.ROOK || current.getPieceType() == ChessPiece.PieceType.QUEEN){
                        return current.getTeamColor() != color;
                    }
                    else if(current.getTeamColor() == color){
                        return false;
                    }
                }
            }
            if(isValid(row + i, col - i)){
                ChessPiece current = board.getPiece(new ChessPosition(row + i, col - i));
                if(current != null){
                    if(current.getPieceType() == ChessPiece.PieceType.BISHOP || current.getPieceType() == ChessPiece.PieceType.QUEEN){
                        return current.getTeamColor() != color;
                    }
                    else if(current.getTeamColor() == color){
                        return false;
                    }
                }
            }
            if(isValid(row - i, col + i)){
                ChessPiece current = board.getPiece(new ChessPosition(row - i, col + i));
                if(current != null){
                    if(current.getPieceType() == ChessPiece.PieceType.BISHOP || current.getPieceType() == ChessPiece.PieceType.QUEEN){
                        return current.getTeamColor() != color;
                    }
                    else if(current.getTeamColor() == color){
                        return false;
                    }
                }
            }
            if(isValid(row - i, col - i)){
                ChessPiece current = board.getPiece(new ChessPosition(row - i, col - i));
                if(current != null){
                    if(current.getPieceType() == ChessPiece.PieceType.BISHOP || current.getPieceType() == ChessPiece.PieceType.QUEEN){
                        return current.getTeamColor() != color;
                    }
                    else if(current.getTeamColor() == color){
                        return false;
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
