package chess;

import chess.check.*;

import java.util.ArrayList;
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
        board.resetBoard();
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
        else{
            Collection<ChessMove> potential_moves = current_piece.pieceMoves(board, startPosition);
            for (ChessMove move : potential_moves) {
                ChessPiece opposite = board.getPiece(move.getEndPosition()); //check if null
                board.movePiece(move);
                boolean checker = isInCheck(current_piece.getTeamColor());
                if (!checker) {
                    final_moves.add(move);
                }
                board.movePiece(new ChessMove(move.getEndPosition(), move.getStartPosition(), move.getPromotionPiece()));
                if (opposite != null) {
                    board.addPiece(move.getEndPosition(), opposite);
                }
            }

            return final_moves;
        }

    }

    /**
     * call this function when you're in check and trying to see if anything else can move to save the king
     */


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
                            System.out.println(board.toString());
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
        else{
            throw new InvalidMoveException("Invalid Move");
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
            CheckKnights knight = new CheckKnights();
            boolean knights = knight.checkDirection(teamColor, kingSpot.getRow(), kingSpot.getColumn(), board);
            CheckDiagonal diag = new CheckDiagonal();
            boolean diagonal = diag.checkDirection(teamColor, kingSpot.getRow(), kingSpot.getColumn(), board);
            CheckHorizontal hori = new CheckHorizontal();
            boolean horizontal = hori.checkDirection(teamColor, kingSpot.getRow(), kingSpot.getColumn(), board);
            CheckVertical vert = new CheckVertical();
            boolean vertical = vert.checkDirection(teamColor, kingSpot.getRow(), kingSpot.getColumn(), board);
            CheckPawn pawn = new CheckPawn();
            boolean pawns = pawn.checkDirection(teamColor, kingSpot.getRow(), kingSpot.getColumn(), board);
            CheckKing King = new CheckKing();
            boolean kingCheck = King.checkDirection(teamColor, kingSpot.getRow(), kingSpot.getColumn(), board);
            return knights || diagonal || horizontal || vertical || pawns || kingCheck;
        }
            return false;
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
                for (int r = kingSpot.getRow() - 1; r < kingSpot.getRow() + 3; r++) {
                    for (int c = kingSpot.getColumn() - 1; c < kingSpot.getColumn() + 3; c++) {
                        if (isValid(r, c)) {
                            if (board.getPiece(new ChessPosition(r, c)) == null || board.getPiece(new ChessPosition(r, c)).getTeamColor() != teamColor) {
                                CheckKnights knight = new CheckKnights();
                                boolean knights = knight.checkDirection(teamColor, r, c, board);
                                CheckDiagonal diag = new CheckDiagonal();
                                boolean diagonal = diag.checkDirection(teamColor, r, c, board);
                                CheckHorizontal hori = new CheckHorizontal();
                                boolean horizontal = hori.checkDirection(teamColor, r, c, board);
                                CheckVertical vert = new CheckVertical();
                                boolean vertical = vert.checkDirection(teamColor, r, c, board);
                                CheckPawn pawn = new CheckPawn();
                                boolean pawns = pawn.checkDirection(teamColor, r, c, board);
                                CheckKing King = new CheckKing();
                                boolean kingCheck = King.checkDirection(teamColor, r, c, board);
                                check = knights || diagonal || horizontal || vertical || pawns || kingCheck; //checking if anything can eat
                            }
                        }
                    }
                }
            }
        }
        if(check){
            for(int r = 1; r < 9; r++){
                for(int c = 1; c < 9; c++){
                    ChessPosition current = new ChessPosition(r,c);
                    if(board.getPiece(current)!= null && board.getPiece(current).getTeamColor() == teamColor){
                        Collection<ChessMove> test = validMoves(current);
                        if(!test.isEmpty()){
                            return false;
                        }
                    }
                }
            }
        }
//        if(check){
//            ChessPosition startPosition = new ChessPosition(kingSpot.getRow(), kingSpot.getColumn());
//            Collection<ChessMove> potential_moves = king.pieceMoves(board, startPosition);
//            for (ChessMove move : potential_moves) {
//                ChessPiece opposite = board.getPiece(move.getEndPosition()); //check if null
//                board.movePiece(move);
//                if(!isInCheck(king.getTeamColor())) {
//                    check = false;
//                }
//                board.movePiece(new ChessMove(move.getEndPosition(), move.getStartPosition(), move.getPromotionPiece()));
//                if(opposite != null){
//                    board.addPiece(move.getEndPosition(), opposite);
//                }
//            }
//        }
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
        return !isInCheckmate(teamColor);
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
