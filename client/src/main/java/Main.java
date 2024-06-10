import com.mysql.cj.log.Log;
import model.*;
import request.*;
import results.*;
import ui.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PrintingHelp help = new PrintingHelp();
        UserData loginResult = null;
        ServerFacade serverFacade = new ServerFacade("http://localhost:");
        boolean keepRunning = true;
        boolean loginStatus = false;
        System.out.println("Welcome to Chess. Type help to get started.");
        Scanner scanner = new Scanner(System.in);
        while (keepRunning) {
            //getting input and splitting it
            String input = scanner.nextLine();
            var inputs = input.split(" ");
            var nextStep = inputs[0];
            //set up needed info
            if(!loginStatus){
                switch (nextStep) {
                    case "quit" -> {
                        keepRunning = false;
                    }
                    case "help" -> {
                        help.printBeforeLogin();
                    }
                    case "register" -> {
                        UserData registerRequest = new UserData(null, null, null);
                        RegisterResults registerResults;
                        if (inputs.length == 4) { // tokens are correct length
                            registerRequest = new UserData(inputs[1], inputs[2], inputs[3]);
                            try {
                                System.out.println("request - request");
                                registerResults = serverFacade.register(registerRequest);

                            } catch (ResponseException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        else{
                            System.out.println("you have not provided enough information. type 'help' for specific commands.");
                        }
                    }
                    case "login" -> {
                        LoginRequest loginRequest;
                        if(inputs.length == 3){
                            loginRequest = new LoginRequest(inputs[1], inputs[2]);
                            try {
                                System.out.println("request - login");
//                                loginResult = serverFacade.login(loginRequest);
                            } catch (Exception e){
                                System.out.println(e.getMessage());
                            }
                        }
                        else{
                            System.out.println("you have not provided enough information. type 'help' for specific commands.");
                        }
                    }
                    default -> {
                        System.out.println(nextStep + " is not a valid command, please type 'help' for a list of commands");
                    }

                }
            }
            else{
                System.out.println("implement login part");
            }
        }
    }
}