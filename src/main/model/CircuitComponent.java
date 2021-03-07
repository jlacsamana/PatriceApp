package model;

import model.gates.BinaryCircuitGate;
import model.gates.CircuitGate;

import java.util.ArrayList;

//represents a part of a logical circuit
public abstract class CircuitComponent {
    protected String componentName;
    protected ComponentTypeIdentifier componentTypeIdentifier;
    protected boolean outputSignal;
    protected ArrayList<CircuitComponent> outputConnections;

    //an enum representing all the types of circuit components
    public enum ComponentTypeIdentifier {
        VARIABLE,
        NOT,
        AND,
        OR,
        OUTPUT,
        NONE
    }

    //EFFECT: creates a new circuit component, an empty list of its output connections
    // and sets outPutSignal to false
    public CircuitComponent() {
        outputSignal = false;
        outputConnections = null;
        outputConnections = new ArrayList<>();
        componentName = "";
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
            removeConnectionInput2((BinaryCircuitGate) compToRemove);
        } else {
            removeConnectionInput1(compToRemove);
        }
    }

    //MODIFIES: compToRemove
    //EFFECTS: checks with of the inputs of compToRemove is connected to this and sets it to null
    //if both are, then just sets the 2nd to null
    private void removeConnectionInput2(BinaryCircuitGate compToRemove) {
        if (compToRemove.getInputConnection2() != null
                && compToRemove.getInputConnection2().equals(this)) {
            compToRemove.changeInputConnection2(null);
        } else {
            compToRemove.changeInputConnection1(null);
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

    //EFFECT: returns componentName
    public String getComponentName() {
        return componentName;
    }

    //MODIFIES: this
    //EFFECTS: sets this' name to newName
    public void setName(String newName) {
        this.componentName = newName;
    }

    //EFFECTS returns this component's componentTypeIdentifier
    public ComponentTypeIdentifier getComponentTypeIdentifier() {
        return componentTypeIdentifier;
    }

    //MODIFIES: this
    public void setComponentTypeIdentifier(ComponentTypeIdentifier typeID) {
        this.componentTypeIdentifier = typeID;
    }
}
