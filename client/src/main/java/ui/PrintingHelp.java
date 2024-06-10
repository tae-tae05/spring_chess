package ui;

public class PrintingHelp {
    public void printBeforeLogin(){
        System.out.println("register <USERNAME> <PASSWORD> <EMAIL> - to create an account");
        System.out.println("login <USERNAME> <PASSWORD> - to play chess");
        System.out.println("quit - playing chess");
        System.out.println("help - with possible commands");
    }

    public void printAfterLogin(){
        System.out.println("create <NAME> - a game");
        System.out.println("list - games");
        System.out.println("join <ID> [WHITE|BLACK] - a game");
        System.out.println("observe <ID> - a game");
        System.out.println("logout - when you are done");
        System.out.println("quit - playing chess");
        System.out.println("help - with possible commands");
    }
}
