package ui;

import model.LogicalCircuit;
import model.LogicalExpression;
import java.util.Scanner;

//an interactive workspace where the user can manipulate expressions and circuits
// and convert them to one another
//provides a way to work on and switch between multiple workspaces,
//TODO: implement support for the above in the future (maybe)
public class PatriceWorkspace {
    LogicalCircuit localCircuit;
    LogicalExpression localExpression;
    private Scanner userInput;
    public final String workSpaceName;


    //EFFECTS: creates a new workspace with an empty logical expression and a blank logical, and sets workSpaceName
    //expression.
    // also starts the interaction loop
    public PatriceWorkspace(String workSpaceName) {
        localCircuit = new LogicalCircuit();
        localExpression = new LogicalExpression();
        this.workSpaceName = workSpaceName;
        interactionLoop();

    }

    //MODIFIES: localCircuit, localExpression
    //EFFECT: starts the interactive loop that allows the user to interface with this workspace
    public void interactionLoop() {
        System.out.println(
                "================================================================================================");
        System.out.println("Current workspace: " + this.workSpaceName + "\n \n");


    }

    //EFFECT: display workspace interface command options
    public void displayWorkspaceOptions() {

    }

    //EFFECT: returns workSpaceName
    public String getWorkspaceName() {
        return workSpaceName;
    }

    


}
