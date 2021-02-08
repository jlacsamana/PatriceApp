package model;

import model.gates.BinaryCircuitGate;
import model.gates.CircuitGate;

import java.util.ArrayList;

//represents a part of a logical circuit
public abstract class CircuitComponent {
    protected String componentName;
    protected ComponentTypeIdentifier componentIdentifier;
    protected boolean outputSignal;
    protected ArrayList<CircuitComponent> outputConnections;

    //an enum representing all the types of circuit components
    public enum ComponentTypeIdentifier {
        VARIABLE,
        NOT,
        AND,
        OR,
        OUTPUT
    }

    //EFFECT: creates a new circuit component, an empty list of its output connections
    // and sets outPutSignal to false
    public CircuitComponent() {
        outputSignal = false;
        outputConnections = null;
        outputConnections = new ArrayList<>();
    }

    //REQUIRES: that newConnection not be null
    //MODIFIES: this
    //EFFECT: add newConnection to list of output connections.
    private void addOutputConnection(CircuitComponent newConnection) {
        outputConnections.add(newConnection);
    }

    //REQUIRES: that unaryGate not be null
    //MODIFIES: this
    //EFFECT: add newConnection to list of output connections, and
    //if unaryGate's input isn't null, disconnects it from whatever is connected
    //and connects this instead.
    public void addUnaryConnection(CircuitGate unaryGate) {
        addOutputConnection(unaryGate);
        if (unaryGate.getInputConnection1() != null) {
            unaryGate.getInputConnection1().removeConnection(unaryGate);
        }
        unaryGate.changeInputConnection1(this);


    }

    //REQUIRES: that binaryGate not be null
    //MODIFIES: this
    //EFFECT: add newConnection to list of output connections, and
    //if unaryGate's input corresponding to inputPlace isn't null, disconnects it from whatever is connected
    //and connects this instead.
    public void addBinaryConnection(BinaryCircuitGate binaryGate, int inputPlace) {
        addOutputConnection(binaryGate);
        if (inputPlace == 1) {
            if (binaryGate.getInputConnection1() != null) {
                binaryGate.getInputConnection1().removeConnection(binaryGate);
            }
            binaryGate.changeInputConnection1(this);
        } else {
            if (binaryGate.getInputConnection2() != null) {
                binaryGate.getInputConnection2().removeConnection(binaryGate);
            }
            binaryGate.changeInputConnection2(this);
        }
    }

    //REQUIRES: that compToRemove not be null and can't be a Circuit Input. compToRemove must also exist in
    // this' list of output connections
    //MODIFIES: this
    //EFFECTS: disconnects compToRemove from this component and
    // removes it from the list of output connections
    public void removeConnection(CircuitComponent compToRemove) {
        outputConnections.remove(compToRemove);
        if (compToRemove instanceof BinaryCircuitGate) {
            if (((BinaryCircuitGate) compToRemove).getInputConnection2() != null
                    && ((BinaryCircuitGate) compToRemove).getInputConnection2().equals(this)) {
                ((BinaryCircuitGate) compToRemove).changeInputConnection2(null);
            } else {
                ((BinaryCircuitGate) compToRemove).changeInputConnection1(null);
            }
        } else {
            removeConnectionInput1(compToRemove);
        }
    }

    //MODIFIES: compToRemove
    //EFFECTS: if compToRemove input is not null and equal to this, sets it to null
    private void removeConnectionInput1(CircuitComponent compToRemove) {
        if (((CircuitGate) compToRemove).getInputConnection1() != null) {
            ((CircuitGate) compToRemove).changeInputConnection1(null);
        }
    }

    //EFFECT: returns output connections
    public ArrayList<CircuitComponent> getOutputConnections() {
        return outputConnections;
    }

    //EFFECT: returns output signal
    public boolean getOutputSig() {
        return outputSignal;
    }

}
