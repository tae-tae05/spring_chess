package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

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
        ChessPiece current_piece = board.getPiece(startPosition);
        Collection<ChessMove> final_moves = new HashSet<>();
        if(current_piece == null){
            return final_moves;
        }
        Collection<ChessMove> potential_moves = current_piece.pieceMoves(board, startPosition);
        if(isInCheckmate(current_piece.getTeamColor())) {
            return final_moves;
        }
        else {
            for (ChessMove move : potential_moves) {
                ChessPiece opposite = board.getPiece(move.getEndPosition()); //check if null
                board.movePiece(move);
                boolean checker = isInCheck(current_piece.getTeamColor());
                if (!checker) {
                    final_moves.add(move);
                }
                board.movePiece(new ChessMove(move.getEndPosition(), move.getStartPosition(), move.getPromotionPiece()));
                if(opposite != null){
                    board.addPiece(move.getEndPosition(), opposite);
                }
            }
            return final_moves;
        }
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPiece piece = board.getPiece(move.getStartPosition());
        if(piece != null){
            Collection<ChessMove> moves = validMoves(move.getStartPosition());
            boolean moved = false;
            for (ChessMove possibleMove: moves){
                if(possibleMove.equals(move)){
                    ChessPiece current = board.getPiece(move.getStartPosition());
                    if(current != null){
                        if(current.getTeamColor() == turn){
                            board.movePiece(move);
                            moved = true;
                            if (turn == TeamColor.BLACK) {
                                setTeamTurn(TeamColor.WHITE);
                            } else {
                                setTeamTurn(TeamColor.BLACK);
                            }
                        }
                    }
                }
            }
            if(!moved){
                throw new InvalidMoveException("Invalid Move");
            }
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPiece king = new ChessPiece(teamColor, ChessPiece.PieceType.KING);
        ChessPosition kingSpot = board.findPiece(king);
        if(kingSpot != null) {
            boolean knights = checkKnights(teamColor, kingSpot.getRow(), kingSpot.getColumn());
            boolean diagonal = checkDiagonal(teamColor, kingSpot.getRow(), kingSpot.getColumn());
            boolean horizontal = checkHorizontal(teamColor, kingSpot.getRow(), kingSpot.getColumn());
            boolean vertical = checkVertical(teamColor, kingSpot.getRow(), kingSpot.getColumn());
            boolean pawns = checkPawn(teamColor, kingSpot.getRow(), kingSpot.getColumn());
            boolean kingCheck = checkKing(teamColor, kingSpot.getRow(), kingSpot.getColumn());
            return knights || diagonal || horizontal || vertical || pawns || kingCheck;
        }
        else
        {
            return false;
        }
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        ChessPiece king = new ChessPiece(teamColor, ChessPiece.PieceType.KING);
        ChessPosition kingSpot = board.findPiece(king);
        boolean check = false;
        if(kingSpot != null) {
            if (isInCheck(teamColor)) {
                for (int r = kingSpot.getRow() - 1; r < kingSpot.getRow() + 2; r++) {
                    for (int c = kingSpot.getColumn() - 1; c < kingSpot.getColumn() + 2; c++) {
                        if (isValid(r, c)) {
                            boolean knights = checkKnights(teamColor, r, c);
                            boolean diagonal = checkDiagonal(teamColor, r, c);
                            boolean horizontal = checkHorizontal(teamColor, r, c);
                            boolean vertical = checkVertical(teamColor, r, c);
                            boolean pawns = checkPawn(teamColor, r, c);
                            boolean kingCheck = checkKing(teamColor, r, c);
                            check = knights || diagonal || horizontal || vertical || pawns || kingCheck; //checking if anything can eat
                        }

                    }
                }
            }
        }
        if(check){
            ChessPosition startPosition = new ChessPosition(kingSpot.getRow(), kingSpot.getColumn());
            Collection<ChessMove> potential_moves = king.pieceMoves(board, startPosition);
            for (ChessMove move : potential_moves) {
                ChessPiece opposite = board.getPiece(move.getEndPosition()); //check if null
                board.movePiece(move);
//                System.out.println("check= " + isInCheck(current_piece.getTeamColor()));
//                System.out.println(move);
                if(!isInCheck(king.getTeamColor())) {
                    check = false;
                }
                board.movePiece(new ChessMove(move.getEndPosition(), move.getStartPosition(), move.getPromotionPiece()));
                if(opposite != null){
                    board.addPiece(move.getEndPosition(), opposite);
                }
            }
        }
        return check;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        for(int r = 1; r < 9; r++){
            for (int c = 1; c < 9; c++){
                ChessPiece piece = board.getPiece(new ChessPosition(r, c));
                if(piece != null && piece.getTeamColor() == teamColor){
                    Collection<ChessMove> moves = validMoves(new ChessPosition(r,c));
                    if(!moves.isEmpty()){
                        return false;
                    }
                }
            }
        }
        return true;
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

    public boolean checkKing(TeamColor color, int row, int col){
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return turn == chessGame.turn && Objects.equals(board, chessGame.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(turn, board);
    }
}
