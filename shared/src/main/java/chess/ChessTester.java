package chess;

import chess.check.*;

import java.util.Collection;

public class ChessTester {
    public static void main(String[] args){
        ChessGame game = new ChessGame();
        ChessBoard new_board = new ChessBoard();
        new_board.resetBoard();
//        System.out.println(new_board.toString());
//        new_board.emptyBoard();
        ChessGame.TeamColor black = ChessGame.TeamColor.BLACK;
        ChessGame.TeamColor white = ChessGame.TeamColor.WHITE;

        ChessMove b_knight1 = new ChessMove(new ChessPosition(8, 2), new ChessPosition(6, 3), null);
        new_board.movePiece(b_knight1);
        ChessMove b_knight2 = new ChessMove(new ChessPosition(8, 7), new ChessPosition(6, 6), null);
        new_board.movePiece(b_knight2);
        ChessMove b_pawn1 = new ChessMove(new ChessPosition(7, 5), new ChessPosition(5, 5), null);
        new_board.movePiece(b_pawn1);
        ChessMove w_bishop1 = new ChessMove(new ChessPosition(1, 6), new ChessPosition(4, 3), null);
        new_board.movePiece(w_bishop1);
        ChessMove w_pawn1 = new ChessMove(new ChessPosition(2, 5), new ChessPosition(4, 5), null);
        new_board.movePiece(w_pawn1);
        ChessMove w_queen = new ChessMove(new ChessPosition(1, 4), new ChessPosition(7, 6), null);
        new_board.movePiece(w_queen);

        System.out.println("stalemate - " + game.isInStalemate(black));

//        new_board.addPiece(new ChessPosition(1, 1), new ChessPiece(white, ChessPiece.PieceType.KING));
//        new_board.addPiece(new ChessPosition(3, 3), new ChessPiece(white, ChessPiece.PieceType.BISHOP));
//        new_board.addPiece(new ChessPosition(7, 3), new ChessPiece(white, ChessPiece.PieceType.ROOK));
//        new_board.addPiece(new ChessPosition(1, 8), new ChessPiece(white, ChessPiece.PieceType.ROOK));
//
//        new_board.addPiece(new ChessPosition(8, 8), new ChessPiece(black, ChessPiece.PieceType.KING));
//        new_board.addPiece(new ChessPosition(8, 7), new ChessPiece(black, ChessPiece.PieceType.ROOK));
//        new_board.addPiece(new ChessPosition(5, 5), new ChessPiece(black, ChessPiece.PieceType.ROOK));


//        game.setBoard(new_board);

//        Collection<ChessMove> moves = game.validMoves(new ChessPosition(2, 4));
//        for(ChessMove move: moves){
//            System.out.println(move.toString());
//        }
//        System.out.println("Checkmate - " + game.isInCheckmate(black));
//
//        int r = 8;
//        int c = 8;
//        CheckKnights knight = new CheckKnights();
//        boolean knights = knight.checkDirection(black, r, c, new_board);
//        CheckDiagonal diag = new CheckDiagonal();
//        boolean diagonal = diag.checkDirection(black, r, c, new_board);
////        System.out.println("diagonal - " + diagonal);
//
//        CheckHorizontal hori = new CheckHorizontal();
//        boolean horizontal = hori.checkDirection(black, r, c, new_board);
////        System.out.println("horizontal - " + horizontal);
//
//        CheckVertical vert = new CheckVertical();
//        boolean vertical = vert.checkDirection(black, r, c, new_board);
////        System.out.println("vertical - " + vertical);
//
//        CheckPawn pawn = new CheckPawn();
//        boolean pawns = pawn.checkDirection(black, r, c, new_board);
////        System.out.println("pawns - " + pawns);
//
//        CheckKing King = new CheckKing();
//        boolean kingCheck = King.checkDirection(black, r, c, new_board);
////        System.out.println("king - " + kingCheck);
//
//        Boolean truth = knights || diagonal || horizontal || vertical || pawns || kingCheck;
//        System.out.println("check - " + truth);
        game.setBoard(new_board);
        System.out.print(new_board.toString());
    }
}
