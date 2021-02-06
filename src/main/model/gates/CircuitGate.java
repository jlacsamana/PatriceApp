package model.gates;

import model.CircuitComponent;

//represents a Gate on a logical circuit; necessarily has an output and input connection
public abstract class CircuitGate extends CircuitComponent {
    protected CircuitComponent inputConnection1;

    //EFFECT: creates a new circuit gate and sets the input connection to null
    public CircuitGate() {
        super();
        inputConnection1 = null;
    }

    //REQUIRES: that newConnection not be null
    //MODIFIES: this
    //EFFECT: sets inputConnection1's reference to newConnection's
    public void changeInputConnection1(CircuitComponent newConnection) {
        inputConnection1 = newConnection;
    }

    //MODIFIES: this
    //EFFECTS: sets this' outputSignal based on the outputSignals it receives from its input signal(s)
    //and type of gate that implements this method
    public abstract void gateLogicCalc();

    //EFFECTS: returns the CircuitComponent referenced by inputConnection1
    public CircuitComponent getInputConnection1() {
        return inputConnection1;
    }

}
