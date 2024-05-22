package chess;

import java.util.Arrays;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece[][] BOARD = new ChessPiece[8][8];
    public ChessBoard() {
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        BOARD[position.getRow()-1][position.getColumn()-1] = piece;
    }

    public ChessPosition findPiece(ChessPiece piece) {
        int r = 1;
        int c = 1;
        boolean found = false;
        for (ChessPiece[] row : BOARD) {
            c = 1;
            for (ChessPiece tempPiece : row) {
                if (piece.equals(tempPiece)) {
                    return new ChessPosition(r, c);
                }
                c += 1;
            }
            r += 1;
        }
        return null;
    }

    public void movePiece(ChessMove move) {
        ChessPiece current = getPiece(move.getStartPosition());
        ChessPiece.PieceType upgrade = move.getPromotionPiece();
        BOARD[move.getStartPosition().getRow()-1][move.getStartPosition().getColumn() - 1] = null;
        int r = move.getEndPosition().getRow();
        int c = move.getEndPosition().getColumn();
        if(upgrade == null) {
            BOARD[r-1][c-1] = current;
        }
        else{
            BOARD[r-1][c-1] = new ChessPiece(current.getTeamColor(), upgrade);
        }
    }

    public void removePiece(ChessPosition position){
        BOARD[position.getRow()-1][position.getColumn()-1] = null;
    }
    public ChessPiece getPiece(ChessPosition position) {
        return BOARD[position.getRow()-1][position.getColumn()-1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        ChessPiece[][] squares = new ChessPiece[8][8];
        for(int i = 0; i < 8; i++){
            ChessPiece pawn = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
            squares[1][i] = pawn;
        }
        for(int i = 0; i < 8; i++){
            ChessPiece pawn = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
            squares[6][i] = pawn;
        }
        squares[0][0] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        squares[0][7] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        squares[7][0] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        squares[7][7] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        squares[0][1] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        squares[0][6] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        squares[7][1] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        squares[7][6] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        squares[0][2] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        squares[0][5] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        squares[7][2] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        squares[7][5] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        squares[0][4] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        squares[0][3] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        squares[7][4] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        squares[7][3] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        this.BOARD = squares;
    }
    //for testing purposes
    public void emptyBoard(){
        this.BOARD = new ChessPiece[8][8];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Arrays.deepEquals(BOARD, that.BOARD);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(BOARD);
    }

    @Override
    public String toString() {
        String chessboard = "";
        for(int i = 7; i > -1; i--){
            ChessPiece[] row = BOARD[i];
            chessboard += "|";
            for(int j = 0; j < 8; j++){
                ChessPiece piece = row[j];
                if(piece == null) {
                    chessboard += " |";
                }
                else{
                    if(piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                        if (piece.getPieceType() == ChessPiece.PieceType.PAWN) {
                            chessboard += "P|";
                        }
                        if (piece.getPieceType() == ChessPiece.PieceType.ROOK) {
                            chessboard += "R|";
                        }
                        if (piece.getPieceType() == ChessPiece.PieceType.KNIGHT) {
                            chessboard += "N|";
                        }
                        if (piece.getPieceType() == ChessPiece.PieceType.BISHOP) {
                            chessboard += "B|";
                        }
                        if (piece.getPieceType() == ChessPiece.PieceType.QUEEN) {
                            chessboard += "Q|";
                        }
                        if (piece.getPieceType() == ChessPiece.PieceType.KING) {
                            chessboard += "K|";
                        }
                    }
                    else {
                        if (piece.getPieceType() == ChessPiece.PieceType.PAWN) {
                            chessboard += "p|";
                        }
                        if (piece.getPieceType() == ChessPiece.PieceType.ROOK) {
                            chessboard += "r|";
                        }
                        if (piece.getPieceType() == ChessPiece.PieceType.KNIGHT) {
                            chessboard += "n|";
                        }
                        if (piece.getPieceType() == ChessPiece.PieceType.BISHOP) {
                            chessboard += "b|";
                        }
                        if (piece.getPieceType() == ChessPiece.PieceType.QUEEN) {
                            chessboard += "q|";
                        }
                        if (piece.getPieceType() == ChessPiece.PieceType.KING) {
                            chessboard += "k|";
                        }
                    }
                }
            }
            chessboard += "\n";
        }
        return chessboard;
    }
}

