package chess;

public class EscapeCheckmate implements Check{
    @Override
    public Boolean checkDirection(ChessGame.TeamColor color, int row, int col, ChessBoard board) {
        //if king is in checkmate, call this function
        //first, cycle through the board and find all the pieces
        //once you find a piece, get all possible moves
        //cycle through each move. does making that move leave the king in check?
        //if no, return
        return false;
    }

    @Override
    public Boolean isValid(int row, int col) {
        return row > 0 && row < 9 && col > 0 && col < 9;
    }
}
