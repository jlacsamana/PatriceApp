package model;

import model.gates.BinaryCircuitGate;
import model.gates.CircuitGate;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

//represents a logical circuit, and provides a means to edit, append to it, and to generate an equivalent
//logical expression
public class LogicalCircuit {
    ArrayList<CircuitComponent> circuitParts;
    public final CircuitOutput headPart;
    Set<VariableIdentifier> usedVarIDs;

    //represents all available variable identifiers; each one may only be used by a single CircuitVariable
    public enum VariableIdentifier {
        NONE,
        A,
        B,
        C,
        D
    }

    //EFFECTS: creates a new logical circuit with one part (which is also assigned to be the headPart), a circuit
    // output, and creates a new list of used variables
    public LogicalCircuit() {
        CircuitOutput headComp = new CircuitOutput();
        circuitParts = new ArrayList<>();
        usedVarIDs = new HashSet<>();
        circuitParts.add(headComp);
        headPart = headComp;
    }

    //REQUIRES: That the logical circuit that this represents be valid:
    //-no input and output connections point to a null object
    //-no sequential circuitry
    //-there only be a single circuit output
    //-all other circuit components are either directly or indirectly connected to the circuit output
    //EFFECTS: returns a logical expression that is analogous to the logical circuit represented by this
    public LogicalExpression generateExpression() {
        LogicalExpression generatedExpression = new LogicalExpression();

        return generatedExpression;
    }

    //EFFECT: returns the head part in this circuit
    public CircuitOutput getHead() {
        return headPart;
    }

    //EFFECT: returns the list of this circuit's components
    public ArrayList<CircuitComponent> getCircuitComponents() {
        return circuitParts;
    }

    //REQUIRES: newPart can't be a Circuit Output
    //MODIFIES: this
    //EFFECTS: adds the circuit component newPart to the list of parts; if its a CircuitVariable, assigns an unused
    //variableID to it. If no variables are free, the part is not added
    public void addCircuitPart(CircuitComponent newPart) {
        if (newPart.getClass() == CircuitVariable.class) {
            CircuitVariable newVar = (CircuitVariable) newPart;
            for (VariableIdentifier varID : VariableIdentifier.values()) {
                if (usedVarIDs.contains(varID) == false
                        && varID != VariableIdentifier.NONE) {
                    newVar.setVarID(varID);
                    usedVarIDs.add(varID);
                    this.circuitParts.add(newVar);
                    break;
                }
            }
        } else {
            this.circuitParts.add(newPart);
        }

    }

    //REQUIRES: the given CircuitComponent must exist in the list of circuit parts. Variable Outputs
    //cant be removes
    //MODIFIES: this
    //EFFECTS: Changes all input/output connections that reference circuitToRemove to null and removes it from the
    //list of circuit parts. If component is a CircuitVariable, removes its assigned variableID from the list
    // of used IDs
    public void removeCircuitPart(CircuitComponent circuitToRemove) {
        removeOutputConnections(circuitToRemove);
        removeInputConnection(circuitToRemove);
        this.circuitParts.remove(circuitToRemove);
        if (circuitToRemove instanceof CircuitVariable) {
            usedVarIDs.remove(((CircuitVariable) circuitToRemove).getVarID());
        }
    }

    //MODIFIES: circToDisconnect
    //EFFECT: disconnects circToDisconnect's output from anything connected to it;
    public void removeOutputConnections(CircuitComponent circToDisconnect) {
        if (circToDisconnect.getOutputConnection() != null) {
            if (circToDisconnect.getOutputConnection() instanceof BinaryCircuitGate) {
                BinaryCircuitGate binaryConnToNullify = (BinaryCircuitGate) circToDisconnect.getOutputConnection();
                if (binaryConnToNullify.getInputConnection1().equals(circToDisconnect)) {
                    binaryConnToNullify.changeInputConnection1(null);
                } else {
                    binaryConnToNullify.changeInputConnection2(null);
                }
            } else if (circToDisconnect.getOutputConnection() instanceof CircuitGate) {
                CircuitGate unaryConnectionToNullify = (CircuitGate) circToDisconnect.getOutputConnection();
                unaryConnectionToNullify.changeInputConnection1(null);
            }
            circToDisconnect.changeOutputConnection(null);
        }
    }

    //MODIFIES: circToDisconnect
    //EFFECT: disconnects circToDisconnect's input(s) from anything connected to it;
    public void removeInputConnection(CircuitComponent circToDisconnect) {
        if (circToDisconnect instanceof BinaryCircuitGate) {
            BinaryCircuitGate binaryCircToRemove = (BinaryCircuitGate) circToDisconnect;
            binaryCircToRemove.getInputConnection1().changeOutputConnection(null);
            binaryCircToRemove.getInputConnection2().changeOutputConnection(null);
        } else if (circToDisconnect instanceof CircuitGate) {
            CircuitGate binaryCircToRemove = (CircuitGate) circToDisconnect;
            binaryCircToRemove.getInputConnection1().changeOutputConnection(null);
        }
    }

    //REQUIRES: the given CircuitComponents must exist in the list of circuit parts. if circPart is a unary gate, then
    // inputPlace must be one, but if it is binary, this can be either 1 or 2.
    // circPart can't be a CircuitOutput
    //MODIFIES: circPart, newConnection
    //EFFECTS: sets the outputConnection of circPart to newConnection. The input corresponding to inputPlace in
    //newConnection is set to circPart. The output of the CircuitComponent previously connected to that input
    //is set to null(if that CircuitComponent isn't null)
    public void changeOutPutConnection(CircuitComponent circPart, CircuitComponent newConnection, int inputPlace) {
        if (newConnection instanceof BinaryCircuitGate) {
            if (inputPlace == 1) {
                switchConnection1(circPart, (CircuitGate) newConnection);
            } else if (inputPlace == 2) {
                switchConnection2(circPart, (BinaryCircuitGate) newConnection);
            }

        } else {
            switchConnection1(circPart, (CircuitGate) newConnection);
        }
        circPart.changeOutputConnection(newConnection);
    }

    //MODIFIES: circPart, newConnection
    //EFFECTS: disconnects circPart's outputConnection and newConnection's first input connection
    //from whatever they're connected to, if anything and connects
    //circPart's outputConnection to newConnection first input connection
    public void switchConnection1(CircuitComponent circPart, CircuitGate newConnection) {
        if (circPart.getOutputConnection() != null) {
            ((CircuitGate) circPart.getOutputConnection()).changeInputConnection1(null);
        }
        if (newConnection.getInputConnection1() != null) {
            newConnection.getInputConnection1().changeOutputConnection(null);
        }
        newConnection.changeInputConnection1(circPart);
        circPart.changeOutputConnection(newConnection);
    }

    //MODIFIES:circPart, newConnection
    //EFFECTS: disconnects circPart's outputConnection and newConnection's second input connection
    //from whatever they're connected to, if anything and connects
    //circPart's outputConnection to newConnection second input connection
    public void switchConnection2(CircuitComponent circPart, BinaryCircuitGate newConnection) {
        if (circPart.getOutputConnection() != null) {
            ((BinaryCircuitGate) circPart.getOutputConnection()).changeInputConnection2(null);
        }
        if (newConnection.getInputConnection2() != null) {
            newConnection.getInputConnection2().changeOutputConnection(null);
        }
        newConnection.changeInputConnection2(circPart);
        circPart.changeOutputConnection(newConnection);
    }


}
