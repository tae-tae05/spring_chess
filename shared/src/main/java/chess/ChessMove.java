package chess;

import java.util.Objects;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove {
    private final ChessPosition START;
    private final ChessPosition END;
    private final ChessPiece.PieceType PROMOTE;

    public ChessMove(ChessPosition startPosition, ChessPosition endPosition,
                     ChessPiece.PieceType promotionPiece) {
        this.START = startPosition;
        this.END = endPosition;
        this.PROMOTE = promotionPiece;
    }

    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition() {
        return START;
    }

    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition() {
        return END;
    }
    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    public ChessPiece.PieceType getPromotionPiece() {
        return PROMOTE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessMove chessMove = (ChessMove) o;
        return Objects.equals(START, chessMove.START) && Objects.equals(END, chessMove.END) && PROMOTE == chessMove.PROMOTE;
    }

    @Override
    public int hashCode() {
        return Objects.hash(START, END, PROMOTE);
    }


    @Override
    public String toString() {
        return "ChessMove{" +
                "start=" + START +
                ", end=" + END +
                '}' + "\n";
    }
}
