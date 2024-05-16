package chess;
public class ChessTester {
    public static void main(String[] args){
        ChessGame game = new ChessGame();
        ChessBoard new_board = new ChessBoard();
//        System.out.println(new_board.toString());
        new_board.emptyBoard();
        new_board.addPiece(new ChessPosition(8, 4), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING));

        new_board.addPiece(new ChessPosition(6, 4), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING));
        new_board.addPiece(new ChessPosition(3, 1), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP));

        game.setBoard(new_board);
//        System.out.println(game.isInCheck(ChessGame.TeamColor.BLACK));

        int r = 6;
        int c = 4;
        ChessGame.TeamColor teamColor = ChessGame.TeamColor.BLACK;
        boolean knights = game.checkKnights(teamColor, r, c);
        boolean diagonal = game.checkDiagonal(teamColor, r, c);
        boolean horizontal = game.checkHorizontal(teamColor, r, c);
        boolean vertical = game.checkVertical(teamColor, r, c);
        boolean pawns = game.checkPawn(teamColor, r, c);
        boolean kingCheck = game.checkKing(teamColor, r, c);
        boolean check = knights || diagonal || horizontal || vertical || pawns || kingCheck; //checking if anything can eat
        System.out.println(diagonal);
//        System.out.println(game.checkPawn(ChessGame.TeamColor.BLACK));
//        System.out.println(game.isInCheckmate(ChessGame.TeamColor.BLACK));
//        System.out.println(game.isInCheckmate(ChessGame.TeamColor.BLACK));
//        System.out.println(game.isInCheckmate(ChessGame.TeamColor.BLACK));
//        System.out.println(game.isInCheckmate(ChessGame.TeamColor.BLACK));

        System.out.print(new_board.toString());
    }
}
