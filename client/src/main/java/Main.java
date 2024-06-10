import com.mysql.cj.log.Log;
import model.*;
import request.*;
import results.*;
import server.Server;
import ui.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PrintingHelp help = new PrintingHelp();
        UserData loginResult = null;

        Server server = new Server();
        var port = server.run(0);
//        System.out.println("http://localhost:" + port);

        ServerFacade serverFacade = new ServerFacade("http://localhost:" + port);
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
//                        RegisterResults registerResults;
                        if (inputs.length == 4) { // tokens are correct length
                            registerRequest = new UserData(inputs[1], inputs[2], inputs[3]);
                            try {
//                                registerResults = serverFacade.register(registerRequest);
                                serverFacade.register(registerRequest);
                                loginStatus = true;

                            } catch (ResponseException e) {
                                System.out.println("could not get results -> " + e.getMessage());
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
//                                loginResult = serverFacade.login(loginRequest);
                                serverFacade.login(loginRequest);
                                System.out.println("Success! You are now logged in.");
                                loginStatus = true;
                            }
                            catch (ResponseException e) {
                                System.out.println("failed to login -> " + e.getMessage());
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
                switch (nextStep) {
                    case "logout" -> {
                        System.out.println("authorized logout");
                    }
                    case "help" -> {
                        help.printAfterLogin();
                    }
                    case "create" -> {
                        System.out.println("authorized create");
                    }
                    case "join" -> {
                        System.out.println("authorized join");
                    }
                    case "list" -> {
                        System.out.println("authorized list");
                    }
                    case "observe" -> {
                        System.out.println("authorized observe");
                    }
                    case "quit" -> {
                        System.out.println("authorized quit");
                    }
                    default -> {
                        System.out.println(nextStep + " is not a valid command, please type 'help' for a list of commands");
                    }
                }
            }
        }
        //clear auth table?
    }
}