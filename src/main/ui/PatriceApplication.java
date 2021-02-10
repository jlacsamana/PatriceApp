package ui;

import java.util.ArrayList;
import java.util.Scanner;

//The PATRICE application
public class PatriceApplication {
    //based off
    //https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/master/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
    private ArrayList<PatriceWorkspace> openWorkspaces;
    private PatriceWorkspace activeWorkSpace;
    private Scanner userInput;
    //TODO: multiple open sessions is not implemented yet, please disregard openWorkSpaces for now

    //EFFECT: create a new instance of the PATRICE application, and runs its execution loop;
    // creates a new list of PATRICE workspaces, and sets the current active workspace to null
    public PatriceApplication() {
        openWorkspaces = new ArrayList<>();
        activeWorkSpace = null;
        runPatriceMenu();
    }

    //EFFECTS: displays the main menu
    public void runPatriceMenu() {
        displayProgramStart();
        while (true) {
            displayMenuCommOptions();
            userInput = new Scanner(System.in);
            String chosenCmd = userInput.next();

            if (chosenCmd.equals("x")) {
                break;
            } else if (chosenCmd.equals("info")) {
                displayApplicationUsageInfo();
                System.out.println();
            } else if (chosenCmd.equals("new")) {
                activeWorkSpace = new PatriceWorkspace();
            } else if (chosenCmd.equals("load")) {
                //TODO: change this text when data persistence is implemented
                System.out.println("Not yet implemented, please choose another action");
            } else {
                System.out.println("Invalid command");
            }

        }

        System.out.println("Shutting down...");
    }

    //EFFECTS: displays the program's (tentative) Logo
    private void displayProgramStart() {
        System.out.println(
                  "================================================================================================ \n"
                + "00 00 00 00  00 00 00 00  00 00 00 00 00  00 00 00 00  00 00 00 00 00  00 00 00 00   00 00 00 00 \n"
                + "00       00  00       00        00        00       00        00       00             00          \n"
                + "00       00  00       00        00        00       00        00       00             00          \n"
                + "00 00 00 00  00 00 00 00        00        00 00 00 00        00       00             00 00 00 00 \n"
                + "00           00       00        00        00 00              00       00             00          \n"
                + "00           00       00        00        00    00           00       00             00          \n"
                + "00           00       00        00        00       00  00 00 00 00 00  00 00 00 00   00 00 00 00 \n"
                + "================================================================================================ \n"
                + "A Logical Expression-Circuit Translation App \n"
        );
    }

    //EFFECTS: Displays Main Menu commands
    private void displayMenuCommOptions() {
        System.out.println("Usage Information: info");
        System.out.println("Open a new workspace: new");
        System.out.println("Load an existing workspace load [WIP]");
        System.out.println("Close Program: x");
    }

    //EFFECTS: displays usage rules/info
    private void displayApplicationUsageInfo() {
        System.out.println(
                  "==============================[Expression Usage Rules]==============================\n"
                + "-Can not be sequential\n"
                + "-Must be well-formed\n"
                + "-Currently only ~, ∨, and ∧ are supported currently\n"
                + "-The parser is quite sensitive; outermost brackets are usually omitted,\n"
                + " but please include them in your statements\n"
                + " i.e '(~A ∧ B) ∨ A' is won't work, but '((~A ∧ B) ∨ A)' will\n"
                + "-Please only use the available variable names and always in upper case\n"
                + "\n"
                + "==============================[Circuit Usage Rules]==============================\n"
                + "-Can't be sequential \n"
                + "-Must be well-formed \n"
        );
    }


}
