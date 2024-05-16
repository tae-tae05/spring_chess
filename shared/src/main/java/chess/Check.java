package chess;

public interface Check {
    Boolean checkDirection(ChessGame.TeamColor color, int row, int col, ChessBoard board);
    Boolean isValid(int row, int col);
}
