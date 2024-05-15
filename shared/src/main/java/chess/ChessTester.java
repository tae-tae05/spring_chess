package chess;

public class ChessTester {
    public static void main(String[] args){
        ChessGame game = new ChessGame();
        ChessBoard new_board = new ChessBoard();
        new_board.emptyBoard();
        new_board.addPiece(new ChessPosition(3, 2), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING));

        new_board.addPiece(new ChessPosition(8, 8), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING));
        new_board.addPiece(new ChessPosition(3, 6), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK));

        game.setBoard(new_board);
        System.out.println(game.isInCheck(ChessGame.TeamColor.WHITE));
        System.out.print(new_board.toString());

//        int r = 7;
//        int c = 8;
//        ChessGame.TeamColor teamColor = ChessGame.TeamColor.BLACK;
//        boolean knights = game.checkKnights(teamColor, r, c);
//        boolean diagonal = game.checkDiagonal(teamColor, r, c);
//        boolean horizontal = game.checkHorizontal(teamColor, r, c);
//        boolean vertical = game.checkVertical(teamColor, r, c);
//        boolean pawns = game.checkPawn(teamColor, r, c);
//        boolean kingCheck = game.checkKing(teamColor, r, c);
//        boolean check = knights || diagonal || horizontal || vertical || pawns || kingCheck; //checking if anything can eat
//        System.out.println(check);
//        System.out.println(game.checkPawn(ChessGame.TeamColor.BLACK));
//        System.out.println(game.isInCheckmate(ChessGame.TeamColor.BLACK));
//        System.out.println(game.isInCheckmate(ChessGame.TeamColor.BLACK));
//        System.out.println(game.isInCheckmate(ChessGame.TeamColor.BLACK));
//        System.out.println(game.isInCheckmate(ChessGame.TeamColor.BLACK));
    }
}
