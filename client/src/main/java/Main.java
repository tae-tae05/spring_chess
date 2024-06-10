import chess.ChessGame;
import model.*;
import request.*;
import results.*;
import ui.*;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ResponseException {
        Repl repl = new Repl();
        String url = "http://localhost:8080";
        repl.run(url);
    }
}