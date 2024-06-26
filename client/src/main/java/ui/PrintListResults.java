package ui;

import model.*;
import results.ListGameResults;

public class PrintListResults {
    public void printGames(ListGameResults listGame){
        int count = 1;
        for(GameData game: listGame.games()){
            System.out.println(count + ". " + game.gameName() + "\nWhite Player - " + game.whiteUsername() + "\nBlack Player - " + game.blackUsername());
            count++;
        }
    }
}
