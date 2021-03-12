package ui;

import java.util.Scanner;



//provides a bootstrapper to launch either the CLI or GUI version of the program from
public class BootStrapper {
    private Scanner userInput;

    //EFFECT: starts the bootstrapper and provides dialogues for loading the program
    public BootStrapper() {
        while (true) {
            System.out.println("OPTIONS: \n"
                    + "[1] Load CLI app\n"
                    + "[2] Load GUI app\n"
                    + "[x] close program\n");
            String userChoice = promptUserForUIChoice();

            if (userChoice.equals("1")) {
                new PatriceGuiApplication();
                break;
            } else if (userChoice.equals("2")) {
                new PatriceApplication();
                break;
            } else if (userChoice.equals("x")) {
                System.out.println("Quit Program");
                break;
            }
        }




    }

    //EFFECT: returns user input entered after being prompted to
    public String promptUserForUIChoice() {
        userInput = new Scanner(System.in);
        return userInput.nextLine();
    }
}
