package model;

import java.util.ArrayList;

//represents a logical circuit, and provides a means to edit, append to it, and to generate an equivalent
//logical expression
public class LogicalCircuit {
    ArrayList<CircuitComponent> circuitParts;
    public final CircuitOutput headPart;

    //EFFECTS: creates a new logical circuit with one part (which is also assigned to be the headPart), a circuit
    // output
    public LogicalCircuit() {
        CircuitOutput headComp = new CircuitOutput();
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

    //MODIFIES: this
    //EFFECTS: adds the circuit component newPart to the list of parts
    public void addCircuitPart(CircuitComponent newPart){

    }

    //REQUIRES: the given CircuitComponent must exist in the list of circuit parts
    //MODIFIES: this
    //EFFECTS: Changes all input/output connections that reference circuitToRemove to null and removes it from the
    //list of circuit parts
    public void removeCircuitPart(CircuitComponent circuitToRemove){

    }

    //REQUIRES: the given CircuitComponents must exist in the list of circuit parts. if circPart is a unary gate, then
    // inputPlace must be one, but if it is binary, this can be either 1 or 2.
    //MODIFIES: circPart, newConnection
    //EFFECTS: sets the outputConnection of circPart to newConnection. The input corresponding to inputPlace in
    //newConnection is set to circPart. The output of the CircuitComponent previously connected to that input
    //is set to null(if that CircuitComponent isn't null)
    public void changeOutPutConnection(CircuitComponent circPart, CircuitComponent newConnection, int inputPlace) {

    }







}
