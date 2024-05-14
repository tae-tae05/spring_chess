package chess;

import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor turn = TeamColor.WHITE;
    private ChessBoard board = new ChessBoard();
    public ChessGame() {

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return turn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.turn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    public boolean checkPawn(TeamColor color, int row, int col){
        if (color == TeamColor.BLACK) { //- since white can only move up
            if(isValid(row-1, col-1)) {
                ChessPiece current = board.getPiece(new ChessPosition(row - 1, col - 1));
                if (current != null && (current.getPieceType() == ChessPiece.PieceType.PAWN)) {
                    if (current.getTeamColor() != color) {
                        return true;
                    }
                }
            }
            if(isValid(row -1, col + 1)){
                ChessPiece current = board.getPiece(new ChessPosition(row - 1, col + 1));
                if (current != null && (current.getPieceType() == ChessPiece.PieceType.PAWN)) {
                    if (current.getTeamColor() != color) {
                        return true;
                    }
                }
            }
        }
        else {
            if(isValid(row+1, col-1)) {
                ChessPiece current = board.getPiece(new ChessPosition(row+1, col - 1));
                if (current != null && (current.getPieceType() == ChessPiece.PieceType.PAWN)) {
                    return current.getTeamColor() != color;
                }
            }
            if(isValid(row+1, col + 1)){
                ChessPiece current = board.getPiece(new ChessPosition(row+1, col + 1));
                if (current != null && (current.getPieceType() == ChessPiece.PieceType.PAWN)) {
                    return current.getTeamColor() != color;
                }
            }
        }
        return false;
    }
    public boolean checkKnights(TeamColor color, int row, int col){
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
    public boolean checkVertical(TeamColor teamColor, int row, int col){
        for(int i = row - 1; i > 0; i--){
            ChessPiece current = board.getPiece(new ChessPosition(i, col));
            if(current != null){
                if(current.getPieceType() == ChessPiece.PieceType.QUEEN || current.getPieceType() == ChessPiece.PieceType.ROOK){
                    return current.getTeamColor() != teamColor;
                }
            }
        }
        for(int i = row + 1; i < 9; i++){
            ChessPiece current = board.getPiece(new ChessPosition(i, col));
            if(current != null){
                if(current.getPieceType() == ChessPiece.PieceType.QUEEN || current.getPieceType() == ChessPiece.PieceType.ROOK){
                    return current.getTeamColor() != teamColor;
                }
            }
        }
        return false;
    }

    public boolean checkHorizontal(TeamColor teamColor, int row, int col){
        for(int i = col - 1; i > 0; i--){
            ChessPiece current = board.getPiece(new ChessPosition(row, i));
            if(current != null){
                if(current.getPieceType() == ChessPiece.PieceType.QUEEN || current.getPieceType() == ChessPiece.PieceType.ROOK){
                    return current.getTeamColor() != teamColor;
                }
            }
        }
        for(int i = col + 1; i < 9; i++){
            ChessPiece current = board.getPiece(new ChessPosition(row, i));
            if(current != null){
                if(current.getPieceType() == ChessPiece.PieceType.QUEEN || current.getPieceType() == ChessPiece.PieceType.ROOK){
                    return current.getTeamColor() != teamColor;
                }
            }
        }
        return false;
    }

    public boolean checkDiagonal(TeamColor teamColor, int row, int col){
        for(int i = 1; i < 9; i++){
            if(isValid(row + i, col + i)){
                ChessPiece current = board.getPiece(new ChessPosition(row + i, col + i));
                if(current != null){
                    if(current.getPieceType() == ChessPiece.PieceType.ROOK || current.getPieceType() == ChessPiece.PieceType.QUEEN){
                        return current.getTeamColor() != teamColor;
                    }
                    else if(current.getTeamColor() == teamColor){
                        return false;
                    }
                }
            }
            if(isValid(row + i, col - i)){
                ChessPiece current = board.getPiece(new ChessPosition(row + i, col - i));
                if(current != null){
                    if(current.getPieceType() == ChessPiece.PieceType.ROOK || current.getPieceType() == ChessPiece.PieceType.QUEEN){
                        return current.getTeamColor() != teamColor;
                    }
                    else if(current.getTeamColor() == teamColor){
                        return false;
                    }
                }
            }
            if(isValid(row - i, col + i)){
                ChessPiece current = board.getPiece(new ChessPosition(row - i, col + i));
                if(current != null){
                    if(current.getPieceType() == ChessPiece.PieceType.ROOK || current.getPieceType() == ChessPiece.PieceType.QUEEN){
                        return current.getTeamColor() != teamColor;
                    }
                    else if(current.getTeamColor() == teamColor){
                        return false;
                    }
                }
            }
            if(isValid(row - i, col - i)){
                ChessPiece current = board.getPiece(new ChessPosition(row - i, col - i));
                if(current != null){
                    if(current.getPieceType() == ChessPiece.PieceType.ROOK || current.getPieceType() == ChessPiece.PieceType.QUEEN){
                        return current.getTeamColor() != teamColor;
                    }
                    else if(current.getTeamColor() == teamColor){
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public boolean isValid(int row, int col){
        return row < 9 && row > 0 && col < 9 && col > 0;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }
}
