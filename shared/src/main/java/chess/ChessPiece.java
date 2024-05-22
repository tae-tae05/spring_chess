package chess;


import chess.Moves.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private final ChessGame.TeamColor COLOR;
    private final PieceType PIECE;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.COLOR = pieceColor;
        this.PIECE = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return COLOR;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return PIECE;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        Collection<ChessMove> moves = new ArrayList<>();
        switch (PIECE) {
            case PieceType.BISHOP -> {
                BishopMoves bishop = new BishopMoves();
                moves = bishop.pieceMoves(board, myPosition, COLOR);
            }
            case PieceType.KING ->
            {
                KingMoves king = new KingMoves();
                moves = king.pieceMoves(board, myPosition, COLOR);
            }
            case PieceType.KNIGHT ->
            {
                KnightMoves knight = new KnightMoves();
                moves = knight.pieceMoves(board, myPosition, COLOR);
            }
            case PieceType.PAWN ->
            {
                PawnMoves pawn = new PawnMoves();
                moves = pawn.pieceMoves(board, myPosition, COLOR);
            }
            case PieceType.QUEEN ->
            {
                QueenMoves queen = new QueenMoves();
                moves = queen.pieceMoves(board, myPosition, COLOR);
            }
            case PieceType.ROOK ->
            {
                RookMoves rook = new RookMoves();
                moves = rook.pieceMoves(board, myPosition, COLOR);
            }
            default ->
            {
                return null;
            }
        }
        return moves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return COLOR == that.COLOR && PIECE == that.PIECE;
    }

    @Override
    public int hashCode() {
        return Objects.hash(COLOR, PIECE);
    }
}
