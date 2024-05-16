package chess;

import chess.check.*;

public class ChessTester {
    public static void main(String[] args){
        ChessGame game = new ChessGame();
        ChessBoard new_board = new ChessBoard();
//        System.out.println(new_board.toString());
        new_board.emptyBoard();
        new_board.addPiece(new ChessPosition(5, 5), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING));

        new_board.addPiece(new ChessPosition(5, 8), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING));
        new_board.addPiece(new ChessPosition(7, 3), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP));
        new_board.addPiece(new ChessPosition(5, 4), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN));
        new_board.addPiece(new ChessPosition(7, 4), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN));
        new_board.addPiece(new ChessPosition(3, 3), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK));
        new_board.addPiece(new ChessPosition(2, 5), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT));

        game.setBoard(new_board);
        ChessGame.TeamColor black = ChessGame.TeamColor.BLACK;
        ChessGame.TeamColor white = ChessGame.TeamColor.WHITE;

//        System.out.println("Checkmate - " + game.isInCheckmate(white));

        int r = 3;
        int c = 5;
        CheckKnights knight = new CheckKnights();
        boolean knights = knight.checkDirection(black, r, c, new_board);
        CheckDiagonal diag = new CheckDiagonal();
        boolean diagonal = diag.checkDirection(black, r, c, new_board);
//        System.out.println("diagonal - " + diagonal);
        CheckHorizontal hori = new CheckHorizontal();
        boolean horizontal = hori.checkDirection(white, r, c, new_board);
//        System.out.println("horizontal - " + horizontal);
        CheckVertical vert = new CheckVertical();
        boolean vertical = vert.checkDirection(black, r, c, new_board);
//        System.out.println("vertical - " + vertical);
        CheckPawn pawn = new CheckPawn();
        boolean pawns = pawn.checkDirection(black, r, c, new_board);
//        System.out.println("pawns - " + pawns);
        CheckKing King = new CheckKing();
        boolean kingCheck = King.checkDirection(black, r, c, new_board);
//        System.out.println("king - " + kingCheck);
        Boolean truth = knights || diagonal || horizontal || vertical || pawns || kingCheck;
        System.out.println("check - " + truth);

        System.out.print(new_board.toString());
    }
}
