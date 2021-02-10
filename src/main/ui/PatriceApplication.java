package ui;

import java.util.ArrayList;
import java.util.Scanner;

//The PATRICE application
public class PatriceApplication {
    //based off
    //https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/master/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
    private ArrayList<PatriceWorkspace> openWorkspaces;
    private ArrayList<String> patriceWorkspaceNames;
    private PatriceWorkspace activeWorkSpace;
    private Scanner userInput;

    //EFFECT: create a new instance of the PATRICE application, and runs its execution loop;
    // creates a new list of PATRICE workspaces, and sets the current active workspace to null
    // creates a list to hold all of the names of open workspaces
    public PatriceApplication() {
        openWorkspaces = new ArrayList<>();
        patriceWorkspaceNames = new ArrayList<>();
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
                createNewWorkspace();
            } else if (chosenCmd.equals("switch")) {
                switchWorkspaces();
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
                + "||                        A Logical Expression-Circuit Translation App                        || \n"
        );
    }

    //EFFECTS: Displays Main Menu commands
    private void displayMenuCommOptions() {
        System.out.println(
                "================================================================================================");
        System.out.println("Usage Information: info");
        System.out.println("Create a new workspace: new");
        System.out.println("Switch to an already loaded workspace: switch [WIP]");
        System.out.println("Load an existing workspace load [WIP]");
        System.out.println("Close Program: x");
        System.out.println(
                "================================================================================================");
    }

    //EFFECTS: displays usage rules/info
    private void displayApplicationUsageInfo() {
        System.out.println(
                  "================================================================================================ \n"
                + "====================================[Expression Usage Rules]==================================== \n"
                + "-Can not be sequential\n"
                + "-Must be well-formed\n"
                + "-Currently only ~, ∨, and ∧ are supported currently\n"
                + "-The parser is quite sensitive; outermost brackets are usually omitted,\n"
                + " but please include them in your statements\n"
                + " i.e '(~A ∧ B) ∨ A' is won't work, but '((~A ∧ B) ∨ A)' will\n"
                + "-Please only use the available variable names and always in upper case\n"
                + "\n"
                + "=====================================[Circuit Usage Rules]====================================== \n"
                + "-Can't be sequential \n"
                + "-Must be well-formed \n"
                + "\n"
                + "================================================================================================"
        );
    }

    //EFFECTS: opens the given workspace by name if one can be found among the loaded workspaces
    public void openLoadedWorkSpace(String workspaceName) {
        for (PatriceWorkspace p: openWorkspaces) {
            if (p.getWorkspaceName().equals(workspaceName)) {
                System.out.println(p.getWorkspaceName() + " successfully loaded");
                p.interactionLoop();
                return;
            }
        }
        System.out.println("specified workspace cannot be found");
        return;
    }

    //EFFECTS: display names of all open workspaces
    public String displayOpenworkSpaceNames() {
        ArrayList<String> workspaceNames = new ArrayList<>();
        for (PatriceWorkspace workSpace: openWorkspaces) {
            workspaceNames.add(workSpace.getWorkspaceName());
        }

        return workspaceNames.toString();
    }


    //EFFECTS: creates a new workspace and prompts user for a name to call it
    //if name is left blank, or user specifies a name already in use, will propmt them again until a valid
    //name is provided
    private void createNewWorkspace() {
        String newWorkspaceName = "";
        while (true) {
            System.out.println("What do you want to call this workspace?");
            userInput = new Scanner(System.in);
            newWorkspaceName = userInput.next();

            if (!patriceWorkspaceNames.contains(newWorkspaceName) || newWorkspaceName.equals("")) {
                break;
            }
            System.out.println("Specified Name is in use or invalid.");
        }
        openWorkspaces.add(new PatriceWorkspace(newWorkspaceName));
        patriceWorkspaceNames.add(newWorkspaceName);

    }

    //EFFECTS: prompts user for a name of an opened workspace, and opens it, if there is a
    //workspace of that name in the list of workspaces
    private void switchWorkspaces() {
        displayOpenworkSpaceNames();
        System.out.println("Name workspace to open");
        userInput = new Scanner(System.in);
        String wrkspaceToLoad = userInput.next();
        openLoadedWorkSpace(wrkspaceToLoad);
    }


}
