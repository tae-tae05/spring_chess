//import chess.*;

import ui.PrintingHelp;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean keepRunning = true;
        boolean loginStatus = false;
        System.out.println("Welcome to Chess. Type help to get started.");
        Scanner scanner = new Scanner(System.in);
        while (keepRunning) {
            String input = scanner.nextLine();
//            input = input.toLowerCase();
            var tokens = input.split(" ");
            var nextStep = tokens[0];
            if(!loginStatus){
                switch (nextStep) {
                    case "quit" -> {
                        keepRunning = false;
                    }
                    case "help" -> {
                        PrintingHelp help = new PrintingHelp();
                        help.printBeforeLogin();
                    }
                    case "login" -> {
                        System.out.println("request - login");
                    }
                    default -> {
                        System.out.println(nextStep + " is not a valid command, please type 'help' for a list of commands");
                    }

                }
            }
        }
    }
}