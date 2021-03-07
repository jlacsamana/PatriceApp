package ui;

import model.*;
import model.gates.*;
import persistence.WorkspaceSaver;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

//an interactive workspace where the user can manipulate expressions and circuits
// and convert them to one another
//provides a way to work on and switch between multiple workspaces
//user input handling based off
//https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/master/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
public class PatriceWorkspace {
    LogicalCircuit localCircuit;
    LogicalExpression localExpression;
    private Scanner userInput;
    public final String workSpaceName;
    private WorkspaceSaver workspaceSaver;

    //EFFECTS: creates a new workspace with an empty logical expression and a blank logical, and sets workSpaceName
    //expression. Also creates a workspace saver object to save data.
    // starts interaction loop if startLoop is passed true, doesn't if false
    public PatriceWorkspace(String workSpaceName, boolean startLoop) {
        localCircuit = new LogicalCircuit();
        localCircuit.getHead().setName("OUTPUT");
        localExpression = new LogicalExpression();
        workspaceSaver = new WorkspaceSaver();
        this.workSpaceName = workSpaceName;
        if (startLoop) {
            interactionLoop();
        }
    }

    //MODIFIES: localCircuit, localExpression
    //EFFECT: starts the interactive loop that allows the user to interface with this workspace
    public void interactionLoop() {
        while (true) {
            System.out.println(
                    "================================================================================================");
            System.out.println("Current workspace: " + this.workSpaceName);
            displayWorkspaceOptions();
            userInput = new Scanner(System.in);
            String chosenCmd = userInput.nextLine();

            if (chosenCmd.equals("close")) {
                break;
            } else if (chosenCmd.equals("view") || chosenCmd.equals("newExp") || chosenCmd.equals("modCirc")
                    || chosenCmd.equals("save")) {
                processWorkSpaceMenuCmd(chosenCmd);
            } else {
                System.out.println("Invalid command, please try again");
            }
        }
        System.out.println("returning to main menu...");
    }

    //EFFECT: executes the command corresponding to cmd
    private void processWorkSpaceMenuCmd(String cmd) {
        if (cmd.equals("view")) {
            displayLogicalExpression();
            displayCircuitInfo(localCircuit);
        } else if (cmd.equals("newExp")) {
            tryEnterNewExp();
        } else if (cmd.equals("modCirc")) {
            tryCreateNewCirc();
        } else if (cmd.equals("save")) {
            trySaveFile();
        }
    }

    //EFFECTS: attempts to save this workspace's data to a file corresponding to the name of the file specified
    //by the user, If no such file exists, a new one is created of that name and the data is saved to it
    private void trySaveFile() {
        System.out.println("Enter a file name:");
        userInput = new Scanner(System.in);
        String fileName = userInput.nextLine();
        String saveStatus = workspaceSaver.saveToFile(fileName, this);
        System.out.println(saveStatus);
    }

    //EFFECT: saves

    //MODIFIES: localCircuit, localExpression
    //EFFECT: prompts user to enter a new expression, and tries to convert it to a circuit. If successful this
    //new set of Expression and Circuit will be the one currently stored by this workspace's local expression and
    // circuit. If it fails, the current one is kept.
    private void tryEnterNewExp() {
        System.out.println("Enter an expression:");
        userInput = new Scanner(System.in);
        String enteredExp = userInput.nextLine();
        LogicalExpression tryNexExp;
        LogicalCircuit tryNewCirc;
        try {
            tryNexExp = new LogicalExpression();
            tryNexExp.setLogicalExpression(enteredExp);
            tryNewCirc = tryNexExp.generateCircuit();
            System.out.println("expression successfully converted!");
        } catch (Exception a) {
            System.out.println("entered expression could not be converted");
            return;
        }

        localExpression = tryNexExp;
        localCircuit = tryNewCirc;
    }

    //MODIFIES: localCircuit, localExpression
    //EFFECT: prompts user to construct a new logical circuit, and tries to convert it to an expression. If successful
    // this new set of Expression and Circuit will be the one currently stored by this workspace's local expression and
    // circuit. If it fails, the current one is kept.
    //the pre-added circuit output will ba named "Out"
    private void tryCreateNewCirc() {
        LogicalCircuit tryNewCirc = new LogicalCircuit();
        tryNewCirc.getHead().setName("OUTPUT");
        LogicalExpression generatedLogicalExp = new LogicalExpression();
        while (true) {
            String editCmd = circuitCreationDialogue(tryNewCirc);

            if (editCmd.equals("add") || editCmd.equals("edit") || editCmd.equals("delete")) {
                processModifyCircuitCommand(editCmd, tryNewCirc);
            } else if (editCmd.equals("submit")) {
                if (attemptConvertCircuitToExpression(tryNewCirc)) {
                    generatedLogicalExp = tryNewCirc.generateExpression();
                    break;
                }
            } else if (editCmd.equals("cancel")) {
                System.out.println("circuit creation cancelled");
                return;
            } else {
                System.out.println("invalid command");
            }
        }
        localCircuit = tryNewCirc;
        localExpression = generatedLogicalExp;
        System.out.println("successfully converted!");
    }

    //EFFECTS: displays the circuit currently being worked on, and available commands to manipulate it
    //and prompts the user for one of those commands. Returns this user-entered command.

    public String circuitCreationDialogue(LogicalCircuit tryNewCirc) {
        displayLogicalCircuit(tryNewCirc);
        displayEditCircCommands();
        userInput = new Scanner(System.in);
        return userInput.nextLine();
    }

    //MODIFIES: tryNewCirc
    //EFFECTS: applies the method corresponding to command to tryNewCirc
    private void processModifyCircuitCommand(String command, LogicalCircuit tryNewCirc) {
        if (command.equals("add")) {
            tryAddCircuit(tryNewCirc);
        } else if (command.equals("edit")) {
            tryEditCircuitPartConnection(tryNewCirc);
        } else if (command.equals("delete")) {
            deleteFromCircuit(tryNewCirc);
        }
    }

    //MODIFIES: tryNewCirc, generatedLogicalExp
    //EFFECT: attempts so convert the circuit represented by tryNewCirc, returns true if this is succcessful
    private boolean attemptConvertCircuitToExpression(LogicalCircuit tryNewCirc) {
        try {
            tryNewCirc.generateExpression();
            return true;
        } catch (Exception e) {
            System.out.println("proposed circuit is not well formed.");
            return false;
        }
    }

    //MODIFIES: circToAppend, partToEdit
    //EFFECTS: prompts user to input a circuit name to edit its output(partToEdit), and another part to connect it to
    // (newConnection)
    // tries to find circuit parts with names partToEdit, and newConnection. If they can be found,
    //prompts user for which circuit connection to edit if newConnection has 2 inputs
    //if a valid connection is given, in partToEdit, connects newConnection to the given connection.
    //if newConnection has one input, connects to that.
    //if newConnection is a Circuit Output, will alert user that they can't set change this part's output
    private void tryEditCircuitPartConnection(LogicalCircuit circToAppend) {
        System.out.println("Enter a name for the part to edit: ");
        userInput = new Scanner(System.in);
        String partToEditName = userInput.nextLine();
        System.out.println("Enter a name for the part to connect to: ");
        userInput = new Scanner(System.in);
        String partToConnectName = userInput.nextLine();
        CircuitComponent partToEdit = findPartInList(circToAppend, partToEditName);
        CircuitComponent newConnection = findPartInList(circToAppend, partToConnectName);
        if (partToEdit == null) {
            System.out.println("The specified part to be edited can't be found");
            return;
        }
        if (partToConnectName == null) {
            System.out.println("The specified part that is to be connected to the part to be edited can't be found");
            return;
        }
        assignConnection(circToAppend, partToEdit, newConnection);
    }

    //MODIFIES: circToAppend, partToEdit, newConnection
    //EFFECTS: inside circToAppend will connect partToEdit's output to newConnection's input or
    //one of them if there are multiple
    private void assignConnection(LogicalCircuit circToAppend, CircuitComponent partToEdit,
                                  CircuitComponent newConnection) {
        if (newConnection instanceof BinaryCircuitGate) {
            connectToBinaryGateInputs(circToAppend, partToEdit, newConnection);
        } else if (newConnection instanceof CircuitGate) {
            circToAppend.changeOutPutConnection(partToEdit, newConnection, 1);
        } else {
            System.out.println("You can't connect to an input");
            return;
        }
    }

    //MODIFIES: circToAppend, partToEdit
    //EFFECTS: prompts user to specify whether they want partToEdit's output to newConnection's first
    //or second inputs
    private void connectToBinaryGateInputs(LogicalCircuit circToAppend, CircuitComponent partToEdit,
                                           CircuitComponent newConnection) {
        System.out.println("Enter one of the following inputs: [1] [2]");
        String chosenInput = userInput.nextLine();
        if (chosenInput.equals("1")) {
            circToAppend.changeOutPutConnection(partToEdit, newConnection, 1);
        } else if (chosenInput.equals("2")) {
            circToAppend.changeOutPutConnection(partToEdit, newConnection, 2);
        } else {
            System.out.println("Specified input is invalid");
        }
    }

    //MODIFIES: circToAppend
    //EFFECTS: attempts to create a new circuit part of the given
    // type and with that name and adds it to the circuit. Does nothing if there is already exists a part
    // in the circuit with the given name
    private void tryAddCircuit(LogicalCircuit circToAppend) {
        System.out.println("Enter a name for the new part: ");
        userInput = new Scanner(System.in);
        String newPartName = userInput.nextLine();
        System.out.println("Types: [and] [or] [not] [variable]");
        System.out.println("Enter a type for the new part(All listed above): ");
        userInput = new Scanner(System.in);
        String newPartType = userInput.nextLine();
        CircuitComponent circpartAlreadyExists = findPartInList(circToAppend, newPartName);

        if (circpartAlreadyExists != null) {
            System.out.println("A part with this name already exists");
            return;
        } else {
            CircuitComponent newPart = makeCircPart(newPartType);
            if (newPart == null) {
                System.out.println("specified type is invalid. Can't create circuit part ");
                return;
            } else {
                circToAppend.addCircuitPart(newPart);
                newPart.setName(newPartName);
            }
        }
    }

    //MODIFIES: circToAppend
    //EFFECTS: finds a circuit part with name circuitPartName in circToAppend, and deletes it if it is found.
    //if part name corresponds to the output, alerts user that the output can't be removed
    //If one can't be found, alerts user that it doesn't exist
    public void deleteFromCircuit(LogicalCircuit circToAppend) {
        System.out.println("Enter a name for the part: ");
        userInput = new Scanner(System.in);
        String newPartName = userInput.nextLine();
        CircuitComponent circToDelete = findPartInList(circToAppend, newPartName);

        if (circToDelete != null) {
            if (circToDelete.equals(circToAppend.getHead().getComponentName())) {
                System.out.println("you can't remove the circuit's output!");
                return;
            }
            circToAppend.removeCircuitPart(circToDelete);
            System.out.println("part removed successfully");
        } else {
            System.out.println("part does not exist in this circuit");
        }
    }

    //EFFECT: display workspace interface command options
    private void displayWorkspaceOptions() {
        System.out.println("View current expression and it's associated circuit: view");
        System.out.println("Enter a new expression and translate it to a circuit: newExp");
        System.out.println("Modify Existing circuit and derive an expression from it: modCirc");
        System.out.println("Save current Patrice Workspace: save");
        System.out.println("Return to Main menu: close");
        System.out.println("\n");
        System.out.println(
                "================================================================================================");
    }

    //EFFECT: if all of the circuit parts in the given circuit have names, will display their info with
    //names, but if even one part doesn't have a name, displays their index in the array of parts as their names instead
    private void displayCircuitInfo(LogicalCircuit circToDisplay) {
        boolean displayAnon = false;
        for (CircuitComponent cc : circToDisplay.getCircuitComponents()) {
            if (cc.getComponentName() == null) {
                displayAnon = true;
            }
        }

        if (displayAnon) {
            displayLogicalCircuitAnon(circToDisplay);
        } else {
            displayLogicalCircuit(circToDisplay);
        }
    }

    //EFFECTS: displays the current logical expression
    private void displayLogicalExpression() {
        System.out.println("Logical Expression: " + localExpression.getLogicalExpression() + "\n");
    }

    //EFFECTS: displays information about all the parts in circTodisplay; all circuit part names are replaced
    // by their indices in the list of parts
    private void displayLogicalCircuitAnon(LogicalCircuit circTodisplay) {
        System.out.println("Logical Circuit: \n");
        for (CircuitComponent part: circTodisplay.getCircuitComponents()) {
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.println("Name: " + circTodisplay.getCircuitComponents().indexOf(part));
            System.out.println("Type: " + part.getClass());
            if (part instanceof BinaryCircuitGate) {
                displayInput1((CircuitGate) part, circTodisplay);
                displayInput2((BinaryCircuitGate) part, circTodisplay);
            } else if (part instanceof CircuitGate) {
                displayInput1((CircuitGate) part, circTodisplay);
            } else {
                System.out.println("Circuit Input takes no input connections");
                System.out.println(((CircuitVariable) part).getVarID());
            }
            System.out.println("Outputs:" + getOutputListIndices(part, circTodisplay));
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
        }
    }

    //REQUIRES: that every circuit in circTodisplay have a name; the component name field isn't null
    //EFFECTS: displays information about all the parts in circTodisplay
    private void displayLogicalCircuit(LogicalCircuit circTodisplay) {
        System.out.println("Logical Circuit: \n");
        for (CircuitComponent part: circTodisplay.getCircuitComponents()) {
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.println("Name: " + part.getComponentName());
            System.out.println("Type: " + part.getClass());
            if (part instanceof BinaryCircuitGate) {
                displayInput1((CircuitGate) part, circTodisplay);
                displayInput2((BinaryCircuitGate) part, circTodisplay);
            } else if (part instanceof CircuitGate) {
                displayInput1((CircuitGate) part, circTodisplay);
            } else {
                System.out.println("Circuit Input takes no input connections");
                System.out.println(((CircuitVariable) part).getVarID());
            }
            System.out.println("Outputs:" + getOutputNames(part));
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
        }
    }

    //EFFECTS: tries to display the name of the circuit component attached to part's first input, if none attached,
    // tells user that nothing is connected.
    // if no name is specificied, displays the index of the specified circuit part in the given logical circuit
    private void displayInput1(CircuitGate part, LogicalCircuit circToRead) {
        if (part.getInputConnection1() == null) {
            System.out.println("Input 1: None Connected");
        } else if (part.getInputConnection1().getComponentName() != null) {
            System.out.println("Input 1: " + part.getInputConnection1().getComponentName());
        } else {
            System.out.println("Input 1: " + circToRead.getCircuitComponents().indexOf(part.getInputConnection1()));
        }
    }

    //EFFECTS: tries to display the name of the circuit component attached to part's first and
    // second input, if none attached, tells user that nothing is connected
    private void displayInput2(BinaryCircuitGate part, LogicalCircuit circToRead) {
        if (part.getInputConnection2() == null) {
            System.out.println("Input 2: None Connected");
        } else if (part.getInputConnection2().getComponentName() != null) {
            System.out.println("Input 2: " + part.getInputConnection2().getComponentName());
        } else {
            System.out.println("Input 2: " + circToRead.getCircuitComponents().indexOf(part.getInputConnection2()));
        }
    }

    //EFFECTS: returns the part by name in the list of circuit components of circToSearch; null if it can't be found
    private CircuitComponent findPartInList(LogicalCircuit circToSearch, String partName) {
        for (CircuitComponent circPart: circToSearch.getCircuitComponents()) {
            if (circPart.getComponentName().equals(partName)) {
                return circPart;
            }
        }
        return null;
    }

    //creates a new circuit part of type based on user-specified circuitType. If user input doesn't correspond
    //to a valid type, then returns null and alerts user that input was invalid
    private CircuitComponent makeCircPart(String circuitType) {
        switch (circuitType) {
            case "and":
                return new AndGate();
            case "or":
                return new OrGate();
            case "not":
                return new NotGate();
            case "variable":
                return new CircuitVariable();
            default:
                System.out.println("given type doesn't correspond to any type");
                return null;
        }
    }

    //EFFECT: returns a list of the indices of the parts in the given circuit part's output list in the list of parts
    //of the given logical circuit
    public ArrayList<Integer> getOutputListIndices(CircuitComponent circPart, LogicalCircuit circToRead) {
        ArrayList<Integer> indices = new ArrayList<>();
        for (CircuitComponent c: circPart.getOutputConnections()) {
            indices.add(circToRead.getCircuitComponents().indexOf(c));
        }
        return indices;
    }

    //EFFECT: returns a list of the names of the output connections in the given circuit part's output list
    public ArrayList<String> getOutputNames(CircuitComponent circPart) {
        ArrayList<String> names = new ArrayList<>();
        for (CircuitComponent c: circPart.getOutputConnections()) {
            names.add(c.getComponentName());
        }

        return names;
    }

    //EFFECTS: displays the commands available to the user when editing a logical circuit
    private void displayEditCircCommands() {
        System.out.println(
                "================================================================================================");
        System.out.println("add a circuit component: add");
        System.out.println("edit a connection on a circuit part: edit");
        System.out.println("delete a circuit part: delete");
        System.out.println("attempt to submit circuit for translation: submit");
        System.out.println("cancel attempt to make circuit: cancel");
        System.out.println(
                "================================================================================================");
    }

    //EFFECT: returns workSpaceName
    public String getWorkspaceName() {
        return workSpaceName;
    }

    //EFFECT: returns this workspace's current logical circuit
    public LogicalCircuit getLocalCircuit() {
        return this.localCircuit;
    }

    //EFFECT: returns this workspace's current logical expression
    public LogicalExpression getLocalExpression() {
        return this.localExpression;
    }

    //EFFECT: returns this' workspaceSaver
    public WorkspaceSaver getWorkspaceSaver() {
        return this.workspaceSaver;
    }



}
