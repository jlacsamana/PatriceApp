package model;

//represents an OR gate on a logical circuit
public class OrGate extends BinaryCircuitGate {

    //EFFECTS: creates a new OR gate and sets its component type identifier to OR
    public OrGate(){

    }

    @Override
    //MODIFIES: this
    //EFFECTS: sets this' outputSignal to true if at least one of it's input signals' outputSignals are true
    public void gateLogicCalc() {

    }
}
