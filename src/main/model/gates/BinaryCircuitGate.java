package model.gates;

import model.CircuitComponent;

//represents a Binary Gate on a logical circuit; necessarily has an output and two input connections
public abstract class BinaryCircuitGate extends CircuitGate {
    protected CircuitComponent inputConnection2;

    //EFFECTS: creates a new BinaryCircuitGate and sets the 2nd input connection to null
    public BinaryCircuitGate() {
        super();
    }

    //REQUIRES: that newConnection not be null
    //MODIFIES: this
    //EFFECT: sets inputConnection2's reference to newConnection's
    public void changeInputConnection2(CircuitComponent newConnection){

    }

    //EFFECTS: returns the CircuitComponent referenced by inputConnection2
    public CircuitComponent getInputConnection2() {
        return inputConnection2;
    }

}
