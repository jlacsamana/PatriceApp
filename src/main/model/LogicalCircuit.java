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
    protected ArrayList<CircuitComponent> circuitParts;
    protected final CircuitOutput headPart;
    protected Set<VariableIdentifier> usedVarIDs;

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
    //cant be removed
    //MODIFIES: this
    //EFFECTS: Changes all input/output connections that reference circuitToRemove to null and removes it from the
    //list of circuit parts. If component is a CircuitVariable, removes its assigned variableID from the list
    // of used IDs
    public void removeCircuitPart(CircuitComponent circuitToRemove) {
        if (circuitToRemove instanceof BinaryCircuitGate) {
            clearInputConnection1((CircuitGate) circuitToRemove);
            clearInputConnection2((BinaryCircuitGate) circuitToRemove);
        } else if (circuitToRemove instanceof CircuitGate) {
            clearInputConnection1((CircuitGate) circuitToRemove);
        }
        clearAllOutputConnections(circuitToRemove);
        circuitParts.remove(circuitToRemove);

        if (circuitToRemove instanceof CircuitVariable) {
            usedVarIDs.remove(((CircuitVariable) circuitToRemove).getVarID());
        }

    }

    //MODIFIES: this
    //EFFECTS: sets all output connections from partToDisconnect to null
    private void clearAllOutputConnections(CircuitComponent partToDisconnect) {
        ArrayList<CircuitComponent> duplicateOutputList = new ArrayList<>(partToDisconnect.getOutputConnections());
        for (CircuitComponent circPart : duplicateOutputList) {
            partToDisconnect.removeConnection(circPart);
        }
    }

    //MODIFIES: this
    //EFFECTS: if partToDisconnect's first input is connected to anything, disconnects it
    private void clearInputConnection1(CircuitGate partToDisconnect) {
        if (partToDisconnect.getInputConnection1() != null) {
            partToDisconnect.getInputConnection1().removeConnection(partToDisconnect);
        }
    }

    //MODIFIES: this
    //EFFECTS: if partToDisconnect's second input is connected to anything, disconnects it
    private void clearInputConnection2(BinaryCircuitGate partToDisconnect) {
        if (partToDisconnect.getInputConnection2() != null) {
            partToDisconnect.getInputConnection2().removeConnection(partToDisconnect);
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
            circPart.addBinaryConnection((BinaryCircuitGate) newConnection, inputPlace);
        } else {
            circPart.addUnaryConnection((CircuitGate) newConnection);
        }
    }

    //EFFECT: return currently used variable IDs
    public Set<VariableIdentifier> getUsedVarIDs() {
        return usedVarIDs;
    }


}
