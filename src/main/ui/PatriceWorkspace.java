package ui;

import model.*;
import model.gates.*;

import java.util.ArrayList;
import java.util.Scanner;

//an interactive workspace where the user can manipulate expressions and circuits
// and convert them to one another
//provides a way to work on and switch between multiple workspaces,
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
        localCircuit.getHead().setName("Output");
        localExpression = new LogicalExpression();
        this.workSpaceName = workSpaceName;
        interactionLoop();

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
            String chosenCmd = userInput.next();

            if (chosenCmd.equals("close")) {
                break;
            } else if (chosenCmd.equals("view")) {
                displayLogicalExpression();
                displayLogicalCircuitAnon(localCircuit);
            } else if (chosenCmd.equals("newExp")) {
                System.out.println("Enter an expression:");
                userInput = new Scanner(System.in);
                String enteredExp = userInput.nextLine();
                tryEnterNewExp(enteredExp);
            } else if (chosenCmd.equals("modCirc")) {
                tryCreateNewCirc();
            } else {
                System.out.println("Invalid command, please try again");
            }
        }
        System.out.println("returning to main menu...");
    }

    //MODIFIES: localCircuit, localExpression
    //EFFECT: prompts user to enter a new expression, and tries to convert it to a circuit. If successful this
    //new set of Expression and Circuit will be the one currently stored by this workspace's local expression and
    // circuit. If it fails, the current one is kept.
    private void tryEnterNewExp(String newExp) {
        LogicalExpression tryNexExp;
        LogicalCircuit tryNewCirc;
        try {
            tryNexExp = new LogicalExpression();
            tryNexExp.setLogicalExpression(newExp);
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
    private void tryCreateNewCirc() {
        LogicalCircuit tryNewCirc = new LogicalCircuit();
        LogicalExpression generatedLogicalExp;
        while (true) {
            displayLogicalCircuitAnon(tryNewCirc);
            displayEditCircCommands();
            userInput = new Scanner(System.in);
            String editCmd = userInput.next();

            if (editCmd.equals("add")) {
                System.out.println("Enter a name for the new part: ");
                String newPartName = userInput.next();
                System.out.println("Enter a type for the new part: ");
                String newPartType = userInput.next();
                tryAddCircuit(tryNewCirc, newPartType, newPartName);
            } else if (editCmd.equals("edit")) {
                System.out.println("Enter a name for the part to edit: ");
                String partToEditName = userInput.next();
                System.out.println("Enter a name for the part to connect to: ");
                String partToConnectName = userInput.next();
                tryEditCircuitPartConnection(tryNewCirc, partToEditName, partToConnectName);
            } else if (editCmd.equals("delete")) {
                System.out.println("Enter a name for the part: ");
                String newPartName = userInput.next();
                deleteFromCircuit(tryNewCirc, newPartName);
            } else if (editCmd.equals("submit")) {
                try {
                    generatedLogicalExp = tryNewCirc.generateExpression();
                    break;
                } catch (Exception e) {
                    System.out.println("proposed circuit is not well formed.");
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

    }

    //MODIFIES: circToAppend, partToEdit
    //EFFECTS: tries to find circuit parts with names partToEdit, and newConnection. If they can be found,
    //prompts user for which circuit connection to edit if newConnection has 2 inputs
    //if a valid connection is given, in partToEdit, connects newConnection to the given connection.
    //if newConnection has one input, connects to that.
    //Will do nothing and alert user if newConnection is a Circuit Variable
    private void tryEditCircuitPartConnection(LogicalCircuit circToAppend, String partToEditName,
                                    String newConnectionName) {
        userInput = new Scanner(System.in);
        CircuitComponent partToEdit = findPartInList(circToAppend, partToEditName);
        CircuitComponent newConnection = findPartInList(circToAppend, newConnectionName);
        if (partToEdit == null) {
            System.out.println("The specified part to be edited can't be found");
            return;
        }

        if (newConnectionName == null) {
            System.out.println("The specified part that is to be connected to the part to be edited can't be found");
            return;
        }

        if (newConnection instanceof BinaryCircuitGate) {
            System.out.println("Enter one of the following inputs: [1] [2]");
            String chosenInput = userInput.next();
            if (chosenInput.equals("1")) {
                circToAppend.changeOutPutConnection(partToEdit, newConnection, 1);
            } else if (chosenInput.equals("2")) {
                circToAppend.changeOutPutConnection(partToEdit, newConnection, 2);
            } else {
                System.out.println("Specified input is invalid");
            }


        } else if (newConnection instanceof CircuitComponent) {
            circToAppend.changeOutPutConnection(partToEdit, newConnection, 1);
        } else {
            System.out.println("You can't connect to an input");
            return;
        }



    }

    //MODIFIES: circToAppend
    //EFFECTS: attempts to create a new circuit part of the given
    // type and with that name and adds it to the circuit. Does nothing if there is already exists a part
    // in the circuit with the given name
    private void tryAddCircuit(LogicalCircuit circToAppend, String circuitPartType, String circuitPartName) {
        CircuitComponent circpartAlreadyExists = findPartInList(circToAppend, circuitPartName);

        if (circpartAlreadyExists != null) {
            System.out.println("A part with this name already exists");
            return;
        } else {
            CircuitComponent newPart = makeCircPart(circuitPartType);
            if (newPart == null) {
                System.out.println("specified type is invalid. Can't create circuit part ");
                return;
            } else {
                circToAppend.addCircuitPart(newPart);
                newPart.setName(circuitPartName);
            }
        }
    }

    //MODIFIES: circToAppend
    //EFFECTS: finds a circuit part with name circuitPartName in circToAppend, and deletes it if it is found.
    //If one can't be found, does nothing
    public void deleteFromCircuit(LogicalCircuit circToAppend, String circuitPartName) {
        CircuitComponent circToDelete = findPartInList(circToAppend, circuitPartName);

        if (circToDelete != null) {
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
        System.out.println("Return to Main menu: close");
        System.out.println("\n");
        System.out.println(
                "================================================================================================");
    }

    //EFFECTS: displays the current logical expression
    private void displayLogicalExpression() {
        System.out.println("Logical Expression: " + localExpression.getLogicalExpression() + "\n");
    }

    //EFFECTS: displays information about all the parts in circTodisplay; all circuit part names are replaced
    //    // by their indices in the list of parts
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


}
