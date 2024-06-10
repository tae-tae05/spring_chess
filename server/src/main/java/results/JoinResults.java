package results;

import chess.ChessGame;

public record JoinResults(ChessGame game, String message) {
    public JoinResults setGame(ChessGame newGame){
        return new JoinResults(newGame, message);
    }
    public JoinResults setMessage(String newMessage){
        return new JoinResults(game, newMessage);
    }
}
