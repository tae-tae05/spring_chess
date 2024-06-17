import chess.ChessGame;
import model.*;
import request.*;
import results.*;
import ui.*;

import java.rmi.ServerException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ResponseException, ServerException {
        String url = "http://localhost:8080";
        Repl repl = new Repl(url);
        repl.run(url);
    }
}